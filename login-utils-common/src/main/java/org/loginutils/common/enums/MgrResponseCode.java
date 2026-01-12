package org.loginutils.common.enums;

public enum MgrResponseCode {
    // 成功
    //翻译 中:刷新成功 英:Refresh successfully
    SUCCESS("0000", "Refresh successfully"),

    //翻译 中:无效Token 英:Invalid token
    INVALID_TOKEN("0001", "Invalid token"),
    //翻译 中:验证码错误 英:Verification code error
    INVALID_CAPTCHA("0002", "Verification code error"),

    //翻译 中:参数不存在 英:Parameter does not exist
    PARAM_NOT_FOUND("0101", "Parameter does not exist"),
    //翻译 中:无效的参数 英:Invalid parameter
    PARAM_INVALID("0102", "Invalid parameter"),
    //翻译 中:参数不可留白 英:Parameters cannot be left blank
    PARAM_EMPTY("0103", "Parameters cannot be left blank"),

    //翻译 中:数据库操作失败 英:Database operation failed
    DB_FAIL("0201", "Database operation failed"),
    //翻译 中:数据库存在相同主鍵資料 英:The database has the same primary key data
    DB_DUPLICATE_ENTRY("0202", "The database has the same primary key data"),


    //翻译 中:账户不存在 英:Account does not exist
    USER_NOT_FOUND("0301", "Account does not exist"),
    //翻译 中:账户已禁用 英:Account disabled
    USER_DISABLED("0302", "Account disabled"),
    //翻译 中:密码错误 英:Wrong password
    USER_PASSWORD_INVALID("0303", "Wrong password"),
    //翻译 中:账户没有权限 英:Account does not have permission
    USER_NO_PERMISSION("0304", "Account does not have permission"),
    //翻译 中:账户不是代理 英:Account is not a agent
    USER_NOT_AGENT("0305", "Account is not a agent"),
    //翻译 中:账户已存在 英:Account already exists
    USER_ALREADY_EXIST("0306", "Account already exists"),
    //翻译 中:父账户不存在 英:Parent account does not exist
    USER_PARENT_NOT_FOUND("0307", "Parent account does not exist"),
    //翻译 中:账户为必填 英:Account is required
    USER_IS_REQUIRED("0308", "Account is required"),
    //翻译 中:此账号已绑定过OTP 英:This account has been bound to OTP
    USER_BINDING_OTP("0309", "This account has been bound to OTP"),
    //翻译 中:此账号未绑定OTP 英:This account is not bound to OTP
    USER_NOT_BINDING_OTP("0310", "This account is not bound to OTP"),
    //翻译 中:OTP验证码错误 英:OTP verification code error
    USER_VERIFY_OTP_FAILURE("0311", "OTP verification code error"),
    //翻译 中:此账号已绑定OTP，请输入OTP验证码登录 英:This account has been bound to OTP, please enter the OTP verification code to log in
    USER_TOTP_IS_REQUIRED("0312", "This account has been bound to OTP, please enter the OTP verification code to log in"),

    //翻译 中:角色不存在 英:Role does not exist
    ROLE_NOT_FOUND("0401", "Role does not exist"),
    //翻译 中:角色权限为空 英:Role permissions are empty
    ROLE_LIST_IS_EMPTY("0402", "Role permissions are empty"),
    //翻译 中:角色名称已存在 英:Role name already exists
    ROLE_NAME_IS_EXIST("0403", "Role name already exists"),

    //翻译 中:商户不存在 英:Merchant does not exist
    MERCHANT_NOT_FOUND("0501", "Merchant does not exist"),
    //翻译 中:商户余额不足或是商户不存在 英:The merchant balance is insufficient or the merchant does not exist
    MERCHANT_NOT_ENOUGH_BALANCE("0502", "The merchant balance is insufficient or the merchant does not exist"),
    //翻译 中:商户的代付设定不存在 英:The merchant’s withdraw setting does not exist
    MERCHANT_WITHDRAW_SETTING_NOT_FOUND("0503", "The merchant’s withdraw setting does not exist"),
    //翻译 中:商户冻结金额不足或是商户不存在 英:The merchant’s frozen amount is insufficient or the merchant does not exist
    MERCHANT_NOT_ENOUGH_FROZEN_AMOUNT("0504", "The merchant’s frozen amount is insufficient or the merchant does not exist"),
    //翻译 中:代理/商户没有权限 英:The agent/merchant does not have permission
    MERCHANT_NO_PERMISSION("0505", "The agent/merchant does not have permission"),
    //翻译 中:商户的用户不存在 英:The user of the merchant does not exist
    MERCHANT_USER_NOT_FOUND("0506", "The user of the merchant does not exist"),
    //翻译 中:此商户名称已存在 英:This merchant name already exists
    MERCHANT_NAME_EXISTD("0507", "This merchant name already exists"),
    //翻译 中:此商户ID已存在 英:This merchant ID already exists
    MERCHANT_ID_EXISTD("0508", "This merchant ID already exists"),
    //翻译 中:预设配置给商户的密钥设定不存在 英:The default key setting configured for the merchant does not exist
    MERCHANT_DEFAULT_REQUESTKEY_NOT_EXISTD("0509", "The default key setting configured for the merchant does not exist"),
    //翻译 中:商户已停用 英:Merchant is disabled
    MERCHANT_IS_DISABLED("0510", "Merchant is disabled"),

