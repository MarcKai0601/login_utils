create table t_account
(
    AccountId           bigint auto_increment comment '支付帳號ID'
        primary key,
    AccountName         varchar(30)                         null comment '支付帳號名稱',
    Status              tinyint      default 0              not null comment '状态 0:下架 1:启用 2:达标 3:风控 4:暫時禁用 5:冷却 6:金额冷却',
    EnableStatusTime    timestamp                           null comment '狀態啟用時間',
    CatchId             varchar(10)                         null comment '小工具 Catch ID',
    CatchPassword       varchar(20)                         not null comment '小工具密碼',
    CatchMode           tinyint                             null comment '小工具模式 0:WebBrowser 1:selenium',
    CatchUpdateTime     timestamp                           null comment '小工具最後同步更新時間',
    AliUid              varchar(100)                        null comment '阿里UID',
    AppId               varchar(30)                         null comment 'App ID',
    PublicKey           blob                                null comment '支付公鑰',
    PrivateKey          blob                                null comment '支付私鑰',
    LoginUsername       varchar(60)                         null comment '登入帳號',
    LoginPassword       varchar(20)                         null comment '登入密碼',
    TransactionPassword varchar(20)                         null comment '支付密碼',
    Mobile              varchar(20)                         null comment '手機號碼',
    CostRate            double       default 0              not null comment '成本，費率',
    CostFixedAmount     bigint       default 0              not null comment '成本，固定金額，單位分',
    ChannelType         varchar(100)                        null comment '支援的渠道類型：PC,WAP,APP,TRANSFER/FACE,FREEZE',
    ChannelCode         varchar(30)                         null comment '渠道編碼',
    AccountCode         varchar(30)                         null comment '渠道編碼',
    BankCardId          bigint                              null comment '銀行卡ID',
    MinAmount           bigint       default 0              not null comment '單筆最低金額',
    MaxAmount           bigint       default 0              not null comment '單筆最高金額',
    DayMaxAmount        bigint       default 0              not null comment '單日最高金額 - 達標金額',
    DayMaxCount         bigint       default 0              not null comment '單日最多筆數 - 達標筆數',
    TodayAmount         bigint       default 0              not null comment '當日累積收款金額',
    TodayCount          bigint       default 0              not null comment '當日累積收款筆數',
    TotalAmount         bigint       default 0              not null comment '累計金額',
    TotalCount          bigint       default 0              not null comment '累計筆數',
    Balance             bigint       default 0              not null comment '帳號餘額',
    FrozenAmount        bigint       default 0              not null comment '不可用餘額，單位分',
    TodayWithdrawCount  bigint       default 0              not null comment '當日累積提款筆數',
    SpecifiedAmounts    varchar(255)                        null comment '資金下發 Server',
    BootServer          varchar(20)                         null comment '支付寶用 跳轉ipServer',
    Gateway             varchar(300)                        null comment '轉跳域名',
    IsGateUsed          tinyint(1)   default 0              not null comment '是否使用Gate',
    CatalogId           bigint                              null comment '商品目錄ID',
    Memo                varchar(255)                        null comment '備註',
    SecurityQuestion    text                                null comment '安全提示問題，JSON格式',
    UpdateTime          timestamp                           null comment '更新時間',
    CreateTime          timestamp                           null comment '創建時間',
    Updater             varchar(30)                         null comment '更新人',
    Creator             varchar(30)                         null comment '創建人',
    RiskStatus          tinyint      default 0              null comment '爬蟲狀態 0 未登入 1 已登入',
    Settlement          int          default 0              not null comment '結算 D0  D1',
    Belong              varchar(100) default '851740624778' not null comment '所屬'
)
    comment '賬號表' engine = InnoDB
                     charset = utf8;

create index t_account_CatchId_IDX
    on t_account (CatchId, CatchPassword);

create table t_account_channel
(
    AccountChannelId   bigint unsigned auto_increment comment '供应商渠道表编号'
        primary key,
    AccountId          bigint                                 not null comment '供应商表编号',
    ChannelId          bigint                                 not null comment '渠道表编号',
    SingleMinAmount    bigint       default 0                 null comment '单笔最低限额',
    SingleMaxAmount    bigint       default 1000000           null comment '单笔最高限额',
    SingleFixedAmount  varchar(50)                            null comment '单笔固定限额',
    SingleDayAmount    bigint       default 10000000000000    null comment '单日限额',
    SingleDayCount     bigint       default 10                null comment '单日限笔',
    TodayWithdrawCount tinyint      default 0                 null comment '当日累积提款笔数',
    TodayCount         bigint       default 0                 null comment '当日累积收款笔数',
    TotalAmount        bigint       default 0                 null comment '累积金额',
    TodayAmount        bigint       default 0                 null comment '当日累积收款金额',
    Balance            bigint       default 0                 null comment '帐号余额',
    TotalCount         bigint       default 0                 null comment '累积笔数',
    FrozenAmount       bigint       default 0                 null comment '不可用余额，单位分',
    Creator            varchar(30)                            not null comment '新建人',
    CreateTime         timestamp    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '新建时间',
    CreateIp           varchar(100)                           not null comment '新增IP',
    Updater            varchar(30)                            not null comment '更新人',
    UpdateTime         timestamp    default CURRENT_TIMESTAMP not null comment '更新时间',
    UpdateIp           varchar(100)                           not null comment '更新者IP',
    ExtParams          varchar(255)                           null comment '扩展参数(Json格式)',
    Status             tinyint      default 1                 null comment '狀態 1:啟用, 0:停用',
    ThirdChannelCode   varchar(30)                            null comment '自定义第三方渠道编码',
    TimeZone           varchar(100) default 'UTC+7'           not null,
    constraint t_account_channel_uindex
        unique (AccountId, ChannelId)
)
    comment '供應商渠道表' engine = InnoDB
                           charset = utf8;

create table t_account_new
(
    AccountId           bigint auto_increment comment '支付帳號ID'
        primary key,
    AccountName         varchar(30)                         null comment '支付帳號名稱',
    VendorId            bigint                              not null comment '第三方金流商代碼',
    MerchantId          varchar(12)                         null,
    ThirdMerchantSign   text                                null comment '商家驗證碼',
    Status              tinyint                             not null comment '狀態',
    ThirdMerchantId     varchar(100)                        not null comment '商家代碼',
    Creator             varchar(30)                         not null comment '新建人',
    CreateTime          timestamp default CURRENT_TIMESTAMP not null comment '新建时间',
    CreateIp            varchar(100)                        not null comment '新增IP',
    Updater             varchar(30)                         not null comment '更新人',
    UpdateTime          timestamp default CURRENT_TIMESTAMP not null comment '更新時間',
    UpdateIp            varchar(100)                        not null comment '更新IP',
    Memo                varchar(255)                        null comment '備註',
    PublicKey           text                                null comment '第三方金流商使用RSA公私鑰',
    PrivateKey          text                                null comment '第三方金流商使用RSA公私鑰簽驗',
    OrderOriginationUrl varchar(100)                        null comment '订单发起URL',
    OrderQueryUrl       varchar(100)                        null comment '订单查询URL',
    BalanceQueryUrl     varchar(512)                        null comment '余额查询URL',
    ThirdMerchantName   varchar(100)                        null comment '第三方金流里的商户名称',
    TraceStatus         tinyint                             null comment '追踪状态',
    ConnStatus          varchar(8)                          null comment '连线状态',
    ConnRefreshTime     timestamp                           null comment '连线状态刷新时间',
    Currency            varchar(100)                        null comment '幣別',
    Weight              int       default 50                not null comment '權重'
)
    comment '賬號表' engine = InnoDB
                     charset = utf8;

create table t_acl
(
    AclId    bigint auto_increment comment 'ACL ID'
        primary key,
    ParentId bigint               null comment '父ACL ID',
    `Key`    varchar(60)          null comment 'ACL碼',
    Name     varchar(60)          null comment 'ACL名稱',
    Api      varchar(60)          null comment 'API Uri',
    IsLog    tinyint(1) default 0 not null comment '是否寫log,0-不寫入,1-寫入'
)
    comment '權限表' engine = InnoDB
                     charset = utf8;

create table t_acl_common
(
    AclCommonId    bigint auto_increment comment 'ACL COMMON ID'
        primary key,
    ParentCommonId bigint               null comment '父ACL COMMON ID',
    `Key`          varchar(60)          null comment 'ACL碼',
    Name           varchar(60)          null comment 'ACL名稱',
    Api            varchar(60)          null comment 'API Uri',
    IsLog          tinyint(1) default 0 not null comment '是否寫log,0-不寫入,1-寫入'
)
    comment '權限共用表' engine = InnoDB
                         charset = utf8;

create table t_acl_role_map
(
    Id     bigint auto_increment
        primary key,
    RoleId bigint null comment '角色ID',
    AclId  bigint null comment 'ACL ID',
    UserId bigint null
)
    comment 'Acl和Role關聯表' engine = InnoDB
                              charset = utf8;

create index t_acl_role_map_RoleId_IDX
    on t_acl_role_map (RoleId);

create table t_acl_role_map_test
(
    Id     bigint auto_increment
        primary key,
    RoleId bigint null comment '角色ID',
    AclId  bigint null comment 'ACL ID',
    UserId bigint null
)
    comment 'Acl和Role關聯表' engine = InnoDB
                              charset = utf8;

create index t_acl_role_map_test_RoleId_IDX
    on t_acl_role_map_test (RoleId);

create table t_acl_test
(
    AclId    bigint auto_increment comment 'ACL ID'
        primary key,
    ParentId bigint               null comment '父ACL ID',
    `Key`    varchar(60)          null comment 'ACL碼',
    Name     varchar(60)          null comment 'ACL名稱',
    Api      varchar(60)          null comment 'API Uri',
    IsLog    tinyint(1) default 0 not null comment '是否寫log,0-不寫入,1-寫入'
)
    comment '權限表' engine = InnoDB
                     charset = utf8;

create table t_bank_abbreviation
(
    Id               bigint auto_increment comment '銀行名稱縮寫ID'
        primary key,
    BankAbbreviation varchar(10)  null comment '銀行名稱縮寫',
    BankName         varchar(255) null comment '銀行名稱'
)
    comment '銀行名稱縮寫表' engine = InnoDB
                             charset = utf8;

create table t_best_pay_bank_code
(
    BankCodeId bigint auto_increment comment 'BestPay的銀行代碼ID'
        primary key,
    BankName   varchar(20) null comment '銀行名稱',
    BankCode   varchar(20) null comment '銀行聯行碼'
)
    comment 'BestPay的銀行代碼列表' engine = InnoDB
                                    charset = utf8;

create table t_black_white_list
(
    Id         bigint unsigned auto_increment comment 'ID'
        primary key,
    MerchantId varchar(20)                         not null,
    HostIp     varchar(100)                        not null comment '品牌',
    Type       varchar(100)                        not null comment '类型(B:黑名单, W:白名单)',
    Status     tinyint   default 1                 null comment '状态(0:停用, 1:启用)',
    Creator    varchar(30)                         not null comment '新建人',
    CreateIp   varchar(100)                        not null comment '新增IP',
    CreateTime timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '新建时间',
    UpdateTime timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    UpdateIp   varchar(100)                        not null comment '更新者IP',
    Updater    varchar(30)                         not null comment '更新人',
    memo       varchar(500)                        null,
    constraint t_black_white_list_pk
        unique (MerchantId, HostIp)
)
    comment '黑白名单' engine = InnoDB
                       charset = utf8;

