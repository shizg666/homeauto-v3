package com.landleaf.homeauto.common.constant.enums;

/**
 * 智齿客服平台错误码定义
 *
 * @author wenyilu
 */
public enum SobotErrorCodeEnumConst {

    SUCCESS_CODE("000000", "操作成功（除此编码以外的编码为错误编码）"),
    ERROR_CODE_TOKEN_EMPTY("900001", "token为空"),
    ERROR_CODE_TOKEN_EXPIRE("900002", "token已失效，请重新获取"),
    ERROR_CODE_SIGNATURE_ERROR("900003", "signature错误"),
    ERROR_CODE_API_NOT_CONFIG("900004", "没有找到公司的api配置信息"),
    ERROR_CODE_SYSTEM_ERROR("999999", "系统未知异常"),
    ERROR_CODE_CREATE_TIME_EMPTY("400001", "创建时间不能为空"),
    ERROR_CODE_END_TIME_LT_START_TIME("400002", "创建结束时间不能小于创建开始时间"),
    ERROR_CODE_TIME_OVER_MONTH("400003", "查询创建时间段不能超过一个月"),
    ERROR_CODE_PARAM_EMPTY("400004", "请求参数不能为空"),
    ERROR_CODE_TIME_FORMAT_ERROR("400005", "时间格式不正确"),
    ERROR_CODE_AGENT_ID_EMPTY("400006", "操作坐席ID不能为空"),
    ERROR_CODE_AGENT_NAME_ERROR("400007", "操作坐席ID不正确"),
    ERROR_CODE_TYPEID_EMPTY("400008", "工单分类ID不能为空"),
    ERROR_CODE_STATUS_EMPTY("400009", "工单状态不能为空"),
    ERROR_CODE_FROM_EMPTY("400010", "工单来源不能为空"),
    ERROR_CODE_LEVEL_EMPTY("400011", "工单级别不能为空"),
    ERROR_CODE_SKILL_ID_EMPTY("400012", "技能组ID不能为空"),
    ERROR_CODE_AGENT_MATCH_ERROR("400013", "受理客服组与受理客服不匹配"),
    ;


    private String code;

    private String msg;

    SobotErrorCodeEnumConst(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
