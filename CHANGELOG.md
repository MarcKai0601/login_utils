# CHANGELOG — login-utils 修改紀錄

---

## 2026-03-11 — 漸進式語系判斷 (Language Preferences)

### 功能新增
- **語言偏好持久化**：於 `k_user` 資料表新增 `Language` 欄位，用來儲存使用者的預設語系（如 `zh-TW`, `en`, `ja`, `ko`）。
- **註冊彈性與預設**：`AddUserRequest` 新增 `language` 接收參數；`UserService` 負責在新建用戶時處理邏輯，若未傳遞語系預設綁定為 `"zh-TW"`。
- **登入與 Session 同步**：登入時從 `UserDo` 讀取 `language`，並將其放入回傳的 `UserDto` 以及存放在 Redis 的 `SessionDto` 中，確保前後端的語系能完全一致。

### 檔案異動

| 模組 | 檔案 | 異動類型 | 說明 |
|------|------|----------|------|
| dal | `UserDo.java` | MODIFY | 新增 `private String language` 屬性 |
| dal | `UserMapper.xml` | MODIFY | `BaseResultMap` 新增 mapping，`insert`/`update` 邏輯納入 `Language` 欄位處理 |
| common | `UserDto.java` | MODIFY | 擴充 `language` 屬性承載資料 |
| common | `SessionDto.java` | MODIFY | 擴充 `language` 屬性使 Token 可反解出語系 |
| mgr | `AddUserRequest.java` | MODIFY | 請求參數新增 `language` |
| mgr | `UserService.java` | MODIFY | 加入未設定語系時，預設填入 `"zh-TW"` 的安全邏輯 |
| mgr | `LoginService.java` | MODIFY | 登入提取並封裝 `Language` |
| mgr | `LoginController.java` | MODIFY | SessionDto 同步提取登入的語言偏好塞回 Cache |

---

## 2026-03-10 — 全域 CORS 設定、Auth API 實作與 Token 滑動視窗

### 功能新增
- **全域 CORS (跨域資源放行)**：實作 `WebMvcConfig` 放行 `/**`，允許 `http://localhost:5173` 與 `http://localhost:3000` 跨域請求，並放行 `Authorization` Header 與 `allowCredentials(true)`。同步調整 `SecurityConfig` 的跨域設定。
- **取得當前使用者狀態 API (`GET /api/v1/auth/me`)**：新增 `AuthController`，接收前端送來的 Bearer Token，呼叫 `TokenService` 驗證 Token 並回傳對應的 `userId` 與這名用戶擁有的系統角色權限列表。
- **Token 滑動視窗 (Sliding Window)**：修改 `TokenService`，定義預設過期時間為 30 分鐘 (`TOKEN_EXPIRE_MINUTES = 30`)，並新增 `verifyAndExtendToken()` 邏輯。當驗證 Token 成功有效時，透過 `redisTemplate.expire()` 重置 Token 的 Redis 快取存活時間為 30 分鐘，以避免活躍用戶被強制登出。

### 檔案異動

| 模組 | 檔案 | 異動類型 | 說明 |
|------|------|----------|------|
| mgr | `WebMvcConfig.java` | NEW | 建立全域 Web CORS 設定，放行前端 Origin (5173 / 3000) 與 Credentials |
| mgr | `SecurityConfig.java` | MODIFY | 修改 Security CORS `allowedOrigins` 以配合 Credentials 的限制與驗證 |
| mgr | `AuthController.java` | NEW | 實作 `/api/v1/auth/me`，負責解析 Header 裡的 Authorization Token 並執行驗證 |
| mgr | `TokenService.java` | MODIFY | 實作滑動視窗延展機制，驗證成功即重置 Token 的 Redis TTL 再延長 30 分鐘 |

---

## 2026-03-06 — 註冊綁定預設角色 & 登入回傳角色

### 功能新增
- **註冊自動綁定預設角色**：`UserService.addUser()` 在 insert `k_user` 取得 `userId` 後，自動寫入 `k_user_role` 綁定預設角色 (`DEFAULT_ROLE_ID = 2`)，整段操作以 `@Transactional` 保護。
- **登入 API 回傳角色代碼**：`LoginResponse` 新增 `List<String> roles` 欄位，`LoginController` 從 `UserDto.roles` 提取 `roleCode` 列表回傳給前端。