create table t_bulletin
(
    Id         bigint auto_increment
        primary key,
    Status     tinyint   null comment '狀態, 0:禁用, 1:啟用',
    Content    text      null comment '內容',
    UpdateTime timestamp null comment '更新時間'
)
    comment '公告' engine = InnoDB
                   charset = utf8;

create table t_catalog
(
    CatalogId   bigint auto_increment comment '商品目錄ID'
        primary key,
    ParentId    bigint       null comment '父商品目錄ID',
    CatalogName varchar(100) null comment '商品目錄名稱'
)
    comment '商品目錄表' engine = InnoDB
                         charset = utf8;

create index t_catalog_ParentId_IDX
    on t_catalog (ParentId);

create table t_channel
(
    ChannelId      bigint auto_increment comment '渠道ID'
        primary key,
    ChannelName    varchar(30)       null comment '渠道名稱',
    ChannelCode    varchar(30)       null comment '支付代碼',
    Status         tinyint default 0 not null comment '狀態,0-禁用,1-啟用',
    DiscountStatus tinyint default 0 not null comment '優惠狀態,0-禁用,1-啟用',
    memo           varchar(255)      null comment '備註',
    UpdateTime     timestamp         null comment '更新時間',
    CreateTime     timestamp         null comment '創建時間',
    Updater        varchar(30)       null comment '更新人',
    Creator        varchar(30)       null comment '創建人',
    Currency       varchar(20)       null comment '三位货币代码，人民币:cny',
    constraint t_channel_ChannelCode_uindex
        unique (ChannelCode)
)
    comment '渠道表' engine = InnoDB
                     charset = utf8;

create table t_control_event
(
    ControlEventId  bigint auto_increment comment '控制事件ID'
        primary key,
    PayOrderId      varchar(30)       null comment '訂單號',
    Status          tinyint default 0 not null comment '狀態, 0:未啟動, 1:進行中, 2:完成, 3:失敗',
    Type            tinyint default 0 not null comment '類型',
    NextProcessTime datetime          null comment '下次處理時間',
    ProcessedCount  int               null,
    Memo            text              null comment '備註',
    CreateTime      timestamp         null comment '創建時間',
    UpdateTime      timestamp         null comment '更新時間'
)
    comment '控制事件表' engine = InnoDB
                         charset = utf8;

create index t_control_event_NextProcessTime_IDX
    on t_control_event (NextProcessTime);

create index t_control_event_PayOrderId_IDX
    on t_control_event (PayOrderId);

create index t_control_event_idx01
    on t_control_event (Type, Status, NextProcessTime);

create table t_currency_exchange_rate
(
    FromCurrency    varchar(10)     not null comment '原始幣別',
    ToCurrency      varchar(10)     not null comment '目標幣別',
    ExchangeRate    decimal(18, 12) not null comment '匯率',
    BuyOffsetValue  decimal(18, 12) not null comment '转入偏移值',
    SaleOffsetValue decimal(18, 12) not null comment '转出偏移值',
    UpdateTime      timestamp       not null comment '更新時間',
    primary key (FromCurrency, ToCurrency)
)
    comment '匯率表' engine = InnoDB
                     charset = utf8;

create table t_daily_report
(
    Id             bigint auto_increment comment 'ID'
        primary key,
    MerchantId     varchar(20)      null comment '商戶ID',
    ChannelCode    varchar(30)      null comment '渠道代碼',
    AccountCode    varchar(30)      null comment '帳號類別',
    Type           tinyint          not null comment '類型',
    PayAmount      bigint default 0 not null comment '收款金額，單位分',
    PayFee         bigint default 0 not null comment '收款手續費，單位分',
    PayCount       bigint default 0 null comment '收款筆數',
    WithdrawAmount bigint default 0 not null comment '提款金額，單位分',
    WithdrawFee    bigint default 0 not null comment '提款手續費，單位分',
    WithdrawCount  bigint default 0 null comment '付款筆數',
    ChargeAmount   bigint default 0 not null comment '人工賬變金額，單位分',
    ChargeCount    bigint default 0 null comment '人工賬變筆數',
    Balance        bigint default 0 not null comment '餘額，單位分',
    ReportDay      date             not null comment '日期',
    CreateTime     timestamp        null comment '創建時間',
    VendorId       bigint           null comment '第三方渠道商ID t_vendor.VendorId (新加)',
    CatchId        varchar(100)     null comment '代付渠道编码',
    Currency       varchar(20)      null comment '三位货币代码，人民币:cny'
)
    comment '每日報表' engine = InnoDB
                       charset = utf8;

create index t_daily_report_IDX1
    on t_daily_report (ReportDay);

create table t_daily_report_gmt7
(
    Id             bigint auto_increment comment 'ID'
        primary key,
    MerchantId     varchar(20)      null comment '商戶ID',
    ChannelCode    varchar(30)      null comment '渠道代碼',
    AccountCode    varchar(30)      null comment '帳號類別',
    Type           tinyint          not null comment '類型',
    PayAmount      bigint default 0 not null comment '收款金額，單位分',
    PayFee         bigint default 0 not null comment '收款手續費，單位分',
    PayCount       bigint default 0 null comment '收款筆數',
    WithdrawAmount bigint default 0 not null comment '提款金額，單位分',
    WithdrawFee    bigint default 0 not null comment '提款手續費，單位分',
    WithdrawCount  bigint default 0 null comment '付款筆數',
    ChargeAmount   bigint default 0 not null comment '人工賬變金額，單位分',
    ChargeCount    bigint default 0 null comment '人工賬變筆數',
    Balance        bigint default 0 not null comment '餘額，單位分',
    ReportDay      date             not null comment '日期',
    CreateTime     timestamp        null comment '創建時間',
    VendorId       bigint           null comment '第三方渠道商ID t_vendor.VendorId (新加)',
    CatchId        varchar(100)     null comment '代付渠道编码',
    Currency       varchar(20)      null comment '三位货币代码，人民币:cny'
)
    comment '每日報表GMT7' engine = InnoDB
                           charset = utf8;

create index t_daily_report_gmt7_IDX1
    on t_daily_report_gmt7 (ReportDay);

create table t_daily_report_perhour
(
    Id             bigint auto_increment comment 'ID'
        primary key,
    MerchantId     varchar(20)      null comment '商戶ID',
    ChannelCode    varchar(30)      null comment '渠道代碼',
    AccountCode    varchar(30)      null comment '帳號類別',
    Type           tinyint          not null comment '類型',
    PayAmount      bigint default 0 not null comment '收款金額，單位分',
    PayFee         bigint default 0 not null comment '收款手續費，單位分',
    PayCount       bigint default 0 null comment '收款筆數',
    WithdrawAmount bigint default 0 not null comment '提款金額，單位分',
    WithdrawFee    bigint default 0 not null comment '提款手續費，單位分',
    WithdrawCount  bigint default 0 null comment '付款筆數',
    ReportDay      datetime         not null comment '日期',
    CreateTime     timestamp        null comment '創建時間',
    VendorId       bigint           null comment '第三方渠道商ID t_vendor.VendorId (新加)',
    CatchId        varchar(100)     null comment '代付渠道编码',
    Currency       varchar(20)      null comment '三位货币代码，人民币:cny'
)
    comment '每日報表(每小時)' engine = InnoDB
                               charset = utf8;

create index t_daily_report_perHour_IDX1
    on t_daily_report_perhour (ReportDay);

create table t_global_param
(
    Id         bigint auto_increment comment 'ID'
        primary key,
    `Key`      varchar(255) not null comment 'Key',
    Value      varchar(255) null comment 'Value',
    Status     tinyint      null comment '狀態',
    Memo       varchar(255) null comment '備註',
    UpdateTime timestamp    null comment '更新時間'
)
    comment '全域設定表' engine = InnoDB
                         charset = utf8;

create index t_global_param_Key_IDX
    on t_global_param (`Key`);

create table t_goods
(
    GoodsId   bigint auto_increment comment '商品ID'
        primary key,
    CatalogId bigint       null comment '商品目錄ID',
    GoodsName varchar(100) null comment '商品名稱'
)
    comment '商品表' engine = InnoDB
                     charset = utf8;

create index t_goods_CatalogId_IDX
    on t_goods (CatalogId);

create table t_groovy_setting
(
    Id         bigint auto_increment comment 'ID'
        primary key,
    CatchId    varchar(100)      null comment '支付代付渠道编码',
    Type       tinyint default 0 not null comment '0:支付，1:代付',
    Status     tinyint default 0 not null comment '0:停用，1:啟用',
    Version    int               null comment '版號',
    Script     text              null comment 'groovy腳本',
    UpdateTime timestamp         null comment '更新時間',
    CreateTime timestamp         null comment '創建時間',
    Updater    varchar(30)       null comment '更新人',
    Creator    varchar(30)       null comment '創建人'
)
    comment 'Groovy腳本' engine = InnoDB
                         charset = utf8;

create index t_groovy_setting_IDX1
    on t_groovy_setting (CatchId, Type);

create table t_jsindb
(
    id   bigint auto_increment
        primary key,
    jdoc json null,
    key1 varchar(255) as (json_extract(`jdoc`, _utf8mb4'$.pid')) stored,
    key2 varchar(255) as (json_extract(`jdoc`, _utf8mb4'$.pid')),
    key3 varchar(255) as (json_extract(`jdoc`, _utf8mb4'$.name'))
)
    engine = InnoDB
    charset = utf8;

create index idx_key1
    on t_jsindb (key1);

create index t_jsindb_key2_IDX
    on t_jsindb (key2);

create table t_merchant
(
    MerchantId         varchar(12)  default ''            not null comment '商戶ID'
        primary key,
    MerchantName       varchar(30)                        null comment '商戶名称',
    ParentId           varchar(12)                        null comment '父商戶ID',
    FullPath           varchar(1024)                      null comment '代理階層路徑，含自己，並且斜線結尾',
    RequestKey         varchar(128) default ''            null comment '请求私钥',
    BankName           varchar(255)                       null comment '銀行名稱',
    PayeeCardName      varchar(100)                       null comment '銀行賬戶名',
    PayeeCardNo        varchar(100)                       null comment '銀行卡號',
    Status             tinyint      default 0             not null comment '状态 0:关闭 1:启用',
    Memo               varchar(255)                       null comment '備註',
    Mobile             varchar(30)                        null comment '手機號碼',
    Email              varchar(255)                       null comment '電子郵箱',
    UpdateTime         timestamp                          null comment '更新時間',
    CreateTime         timestamp                          null comment '創建時間',
    Updater            varchar(30)                        null comment '更新人',
    Creator            varchar(30)                        null comment '創建人',
    Balance            bigint       default 0             not null comment '余额',
    FrozenAmount       bigint       default 0             not null comment '冻结金额',
    IsAdmin            tinyint      default 0             null comment '是否为管理员',
    Username           varchar(30)                        not null,
    IsBackendLogin     tinyint      default 1             null comment '是否为后台可登',
    Packagename        varchar(100)                       null comment 'app packagename',
    Timezone           varchar(40)  default 'Asia/Saigon' null comment '時區',
    SortStatus         tinyint      default 0             not null comment '權重功能是否開啟,0-關閉,1-開啟',
    WithdrawSortStatus tinyint      default 0             not null comment '代付權重功能是否開啟,0-關閉,1-開啟'
)
    comment '商户信息表' engine = InnoDB
                         charset = utf8;

create index t_merchant_FullPath_IDX
    on t_merchant (FullPath(255));

create index t_merchant_MerchantId_IDX
    on t_merchant (MerchantId, ParentId);

