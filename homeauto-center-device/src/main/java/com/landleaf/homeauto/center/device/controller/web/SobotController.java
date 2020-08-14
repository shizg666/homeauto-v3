package com.landleaf.homeauto.center.device.controller.web;

import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.common.utils.Md5Utils;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.bean.properties.homeauto.HomeAutoSobotProperties;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.sobot.datadic.SobotDataDicResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.datadic.SobotDataDicResponseItemDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields.SobotExtendFieldsResponseDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.token.SobotTokenRequestDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.token.SobotTokenResponseDTO;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.configuration.restful.RestTemplateClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName SobotController
 * @Description: 智齿客服系统相关接口
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@RestController
@RequestMapping("/sobot")
@Slf4j
public class SobotController extends BaseController {

    @Autowired
    private HomeAutoSobotProperties homeAutoSobotProperties;
    @Autowired
    private RestTemplateClient restTemplateClient;

    //c3ce6d09cbf74ecf8b29d294aa01a242

    /**
     * 获取token
     */
    @ApiOperation("获取全局token")
    @GetMapping("/get_token")
    public Response getToken() {
        String url = "https://www.sobot.com/api/get_token";
        String appid = homeAutoSobotProperties.getAppid();
        String app_key = homeAutoSobotProperties.getApp_key();
        BigDecimal decimal = new BigDecimal(System.currentTimeMillis()).divide(new BigDecimal(1000));
        String create_time = String.valueOf(decimal.longValue());

        SobotTokenRequestDTO sobotTokenRequestDTO = new SobotTokenRequestDTO();
        sobotTokenRequestDTO.setAppid(appid);
        sobotTokenRequestDTO.setCreate_time(create_time);
        String sign = Md5Utils.getMD5(appid.concat(create_time).concat(app_key), "utf-8");
        sobotTokenRequestDTO.setSign(sign);
        Map<String, String> paramMap = Maps.newHashMap();
        paramMap.put("appid", appid);
        paramMap.put("create_time", create_time);
        paramMap.put("sign", sign);
        url = url + "?" + getUrlParam(paramMap);
        SobotTokenResponseDTO result = restTemplateClient.getForObject(url, new TypeReference<SobotTokenResponseDTO>() {
        });
        return returnSuccess(result);
    }

    /**
     * 查询数据字典
     */
    @ApiOperation("查询数据字典")
    @GetMapping("/get_data_dict")
    public Response getDataDict() {
        String url = "https://www.sobot.com/api/ws/5/ticket/get_data_dict";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", "c3ce6d09cbf74ecf8b29d294aa01a242");

        SobotDataDicResponseDTO result = restTemplateClient.getForObject(url, httpHeaders, new TypeReference<SobotDataDicResponseDTO>() {
        });
        return returnSuccess(result);
    }
    /**
     * 查询自定义字段
     */
    @ApiOperation("查询自定义字段")
    @GetMapping("/query_ticket_extend_fields")
    public Response queryTicketExtendFields() {
        String url = "https://www.sobot.com/api/ws/5/ticket/query_ticket_extend_fields";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("token", "c3ce6d09cbf74ecf8b29d294aa01a242");

        SobotExtendFieldsResponseDTO result = restTemplateClient.getForObject(url, httpHeaders, new TypeReference<SobotExtendFieldsResponseDTO>() {
        });
        return returnSuccess(result);
    }


    public String getUrlParam(Map<String, String> map) {
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
}
