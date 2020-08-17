package com.landleaf.homeauto.center.device.util;

import com.alibaba.fastjson.TypeReference;
import com.landleaf.homeauto.center.device.bean.properties.homeauto.HomeAutoSobotProperties;
import com.landleaf.homeauto.common.domain.dto.device.sobot.datadic.SobotDataDicResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields.SobotExtendFieldsResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveAgentTicketRequestDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketRequestDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.create.SobotSaveUserTicketResponseItemDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query.SobotQueryTicketDetailResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.query.SobotQueryTicketResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.template.SobotQueryFieldsByTypeIdResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.token.SobotTokenResponseDTO;
import com.landleaf.homeauto.common.web.configuration.restful.RestTemplateClient;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName SobotUtils
 * @Description: 智齿客服平台请求工具类
 * @Author wyl
 * @Date 2020/8/15
 * @Version V1.0
 **/
@Component
public class SobotUtils {


    @Autowired
    private RestTemplateClient restTemplateClient;
    /**
     * 平台api前缀
     */
    static final String BASE_URL = "https://www.sobot.com";

    /**
     * 工单中心-获取token
     */
    static final String TICKET_GET_TOKEN_URL = "/api/get_token";

    /**
     * 工单中心-查询数据字典
     */
    static final String TICKET_GET_DATA_DICT_URL = "/api/ws/5/ticket/get_data_dict";

    /**
     * 工单中心-查询自定义字段定义信息
     */
    static final String TICKET_QUERY_TICKET_EXTEND_FIELDS_URL = "/api/ws/5/ticket/query_ticket_extend_fields";

    /**
     * 工单中心-工单自定义字段（选择型）添加选项信息
     */
    static final String TICKET_SAVE_OPTION_DATA_VALUE_URL = "/api/ws/5/ticket/save_option_data_value";

    /**
     * 工单中心-查询工单分类关联的工单模板
     */
    static final String TICKET_QUERY_FILEDS_BY_TYPEID_URL = "/api/ws/5/ticket/query_fileds_by_typeid";

    /**
     * 工单中心-创建工单（客户）
     */
    static final String TICKET_SAVE_USER_TICKET_URL = "/api/ws/5/ticket/save_user_ticket";

    /**
     * 工单中心-创建工单（坐席）
     */
    static final String TICKET_SAVE_AGENT_TICKET_URL = "/api/ws/5/ticket/save_agent_ticket";

    /**
     * 工单中心-查询工单列表
     */
    static final String TICKET_QUERY_TICKETS_URL = "/api/ws/5/ticket/query_tickets";

    /**
     * 工单中心-查询工单详情页
     */
    static final String TICKET_GET_TICKET_BY_ID_URL = "/api/ws/5/ticket/get_ticket_by_id";


    /**
     * 客户中心-获取token
     */
    static final String CUSTOMER_GET_TOKEN_URL = "/api/get_token";
    /**
     * 客户中心-创建客户信息
     */
    static final String CUSTOMER_SAVE_USER_URL = "/api/crm/5/user/save_user";
    /**
     * 客户中心-编辑客户信息
     */
    static final String CUSTOMER_UPDATE_USER_URL = "/api/crm/5/user/update_user";
    /**
     * 客户中心-根据邮箱查询客户信息
     */
    static final String CUSTOMER_GET_USER_BY_EMAIL_URL = "/api/crm/5/user/get_user_by_email";
    /**
     * 客户中心-根据手机号查询客户信息
     */
    static final String CUSTOMER_GET_USER_BY_TEL_URL = "/api/crm/5/user/get_user_by_tel";
    /**
     * 客户中心-根据客户ID查询客户信息
     */
    static final String CUSTOMER_GET_USER_BY_ID_URL = "/api/crm/5/user/get_user_by_id";
    /**
     * 客户中心-查询客户列表
     */
    static final String CUSTOMER_QUERY_USERS_URL = "/api/crm/5/user/query_users";


    /**
     * 获取token
     *
     * @param paramMap
     * @return
     */
    public SobotTokenResponseDTO getTickeToken(Map<String, Object> paramMap) {
        String url = BASE_URL.concat(TICKET_GET_TOKEN_URL);
        url = url + "?" + getUrlParam(paramMap);
        SobotTokenResponseDTO result = restTemplateClient.getForObject(url, new TypeReference<SobotTokenResponseDTO>() {
        });
        return result;
    }