create table t_merchant_account_group_map
(
    Id             bigint auto_increment
        primary key,
    MerchantId     varchar(12) not null comment '商戶ID',
    AccountGroupId bigint      null comment '帳號組ID'
)
    comment 'Merchant和AccountGroup關聯表' engine = InnoDB
                                           charset = utf8;

create index t_merchant_account_group_map_MerchantId_IDX
    on t_merchant_account_group_map (MerchantId, AccountGroupId);

create table t_merchant_balance_change_log
(
    Id                 bigint auto_increment comment '商戶餘額賬變ID'
        primary key,
    MerchantId         varchar(20)       null comment '商戶ID',
    OrderId            varchar(30)       null comment '訂單ID',
    BeforeBalance      bigint  default 0 not null comment '變化前餘額，單位分',
    AfterBalance       bigint  default 0 not null comment '變化後餘額，單位分',
    Amount             bigint  default 0 not null comment '變化金額，單位分',
    Reason             tinyint default 0 not null comment '賬變科目',
    Memo               text              null comment '備註',
    CreateTime         timestamp         null comment '創建時間',
    BeforeFreezeAmount bigint  default 0 null comment '冻结發生前金额',
    AfterFreezeAmount  bigint            null comment '冻结发生后金额'
)
    comment '商戶餘額賬變表' engine = InnoDB
                             charset = utf8;

create index t_merchant_balance_change_log_CreateTime_OrderId_Reason_index
    on t_merchant_balance_change_log (CreateTime, OrderId, Reason);

create index t_merchant_balance_change_log_MerchantId_IDX
    on t_merchant_balance_change_log (MerchantId);

create index t_merchant_balance_change_log_OrderId_IDX
    on t_merchant_balance_change_log (OrderId);

create table t_merchant_currency_exchange_rate
(
    MerchantId       varchar(12)     not null comment '商戶ID',
    FromCurrency     varchar(10)     not null comment '原始幣別',
    ToCurrency       varchar(10)     not null comment '目標幣別',
    BuyExchangeRate  decimal(18, 12) not null comment '买入匯率',
    SaleExchangeRate decimal(18, 12) not null comment '卖出汇率',
    UpdateTime       timestamp       not null comment '更新時間',
    primary key (MerchantId, FromCurrency, ToCurrency)
)
    comment '商户自订匯率表' engine = InnoDB
                             charset = utf8;

create table t_merchant_deposit_notify
(
    OrderId          varchar(30)                             not null comment '订单ID'
        primary key,
    MerchantId       varchar(12)                             not null comment '商户ID',
    MerchantOrderNo  varchar(60)                             not null comment 'å•†æˆ·è®¢å•å·',
    OrderType        tinyint       default 0                 not null comment '订单类型:0支付,1-代付',
    NotifyUrl        varchar(2048) default ''                not null comment '通知地址',
    NotifyCount      int           default 0                 not null comment '通知次数',
    ReturnData       blob                                    null comment '通知响应结果',
    Status           int           default 0                 not null comment '通知状态,0-未通知,1-通知中,2-通知成功,3-通知失败',
    LastNotifyTime   timestamp                               null comment '最后一次通知时间',
    CreateTime       timestamp     default CURRENT_TIMESTAMP not null comment '创建时间',
    UpdateTime       timestamp                               null comment '更新时间',
    NotifyErrorCount tinyint       default 0                 null comment '回調異常次數'
)
    comment '商户存入通知表' engine = InnoDB
                             charset = utf8mb4;

create index idx_orderid_status
    on t_merchant_deposit_notify (OrderId, Status);

create index t_merchant_notify_CreateTime_IDX
    on t_merchant_deposit_notify (CreateTime);

create index t_merchant_notify_IDX1
    on t_merchant_deposit_notify (OrderId);

create index t_merchant_notify_MerchantId_IDX
    on t_merchant_deposit_notify (MerchantId);

create table t_merchant_deposit_notify_month
(
    OrderId          varchar(30)                             not null comment '订单ID',
    MerchantId       varchar(12)                             not null comment '商户ID',
    MerchantOrderNo  varchar(60)                             not null comment '商户订单号',
    OrderType        tinyint       default 0                 not null comment '订单类型:0支付,1-代付',
    NotifyUrl        varchar(2048) default ''                not null comment '通知地址',
    NotifyCount      int           default 0                 not null comment '通知次数',
    ReturnData       blob                                    null comment '通知响应结果',
    Status           int           default 0                 not null comment '通知状态,0-未通知,1-通知中,2-通知成功,3-通知失败',
    LastNotifyTime   timestamp                               null comment '最后一次通知时间',
    CreateTime       timestamp     default CURRENT_TIMESTAMP not null comment '创建时间',
    UpdateTime       timestamp                               null on update CURRENT_TIMESTAMP comment '更新时间',
    NotifyErrorCount tinyint       default 0                 null comment '回調異常次數',
    OrderIdCrc32     int unsigned as (crc32(`OrderId`)) stored,
    CreateDateTime   datetime as (cast(`CreateTime` as datetime)) stored,
    primary key (OrderId, OrderIdCrc32, CreateDateTime)
)
    engine = InnoDB
    collate = utf8mb4_general_ci partition by list (month(`CreateDateTime`)) subpartition by hash (`OrderIdCrc32`) subpartitions 5 (
    partition p01 values in (1),
    partition p02 values in (2),
    partition p03 values in (3),
    partition p04 values in (4),
    partition p05 values in (5),
    partition p06 values in (6),
    partition p07 values in (7),
    partition p08 values in (8),
    partition p09 values in (9),
    partition p10 values in (10),
    partition p11 values in (11),
    partition p12 values in (12)
    );

create index t_merchant_deposit_notify
    on t_merchant_deposit_notify_month (OrderId, Status);

create index t_merchant_notify_CreateTime_IDX
    on t_merchant_deposit_notify_month (CreateTime);

create index t_merchant_notify_IDX1
    on t_merchant_deposit_notify_month (OrderId);

create index t_merchant_notify_MerchantId_IDX
    on t_merchant_deposit_notify_month (MerchantId);

create table t_merchant_notify
(
    OrderId          varchar(30)                             not null comment '订单ID'
        primary key,
    MerchantId       varchar(12)                             not null comment '商户ID',
    MerchantOrderNo  varchar(60)                             not null comment 'å•†æˆ·è®¢å•å·',
    OrderType        tinyint       default 0                 not null comment '订单类型:0支付,1-代付',
    NotifyUrl        varchar(2048) default ''                not null comment '通知地址',
    NotifyCount      int           default 0                 not null comment '通知次数',
    ReturnData       blob                                    null comment '通知响应结果',
    Status           int           default 0                 not null comment '通知状态,0-未通知,1-通知中,2-通知成功,3-通知失败',
    LastNotifyTime   timestamp                               null comment '最后一次通知时间',
    CreateTime       timestamp     default CURRENT_TIMESTAMP not null comment '创建时间',
    UpdateTime       timestamp                               null comment '更新时间',
    NotifyErrorCount tinyint       default 0                 null comment '回調異常次數'
)
    comment '商户通知表' engine = InnoDB
                         charset = utf8;

create index t_merchant_notify_CreateTime_IDX
    on t_merchant_notify (CreateTime);

create index t_merchant_notify_IDX1
    on t_merchant_notify (OrderId);

create index t_merchant_notify_MerchantId_IDX
    on t_merchant_notify (MerchantId);

create table t_merchant_notify_month
(
    OrderId          varchar(30)                             not null comment '订单ID',
    MerchantId       varchar(12)                             not null comment '商户ID',
    MerchantOrderNo  varchar(60)                             not null comment '商户订单号',
    OrderType        tinyint       default 0                 not null comment '订单类型:0支付,1-代付',
    NotifyUrl        varchar(2048) default ''                not null comment '通知地址',
    NotifyCount      int           default 0                 not null comment '通知次数',
    ReturnData       blob                                    null comment '通知响应结果',
    Status           int           default 0                 not null comment '通知状态,0-未通知,1-通知中,2-通知成功,3-通知失败',
    LastNotifyTime   timestamp                               null comment '最后一次通知时间',
    CreateTime       timestamp     default CURRENT_TIMESTAMP not null comment '创建时间',
    UpdateTime       timestamp                               null on update CURRENT_TIMESTAMP comment '更新时间',
    NotifyErrorCount tinyint       default 0                 null comment '回調異常次數',
    OrderIdCrc32     int unsigned as (crc32(`OrderId`)) stored,
    CreateDateTime   datetime as (cast(`CreateTime` as datetime)) stored,
    primary key (OrderId, OrderIdCrc32, CreateDateTime)
)
    engine = InnoDB
    collate = utf8mb4_general_ci partition by list (month(`CreateDateTime`)) subpartition by hash (`OrderIdCrc32`) subpartitions 5 (
    partition p01 values in (1),
    partition p02 values in (2),
    partition p03 values in (3),
    partition p04 values in (4),
    partition p05 values in (5),
    partition p06 values in (6),
    partition p07 values in (7),
    partition p08 values in (8),
    partition p09 values in (9),
    partition p10 values in (10),
    partition p11 values in (11),
    partition p12 values in (12)
    );

create index t_merchant_notify_CreateTime_IDX
    on t_merchant_notify_month (CreateTime);

create index t_merchant_notify_IDX1
    on t_merchant_notify_month (OrderId);

create index t_merchant_notify_MerchantId_IDX
    on t_merchant_notify_month (MerchantId);

create table t_merchant_operate_log
(
    LogId        bigint auto_increment
        primary key,
    MerchantId   varchar(12)                         not null comment '商戶编号(t_merchant.MerchantId)',
    Memo         text                                not null comment '备注内容(Json格式)',
    AclId        bigint                              not null comment '请求方法(t_acl.AclId)',
    CreateTime   timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '创建时间',
    CreatorLevel varchar(30)                         not null comment '创建者Level',
    Creator      varchar(30)                         not null comment '创建者帐号',
    CreatorIp    varchar(15)                         not null comment '创建者IP'
)
    engine = InnoDB
    charset = utf8;

create index merchant_operate_log_index
    on t_merchant_operate_log (MerchantId, CreateTime, AclId);

create table t_merchant_settlement
(
    MerchantSettlementId      bigint auto_increment comment '商戶結算ID'
        primary key,
    MerchantId                varchar(12)      not null comment '商戶ID',
    ChannelId                 bigint           null comment '渠道ID',
    SettlementId              bigint           null comment '結算ID',
    RateDifference            double default 0 not null comment '收款費率差',
    RateFixedAmountDifference bigint default 0 not null comment '手續費差，固定金額，單位分',
    MinAmount                 bigint default 0 not null comment '單筆最低金額',
    MaxAmount                 bigint default 0 not null comment '單筆最高金額',
    DayMaxAmount              bigint default 0 not null comment '單日最高金額 - 達標金額',
    DayMaxCount               bigint default 0 not null comment '單日最多筆數 - 達標筆數',
    TodayAmount               bigint default 0 not null comment '當日累積收款金額',
    TodayCount                bigint default 0 not null comment '當日累積收款筆數',
    TotalAmount               bigint default 0 not null comment '累積收款金額',
    TotalCount                bigint default 0 not null comment '累積收款筆數',
    UpdateTime                timestamp        null comment '更新時間',
    CreateTime                timestamp        null comment '創建時間',
    Updater                   varchar(30)      null comment '更新人',
    Creator                   varchar(30)      null comment '創建人',
    ServiceFee                bigint default 0 null comment '服務費',
    ServiceFeeRate            double default 0 null
)
    comment '商戶的結算表' engine = InnoDB
                           charset = utf8;

