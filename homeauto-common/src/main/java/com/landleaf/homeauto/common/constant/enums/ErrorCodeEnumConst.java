package com.landleaf.homeauto.common.constant.enums;

/**
 * 错误码定义
 *
 * @author wenyilu
 */
public enum ErrorCodeEnumConst {

    /**
     * 未捕获类异常
     */
    ERROR_CODE_ALREADY_EXISTS(3000, "信息已存在"),

    /**
     * 提示信息
     */
    ERROR_CODE_PROMPT_MSG(10000, "替换提示信息"),

    /*
     * 查验参数错误
     */
    CHECK_PARAM_ERROR(-1, "查验参数错误"),

    /*
     * 查验参数错误
     */
    CHECK_DATA_EXIST(-2, "信息不存在"),

    /**
     * Token不能为空
     */
    TOKEN_NOT_BLAND(4003, "未登录"),
    /**
     * 身份验证失败
     */
    AUTHENTICATION_FAILED(4003, "身份验证失败"),
    /**
     * 登录已过期
     */
    LOGIN_EXPIRED(4003, "登录已过期"),
    /**
     * 没有访问权限
     */
    NO_ACCESS(4003, "没有访问权限"),
    /**
     * 需要授权后才能访问
     */
    AUTHENTICATION_REQUIRED(4003, "Full authentication is required to access this resource"),
    /**
     * Token不存在
     */
    TOKEN_NOT_FOUND(4003, "Token不存在"),

    /**
     * Token解析异常
     */
    TOKEN_RESOLVE_ERROR(4003, "Token解析异常"),
    /**
     * 未传token凭据
     */
    AUTHENTICATION_TOKEN_REQUIRED(4003, "An Authentication object was not found in the SecurityContext"),
    /**
     * openId未绑定用户
     */
    OPENID_UNBIND_CUSOTER(4005, "请先绑定家庭"),

    /**
     * 您没有访问权限
     */
    NO_ACCESS_ERROR(4004, "您没有访问权限"),
    /**
     * 您没有访问权限
     */
    SYSTEM_BUSY_ERROR(4006, "系统繁忙请稍后再试"),
    /**
     * 客户销毁异常
     */
    CUSTOMER_DESTROY_ERROR(4007, "客户销毁异常"),


    /********************************用户管理对应错误编码********************************************************/
    /**
     * 用户不存在
     */
    USER_LOGIN_BAD_REQUEST(4100, "用户名/密码必填"),
    /**
     * 用户不存在
     */
    USER_NOT_FOUND(4101, "用户不存在"),
    /**
     * 用户信息获取异常
     */
    USER_INFO_GET_ERROR(4102, "用户信息获取异常"),
    /**
     * 原密码输入错误
     */
    PASSWORD_OLD_INPUT_ERROE(4103, "原密码输入错误"),
    /**
     * 账号已存在
     */
    EMAIL_EXIST_ERROE(4104, "账号已存在"),
    /**
     * 手机号已存在
     */
    MOBILE_EXIST_ERROE(4105, "手机号已存在"),
    /**
     * 用户已停用
     */
    USER_INACTIVE_ERROE(4106, "用户已停用"),
    /**
     * 手机号已存在
     */
    PASSWORD_INPUT_ERROE(4107, "密码错误"),
    /**
     * 手机号已被绑定
     */
    PHONE_HAS_BIND(4108, "手机号已被绑定"),
    /**
     * 手机号码为空
     */
    PHONE_EMPTY_ERROR(4111, "手机号码为空"),
    /**
     * 密码为空
     */
    PASSWORD_EMPTY_ERROR(4112, "密码为空"),
    /**
     * 请求方法只能为POST
     */
    METHOD_NOT_POST_ERROR(4113, "请求方法只能为POST"),
    /**
     * Authentication can not cast of AppAuthenticationToken
     */
    AUTHENTICATION_CAST_ERROR(4114, "Authentication can not cast of AppAuthenticationToken"),
    /**
     * 两次密码输入不一致
     */
    CUSTOMER_PASSWORD_TWICE_INPUT_DIFFER(4019, "两次密码输入不一致"),
    /**
     * 头像上传失败
     */
    AVATAR_UPLOAD_ERROR(4020, "头像上传失败"),
    /**
     * 无任何菜单权限，请通知管理员开放
     */
    NO_ANY_MANU_PERMISSION_ERROR(4021, "无任何菜单权限，请通知管理员开放"),
    /**
     * 用户名或密码错误
     */
    USER_NAME_PASSWORD_ERROR(4022, "用户名或密码错误"),
    /**
     * 登录异常
     */
    USER_LOGIN_ERROR(4023, "登录异常"),
    /********************************角色管理对应错误编码********************************************************/

