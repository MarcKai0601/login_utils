-- 1. 初始化系統 (註冊 MGR 管理後台)
INSERT INTO k_system (SystemId, SystemCode, SystemName, Status, CreateTime, UpdateTime)
VALUES (1, 'MGR', '管理後台系統', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 2. 初始化角色
INSERT INTO k_role (RoleId, SystemId, RoleCode, RoleName, Status, CreateTime, UpdateTime)
VALUES
    (1, 1, 'SUPER_ADMIN', '超級管理員', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 'USER', '一般使用者', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 3. 初始化基礎權限菜單 (假設你有 Dashboard 和 Profile)
INSERT INTO k_permission (PermissionId, SystemId, ParentId, PermissionCode, PermissionName, Type, Status, CreateTime, UpdateTime)
VALUES
    (1, 1, NULL, 'MENU_DASHBOARD', '儀表板', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, NULL, 'MENU_PROFILE', '個人資訊', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 4. 初始化角色與權限的綁定
-- 讓 SUPER_ADMIN (RoleId=1) 擁有儀表板 (PermissionId=1) 和 個人資訊 (PermissionId=2) 的權限
INSERT INTO k_role_permission (RoleId, PermissionId) VALUES (1, 1), (1, 2);
-- 讓 USER (RoleId=2) 只擁有個人資訊 (PermissionId=2) 的權限
INSERT INTO k_role_permission (RoleId, PermissionId) VALUES (2, 2);