create index t_merchant_settlement_ChannelId_IDX
    on t_merchant_settlement (ChannelId);

create index t_merchant_settlement_MerchantId_IDX
    on t_merchant_settlement (MerchantId);

create index t_merchant_settlement_SettlementId_IDX
    on t_merchant_settlement (SettlementId);

create table t_merchant_user
(
    Id               bigint auto_increment
        primary key,
    MerchantId       varchar(15)  null comment '商戶ID',
    MerchantUserId   varchar(255) null comment '商戶的用戶ID',
    Status           tinyint      null comment '商戶的用戶的狀態, 0:禁用, 1:啟用 ',
    AccountIds       varchar(255) null comment 'AccountId列表, 逗點分隔',
    BackupAccountIds varchar(255) null,
    Memo             varchar(255) null comment '備註',
    CreateTime       timestamp    null comment '創建時間',
    UpdateTime       timestamp    null comment '更新時間',
    constraint t_merchant_user_MerchantId_MerchantUserId_IDX
        unique (MerchantId, MerchantUserId)
)
    comment '商戶的用戶表' engine = InnoDB
                           charset = utf8;

create index t_merchant_user_CreateTime
    on t_merchant_user (CreateTime);

create table t_operate_log
(
    Id         bigint auto_increment
        primary key,
    Url        varchar(60)                         null comment 'URL',
    Request    blob                                null comment '請求參數',
    Status     varchar(150)                        null comment '成功 失敗',
    MethodName varchar(20)                         null comment '功能名稱',
    CreateTime timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    Creator    varchar(30)                         null
)
    engine = InnoDB
    charset = utf8;

create table t_order_http_log
(
    Id              bigint auto_increment comment 'ID'
        primary key,
    OrderId         varchar(30)   default '' not null comment '订单编号',
    Type            tinyint       default 0  not null comment '0:支付，1:代付',
    AccountId       bigint        default 0  not null comment '支付或代付渠道编号',
    SubmitRequest   varchar(2000) default '' not null comment '下单请求',
    SubmitResponse  varchar(2000)            null comment '下单响应',
    SubmitTime      datetime                 null comment '下单时间',
    QueryRequest    varchar(2000)            null comment '查单请求',
    QueryResponse   varchar(2000)            null comment '查单响应',
    QueryTime       datetime                 null comment '最新查单时间',
    CallbackRequest varchar(2000)            null comment '回调接收内容',
    CallbackTime    datetime                 null comment '回调接收时间',
    constraint _order_unique
        unique (OrderId, Type)
)
    engine = InnoDB
    collate = utf8mb4_general_ci;

create index _orderid_type
    on t_order_http_log (OrderId, Type);

create index _request_time
    on t_order_http_log (SubmitTime);

create index idx_t_order_http_log_01
    on t_order_http_log (SubmitTime);

create table t_order_http_log_month
(
    Id              bigint auto_increment,
    OrderId         varchar(30)   default '' not null,
    Type            tinyint       default 0  not null,
    AccountId       bigint        default 0  not null,
    SubmitRequest   varchar(2000) default '' not null,
    SubmitResponse  varchar(2000)            null,
    SubmitTime      datetime                 not null,
    QueryRequest    varchar(2000)            null,
    QueryResponse   varchar(2000)            null,
    QueryTime       datetime                 null,
    CallbackRequest varchar(2000)            null,
    CallbackTime    datetime                 null,
    OrderIdCrc32    int unsigned as (crc32(`OrderId`)) stored,
    primary key (Id, OrderIdCrc32, SubmitTime)
)
    engine = InnoDB
    collate = utf8mb4_general_ci partition by list (month(`SubmitTime`)) subpartition by hash (`OrderIdCrc32`) subpartitions 5 (
    partition p01 values in (1),
    partition p02 values in (2),
    partition p03 values in (3),
    partition p04 values in (4),
    partition p05 values in (5),
    partition p06 values in (6),
    partition p07 values in (7),
    partition p08 values in (8),
    partition p09 values in (9),
    partition p10 values in (10),
    partition p11 values in (11),
    partition p12 values in (12)
    );

create index _crc32
    on t_order_http_log_month (OrderIdCrc32);

create index _orderid_type
    on t_order_http_log_month (OrderId, Type);

create index _submit_time
    on t_order_http_log_month (SubmitTime);

create table t_otp_log
(
    Id         bigint auto_increment comment '身份验证纪录ID'
        primary key,
    UserId     bigint                              not null comment '用户ID',
    SecretKey  varchar(100)                        null comment '密钥',
    TotpCode   varchar(10)                         not null comment '验证编码',
    Status     tinyint   default 1                 null comment '状态 0:失败 1:成功',
    Action     varchar(20)                         not null comment '行为',
    CreateTime timestamp default CURRENT_TIMESTAMP null comment '新建时间',
    Creator    varchar(30)                         not null comment '新建人'
)
    comment '身份验证纪录表' engine = InnoDB
                             charset = utf8;

create index t_otp_log_IDX1
    on t_otp_log (UserId, SecretKey);

create index t_otp_log_IDX2
    on t_otp_log (SecretKey);

create table t_pay_order
(
    PayOrderId            varchar(30)                         not null comment '支付订单号'
        primary key,
    MerchantId            varchar(12)                         not null comment '商户ID',
    MerchantUserId        varchar(255)                        null comment '商戶的用戶ID',
    MerchantOrderNo       varchar(64)                         not null comment '商户方的订单号',
    ChannelId             bigint                              null comment '渠道ID',
    ChannelCode           varchar(30)                         null comment '渠道編碼',
    ChannelType           varchar(30)                         null comment '渠道類型',
    AccountGroupCode      varchar(10)                         null comment '帐号组代码',
    ChannelOrderNo        varchar(64)                         null comment '渠道方的订单号',
    ChannelRemark         text                                null comment '备注信息',
    AccountId             bigint                              null comment '賬號ID',
    Amount                bigint    default 0                 not null comment '支付金额,单位分',
    ActualAmount          bigint    default 0                 not null comment '實際支付金額，單位分',
    ConfirmedActualAmount bigint    default 0                 not null comment '確認實際支付金額，單位分',
    DiscountAmount        bigint    default 0                 not null comment '優惠金額，單位分',
    Rate                  double    default 0                 not null comment '手续费費率',
    RateFixedAmount       bigint    default 0                 not null comment '手續費，固定金額，單位分',
    Fee                   bigint                              null comment '手續費，單位分',
    Currency              varchar(20)                         null comment '三位货币代码，人民币:cny',
    Status                tinyint   default 0                 not null comment '支付状态, 0=订单生成, 1=未支付, 2=待支付, 3=支付中, 4=支付完成, 5=处理完成, 6=订单关闭（未支付）, 7=订单关闭（待支付）, 8=订单关闭（支付中）',
    NotifyUrl             varchar(128)                        not null comment '通知地址',
    ReturnUrl             varchar(128)                        null comment '同步跳转地址',
    QuitUrl               varchar(128)                        null comment '支付取消返回地址',
    Subject               varchar(100)                        null comment '訂單標題',
    Body                  varchar(100)                        null comment '訂單描述',
    SuccessTime           timestamp                           null comment '订单支付成功时间',
    RateDifferences       text                                null comment '整個代理結構的費率差列表，JSON格式',
    ClientIp              varchar(300)                        null comment '客户端IP',
    ClientDevice          varchar(64)                         null comment '客戶端设备',
    ClientExtra           text                                null comment '客戶端发起时额外参数',
    CreateTime            timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    UpdateTime            timestamp                           null comment '更新时间',
    CheckPaidProcess      tinyint                             null comment '指出，去支付寶撈訂單，再將訂單狀態設為PAID，是走哪個流程，0:支付寶, 1:刷新',
    ServiceFee            bigint    default 0                 null comment '服務費''',
    IsBelong              int       default 0                 null,
    AccountChannelId      bigint                              null comment '该笔订单使用的t_account_channel.AccountChannelId (新加)',
    ChannelNotifyUrl      varchar(1000)                       null comment '渠道通知网址',
    CatchId               varchar(100)                        null comment '渠道编码',
    OtherParams           char(100)                           null comment '额外参数，JSON格式',
    RealName              varchar(100)                        null comment '客戶真實姓名',
    CurrencyRate          decimal(12, 6)                      null comment '汇率',
    USDT                  decimal(18, 6)                      null comment '虚拟币金额',
    ExecuteStatus         tinyint   default 0                 not null comment '下單執行狀態',
    Email                 varchar(50)                         null comment '信箱地址',
    PhoneNum              varchar(50)                         null comment '电话号码',
    PayAccountNum         varchar(50)                         null comment '支付帐号',
    MerchantNotifyStatus  int                                 null comment '通知状态,0-未通知,1-通知中,2-通知成功,3-通知失败',
    LastNotifyTime        timestamp                           null comment '最后一次通知时间'
)
    comment '支付订单表' engine = InnoDB
                         charset = utf8;

create index t_pay_order_AccountId_IDX
    on t_pay_order (AccountId);

create index t_pay_order_ChannelId_IDX
    on t_pay_order (ChannelId);

create index t_pay_order_CreateTime_IDX
    on t_pay_order (CreateTime);

create index t_pay_order_IDX1
    on t_pay_order (Status, CreateTime);

create index t_pay_order_IDX2
    on t_pay_order (MerchantId asc, CreateTime desc);

create index t_pay_order_MerchantId_IDX
    on t_pay_order (MerchantId);

create index t_pay_order_MerchantIdsucess_IDX
    on t_pay_order (MerchantId, AccountId, Amount, Status, SuccessTime);

create index t_pay_order_MerchantOrderNo_IDX
    on t_pay_order (MerchantOrderNo);

create index t_pay_order_SuccessTime_IDX
    on t_pay_order (SuccessTime);

create index t_pay_order_merchantNotifyStatus_IDX
    on t_pay_order (MerchantNotifyStatus);

create index t_pay_order_status_IDX
    on t_pay_order (MerchantId, Amount, ActualAmount, CreateTime, Status);