    //翻译 中:渠道不存在 英:Channel does not exist
    CHANNEL_NOT_FOUND("0601", "Channel does not exist"),
    //翻译 中:此支付编码已存在 英:This channel code already exists
    CHANNEL_CODE_EXIST("0602", "This channel code already exists"),
    //翻译 中:此渠道名称已存在 英:This channel name already exists
    CHANNEL_NAME_EXIST("0603", "This channel name already exists"),
    //翻译 中:此渠道權重必須在1~100 英:The weight of this channel must be between 1 and 100
    CHANNEL_WEIGHT_ERROR("0604", "The weight of this channel must be between 1 and 100"),


    //翻译 中:结算周期不存在 英:Settlement cycle does not exist
    SETTLEMENT_NOT_FOUND("0701", "Settlement cycle does not exist"),
    //翻译 中:结算周期个数不正确 英:
    SETTLEMENT_INVALID_COUNT("0702", "Incorrect number of settlement cycle"),
    //翻译 中:结算周期百分比和必须是100% 英:
    SETTLEMENT_INVALID_SUM("0703", "The sum of the settlement cycle percentage must be 100%"),
    //翻译 中:结算的单笔最高金额不可以大于单日限额 英:The maximum single settlement amount cannot be greater than the single-day max amount
    SETTLEMENT_MAX_AMOUNT_LARGER_THEN_DAY_MAX_AMOUNT("0704", "The maximum single settlement amount cannot be greater than day max amount"),
    //翻译 中:结算的单笔最低金额不可以大于单笔最高金额 英:The minimum single settlement amount cannot be greater than the single maximum amount
    SETTLEMENT_MIN_AMOUNT_LARGER_THEN_MAX_AMOUNT("0705", "The minimum single settlement amount cannot be greater than the single maximum amount"),

    //翻译 中:账号不存在 英:Account does not exist
    ACCOUNT_NOT_FOUND("0801", "Account does not exist"),
    //翻译 中:账号组不存在 英:Account group does not exist
    ACCOUNT_GROUP_NOT_FOUND("0802", "Account group does not exist"),
    //翻译 中:账号适用金额不正确 英:Incorrect applicable amount of account
    ACCOUNT_INVALID_APPLICABLE_AMOUNT("0803", "Incorrect applicable amount of account"),
    //翻译 中:账号小工具编号已存在 英:Account catch id already exists
    ACCOUNT_CATCH_ID_ALREADY_EXIST("0804", "Account catch id already exists"),
    //翻译 中:测单失败 英:Test order failed
    ACCOUNT_TEST_FAILED("0805", "Test order failed"),
    //翻译 中:登陆账号失败 英:Login account failed
    ACCOUNT_LOGIN_FAILED("0806", "Login account failed"),
    //翻译 中:账号未登陆 英:Account is not logged in
    ACCOUNT_NOT_LOGGED_IN("0807", "Account is not logged in"),
    //翻译 中:账号查询订单失败 英:Account query order failed
    ACCOUNT_QUERY_FAILED("0808", "Account query order failed"),
    //翻译 中:帐号组代码已经存在 英:Account group code already exists
    ACCOUNT_GROUP_CODE_DUPLICATED("0809", "Account group code already exists"),
    //翻译 中:此账号名已存在 英:This account name already exists
    ACCOUNT_ACCOUNT_NAME_DUPLICATED("0810","This account name already exists"),
    //翻译 中:单笔固定金额需介于单笔限额之间 英:The fixed amount of a single order must be between the limit amount of a single order
    ACCOUNT_CHANNEL_FIXED_AMOUNT_INVALID("0811","The fixed amount of a single order must be between the limit amount of a single order "),

