package com.landleaf.homeauto.contact.screen.http.handle;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.context.SpringManager;
import com.landleaf.homeauto.common.domain.dto.screen.ContactScreenHeader;
import com.landleaf.homeauto.common.domain.dto.screen.ContactScreenResponse;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.contact.screen.common.context.ContactScreenContext;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 大屏通过http请求分发器
 * @author wenyilu
 */

@Slf4j
@Component
public class HomeAutoHttpRequestDispatch {


    public ContactScreenResponse dispatch(String payload) {

        ContactScreenHeader context = ContactScreenContext.getContext();
        ContactScreenNameEnum nameEnum = ContactScreenNameEnum.getByCode(context.getName());

        log.info("requestName：{},{},messageId:{},familyCode:{}", nameEnum.getCode(), nameEnum.getName(), context.getMessageId(), context.getFamilyCode());
        log.info("入参：{}", JSON.toJSONString(payload));
        try {
            Object bean = null;
            Method method = null;
            try {
                bean = SpringManager.getApplicationContext().getBean(nameEnum.getBeanName());
            } catch (Exception e) {
                log.error(String.format("%s,%s", "未找到具体处理类", e.getMessage()), e);
                throw new BusinessException("未找到具体处理类");
            }
            Object requestBody = null;
            try {
                switch (nameEnum.getParamType()) {
                    //object
                    case 1:
                        requestBody = JSON.parseObject(payload, nameEnum.getParamName());
                        method = bean.getClass().getMethod(nameEnum.getMethodName(), new Class[]{nameEnum.getParamName()});
                        break;
                    //集合
                    case 2:
                        requestBody = JSON.parseArray(payload, nameEnum.getParamName());
                        method = bean.getClass().getMethod(nameEnum.getMethodName(), new Class[]{List.class});
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                log.error(String.format("%s,%s", "入参转换错误", e.getMessage()), e);
                throw new BusinessException("入参转换错误");
            }
            ContactScreenResponse result = (ContactScreenResponse) ReflectionUtils.invokeMethod(method, bean, requestBody);
            return result;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }


}