create table t_pay_order_hist
(
    PayOrderId            varchar(30)                         not null comment '支付订单号'
        primary key,
    MerchantId            varchar(12)                         not null comment '商户ID',
    MerchantUserId        varchar(255)                        null comment '商戶的用戶ID',
    MerchantOrderNo       varchar(64)                         not null comment '商户方的订单号',
    ChannelId             bigint                              null comment '渠道ID',
    ChannelCode           varchar(30)                         null comment '渠道編碼',
    ChannelType           varchar(30)                         null comment '渠道類型',
    AccountGroupCode      varchar(10)                         null comment '帐号组代码',
    ChannelOrderNo        varchar(64)                         null comment '渠道方的订单号',
    ChannelRemark         mediumtext                          null comment '备注信息',
    AccountId             bigint                              null comment '賬號ID',
    Amount                bigint    default 0                 not null comment '支付金额,单位分',
    ActualAmount          bigint    default 0                 not null comment '實際支付金額，單位分',
    ConfirmedActualAmount bigint    default 0                 not null comment '確認實際支付金額，單位分',
    DiscountAmount        bigint    default 0                 not null comment '優惠金額，單位分',
    Rate                  double    default 0                 not null comment '手续费費率',
    RateFixedAmount       bigint    default 0                 not null comment '手續費，固定金額，單位分',
    Fee                   bigint                              null comment '手續費，單位分',
    Currency              varchar(20)                         null comment '三位货币代码,人民币:cny',
    Status                tinyint   default 0                 not null comment '支付状态, 0=订单生成, 1=未支付, 2=待支付, 3=支付中, 4=支付完成, 5=处理完成, 6=订单关闭（未支付）, 7=订单关闭（待支付）, 8=订单关闭（支付中）',
    NotifyUrl             varchar(128)                        not null comment '通知地址',
    ReturnUrl             varchar(128)                        null comment '同步跳转地址',
    QuitUrl               varchar(128)                        null comment '支付取消返回地址',
    Subject               varchar(100)                        null comment '訂單標題',
    Body                  varchar(100)                        null comment '訂單描述',
    SuccessTime           timestamp                           null comment '订单支付成功时间',
    RateDifferences       mediumtext                          null comment '整個代理結構的費率差列表，JSON格式',
    ClientIp              varchar(300)                        null comment '客户端IP',
    ClientDevice          varchar(64)                         null comment '客戶端设备',
    ClientExtra           mediumtext                          null comment '客戶端发起时额外参数',
    CreateTime            timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    UpdateTime            timestamp                           null comment '更新时间',
    CheckPaidProcess      tinyint                             null comment '指出，去支付寶撈訂單，再將訂單狀態設為PAID，是走哪個流程，0:支付寶, 1:刷新',
    ServiceFee            bigint    default 0                 null comment '服務費''',
    IsBelong              int       default 0                 null,
    AccountChannelId      bigint                              null comment '该笔订单使用的t_account_channel.AccountChannelId (新加)',
    ChannelNotifyUrl      varchar(1000)                       null comment '渠道通知网址',
    CatchId               varchar(100)                        null comment '渠道编码',
    OtherParams           varchar(100)                        null comment '额外参数，JSON格式',
    RealName              varchar(100)                        null comment '客戶真實姓名',
    CurrencyRate          decimal(12, 6)                      null comment '汇率',
    USDT                  decimal(18, 6)                      null comment '虚拟币金额',
    ExecuteStatus         tinyint   default 0                 not null comment '下單執行狀態',
    Email                 varchar(50)                         null comment '信箱地址',
    PhoneNum              varchar(50)                         null comment '电话号码',
    PayAccountNum         varchar(50)                         null comment '支付帐号',
    MerchantNotifyStatus  int                                 null comment '通知状态,0-未通知,1-通知中,2-通知成功,3-通知失败',
    LastNotifyTime        timestamp                           null comment '最后一次通知时间'
)
    comment '支付订单表' engine = InnoDB
                         charset = utf8;

create index t_pay_order_AccountId_IDX
    on t_pay_order_hist (AccountId);

create index t_pay_order_ChannelId_IDX
    on t_pay_order_hist (ChannelId);

create index t_pay_order_CreateTime_IDX
    on t_pay_order_hist (CreateTime);

create index t_pay_order_IDX1
    on t_pay_order_hist (Status, CreateTime);

create index t_pay_order_IDX2
    on t_pay_order_hist (MerchantId asc, CreateTime desc);

create index t_pay_order_MerchantId_IDX
    on t_pay_order_hist (MerchantId);

create index t_pay_order_MerchantOrderNo_IDX
    on t_pay_order_hist (MerchantOrderNo);

create index t_pay_order_SuccessTime_IDX
    on t_pay_order_hist (SuccessTime);

create index t_pay_order_SuccessTime_IDX2
    on t_pay_order_hist (SuccessTime, Currency);

create index t_pay_order_hist_MerchantId_IDX
    on t_pay_order_hist (MerchantId, Amount, ActualAmount, Status, CreateTime);

create index t_pay_order_hist_MerchantId_sucess_IDX
    on t_pay_order_hist (MerchantId, AccountId, Amount, Status, SuccessTime);

create index t_pay_order_merchantNotifyStatus_IDX
    on t_pay_order_hist (MerchantNotifyStatus);

create table t_pay_order_hist_MylSAM
(
    PayOrderId            varchar(30)                         not null comment '支付订单号'
        primary key,
    MerchantId            varchar(12)                         not null comment '商户ID',
    MerchantUserId        varchar(255)                        null comment '商戶的用戶ID',
    MerchantOrderNo       varchar(64)                         not null comment '商户方的订单号',
    ChannelId             bigint                              null comment '渠道ID',
    ChannelCode           varchar(30)                         null comment '渠道編碼',
    ChannelType           varchar(30)                         null comment '渠道類型',
    AccountGroupCode      varchar(10)                         null comment '帐号组代码',
    ChannelOrderNo        varchar(64)                         null comment '渠道方的订单号',
    ChannelRemark         mediumtext                          null comment '备注信息',
    AccountId             bigint                              null comment '賬號ID',
    Amount                bigint    default 0                 not null comment '支付金额,单位分',
    ActualAmount          bigint    default 0                 not null comment '實際支付金額，單位分',
    ConfirmedActualAmount bigint    default 0                 not null comment '確認實際支付金額，單位分',
    DiscountAmount        bigint    default 0                 not null comment '優惠金額，單位分',
    Rate                  double    default 0                 not null comment '手续费費率',
    RateFixedAmount       bigint    default 0                 not null comment '手續費，固定金額，單位分',
    Fee                   bigint                              null comment '手續費，單位分',
    Currency              varchar(20)                         null comment '三位货币代码,人民币:cny',
    Status                tinyint   default 0                 not null comment '支付状态, 0=订单生成, 1=未支付, 2=待支付, 3=支付中, 4=支付完成, 5=处理完成, 6=订单关闭（未支付）, 7=订单关闭（待支付）, 8=订单关闭（支付中）',
    NotifyUrl             varchar(128)                        not null comment '通知地址',
    ReturnUrl             varchar(128)                        null comment '同步跳转地址',
    QuitUrl               varchar(128)                        null comment '支付取消返回地址',
    Subject               varchar(100)                        null comment '訂單標題',
    Body                  varchar(100)                        null comment '訂單描述',
    SuccessTime           timestamp                           null comment '订单支付成功时间',
    RateDifferences       mediumtext                          null comment '整個代理結構的費率差列表，JSON格式',
    ClientIp              varchar(300)                        null comment '客户端IP',
    ClientDevice          varchar(64)                         null comment '客戶端设备',
    ClientExtra           mediumtext                          null comment '客戶端发起时额外参数',
    CreateTime            timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    UpdateTime            timestamp                           null comment '更新时间',
    CheckPaidProcess      tinyint                             null comment '指出，去支付寶撈訂單，再將訂單狀態設為PAID，是走哪個流程，0:支付寶, 1:刷新',
    ServiceFee            bigint    default 0                 null comment '服務費',
    IsBelong              int       default 0                 null,
    AccountChannelId      bigint                              null comment '该笔订单使用的t_account_channel.AccountChannelId (新加)',
    ChannelNotifyUrl      varchar(1000)                       null comment '渠道通知网址',
    CatchId               varchar(100)                        null comment '渠道编码',
    OtherParams           varchar(100)                        null comment '额外参数，JSON格式',
    RealName              varchar(100)                        null comment '客戶真實姓名',
    CurrencyRate          decimal(12, 6)                      null comment '汇率',
    USDT                  decimal(18, 6)                      null comment '虚拟币金额',
    ExecuteStatus         tinyint   default 0                 not null comment '下單執行狀態',
    Email                 varchar(50)                         null comment '信箱地址',
    PhoneNum              varchar(50)                         null comment '电话号码',
    PayAccountNum         varchar(50)                         null comment '支付帐号',
    MerchantNotifyStatus  int                                 null comment '通知状态,0-未通知,1-通知中,2-通知成功,3-通知失败',
    LastNotifyTime        timestamp                           null comment '最后一次通知时间'
)
    comment '支付订单表' engine = MyISAM
                         charset = utf8;

create index t_pay_order_hist_MylSAM_MerchantId_CreateTime_Status_index
    on t_pay_order_hist_MylSAM (MerchantId, CreateTime, Status);

create table t_pay_order_p
(
    PayOrderId            varchar(30)                         not null comment '支付订单号'
        primary key,
    MerchantId            varchar(12)                         not null comment '商户ID',
    MerchantUserId        varchar(255)                        null comment '商戶的用戶ID',
    MerchantOrderNo       varchar(64)                         not null comment '商户方的订单号',
    ChannelId             bigint                              null comment '渠道ID',
    ChannelCode           varchar(30)                         null comment '渠道編碼',
    ChannelType           varchar(30)                         null comment '渠道類型',
    AccountGroupCode      varchar(10)                         null comment '帐号组代码',
    ChannelOrderNo        varchar(64)                         null comment '渠道方的订单号',
    ChannelRemark         text                                null comment '备注信息',
    AccountId             bigint                              null comment '賬號ID',
    Amount                bigint    default 0                 not null comment '支付金额,单位分',
    ActualAmount          bigint    default 0                 not null comment '實際支付金額，單位分',
    ConfirmedActualAmount bigint    default 0                 not null comment '確認實際支付金額，單位分',
    DiscountAmount        bigint    default 0                 not null comment '優惠金額，單位分',
    Rate                  double    default 0                 not null comment '手续费費率',
    RateFixedAmount       bigint    default 0                 not null comment '手續費，固定金額，單位分',
    Fee                   bigint                              null comment '手續費，單位分',
    Currency              varchar(20)                         null comment '三位货币代码，人民币:cny',
    Status                tinyint   default 0                 not null comment '支付状态, 0=订单生成, 1=未支付, 2=待支付, 3=支付中, 4=支付完成, 5=处理完成, 6=订单关闭（未支付）, 7=订单关闭（待支付）, 8=订单关闭（支付中）',
    NotifyUrl             varchar(128)                        not null comment '通知地址',
    ReturnUrl             varchar(128)                        null comment '同步跳转地址',
    QuitUrl               varchar(128)                        null comment '支付取消返回地址',
    Subject               varchar(100)                        null comment '訂單標題',
    Body                  varchar(100)                        null comment '訂單描述',
    SuccessTime           timestamp                           null comment '订单支付成功时间',
    RateDifferences       text                                null comment '整個代理結構的費率差列表，JSON格式',
    ClientIp              varchar(300)                        null comment '客户端IP',
    ClientDevice          varchar(64)                         null comment '客戶端设备',
    ClientExtra           text                                null comment '客戶端发起时额外参数',
    CreateTime            timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    UpdateTime            timestamp                           null comment '更新时间',
    CheckPaidProcess      tinyint                             null comment '指出，去支付寶撈訂單，再將訂單狀態設為PAID，是走哪個流程，0:支付寶, 1:刷新',
    ServiceFee            bigint    default 0                 null comment '服務費''',
    IsBelong              int       default 0                 null,
    AccountChannelId      bigint                              null comment '该笔订单使用的t_account_channel.AccountChannelId (新加)',
    ChannelNotifyUrl      varchar(1000)                       null comment '渠道通知网址',
    CatchId               varchar(100)                        null comment '渠道编码',
    OtherParams           char(100)                           null comment '额外参数，JSON格式',
    RealName              varchar(100)                        null comment '客戶真實姓名',
    CurrencyRate          decimal(12, 6)                      null comment '汇率',
    USDT                  decimal(18, 6)                      null comment '虚拟币金额'
)
    comment '支付订单表' engine = InnoDB
                         charset = utf8;

create index t_pay_order_AccountId_IDX
    on t_pay_order_p (AccountId);

create index t_pay_order_ChannelId_IDX
    on t_pay_order_p (ChannelId);

create index t_pay_order_CreateTime_IDX
    on t_pay_order_p (CreateTime);

create index t_pay_order_IDX1
    on t_pay_order_p (Status, CreateTime);

create index t_pay_order_IDX2
    on t_pay_order_p (MerchantId asc, CreateTime desc);

