# CHANGELOG — login-utils 修改紀錄

---

## 2026-03-19 (Update 2) — Spring Web Mail 與一次性密碼 (OTP) 強制更新機制

### 功能新增
- **真實郵件派送 (`Spring Boot Mail`)**：正式捨棄 Mock 發信，於 `pom.xml` 導入 `spring-boot-starter-mail`，並實作 `EmailService` 進行實體信件派發。信件內容內涵「一次性密碼宣告」與安全免責聲明。
- **一次性密碼 (OTP) 標記**：資料庫 `k_user` 新增 `IsTempPassword` (0=正常, 1=臨時) 核心註記。當使用者重設信箱密碼時自動標記為 `1`；當使用者登入後成功修改密碼 (`updatePassword`) 時會自動解除並降回 `0`。此標記現已同步附帶於登入回傳的 `UserDto` 內，供前端檢核並強制引導至重設頁面。

### 檔案異動
| 模組 | 檔案 | 異動類型 | 說明 |
|------|------|----------|------|
| sql | `update_temp_password.sql` | NEW | 新增 `IsTempPassword` 欄位的 MySQL 結構修正腳本 |
| dal | `UserDo.java`, `UserDto.java` | MODIFY | 增設 `isTempPassword` 以傳遞臨時密碼狀態 |
| dal | `UserMapper.xml` | MODIFY | 更新了 MyBatis 的 `<insert>` 與 `<update>` 指令以支援 OTP 狀態變更寫入 |
| mgr | `pom.xml` | MODIFY | 加入 `spring-boot-starter-mail` 依賴 |
| mgr | `application.properties` | MODIFY | 預先載入 Spring SMTP Configuration 基礎對接參數 |
| mgr | `UserService.java` | MODIFY | 擴充了覆寫密碼與重設密碼時控制 `isTempPassword = 1` 或 `0` 的機制 |
| mgr | `LoginService.java` | MODIFY | 登入完成回傳時交還 `isTempPassword` 使前端專案可擷取 |
| mgr | `EmailService.java` | NEW | 取代了原本的 `MockEmailService`，引入了嚴謹有免責聲明的 `JavaMailSender` |

---

## 2026-03-19 — 忘記密碼與 Email 登入升級 (Forgot Password & Email Login)

### 功能新增
- **引進 Email 登入基石**：升級重構底層查詢，打通了 `LoginService` 的輸入機制，現在使用者可以在帳號欄直接輸入 Email 或是 Username 來進行登入。
- **忘記密碼與重啟 API (`POST /api/v1/auth/forgot-password`)**：新增端點提供使用者重發新密碼信件。服務層內置了同時間視窗 (30天) 最多 3 次的限流防護設計，保護系統免於短時間密集騷擾。
- **Mock Mail 模擬系統**：先期預留了發送真實 Email 的彈性，暫時以 `MockEmailService` 利用 `log.info` 在後台列印，後續若要切換為 Spring Mail 可以直接抽換。
- **註冊 Email 防撞機制**：在註冊時強制綁定了 Email 為必填 (`@NotBlank`)，並擴充了獨佔性驗證：若信箱已被其他帳號使用，將回報 `EMAIL_ALREADY_EXISTS` 錯誤。

### 檔案異動

| 模組 | 檔案 | 異動類型 | 說明 |
|------|------|----------|------|
| sql | `update_k_user.sql` | NEW | 修改 `k_user` 新增 Unique Index 並掛載 `pwd_reset_count` 與 `pwd_reset_window_start` |
| dal | `UserDo.java` & `UserDto.java` | MODIFY | 擴充實體屬性對接密碼重設的控管 |
| dal | `UserMapper.xml` & `.java` | MODIFY | 寫入 `<select id="selectByUsernameOrEmail">` |
| common | `MgrResponseCode.java` | MODIFY | 新增 `EMAIL_REQUIRED`, `EMAIL_ALREADY_EXISTS`, `PWD_RESET_LIMIT_EXCEEDED` 等定義 |
| mgr | `AddUserRequest.java` | MODIFY | 強制 Email 不可為空 |
| mgr | `UserService.java` | MODIFY | 加入註冊唯一性排他邏輯，並撰寫了 `resetPassword()` 重設邏輯 |
| mgr | `LoginService.java` | MODIFY | 放寬驗證查找方式 `userMapper.selectByUsernameOrEmail` |
| mgr | `MockEmailService.java` | NEW | 初步模擬實作派發郵件流程的類別 |
| mgr | `AuthController.java` | MODIFY | 增設開放的 REST 端點 `@PostMapping("/forgot-password")` |
| mgr | `ForgotPasswordRequest.java` | NEW | 對應忘記密碼封裝的 Request DTO |

---

## 2026-03-12 — 系統版號 API (System Version API)

### 功能新增
- **系統版號公開 API (`GET /api/v1/system/version`)**：新增 `SystemController`，提供前端抓取系統當前版號。版號定義於 `application.properties` 中 (`system.mgr.version`)。

### 檔案異動

| 模組 | 檔案 | 異動類型 | 說明 |
|------|------|----------|------|
| mgr | `application.properties` | MODIFY | 新增 `system.mgr.version=R20260312v2.0.0` 屬性 |
| mgr | `SystemController.java` | NEW | 實作 `/api/v1/system/version` GET 路由，回傳 `system.mgr.version` |

---

## 2026-03-11 — 漸進式語系判斷 (Language Preferences)

### 功能新增
- **修改密碼 API (`PUT /api/mgr/user/password`)**：新增一支允許當前登入者修改自己的密碼的功能。API 會自動從 `Authorization` Token 中解析 `userId`，並在後端 `UserService` 使用 `BCrypt` 比對 `oldPassword`；若比對成功，則自動加密 `newPassword` 並更新資料庫紀錄。
- **單一裝置登入 (Single Active Session)**：在 `TokenService` 中定義反向索引機制（`user_token:{userId}`）。當用戶獲得新的登入 Token 準備寫入 Redis 時，系統會事先檢查該用戶是否已擁有活躍的連線；若有，則主動將舊的連線 (`token:{oldToken}`) 刪除，強制讓前一次的 session 失效，杜絕 Token 無限增生。
- **滑動視窗強化**：`verifyAndExtendToken()` 驗證機制除延展 Token 自身的過期時間，也同步延展反向索引的 TTL 時效至 30 分鐘，維持雙向資訊存活一致性。
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
| mgr | `UpdatePasswordRequest.java` | NEW | 接收使用者的舊密碼 (`oldPassword`) 與新密碼 (`newPassword`) |
| mgr | `UserService.java` | MODIFY | 實作 `updatePassword`，負責驗證舊密碼與雜湊新密碼後存入資料庫 |
| mgr | `LoginService.java` | MODIFY | 登入提取並封裝 `Language` |
| mgr | `LoginController.java` | MODIFY | SessionDto 同步提取登入的語言偏好塞回 Cache |
| mgr | `UserController.java` | MODIFY | 實作 `/password` PUT 路由並串接 `TokenService` 提取身份 |
| mgr | `TokenService.java` | MODIFY | 實作反向索引，控制單一裝置登入「踢除」舊 Token 確保唯一有效連線 |

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