    /**
     * 角色不存在
     */
    ROLE_NOT_EXIST_ERROE(4201, "角色名称已存在"),
    /**
     * 角色名称已存在
     */
    ROLE_EXIST_ERROE(4202, "角色名称已存在"),

    /********************************权限管理对应错误编码********************************************************/

    /**
     * 权限已关联角色,请先解除关联
     */
    PERMISSION_BIND_ROLE_ERROR(4301, "权限已关联角色,请先解除关联"),
    /**
     * 权限已存在
     */
    PERMISSION_EXIST_ERROE(4302, "权限已存在"),

    /**
     * 业务异常
     */
    ERROR_CODE_BUSINESS_EXCEPTION(20001, "业务异常"),
    /**
     * 未捕获类异常
     */
    ERROR_CODE_UNHANDLED_EXCEPTION(90000, "未知异常"),

    ERROR_CODE_EM_MODULE_EXCEPTION(94000, "工程模块异常"),
    /**
     * 文件为空
     */
    FILE_EMPTY_EXCEPTION(5001, "文件为空"),

    //em 工程管理异常---------------------------------------start-----------------------------------------------------
    /**
     * 文件上传失败
     */
    FILE_UPLOAD_FAIL(5002, "文件上传失败"),
    FILE_DOWNLOAD_FAIL(5003, "文件下载失败"),
    /**
     * 地址解析不到
     */
    ERROR_CODE_MAP_UNRESOLVE(60000, "地址信息错误解析不到"),

    /**
     * 调取百度地址API异常
     */
    ERROR_CODE_MAP_BAIDUAPI(60001, "调取百度地址API异常"),

    ERROR_EXPORT_DATA(60002, "信息导出失败"),

    ERROR_IMPORT_DATA(60003, "信息导入失败"),


    /******************************大屏相关****************************************************/
    SCREEN_MAC_EXIST_ERROR(62001, "大屏mac地址已存在"),
    SCREEN_NOT_EXIST_ERROR(62002, "大屏不存在"),
    SCREEN_APK_EXIST_ERROR(63001, "apk版本号已存在"),
    SCREEN_APK_NOT_EXIST_ERROR(63002, "apk不存在"),
    SCREEN_APK_EXIST_UPDATE_ERROR(63003, "应用已有推送记录不能修改"),
    SCREEN_APK_UPDATE_DETAIL_NOT_EXIST_ERROR(63004, "应用推送记录不存在"),
    SCREEN_APK_UPDATE_PROJECT_NOT_FOUND_ERROR(63005, "推送区域下未找到家庭"),


    ERROR_CODE_MC_EXCEPTION(70000, "消息模块基础异常码"),

    ERROR_CODE_MC_JG_SEND_ERROR(71000, "极光信息发送失败"),

    ERROR_CODE_JG_CODE_VERIFY_ERROR(72001, "验证码错误"),

    ERROR_CODE_MC_JG_CODE_NOT_EXPIRE(720002, "验证码还未失效"),


    ERROR_CODE_MC_EMAIL_SEND_ERROR(73001, "邮件发送失败"),

    ERROR_CODE_MC_EMAIL_CODE_NOT_ERROR(73001, "邮箱验证码错误"),

    ERROR_CODE_MC_EMAIL_CODE_EXPIRE(73002, "邮箱验证码已过期"),


    ERROR_CODE_MC_PATH_SCOPE_OPERATION_PERMISSION(74001, "您没有当前数据范围的操作权限"),

    ERROR_CODE_MC_PUSH_ERROR(75000, "极光推送失败"),


