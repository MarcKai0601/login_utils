-- auto-generated definition
create table k_user
(
    UserId           bigint unsigned auto_increment comment '帳戶ID'
        primary key,
    Email            varchar(30)                 null,
    Username         varchar(30)                 not null comment '登录账户',
    Password         varchar(60)                 null comment '登录密码',
    Status           tinyint                     not null comment '狀態,1-启用,0-禁用',
    CreateTime       timestamp                   null comment '創建時間',
    UpdateTime       timestamp                   not null comment '修改時間',
    LoginIp          varchar(64)                 null comment '最後登錄IP',
    Language         varchar(10) default 'zh-TW' null comment '使用者偏好語系 (zh-TW, en, ja, ko)',
    LoginTime        timestamp                   null comment '最後登錄時間',
    FailedLoginCount bigint      default 0       not null comment '連續登入失敗次數',
    Memo             varchar(255)                null comment '備註'
)
    comment '用户（賬戶）表';

-- =============================================
-- RBAC 多系統權限資料表
-- =============================================

-- 系統表
create table k_system
(
    SystemId   bigint unsigned auto_increment comment '系統ID'
        primary key,
    SystemCode varchar(30)  not null comment '系統代碼 (如 FAS, MGR)',
    SystemName varchar(100) not null comment '系統名稱',
    Status     tinyint      not null comment '狀態, 1-啟用, 0-停用',
    CreateTime timestamp    null comment '創建時間',
    UpdateTime timestamp    not null comment '修改時間',
    constraint uk_system_code unique (SystemCode)
)
    comment '系統表';

-- 角色表
create table k_role
(
    RoleId     bigint unsigned auto_increment comment '角色ID'
        primary key,
    SystemId   bigint unsigned not null comment '所屬系統ID',
    RoleCode   varchar(50)     not null comment '角色代碼 (如 ADMIN, OPERATOR)',
    RoleName   varchar(100)    not null comment '角色名稱',
    Status     tinyint         not null comment '狀態, 1-啟用, 0-停用',
    CreateTime timestamp       null comment '創建時間',
    UpdateTime timestamp       not null comment '修改時間',
    constraint uk_system_role_code unique (SystemId, RoleCode)
)
    comment '角色表';

-- 權限/菜單表
create table k_permission
(
    PermissionId   bigint unsigned auto_increment comment '權限ID'
        primary key,
    SystemId       bigint unsigned not null comment '所屬系統ID',
    ParentId       bigint unsigned null comment '父級權限ID (頂層為 NULL)',
    PermissionCode varchar(100)    not null comment '權限代碼',
    PermissionName varchar(100)    not null comment '權限名稱',
    Type           tinyint         not null comment '類型, 1-選單, 2-按鈕, 3-API',
    Status         tinyint         not null comment '狀態, 1-啟用, 0-停用',
    CreateTime     timestamp       null comment '創建時間',
    UpdateTime     timestamp       not null comment '修改時間',
    constraint uk_system_permission_code unique (SystemId, PermissionCode)
)
    comment '權限（菜單）表';

-- 用戶角色關聯表
create table k_user_role
(
    Id     bigint unsigned auto_increment comment '主鍵ID'
        primary key,
    UserId bigint unsigned not null comment '用戶ID',
    RoleId bigint unsigned not null comment '角色ID',
    constraint uk_user_role unique (UserId, RoleId)
)
    comment '用戶角色關聯表';

-- 角色權限關聯表
create table k_role_permission
(
    Id           bigint unsigned auto_increment comment '主鍵ID'
        primary key,
    RoleId       bigint unsigned not null comment '角色ID',
    PermissionId bigint unsigned not null comment '權限ID',
    constraint uk_role_permission unique (RoleId, PermissionId)
)
    comment '角色權限關聯表';
