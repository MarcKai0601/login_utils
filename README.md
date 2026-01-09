# login-utils 微服務專案結構設計總覽

本專案採用 **模組化微服務架構**，以「職責單一、層次清楚、跨服務低耦合」為核心設計原則。

---

## 專案整體結構

```text
login-utils-parent
├─ login-utils-common                     # 僅放跨服務共用的「契約」
│  └─ src/main/java/org/loginutils/common
│     ├─ dto                              # ClientDTO / SessionDTO / 通用回傳格式
│     ├─ enums                            # 跨服務共用 Enum（ErrorCode、AccountStatus…）
│     └─ util                             # 純工具類（不可依賴任何特定服務）
│
├─ login-utils-mgr                        # 對外 API（後台 / 管理端）服務
│  └─ src/main/java/org/loginutils/mgr
│     ├─ web
│     │  ├─ controller                   # RestController（僅負責請求轉發）
│     │  ├─ xxx                          # 只屬於 mgr 的 API Request DTO or Response / VO (依據Table名稱命名 建立相關的服務)
│     ├─ service                          # 業務服務層（商業邏輯）
│     ├─ domain                           # Domain Model / Principal / VO（非 DB、非 API）
│     ├─ config                           # Spring / Security / Web 設定
│     └─ exception                        # 自訂例外與全域例外處理
│
└─ login-utils-dal                        # DB 存取模組（可被多服務共用）
   └─ src/main/java/org/loginutils/dal
      ├─ modle                           # DB Table 對應的 Entity / DO
      └─ mapper                           # MyBatis Mapper（DAO）
```


---

## 各模組設計原則

### login-utils-common（契約模組）

**用途**
- 微服務之間資料交換的「唯一標準」
- Session / Token / Feign / Gateway 使用的 DTO
- 跨服務共用的 Enum 與錯誤碼

**嚴格限制**
- ❌ 不放 Entity / DO / MODLE
- ❌ 不放某一服務專用的 Request / Response
- ❌ 不依賴任何 service / dal module

---

### login-utils-mgr（對外 API 服務）

**用途**
- 對外提供 REST API（後台 / 管理系統）
- 組合 service + domain 邏輯
- 將 domain 結果轉成 response

**規範**
- web/request、web/response 僅屬於此服務
- 不將 Entity / DO 直接回傳給前端
- 權限、身份使用 domain（如 UserPrincipal）判斷

---

### login-utils-dal（資料存取模組）

**用途**
- 純 DB 存取（MyBatis）
- Table ↔ Java 類別映射

**規範**
- 僅放 Entity / Mapper / MODEL
- ❌ 不寫業務邏輯
- ❌ 不依賴 mgr
- 類名使用 `XXXDO`，package 使用 `model`

---

## 命名與分層約定（摘要）

| 類型 | 放置位置 | 說明 |
|----|----|----|
| Entity / DO | dal/entity | DB table 對應 |
| Mapper | dal/mapper | MyBatis Mapper |
| Request | mgr/web/request | 對外 API 請求 |
| Response | mgr/web/response | 對外 API 回傳 |
| ClientDTO | common/dto | 跨服務契約 |
| Enum | common/enums | 跨服務共用語意 |
| Domain Model | mgr/domain | 業務語意、權限判斷、狀態行為（非 DB、非 API） |

---

## 核心設計原則（備忘）

1. **契約集中於 common，且不可依賴服務內部實作**
2. **Entity 不外流，DTO 不進 dal**
3. **Request / Response 僅屬於對外服務**
4. **Domain 負責業務語意與判斷，不關心 DB 與 API**
5. **寧可多一層 mapping，也不要跨層共用 class**

---

## 模組擴充原則（Optional）

- 若出現「跨多個服務共用的資料交換格式」，新增至 `login-utils-common`
- 若出現新的對外 API，新增對應的 service module（例如 login-utils-auth）
- Domain Model 一律留在所屬服務內，不共用、不外流

---