    /*
     * 负载根据server-id找不到对应可用服务
     */
    UNKNOW_BALANCER(80001, "无可用的balance"),

    /*
     * resttemplate请求失败
     */
    RESTTEMPLATE_ERROR(80002, "resttemplate请求失败"),
    /*
     * 任务正在被其他程序操作,请稍后重试.
     */
    TASK_OPER_BY_OTHER_PROGRAMS(80010, "任务正在被其他程序操作,请稍后重试."),
    /*
     * 任务已经存在
     */
    TASK_EXISTS(80011, "任务已经存在."),
    /*
     * 任务名称已经存在
     */
    TASK_NAME_EXISTS(80012, "任务名称已经存在."),
    /*
     * 任务插入数据库失败
     */
    TASK_ADD_MYSQL_ERROR(80013, "任务插入数据库失败."),
    /*
     * 任务启动失败
     */
    TASK_START_ERROR(80014, "任务启动失败."),
    /*
     * 任务不存在
     */
    TASK_NOT_EXISTS(80015, "任务不存在."),
    /*
     * 任务无修改项
     */
    TASK_NOTHING_UPDATE(80016, "任务无修改项."),
    /*
     * 修改数据库任务失败
     */
    TASK_UPDATE_MYSQL_ERROR(80017, "修改数据库任务失败."),
    /*
     * 任务重启失败
     */
    TASK_RESTART_ERROR(80018, "任务重启失败."),
    /*
     * 任务已经是禁用状态
     */
    TASK_DISABLE_ALREADY(80019, "任务已经是禁用状态."),
    /*
     *任务已经是启用状态
     */
    TASK_ENABLE_ALREADY(80019, "任务已经是启用状态."),
    /*
     * 任务停止失败
     */
    TASK_STOP_ERROR(80020, "任务停止失败."),
    /*
     * 任务已删除
     */
    TASK_DEL_ALREADY(80021, "任务已删除."),
    /*
     * 删除数据库任务失败
     */
    TASK_DEL_MYSQL_ERROR(80021, "删除数据库任务失败."),
    RMQ_LISTEN_INIT_ERROR(900001, "rmq监听启动失败"),

    /********************************工程管理对应错误编码********************************************************/
    /*
     *
     */
    PROJECT_UNBIND(50010, "用户尚未未绑定工程!"),
    /*
     * 删除家庭组成员失败
     */
    PROJECT_UNAUTHORIZATION(50011, "管理员才可操作!"),
    /*
     * 删除家庭组成员失败
     */
    PROJECT_DELTET_FALSE(50012, "只有管理员存在时才可删除!"),

    /*
     *
     */
    PROJECT_AUTHORIZATION(50013, "已临时授权无法操作!"),

    PROJECT_ID_NOTPRESSENT(50014, "消息头中工程id不存在!"),


    /**
     * 未捕获类异常
     */
    IMPORT_FAMILY_CHECK(50026, "家庭户号已存在"),
/********************************工程管理对应错误编码********************************************************/


    /********************************rokcetMq对应错误编码********************************************************/
    ROCKETMQ_NOT_FOUND_CONSUMESERVICE(100001, "根据topic和tag没有找到对应的消费服务!"),
    ROCKETMQ_HANDLE_RESULT_NULL(100002, "消费方法返回值为空!"),
    ROCKETMQ_CONSUME_FAIL(100003, "消费失败!"),
    ROCKETMQ_TOPIC_EMPTY(100004, "topic为空!"),
    ROCKETMQ_TAG_EMPTY(100005, "tag为空!"),
    ROCKETMQ_MSG_EMPTY(100006, "msg为空!"),
    ROCKETMQ_GROUPNAME_EMPTY(100007, "groupName!"),
    ROCKETMQ_NAMESERVERADDR_EMPTY(100008, "msg为空!"),

    APP_VERSION_ALREADY_PUSH_ERROR(110001, "APP版本已经推送,不可再做此操作!"),
    NETWORK_ERROR(199999, "网络异常,请稍后再试!"),
    MQTT_CLIENT_ERROR(199998, "客户端不在线,请检查!"),
    ;

    private int code;

    private String msg;

    ErrorCodeEnumConst(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