    //翻译 中:支付订单不存在 英:Payment order does not exist
    PAY_ORDER_NOT_FOUND("0901", "Payment order does not exist"),
    //翻译 中:订单刷新失败 英:Order refresh failed
    PAY_ORDER_PULL_FAILED("0902", "Order refresh failed"),
    //翻译 中:尚有报表导出申请上未完成 英:There are still unfinished report export applications
    PAY_ORDER_CSV_REPORT_EXIST("0903", "There are still unfinished report export applications"),
    PAY_ORDER_CSV_REPORT_HAD_SOME_PROBLEM("0904", "There were some errors exporting the task, please try again later"),

    //翻译 中:资金下发订单不存在 英:Funds to coffer order does not exist
    FOUND_TO_COFFER_ORDER_NOT_FOUND("1001", "Funds to coffer order does not exist"),
    //翻译 中:资金下发订单状态已完结 英:The fund to coffer order status done
    FOUND_TO_COFFER_ORDER_DONE("1002", "The fund to coffer order status done"),
    //翻译 中:金额不正确 英:Incorrect amount
    FOUND_TO_COFFER_INVALID_AMOUNT("1003", "Incorrect amount"),
    //翻译 中:订单状态错误 英:Order status error
    FOUND_TO_ORDER_INVALID_STATUS("1004","Order status error"),

    //翻译 中:资金结算订单不存在 英:Fund settlement order does not exist
    FOUND_TO_MERCHANT_ORDER_NOT_FOUND("1101", "Fund settlement order does not exist"),
    //翻译 中:资金结算订单状态已完结 英:Fund settlement order status done
    FOUND_TO_MERCHANT_ORDER_DONE("1102", "Fund settlement order status done"),
    //翻译 中:结算金额大于商户余额 英:The settlement amount is greater than the merchant balance
    FOUND_TO_MERCHANT_ORDER_AMOUNT_LARGER_THAN_BALANCE("1103", "The settlement amount is greater than the merchant balance"),
    //翻译 中:结算手续费不正确 英:Incorrect settlement fee
    FOUND_TO_MERCHANT_ORDER_INVALID_FEE("1104", "Incorrect settlement fee"),

    //翻译 中:代付渠道不存在 英:No withdraw channel exists
    WITHDRAW_CHANNEL_NOT_FOUND("1201", "No withdraw channel exists"),
    //翻译 中:代付渠道URL设定不存在 英:The withdraw channel URL setting does not exist
    WITHDRAW_CHANNEL_URL_NOT_FOUND("1202", "The withdraw channel URL setting does not exist"),
    //翻译 中:代付订单不存在 英:The withdraw order does not exist
    WITHDRAW_ORDER_NOT_FOUND("0103", "The withdraw order does not exist"),
    //翻译 中:此代付银行名称已存在 英:This withdraw bank name already exists
    WITHDRAW_CHANNEL_BANKCODE_DUPLICATED("1203","This withdraw bank name already exists"),
    //翻译 中:代付渠道编码参数不可为空 英:Catch id of withdraw channel cannot be empty
    WITHDRAW_CHANNEL_CATCH_ID_PARAM_EMPTY("1204", "Catch id of withdraw channel cannot be empty"),
    //翻译 中:订单状态查询参数错误 英:Parameter error for query order status
    WITHDRAW_QRY_ORDER_INVALID_STATUS("1205","Parameter error for query order status"),
    //翻译 中:不开放非商户角色新增 英:Not open non-merchant roles to add
    WITHDRAW_BANKCARD_WHITE_ADD("12051", "Not open non-merchant roles to add"),
    //翻译 中:不开放非商户角色删除 英:Not open to delete non-merchant roles
    WITHDRAW_BANKCARD_WHITE_DELETE("12052", "Not open to delete non-merchant roles"),
    //翻译 中:此账号名已存在 英:This account name already exists
    WITHDRAW_ACCOUNT_NAME_DUPLICATED("12053","This account name already exists"),
    //翻译 中:上游不提供查询余额接口 英:This vendor not support query balance
    WITHDRAW_CHANNEL_NOT_SUPPORT_QUERY_BALANCE("12054","This vendor not support query balance"),
    //翻译 中:上游提供查询余额接口，请输入查询余额URL 英:This vendor support query balance,please enter URL
    WITHDRAW_CHANNEL_SUPPORT_QUERY_BALANCE("12055", "This vendor support query balance,please enter URL"),
    //翻译 中:请输入支持币种，请输入查询余额URL 英:Please enter the supported currency
    WITHDRAW_CHANNEL_CURRENCY_NOT_FOUND("12056", "Please enter the supported currency"),

