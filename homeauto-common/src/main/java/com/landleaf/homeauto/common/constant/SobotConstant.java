package com.landleaf.homeauto.common.constant;

/**
 * @ClassName SobotConstant
 * @Description: 智齿平台相关常量
 * @Author wyl
 * @Date 2020/8/15
 * @Version V1.0
 **/
public interface SobotConstant {

    /**
     * 平台api前缀
     */
    String BASE_URL="https://www.sobot.com";

    /**
     * 工单中心-获取token
     */
    String TICKET_GET_TOKEN_URL="/api/get_token";

    /**
     * 工单中心-查询数据字典
     */
    String TICKET_GET_DATA_DICT_URL="/api/ws/5/ticket/get_data_dict";

    /**
     * 工单中心-查询自定义字段定义信息
     */
    String TICKET_QUERY_TICKET_EXTEND_FIELDS_URL="/api/ws/5/ticket/query_ticket_extend_fields";

    /**
     * 工单中心-工单自定义字段（选择型）添加选项信息
     */
    String TICKET_SAVE_OPTION_DATA_VALUE_URL="/api/ws/5/ticket/save_option_data_value";

    /**
     * 工单中心-查询工单分类关联的工单模板
     */
    String TICKET_QUERY_FILEDS_BY_TYPEID_URL="/api/ws/5/ticket/query_fileds_by_typeid";

    /**
     * 工单中心-创建工单（客户）
     */
    String TICKET_SAVE_USER_TICKET_URL="/api/ws/5/ticket/save_user_ticket";

    /**
     * 工单中心-创建工单（坐席）
     */
    String TICKET_SAVE_AGENT_TICKET_URL="/api/ws/5/ticket/save_agent_ticket";

    /**
     * 工单中心-查询工单列表
     */
    String TICKET_QUERY_TICKETS_URL="api/ws/5/ticket/query_tickets";

    /**
     * 工单中心-查询工单详情页
     */
    String TICKET_GET_TICKET_BY_ID_URL="/api/ws/5/ticket/get_ticket_by_id";


    /**
     * 客户中心-获取token
     */
    String CUSTOMER_GET_TOKEN_URL="/api/get_token";
    /**
     * 客户中心-创建客户信息
     */
    String CUSTOMER_SAVE_USER_URL="/api/crm/5/user/save_user";
    /**
     * 客户中心-编辑客户信息
     */
    String CUSTOMER_UPDATE_USER_URL="/api/crm/5/user/update_user";
    /**
     * 客户中心-根据邮箱查询客户信息
     */
    String CUSTOMER_GET_USER_BY_EMAIL_URL="/api/crm/5/user/get_user_by_email";
    /**
     * 客户中心-根据手机号查询客户信息
     */
    String CUSTOMER_GET_USER_BY_TEL_URL="/api/crm/5/user/get_user_by_tel";
    /**
     * 客户中心-根据客户ID查询客户信息
     */
    String CUSTOMER_GET_USER_BY_ID_URL="/api/crm/5/user/get_user_by_id";
    /**
     * 客户中心-查询客户列表
     */
    String CUSTOMER_QUERY_USERS_URL="/api/crm/5/user/query_users";
}
