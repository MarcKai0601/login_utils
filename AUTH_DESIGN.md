# AUTH_DESIGN — 認證與權限架構設計文件

本文件說明 login-utils 專案的密碼加密、Token 機制、以及多系統 RBAC 權限模型設計。

---

## 1. 密碼加密機制

| 項目 | 說明 |
|------|------|
| **加密算法** | BCrypt（由 Spring Security 提供的 `BCryptPasswordEncoder`） |
| **處理類別** | `UserService.addUser()` — 註冊時對明文密碼進行 `passwordEncoder.encode(password)` |
| **驗證類別** | `LoginService.login()` — 登入時使用 `passwordEncoder.matches(rawPassword, hashedPassword)` |
| **Bean 註冊** | `SecurityConfig`（位於 `org.loginutils.mgr.config`）透過 `@Bean` 提供 `PasswordEncoder` |

> BCrypt 每次加密會產生不同的 Salt，因此同一明文密碼加密後的雜湊值不會相同，需透過 `matches()` 進行驗證。

---

## 2. Token 生成與驗證機制

### 2.1 Token 格式與生成

| 項目 | 說明 |
|------|------|
| **格式** | UUID（去除 `-` 的 32 字元字串） |
| **生成類別** | `TokenService.generateToken(UserDto)` |
| **儲存方式** | Redis `StringRedisTemplate` |
| **Redis Key** | `token:{uuid}` |
| **Redis Value** | JSON 格式，包含 `userId`、`username`、`roles`（多系統角色列表） |
| **過期時間** | 24 小時 |

### 2.2 Token 發放

登入成功後，`LoginController` 會：
1. 在 Response **Body** 中回傳 `LoginResponse`（包含 `token` 與 `userId`）
2. 在 Response **Header** 中設定 `Authorization: Bearer <token>`

### 2.3 前端傳遞方式

後續所有需要認證的 API 請求，前端應在 HTTP Header 中帶上：

```
Authorization: Bearer <token>
```

### 2.4 外部系統驗證流程

1. 從 HTTP Header 取得 `Authorization` 值，移除 `Bearer ` 前綴得到 `token`
2. 以 `token:{token}` 為 Key 查詢 Redis
3. 反序列化 JSON Value，即可取得 `userId`、`username` 以及該使用者的**多系統角色列表**

---

## 3. 多系統 RBAC 權限模型

### 3.1 資料表設計

```
k_user ──1:N──> k_user_role ──N:1──> k_role ──N:1──> k_system
                                       │
                                       └──1:N──> k_role_permission ──N:1──> k_permission
```

| 資料表 | 用途 |
|--------|------|
| `k_system` | 定義系統（如 FAS 金流、MGR 後台管理），每個系統有獨立的 `SystemCode` |
| `k_role` | 角色歸屬於某一系統，透過 `SystemId` 關聯。同一系統下的 `RoleCode` 唯一 |
| `k_permission` | 權限/菜單歸屬於某系統，支援樹狀結構（`ParentId`），Type 區分選單/按鈕/API |
| `k_user_role` | 關聯表：一個使用者可以在**不同系統**中擔任不同角色 |
| `k_role_permission` | 關聯表：一個角色可以擁有多個權限 |

### 3.2 如何解決「同一使用者在不同系統擁有不同權限」

核心在於 `k_role` 表綁定了 `SystemId`：

- **使用者 A** 在 `FAS` 系統被分配 `OPERATOR` 角色
- **使用者 A** 同時在 `MGR` 系統被分配 `ADMIN` 角色
- 登入時，`LoginService` 透過 `UserRoleMapper.findRolesByUserId()` 一次查出所有系統的角色
- 這些角色資訊被序列化為 JSON 存入 Redis Session
- 外部系統驗證 Token 時，可根據其自身的 `SystemCode` 篩選出對應角色，實現精準的權限控制

### 3.3 查詢範例

```sql
SELECT ur.UserId, r.RoleCode, r.RoleName, s.SystemCode, s.SystemName
FROM k_user_role ur
INNER JOIN k_role r ON ur.RoleId = r.RoleId
INNER JOIN k_system s ON r.SystemId = s.SystemId
WHERE ur.UserId = ?
```

### 3.4 Redis Session 結構範例

```json
{
  "userId": 1,
  "username": "admin",
  "roles": [
    { "roleId": 1, "roleCode": "ADMIN", "roleName": "管理員", "systemCode": "MGR", "systemName": "後台管理系統" },
    { "roleId": 5, "roleCode": "OPERATOR", "roleName": "操作員", "systemCode": "FAS", "systemName": "金流系統" }
  ]
}
```