create index t_pay_order_MerchantId_IDX
    on t_pay_order_p (MerchantId);

create index t_pay_order_MerchantOrderNo_IDX
    on t_pay_order_p (MerchantOrderNo);

create index t_pay_order_SuccessTime_IDX
    on t_pay_order_p (SuccessTime);

create table t_pay_page
(
    PayOrderId varchar(45) default ''                not null comment '支付单ID'
        primary key,
    Html       text                                  null comment '支付页面代码',
    Status     int         default 0                 not null comment '状态 0-待支付 1-已支付 2-已过期',
    CreateTime timestamp   default CURRENT_TIMESTAMP not null comment '创建时间',
    UpdateTime timestamp                             null comment '更新時間'
)
    comment '支付页面表' engine = InnoDB
                         charset = utf8;

create table t_risk_report
(
    Id              bigint auto_increment
        primary key,
    MerchantId      varchar(100) not null,
    Type            tinyint      not null comment '0:pay, 1:withdraw',
    AccountId       bigint       not null,
    ChannelId       bigint       not null,
    ReportDateTime  datetime     not null,
    TotalOrderCount bigint       not null,
    TotalAmount     bigint       not null,
    PaidAmount      bigint       not null,
    DoingCount      bigint       not null,
    SuccessCount    bigint       not null,
    CloseCount      bigint       not null,
    FailCount       bigint       not null,
    constraint t_risk_report_mtrca_UNI
        unique (MerchantId, Type, ReportDateTime, ChannelId, AccountId)
)
    engine = InnoDB
    charset = utf8;

create index t_risk_report_channelId_IDX
    on t_risk_report (ChannelId);

create index t_risk_report_merchantId_IDX
    on t_risk_report (MerchantId);

create index t_risk_report_reportDateTime_IDX
    on t_risk_report (ReportDateTime);

create index t_risk_report_type_IDX
    on t_risk_report (Type);

create table t_role
(
    RoleId     bigint auto_increment comment '角色ID'
        primary key,
    RoleName   varchar(30)       null comment '角色名稱',
    Memo       varchar(255)      null comment '備註',
    Status     tinyint default 0 not null comment '状态,1-启用,0-禁用',
    Level      tinyint default 0 not null comment '等級',
    Creator    varchar(30)       null comment '創建人',
    Updater    varchar(30)       null comment '修改人',
    UpdateTime timestamp         null comment '修改時間',
    CreateTime timestamp         null comment '創建時間',
    MerchantId varchar(12)       null,
    FullPath   varchar(1000)     null
)
    comment '角色列表' engine = InnoDB
                       charset = utf8;

create index t_role_Level_IDX
    on t_role (Level);

create index t_role_RoleName_IDX
    on t_role (RoleName);

create table t_security
(
    SecurityId bigint auto_increment comment '名單ID'
        primary key,
    Type       tinyint default 0 not null comment '安全類型, 0:商戶接口(含支付和代付), 1:系統賬戶, 2:代付卡號',
    Status     tinyint default 0 not null comment '狀態, 0:禁用, 1:啟用',
    `Key`      varchar(50)       not null comment 'Key, 商戶接口:IP, 系統賬戶:Username, 代付卡號:CardNo',
    Value      text              null comment 'Value, 商戶接口:empty, 系統賬戶:IP list, 代付卡號:Cardholder name',
    Memo       text              null comment '備註',
    CreateTime timestamp         null comment '創建時間',
    UpdateTime timestamp         null comment '更新時間'
)
    comment '安全黑白名單表' engine = InnoDB
                             charset = utf8;

create index t_security_Key_IDX
    on t_security (`Key`);

create table t_test_t
(
    id         bigint auto_increment
        primary key,
    createdate date         null,
    name       varchar(100) null
)
    engine = InnoDB
    charset = utf8;

create table t_user
(
    UserId            bigint unsigned auto_increment comment '帳戶ID'
        primary key,
    RoleId            bigint            null comment '用户角色',
    Username          varchar(30)       not null comment '登录账户',
    Password          varchar(60)       null comment '登录密码',
    Status            tinyint default 0 not null comment '状态,1-启用,0-禁用',
    Memo              varchar(255)      null comment '備註',
    Level             tinyint           null comment '用戶層級,-3-一般,-2-商戶,-1-管理員,0-總代,1-一代',
    ParentId          bigint            null comment '父帳戶ID',
    FullPath          varchar(1024)     null comment '代理階層路徑，含自己，並且斜線結尾',
    MerchantId        varchar(12)       null comment '商戶ID',
    Nickname          varchar(30)       null comment '账户名称',
    LoginIp           varchar(64)       null comment '最后登录IP',
    LoginTime         timestamp         null comment '最后登录时间',
    FailedLoginCount  bigint  default 0 not null comment '連續登入失敗次數',
    Updater           varchar(30)       null comment '更新人',
    Creator           varchar(30)       null comment '創建人',
    UpdateTime        timestamp         null comment '修改時間',
    CreateTime        timestamp         null comment '創建時間',
    SecretKey         varchar(100)      null comment 'OTP密钥',
    Email             varchar(100)      null,
    Phone             varchar(20)       null,
    IsVerifySecretKey tinyint default 0 null comment '是否通过OTP绑定验证',
    constraint t_user_UN
        unique (UserId, MerchantId)
)
    comment '用户（賬戶）表' engine = InnoDB
                           charset = utf8;

create table t_vendor
(
    VendorId            bigint unsigned auto_increment comment '供应商ID'
        primary key,
    VendorName          varchar(30)                         not null comment '供应商名称',
    ThirdPayUrl         varchar(100)                        not null comment '第三方金流商链接',
    Status              tinyint   default 1                 null comment '状态 0:启用 1:禁用',
    OrderOriginationUrl varchar(100)                        null comment '订单发起URL',
    OrderQueryUrl       varchar(100)                        null comment '订单查询URL',
    BalanceQueryUrl     varchar(100)                        null comment '余额查询URL',
    Creator             varchar(30)                         not null comment '新建人',
    CreateTime          timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '新建时间',
    CreateIp            varchar(100)                        not null comment '新增IP',
    Updater             varchar(30)                         not null comment '更新人',
    UpdateTime          timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    UpdateIp            varchar(100)                        not null comment '更新者IP',
    OurNotifyUrl        varchar(100)                        null comment '我方提供给该金流商的回调接口位址',
    CatchId             varchar(30)                         null comment '為了與withdraw_vendor join',
    VendorExtraParams   json                                null comment '金流商額外參數',
    constraint t_vendor_CatchId_IDX
        unique (CatchId)
)
    comment '供应商表' engine = InnoDB
                       charset = utf8;

create table t_vendor_channel
(
    VendorChannelId  bigint unsigned auto_increment comment '供应商ID'
        primary key,
    VendorId         bigint                              not null comment '供应商表编号',
    ChannelId        bigint                              not null comment '渠道编号',
    ThirdChannelCode varchar(30)                         not null comment '第三方渠道编码',
    Status           tinyint   default 1                 null comment '状态 0:启用 1:禁用',
    Mode             varchar(20)                         null comment 'URL:获取支付连结, AUTO-SUBMIT:产生跳转连结, NO_SUBMIT:自行产生支付连结, SDK:获取SDK参数',
    Creator          varchar(30)                         not null comment '新建人',
    CreateTime       timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '新建时间',
    CreateIp         varchar(100)                        not null comment '新增IP',
    Updater          varchar(30)                         not null comment '更新人',
    UpdateTime       timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    UpdateIp         varchar(100)                        not null comment '更新者IP'
)
    comment '供應商渠道表' engine = InnoDB
                           charset = utf8;

create table t_withdraw_bankcard_black_list
(
    Id         bigint unsigned auto_increment comment 'ID'
        primary key,
    MerchantId varchar(12)                         not null comment '商户ID',
    BankCardNo bigint                              not null comment '代付银行卡卡号',
    Memo       varchar(255)                        null comment '备注',
    Creator    varchar(30)                         not null comment '新建人',
    CreateTime timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '新建时间',
    CreateIp   varchar(100)                        not null comment '新增IP',
    Updater    varchar(30)                         not null comment '更新人',
    UpdateTime timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    UpdateIp   varchar(100)                        not null comment '更新者IP',
    constraint t_withdraw_bankcard_black_list_uindex
        unique (MerchantId, BankCardNo)
)
    comment '商戶設置銀行卡黑名單' engine = InnoDB
                                   charset = utf8;

create table t_withdraw_bankcard_white_list
(
    Id         bigint unsigned auto_increment comment 'ID'
        primary key,
    MerchantId varchar(12)                         not null comment '商户ID',
    BankCardNo bigint                              not null comment '代付银行卡卡号',
    Status     tinyint   default 1                 null comment '状态 0:启用 1:禁用',
    Memo       varchar(255)                        null comment '备注',
    Creator    varchar(30)                         not null comment '新建人',
    CreateTime timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '新建时间',
    CreateIp   varchar(100)                        not null comment '新增IP',
    Updater    varchar(30)                         not null comment '更新人',
    UpdateTime timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    UpdateIp   varchar(100)                        not null comment '更新者IP',
    constraint t_withdraw_bankcard_white_list_uindex
        unique (MerchantId, BankCardNo)
)
    comment '代付银行卡白名单' engine = InnoDB
                               charset = utf8;

create table t_withdraw_channel
(
    AccountId           bigint auto_increment comment '賬號ID'
        primary key,
    AccountName         varchar(30)                  null comment '賬號名稱',
    Currency            varchar(20)  default 'vnd'   null comment '支持幣種(越南：vnd, 印度：inr)',
    CatchId             varchar(30)                  null comment '代付渠道代碼',
    Status              tinyint      default 0       not null comment '状态 0:下架 1:启用 2:达标 3:风控 4:暫時禁用 5:冷却 6:金额冷却',
    ChannelMerchantId   varchar(100)                 null,
    ChannelMerchantName varchar(30)                  null comment '渠道方提供給我方的商戶名稱',
    ChannelExtra        blob                         null comment '渠道額外參數，JSON格式',
    PublicKey           blob                         null comment '代付公鑰',
    PrivateKey          blob                         null comment '代付私鑰',
    LoginUsername       varchar(30)                  null comment '登入賬號',
    LoginPassword       varchar(30)                  null comment '登入密碼',
    MinAmount           bigint       default 0       not null comment '單筆最低金額，單位分',
    MaxAmount           bigint       default 0       not null comment '單筆最高金額，單位分',
    DayMaxAmount        bigint       default 0       not null comment '單日最高金額 - 達標金額，單位分',
    DayMaxCount         bigint       default 0       not null comment '單日最多筆數 - 達標筆數',
    TotalAmount         bigint       default 0       not null comment '累積收款金額，單位分',
    TotalCount          bigint       default 0       not null comment '累積收款筆數',
    TodayAmount         bigint       default 0       not null comment '當日累績收款金額，單位分',
    TodayCount          bigint       default 0       not null comment '當日累積收款筆數',
    Memo                varchar(255)                 null comment '備註',
    CostRate            double       default 0       not null comment '成本，費率',
    CostFixedAmount     bigint       default 0       not null comment '成本，固定金額，單位分',
    Balance             bigint       default 0       not null comment '賬號餘額，單位分',
    OpenStartTime       time                         null comment '渠道開啟時段',
    OpenEndTime         time                         null comment '渠道開啟時段',
    CreateTime          timestamp                    null comment '創建時間',
    UpdateTime          timestamp                    null comment '更新時間',
    Creator             varchar(30)                  null comment '創建人',
    Updater             varchar(30)                  null comment '更新人',
    OpenDays            varchar(13)                  null comment '渠道可用星期(0,1,2,3,4,5,6)',
    CreateIp            varchar(20)                  not null comment '新建者IP',
    UpdateIp            varchar(20)                  not null comment '更新者IP',
    serviceFeeType      tinyint      default 0       not null comment '代付手續費0:从余额扣除手续费1:从出款金额扣除手续费',
    MerchantId          varchar(12)                  null comment '商户号',
    ThirdMerchantSign   text                         null comment '第三方密钥',
    OrderOriginationUrl varchar(100)                 null comment '订单发起URL',
    OrderQueryUrl       varchar(100)                 null comment '订单查询URL',
    BalanceQueryUrl     varchar(100)                 null comment '余额查询URL',
    TraceStatus         tinyint                      null comment '追踪状态',
    ConnStatus          varchar(8)                   null comment '连线状态',
    Weight              int          default 50      not null comment '權重',
    TimeZone            varchar(100) default 'UTC+7' not null,
    TraderGroup         int          default 0       null comment '群组'
)
    comment '代付渠道表' engine = InnoDB
                         charset = utf8;

