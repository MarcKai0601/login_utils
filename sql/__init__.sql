-- auto-generated definition
create table k_user
(
    UserId           bigint unsigned auto_increment comment '帳戶ID'
        primary key,
    Email            varchar(30)      null,
    Username         varchar(30)      not null comment '登录账户',
    Password         varchar(60)      null comment '登录密码',
    Status           tinyint          not null comment '狀態,1-启用,0-禁用',
    CreateTime       timestamp        null comment '創建時間',
    UpdateTime       timestamp        not null comment '修改時間',
    LoginIp          varchar(64)      null comment '最後登錄IP',
    LoginTime        timestamp        null comment '最後登錄時間',
    FailedLoginCount bigint default 0 not null comment '連續登入失敗次數',
    Memo             varchar(255)     null comment '備註'
)
    comment '用户（賬戶）表';