### 檔案異動

| 模組 | 檔案 | 異動類型 | 說明 |
|------|------|----------|------|
| dal | `UserMapper.xml` | MODIFY | insert 加上 `useGeneratedKeys` 回填 `userId` |
| dal | `UserRoleMapper.java` | MODIFY | 新增 `insertUserRole()` 方法 |
| dal | `UserRoleMapper.xml` | MODIFY | 新增 `insertUserRole` SQL |
| mgr | `UserService.java` | MODIFY | 加上 `@Transactional`，insert 後綁定預設角色 |
| mgr | `LoginResponse.java` | MODIFY | 新增 `List<String> roles` |
| mgr | `LoginController.java` | MODIFY | 提取 `roleCode` 填入 `LoginResponse` |

---

## 2026-03-04 — Token Header 化 & 多系統 RBAC 架構

### 功能新增
- **Token 寫入 Response Header**：`LoginController` 登入成功後在 `HttpServletResponse` 設定 `Authorization: Bearer <token>`。
- **RBAC 資料庫架構**：`__init__.sql` 新增 `k_system`、`k_role`、`k_permission`、`k_user_role`、`k_role_permission` 五張表。
- **登入封裝多系統權限**：`LoginService` 登入成功後查詢用戶所有系統角色；`TokenService` 將 `{userId, username, roles}` 序列化為 JSON 存入 Redis。
- **架構說明文件**：新增 `AUTH_DESIGN.md`。

### 檔案異動

| 模組 | 檔案 | 異動類型 | 說明 |
|------|------|----------|------|
| sql | `__init__.sql` | MODIFY | 新增 5 張 RBAC 表 |
| dal | `UserRoleDo.java` | NEW | 用戶角色 JOIN 查詢結果 DO |
| dal | `UserRoleMapper.java` | NEW | `findRolesByUserId()` |
| dal | `UserRoleMapper.xml` | NEW | 三表 JOIN 查詢 SQL |
| common | `RoleDto.java` | MODIFY | 加上 Lombok 與角色/系統欄位 |
| common | `UserDto.java` | MODIFY | 新增 `List<RoleDto> roles` |
| mgr | `LoginService.java` | MODIFY | 注入 `UserRoleMapper`，登入後查詢角色 |
| mgr | `TokenService.java` | MODIFY | 接受 `UserDto`，序列化角色 JSON 存入 Redis |
| mgr | `LoginController.java` | MODIFY | 加入 `Authorization` Header，傳入 `UserDto` |
| root | `AUTH_DESIGN.md` | NEW | 認證與權限架構設計文件 |

---

## 2026-03-03 — BCrypt 加密、Redis Token、全域例外處理

### 功能新增
- **BCrypt 取代 MD5**：引入 `spring-boot-starter-security`，建立 `SecurityConfig` 提供 `BCryptPasswordEncoder` Bean。
- **Redis Token 發放**：開啟 `spring-boot-starter-data-redis`，建立 `TokenService` 生成 UUID Token 存入 Redis（24h TTL）。
- **全域例外處理**：建立 `GlobalExceptionHandler`，統一攔截 `MgrException` 與 `MethodArgumentNotValidException`。

### 檔案異動

| 模組 | 檔案 | 異動類型 | 說明 |
|------|------|----------|------|
| mgr | `pom.xml` | MODIFY | 啟用 `data-redis`、新增 `security` 依賴 |
| mgr | `application.properties` | MODIFY | 新增 Redis 連線設定 |
| mgr | `SecurityConfig.java` | NEW | `PasswordEncoder` Bean + `SecurityFilterChain` |
| mgr | `UserService.java` | MODIFY | `MD5Utils` → `passwordEncoder.encode()` |
| mgr | `LoginService.java` | MODIFY | `MD5Utils` → `passwordEncoder.matches()` |
| mgr | `TokenService.java` | NEW | UUID Token + Redis 儲存 |
| mgr | `LoginResponse.java` | NEW | 登入回傳 DTO（token, userId） |
| mgr | `LoginController.java` | MODIFY | 整合 `TokenService`，回傳 `LoginResponse` |
| mgr | `GlobalExceptionHandler.java` | NEW | `@RestControllerAdvice` 全域例外攔截 |