    public String getUrlParam(Map<String, Object> map) {
        if (MapUtils.isEmpty(map)) {
            return null;
        }
        StringBuffer buffer = new StringBuffer();
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            buffer.append(key).append("=").append(map.get(key));
            if (iter.hasNext()) {
                buffer.append("&");
            }
        }
        return buffer.toString();
    }

    /**
     * 获取工单 数据字典
     *
     * @param token
     * @return
     */
    public SobotDataDicResponseDTO getTicketDataDict(String token) {
        String url = BASE_URL.concat(TICKET_GET_DATA_DICT_URL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", token);
        SobotDataDicResponseDTO result = restTemplateClient.getForObject(url, httpHeaders, new TypeReference<SobotDataDicResponseDTO>() {
        });
        return result;
    }

    /**
     * 查询自定义字段
     *
     * @param token
     * @return
     */
    public SobotExtendFieldsResponseDTO queryTicketExtendFields(String token) {
        String url = BASE_URL.concat(TICKET_QUERY_TICKET_EXTEND_FIELDS_URL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", token);
        SobotExtendFieldsResponseDTO result = restTemplateClient.getForObject(url, httpHeaders, new TypeReference<SobotExtendFieldsResponseDTO>() {
        });
        return result;
    }

    /**
     * 查询工单分类关联的工单模板
     *
     * @param ticket_typeid
     * @param token
     * @return
     */
    public SobotQueryFieldsByTypeIdResponseDTO queryTicketFiledsByTypeid(String ticket_typeid, String token) {
        String url = BASE_URL.concat(TICKET_QUERY_FILEDS_BY_TYPEID_URL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", token);
        url = url + "?ticket_typeid=" + ticket_typeid;
        SobotQueryFieldsByTypeIdResponseDTO result = restTemplateClient.getForObject(url, httpHeaders, new TypeReference<SobotQueryFieldsByTypeIdResponseDTO>() {
        });
        return result;
    }

    /**
     * 创建工单-客户
     */
    public SobotSaveUserTicketResponseDTO saveUserTicket(SobotSaveUserTicketRequestDTO requestDTO, String token) {
        String url = BASE_URL.concat(TICKET_SAVE_USER_TICKET_URL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", token);
        SobotSaveUserTicketResponseDTO result = restTemplateClient.postForObject(url, requestDTO, httpHeaders, new TypeReference<SobotSaveUserTicketResponseDTO>() {
        });
        return result;
    }

    /**
     * 创建工单-坐席
     */
    public SobotSaveUserTicketResponseItemDTO saveAgentTicket(SobotSaveAgentTicketRequestDTO requestDTO, String token) {
        String url = BASE_URL.concat(TICKET_SAVE_AGENT_TICKET_URL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", token);
        SobotSaveUserTicketResponseItemDTO result = restTemplateClient.postForObject(url, requestDTO, httpHeaders, new TypeReference<SobotSaveUserTicketResponseItemDTO>() {
        });
        return result;
    }

    /**
     * 查询工单列表
     */
    public SobotQueryTicketResponseDTO queryTicket(Map<String, Object> paramMap, String token) {
        String url = BASE_URL.concat(TICKET_QUERY_TICKETS_URL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", token);
        url = url + "?" + getUrlParam(paramMap);
        SobotQueryTicketResponseDTO result = restTemplateClient.getForObject(url, httpHeaders, new TypeReference<SobotQueryTicketResponseDTO>() {
        });
        return result;

    }

    /**
     * 查询工单详情
     */
    public SobotQueryTicketDetailResponseDTO getTicketById(String ticketid, String token) {
        String url = BASE_URL.concat(TICKET_GET_TICKET_BY_ID_URL);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", token);
        url = url + "?ticketid=" + ticketid;
        SobotQueryTicketDetailResponseDTO result = restTemplateClient.getForObject(url, httpHeaders, new TypeReference<SobotQueryTicketDetailResponseDTO>() {
        });
        return result;
    }
}