create index t_withdraw_channel_CatchId_IDX
    on t_withdraw_channel (CatchId);

create table t_withdraw_channel_bank
(
    WithdrawChannelBankId bigint unsigned auto_increment comment '代付银行表ID'
        primary key,
    WithdrawChannelId     bigint                    not null comment '對應到t_withdraw_channel.id',
    BankId                bigint                    null,
    withdrawVendorId      bigint                    not null comment '代付渠道商ID (t_withdraw_vendor.vendorId)',
    ThirdBankCode         varchar(100) charset utf8 null,
    constraint t_withdraw_channel_bank_uindex
        unique (WithdrawChannelId, BankId)
)
    comment '代付渠道銀行表' engine = InnoDB
                             charset = latin1;

create table t_withdraw_channel_bank_code
(
    Id         bigint auto_increment comment 'ID'
        primary key,
    AccountId  bigint        null comment '代付渠道ID',
    BankName   varchar(60)   null comment '銀行名稱',
    BankCode   varchar(30)   null comment '銀行聯行碼',
    Currency   varchar(5)    null comment '支持币种(越南盾：vnd, 印度卢比：inr)',
    Status     tinyint(1)    null comment '狀態',
    Creator    varchar(30)   null,
    CreateTime timestamp     null,
    CreateIp   varchar(100)  null,
    Updater    varchar(30)   null,
    UpdateTime timestamp     null,
    UpdateIp   varchar(100)  null,
    Memo       varchar(1000) null,
    constraint bank_name_unique
        unique (BankName, Currency)
)
    comment '代付渠道方的銀行代碼列表' engine = InnoDB
                                       charset = utf8;

create index t_withdraw_channel_bank_code_AccountId_BankName_IDX
    on t_withdraw_channel_bank_code (AccountId, BankName);

create table t_withdraw_channel_url
(
    Id        bigint auto_increment comment '代付渠道 URL ID'
        primary key,
    AccountId bigint            null comment '代付渠道ID',
    Type      tinyint default 0 not null comment 'URL類別，0:發起訂單URL，1:查定單URL，2:查餘額URL',
    Url       varchar(2048)     null comment '渠道URL'
)
    comment '代付渠道URL表' engine = InnoDB
                            charset = utf8;

create index t_withdraw_channel_url_AccountId_Type_IDX
    on t_withdraw_channel_url (AccountId, Type);

create table t_withdraw_order
(
    WithdrawOrderId        varchar(30)       not null comment '代付訂單號'
        primary key,
    MerchantId             varchar(30)       null comment '商戶ID',
    MerchantOrderNo        varchar(30)       null comment '商戶方的訂單號',
    AccountId              bigint            null comment '渠道ID',
    Status                 tinyint default 0 not null comment '-4;驗證失败,-3;支付失败,-2;提交處理失敗,-1;商户确认失败,0;订单生成,1;商户确认成功,2;待分配渠道,3;提交处理中,4;代付支付中,5;代付支付完成,6;业务处理完成',
    ChannelOrderNo         varchar(100)      null,
    ChannelRate            double  default 0 not null comment '渠道方的手續費',
    ChannelRateFixedAmount bigint  default 0 not null comment '渠道方的手續費，固定金額，單位分',
    ChannelReturnCode      varchar(20)       null comment '渠道方回傳Cod',
    ChannelReturnMessage   text              null comment '渠道方回傳信息',
    Remark                 text              null comment '備註信息',
    PayeeCardNo            varchar(50)       null comment '銀行卡卡號',
    BankName               varchar(60)       null comment '銀行名稱',
    BranchName             varchar(60)       null comment '銀行支行名稱',
    PayeeCardName          varchar(40)       null comment '銀行卡姓名',
    BankProvince           varchar(40)       null comment '銀行所在省',
    BankCity               varchar(40)       null comment '銀行所在市',
    Amount                 bigint  default 0 not null comment '代付金額，單位分',
    ActualAmount           bigint  default 0 not null comment '實際代付金額，單位分',
    DiscountAmount         bigint  default 0 not null comment '優惠金額，單位分',
    Rate                   double  default 0 not null comment '手續費費率',
    RateFixedAmount        bigint  default 0 not null comment '手續費固定金額，單位分',
    Fee                    bigint            null comment '手續費，單位分',
    RateDifferences        blob              null comment '整個代理結構的費率差列表，JSON格式',
    Currency               varchar(20)       null comment '三位货币代码，人民币:cny',
    NotifyUrl              varchar(128)      null comment '通知地址',
    SuccessTime            timestamp         null comment '訂單代付成功時間',
    ClientIp               varchar(300)      null comment '客戶端IP',
    ClientDevice           varchar(64)       null comment '客戶端設備',
    ClientExtra            blob              null comment '渠道方要求的額外參數（JSON格式）',
    CheckWithdrawnProcess  tinyint           null comment '指出，將訂單狀態設為WITHDRAWN，是走哪個流程',
    CreateTime             timestamp         null comment '創建時間',
    UpdateTime             timestamp         null comment '更新時間',
    CatchId                varchar(100)      null comment '代付渠道编码',
    MerchantUserId         varchar(255)      null comment '商戶的用戶ID',
    OtherParams            varchar(128)      null comment '每个国家有各自需要的额外参数，以json格式储存',
    MerchantThirdId        varchar(100)      null comment '渠道方提供給我方的商戶ID',
    NotifyStatus           tinyint default 0 null comment '通知消息狀態',
    CurrencyRate           decimal(12, 6)    null comment '汇率',
    USDT                   decimal(18, 6)    null comment '虚拟币金额',
    UserSubmitTime         timestamp         null,
    MerchantNotifyStatus   int               null comment '通知状态,0-未通知,1-通知中,2-通知成功,3-通知失败',
    LastNotifyTime         timestamp         null comment '最后一次通知时间',
    IdNumber               varchar(100)      null comment '证件号',
    PhoneNumber            varchar(30)       null,
    Email                  varchar(100)      null
)
    comment '代付訂單' engine = InnoDB
                       charset = utf8;

create index t_withdraw_orderCreateTime_IDX
    on t_withdraw_order (CreateTime);

create index t_withdraw_order_AccountId_IDX
    on t_withdraw_order (AccountId);

create index t_withdraw_order_IDX1
    on t_withdraw_order (MerchantId, CreateTime, MerchantUserId);

create index t_withdraw_order_MerchantId_IDX
    on t_withdraw_order (MerchantId);

create index t_withdraw_order_MerchantOrderNo_IDX
    on t_withdraw_order (MerchantOrderNo);

create index t_withdraw_order_SuccessTime_IDX
    on t_withdraw_order (SuccessTime);

create index t_withdraw_order_UserSubmitTime_IDX
    on t_withdraw_order (MerchantId, UserSubmitTime);

create index t_withdraw_order_merchantNotifyStatus_IDX
    on t_withdraw_order (MerchantNotifyStatus);

create table t_withdraw_order_hist
(
    WithdrawOrderId        varchar(30) charset utf8mb4  not null comment '代付訂單號'
        primary key,
    MerchantId             varchar(30) charset utf8mb4  null comment '商戶ID',
    MerchantOrderNo        varchar(30) charset utf8mb4  null comment '商戶方的訂單號',
    AccountId              bigint                       null comment '渠道ID',
    Status                 tinyint default 0            not null comment '-4;驗證失败,-3;支付失败,-2;提交處理失敗,-1;商户确认失败,0;订单生成,1;商户确认成功,2;待分配渠道,3;提交处理中,4;代付支付中,5;代付支付完成,6;业务处理完成',
    ChannelOrderNo         varchar(100) charset utf8mb4 null,
    ChannelRate            double  default 0            not null comment '渠道方的手續費',
    ChannelRateFixedAmount bigint  default 0            not null comment '渠道方的手續費，固定金額，單位分',
    ChannelReturnCode      varchar(20) charset utf8     null comment '渠道方回傳Cod',
    ChannelReturnMessage   text charset utf8mb4         null comment '渠道方回傳信息',
    Remark                 text charset utf8mb4         null comment '備註信息',
    PayeeCardNo            varchar(50) charset utf8mb4  null comment '銀行卡卡號',
    BankName               varchar(60) charset utf8     null comment '銀行名稱',
    BranchName             varchar(60) charset utf8     null comment '銀行支行名稱',
    PayeeCardName          varchar(40) charset utf8mb4  null comment '銀行卡姓名',
    BankProvince           varchar(40) charset utf8     null comment '銀行所在省',
    BankCity               varchar(40) charset utf8     null comment '銀行所在市',
    Amount                 bigint  default 0            not null comment '代付金額，單位分',
    ActualAmount           bigint  default 0            not null comment '實際代付金額，單位分',
    DiscountAmount         bigint  default 0            not null comment '優惠金額，單位分',
    Rate                   double  default 0            not null comment '手續費費率',
    RateFixedAmount        bigint  default 0            not null comment '手續費固定金額，單位分',
    Fee                    bigint                       null comment '手續費，單位分',
    RateDifferences        blob                         null comment '整個代理結構的費率差列表，JSON格式',
    Currency               varchar(20) charset utf8mb4  null comment '三位货币代码，人民币:cny',
    NotifyUrl              varchar(128) charset utf8mb4 null comment '通知地址',
    SuccessTime            timestamp                    null comment '訂單代付成功時間',
    ClientIp               varchar(300) charset utf8mb4 null comment '客戶端IP',
    ClientDevice           varchar(64) charset utf8mb4  null comment '客戶端設備',
    ClientExtra            blob                         null comment '渠道方要求的額外參數（JSON格式）',
    CheckWithdrawnProcess  tinyint                      null comment '指出，將訂單狀態設為WITHDRAWN，是走哪個流程',
    CreateTime             timestamp                    null comment '創建時間',
    UpdateTime             timestamp                    null comment '更新時間',
    CatchId                varchar(100) charset utf8mb4 null comment '代付渠道编码',
    MerchantUserId         varchar(255) charset utf8mb4 null comment '商戶的用戶ID',
    OtherParams            varchar(128) charset utf8    null comment '每个国家有各自需要的额外参数，以json格式储存',
    MerchantThirdId        varchar(100) charset utf8mb4 null comment '渠道方提供給我方的商戶ID',
    NotifyStatus           tinyint default 0            null comment '通知消息狀態',
    CurrencyRate           decimal(12, 6)               null comment '汇率',
    USDT                   decimal(18, 6)               null comment '虚拟币金额',
    UserSubmitTime         timestamp                    null,
    MerchantNotifyStatus   int                          null comment '通知状态,0-未通知,1-通知中,2-通知成功,3-通知失败',
    LastNotifyTime         timestamp                    null comment '最后一次通知时间',
    IdNumber               varchar(100)                 null,
    PhoneNumber            varchar(30)                  null,
    Eamil                  varchar(100)                 null
)
    comment '代付訂單' engine = InnoDB;

