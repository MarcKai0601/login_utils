-- auto-generated definition
create table k_user
(
    UserId     bigint unsigned auto_increment comment '帳戶ID'
        primary key,
    Username   varchar(30) not null comment '登录账户',
    Password   varchar(60) null comment '登录密码',
    Status     tinyint     not null comment '状态,1-启用,0-禁用',
    CreateTime timestamp   null comment '創建時間',
    UpdateTime timestamp   not null comment '修改時間'
)
    comment '用户（賬戶）表';

