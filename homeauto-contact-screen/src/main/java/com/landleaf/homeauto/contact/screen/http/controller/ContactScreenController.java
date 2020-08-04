package com.landleaf.homeauto.contact.screen.http.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.common.domain.dto.screen.ContactScreenHeader;
import com.landleaf.homeauto.common.domain.dto.screen.ContactScreenRequest;
import com.landleaf.homeauto.common.domain.dto.screen.ContactScreenResponse;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameSpaceEnum;
import com.landleaf.homeauto.contact.screen.http.handle.HomeAutoHttpRequestDispatch;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 大屏http/https请求入口
 */
@Slf4j
@RestController
@RequestMapping("/contact-screen")
@Api(value = "/contact-screen", tags = "大屏通讯入口")
public class ContactScreenController {

    @Autowired
    private HomeAutoHttpRequestDispatch homeAutoHttpRequestDispatch;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.POST})
    public ContactScreenResponse screenRequest(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) throws Exception {

        ContactScreenResponse result = null;
        log.info("大屏请求，,请求参数:{}", JSON.toJSONString(jsonObject));
        String familyCode = "";
        ContactScreenRequest commonRequest = null;
        ContactScreenHeader header = null;
        String namespace = "";
        try {
            try {
                String data = JSON.toJSONString(jsonObject);
                commonRequest = JSON.parseObject(data, ContactScreenRequest.class);
            } catch (Exception e) {
                log.error("请求参数异常", e);
                return result;
            }
            try {
                familyCode = commonRequest.getHeader().getFamilyCode();
                // 获取家庭信息
            } catch (Exception e) {
                log.error("familyCode:{}", familyCode);
                log.error("获取家庭信息异常", e);
                return result;
            }
            //将family信息放入本次请求中
            ContactScreenContext.setContext(commonRequest.getHeader());

            //转发到具体处理类
            header = commonRequest.getHeader();
            namespace = header.getNamespace();
            ContactScreenNameSpaceEnum nameSpaceEnum = ContactScreenNameSpaceEnum.getByCode(namespace);

            return homeAutoHttpRequestDispatch.dispatch(JSON.toJSONString(jsonObject.get("payload")));
        } catch (Exception e) {
            e.printStackTrace();
        }


        log.info("返回结果:{}", result);

        return result;
    }


}