create index t_withdraw_orderCreateTime_IDX
    on t_withdraw_order_hist (CreateTime);

create index t_withdraw_order_AccountId_IDX
    on t_withdraw_order_hist (AccountId);

create index t_withdraw_order_IDX1
    on t_withdraw_order_hist (MerchantId, CreateTime, MerchantUserId);

create index t_withdraw_order_MerchantId_IDX
    on t_withdraw_order_hist (MerchantId);

create index t_withdraw_order_MerchantOrderNo_IDX
    on t_withdraw_order_hist (MerchantOrderNo);

create index t_withdraw_order_SuccessTime_IDX
    on t_withdraw_order_hist (SuccessTime);

create index t_withdraw_order_SuccessTime_IDX_2
    on t_withdraw_order_hist (SuccessTime, Currency);

create index t_withdraw_order_UserSubmitTime_IDX
    on t_withdraw_order_hist (UserSubmitTime);

create index t_withdraw_order_WithdrawOrderId_IDX
    on t_withdraw_order_hist (WithdrawOrderId, CreateTime);

create index t_withdraw_order_hist_UserSubmitTime_IDX_m
    on t_withdraw_order_hist (MerchantId, UserSubmitTime);

create index t_withdraw_order_merchantNotifyStatus_IDX
    on t_withdraw_order_hist (MerchantNotifyStatus);

create table t_withdraw_order_http_log
(
    Id              bigint auto_increment comment 'ID'
        primary key,
    OrderId         varchar(30)   default '' not null comment '订单编号',
    Type            tinyint       default 0  not null comment '0:支付，1:代付',
    AccountId       bigint        default 0  not null comment '支付或代付渠道编号',
    SubmitRequest   varchar(2000) default '' not null comment '下单请求',
    SubmitResponse  varchar(2000)            null comment '下单响应',
    SubmitTime      datetime                 null comment '下单时间',
    QueryRequest    varchar(2000)            null comment '查单请求',
    QueryResponse   varchar(2000)            null comment '查单响应',
    QueryTime       datetime                 null comment '最新查单时间',
    CallbackRequest varchar(2000)            null comment '回调接收内容',
    CallbackTime    datetime                 null comment '回调接收时间',
    constraint _order_unique
        unique (OrderId, Type)
)
    engine = InnoDB
    collate = utf8mb4_general_ci;

create index _orderid_type
    on t_withdraw_order_http_log (OrderId, Type);

create index _request_time
    on t_withdraw_order_http_log (SubmitTime);

create index idx_t_order_http_log_01
    on t_withdraw_order_http_log (SubmitTime);

create table t_withdraw_order_http_log_month
(
    Id              bigint auto_increment comment 'ID',
    OrderId         varchar(30)   default '' not null comment '订单编号',
    Type            tinyint       default 0  not null comment '0:支付，1:代付',
    AccountId       bigint        default 0  not null comment '支付或代付渠道编号',
    SubmitRequest   varchar(2000) default '' not null comment '下单请求',
    SubmitResponse  varchar(2000)            null comment '下单响应',
    SubmitTime      datetime                 not null comment '下单时间',
    QueryRequest    varchar(2000)            null comment '查单请求',
    QueryResponse   varchar(2000)            null comment '查单响应',
    QueryTime       datetime                 null comment '最新查单时间',
    CallbackRequest varchar(2000)            null comment '回调接收内容',
    CallbackTime    datetime                 null comment '回调接收时间',
    OrderIdCrc32    int unsigned as (crc32(`OrderId`)) stored,
    primary key (Id, OrderIdCrc32, SubmitTime)
)
    engine = InnoDB
    collate = utf8mb4_general_ci partition by list (month(`SubmitTime`)) subpartition by hash (`OrderIdCrc32`) subpartitions 5 (
    partition p01 values in (1),
    partition p02 values in (2),
    partition p03 values in (3),
    partition p04 values in (4),
    partition p05 values in (5),
    partition p06 values in (6),
    partition p07 values in (7),
    partition p08 values in (8),
    partition p09 values in (9),
    partition p10 values in (10),
    partition p11 values in (11),
    partition p12 values in (12)
    );

create index _crc32
    on t_withdraw_order_http_log_month (OrderIdCrc32);

create index _order_unique
    on t_withdraw_order_http_log_month (OrderId, Type);

create index _submit_time
    on t_withdraw_order_http_log_month (SubmitTime);

create table t_withdraw_vendor
(
    VendorId            bigint unsigned auto_increment comment '代付供应商ID'
        primary key,
    VendorName          varchar(30)                         not null comment '代付供应商名称',
    Currency            varchar(50)                         null comment '支持币种（越南盾：vnd，印度卢比：inr，巴西里奧 : brl，支持多个用逗号串接）',
    Status              tinyint   default 1                 null comment '状态 0:启用 1:禁用',
    OrderOriginationUrl varchar(100)                        null comment '订单发起URL',
    OrderQueryUrl       varchar(100)                        null comment '订单查询URL',
    BalanceQueryUrl     varchar(100)                        null comment '余额查询URL',
    Creator             varchar(30)                         not null comment '新建人',
    CreateTime          time                                null comment '新建时间',
    CreateIp            varchar(100)                        not null comment '新增IP',
    Updater             varchar(30)                         not null comment '更新人',
    UpdateTime          timestamp default CURRENT_TIMESTAMP not null comment '更新时间',
    UpdateIp            varchar(100)                        not null comment '更新者IP',
    CatchId             varchar(30)                         not null comment '代付渠道编码',
    OurNotifyUrl        varchar(100)                        null comment '我方提供给该金流商的回调接口位址',
    ChannelId           bigint                              not null,
    IsSpecial           tinyint   default 0                 null comment '是否回調特殊案例',
    VendorExtraParams   json                                null comment '金流商額外參數',
    constraint t_withdraw_vendor_CatchId_uindex
        unique (CatchId)
)
    comment '代付渠道供应商表' engine = InnoDB
                               charset = utf8;

create table xxl_job_group
(
    id           int auto_increment
        primary key,
    app_name     varchar(64)       not null comment '执行器AppName',
    title        varchar(12)       not null comment '执行器名称',
    address_type tinyint default 0 not null comment '执行器地址类型：0=自动注册、1=手动录入',
    address_list text              null comment '执行器地址列表，多地址逗号分隔',
    update_time  datetime          null
)
    engine = InnoDB
    charset = utf8mb4;

create table xxl_job_info
(
    id                        int auto_increment
        primary key,
    job_group                 int                              not null comment '执行器主键ID',
    job_desc                  varchar(255)                     not null,
    add_time                  datetime                         null,
    update_time               datetime                         null,
    author                    varchar(64)                      null comment '作者',
    alarm_email               varchar(255)                     null comment '报警邮件',
    schedule_type             varchar(50) default 'NONE'       not null comment '调度类型',
    schedule_conf             varchar(128)                     null comment '调度配置，值含义取决于调度类型',
    misfire_strategy          varchar(50) default 'DO_NOTHING' not null comment '调度过期策略',
    executor_route_strategy   varchar(50)                      null comment '执行器路由策略',
    executor_handler          varchar(255)                     null comment '执行器任务handler',
    executor_param            varchar(512)                     null comment '执行器任务参数',
    executor_block_strategy   varchar(50)                      null comment '阻塞处理策略',
    executor_timeout          int         default 0            not null comment '任务执行超时时间，单位秒',
    executor_fail_retry_count int         default 0            not null comment '失败重试次数',
    glue_type                 varchar(50)                      not null comment 'GLUE类型',
    glue_source               mediumtext                       null comment 'GLUE源代码',
    glue_remark               varchar(128)                     null comment 'GLUE备注',
    glue_updatetime           datetime                         null comment 'GLUE更新时间',
    child_jobid               varchar(255)                     null comment '子任务ID，多个逗号分隔',
    trigger_status            tinyint     default 0            not null comment '调度状态：0-停止，1-运行',
    trigger_last_time         bigint      default 0            not null comment '上次调度时间',
    trigger_next_time         bigint      default 0            not null comment '下次调度时间'
)
    engine = InnoDB
    charset = utf8mb4;

create table xxl_job_lock
(
    lock_name varchar(50) not null comment '锁名称'
        primary key
)
    engine = InnoDB
    charset = utf8mb4;

create table xxl_job_log
(
    id                        bigint auto_increment
        primary key,
    job_group                 int               not null comment '执行器主键ID',
    job_id                    int               not null comment '任务，主键ID',
    executor_address          varchar(255)      null comment '执行器地址，本次执行的地址',
    executor_handler          varchar(255)      null comment '执行器任务handler',
    executor_param            varchar(512)      null comment '执行器任务参数',
    executor_sharding_param   varchar(20)       null comment '执行器任务分片参数，格式如 1/2',
    executor_fail_retry_count int     default 0 not null comment '失败重试次数',
    trigger_time              datetime          null comment '调度-时间',
    trigger_code              int               not null comment '调度-结果',
    trigger_msg               text              null comment '调度-日志',
    handle_time               datetime          null comment '执行-时间',
    handle_code               int               not null comment '执行-状态',
    handle_msg                text              null comment '执行-日志',
    alarm_status              tinyint default 0 not null comment '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败'
)
    engine = InnoDB
    charset = utf8mb4;

create index I_handle_code
    on xxl_job_log (handle_code);

create index I_job_id
    on xxl_job_log (job_id);

create index I_jobid_jobgroup
    on xxl_job_log (job_id, job_group);

create index I_trigger_time
    on xxl_job_log (trigger_time);

create table xxl_job_log_report
(
    id            int auto_increment
        primary key,
    trigger_day   datetime      null comment '调度-时间',
    running_count int default 0 not null comment '运行中-日志数量',
    suc_count     int default 0 not null comment '执行成功-日志数量',
    fail_count    int default 0 not null comment '执行失败-日志数量',
    update_time   datetime      null,
    constraint i_trigger_day
        unique (trigger_day)
)
    engine = InnoDB
    charset = utf8mb4;

create table xxl_job_logglue
(
    id          int auto_increment
        primary key,
    job_id      int          not null comment '任务，主键ID',
    glue_type   varchar(50)  null comment 'GLUE类型',
    glue_source mediumtext   null comment 'GLUE源代码',
    glue_remark varchar(128) not null comment 'GLUE备注',
    add_time    datetime     null,
    update_time datetime     null
)
    engine = InnoDB
    charset = utf8mb4;

create table xxl_job_registry
(
    id             int auto_increment
        primary key,
    registry_group varchar(50)  not null,
    registry_key   varchar(255) not null,
    registry_value varchar(255) not null,
    update_time    datetime     null,
    constraint i_g_k_v
        unique (registry_group, registry_key, registry_value)
)
    engine = InnoDB
    charset = utf8mb4;

create table xxl_job_user
(
    id         int auto_increment
        primary key,
    username   varchar(50)  not null comment '账号',
    password   varchar(50)  not null comment '密码',
    role       tinyint      not null comment '角色：0-普通用户、1-管理员',
    permission varchar(255) null comment '权限：执行器ID列表，多个逗号分割',
    constraint i_username
        unique (username)
)
    engine = InnoDB
    charset = utf8mb4;