    //翻译 中:代理不可新增代理 英:Agents cannot add agents
    AGENT_CANNOT_ADD_AGENT("1301", "Agents cannot add agents"),

    //翻译 中:银行卡不存在 英:Bank card does not exist
    BANK_CARD_NOT_FOUND("1401", "Bank card does not exist"),
    //翻译 中:银行卡已存在 英:Bank card already exists
    BANK_CARD_EXIST("1402", "Bank card already exists"),

    //翻译 中:人工账变订单不存在 英:Manual charge order does not exist
    MERCHANT_CHARGE_ORDER_NOT_FOUND("1501", "Manual charge order does not exist"),
    //翻译 中:人工账变订单金额不可为0 英:Manual charge order amount cannot be 0
    MERCHANT_CHARGE_ORDER_AMOUNT_ZERO("1502", "Manual charge order amount cannot be 0"),
    //翻译 中:人工账变订单状态已完结 英:Manual charge order status has been completed
    MERCHANT_CHARGE_ORDER_DONE("1503", "Manual charge order status has been completed"),

    //翻译 中:安全设定不存在 英:Security setting does not exist
    SECURITY_NOT_FOUND("1601", "Security setting does not exist"),
    //翻译 中:安全设定已存在 英:Security settings already exist
    SECURITY_ALREADY_EXIST("1602", "Security settings already exist"),

    //翻译 中:商户通知订单不存在 英:Merchant notify that the order does not exist
    MERCHANT_NOTIFY_NOT_FOUND("1701", "Merchant notify that the order does not exist"),
    //翻译 中:商户通知订单还没被发送过 英:
    MERCHANT_NOTIFY_HAS_NOT_BEEN_SENT_YET("1702", "The merchant notify that the order has not been sent"),
    //翻译 中:商户通知已成功 英:Merchant notify success
    MERCHANT_NOTIFY_SUCCESS("1703", "Merchant notify success"),

    //翻译 中:全域参数不存在 英:Global parameter does not exist
    GLOBAL_PARAM_KEY_NOT_FOUND("1801", "Global parameter does not exist"),

    //翻译 中:禁用IP不存在 英:Disable IP does not exist
    BANNED_IP_NOT_FOUND("1901", "Disable IP does not exist"),

    //翻译 中:支付宝转帐失败 英:Alipay transfer failed
    ALIPAY_TRANSFER_FAILED("2001", "Alipay transfer failed"),

    //翻译 中:目前时段不可重制报表 英:The report cannot be reproduced in the current period
    REPORT_REDO_INVALID_PERIOD("2101", "The report cannot be reproduced in the current period"),
    //翻译 中:本报表不开放管理者查询, 请至商户报表查询 英:This report is not open to managers for inquiries, please go to the merchant report for inquiries
    REPORT_MERCHANT_PRIV("2102", "This report is not open to managers for inquiries, please go to the merchant report for inquiries"),

    //翻译 中:此商户已设定过此HOST/IP 英:This merchant has already set this HOST/IP
    BLACKWHITE_REPEAT("2202", "This merchant has already set this HOST/IP"),

    //翻译 中:查询时间为必填 英:Query time is required
    ACCOUNT_QUOTA_TIME_PARAM_NOT_FOUND("2301","Query time is required"),

    //翻译 中:代付金流商不存在 英:No withdraw vendor exists
    WITHDRAW_VENDOR_NOT_FOUND("2401", "No withdraw vendor exists"),

    //翻译 中:代付銀行代碼不存在 英:No bank code exists
    BANK_CODE_NOT_FOUND("2501", "No bank code exists"),

    //翻译 中:代付銀行代碼重复 英:Bank code has repeat
    BANK_CODE_REPEAT("2502", "Bank code has repeat"),
    EXCHANGE_RATE_NOT_EXISTS("3001", "exchange rate does not exist"),

    // 未知错误
    //翻译 中:功能尚未实作 英:Function not yet implemented
    NOT_IMPLEMENTED("9998", "Function not yet implemented"),
    //翻译 中:未知错误 英:Unknown error
    UNKNOWN_ERROR("9999", "Unknown error");

    private String code;
    private String message;

    MgrResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static MgrResponseCode fromCode(String code) {
        if (code == null) {
            return null;
        }

        MgrResponseCode[] values = MgrResponseCode.values();
        for (MgrResponseCode e : values) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }

        return UNKNOWN_ERROR;
    }
}