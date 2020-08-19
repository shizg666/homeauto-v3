package com.landleaf.homeauto.contact.screen.handle;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.common.context.SpringManager;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.contact.screen.common.enums.ContactScreenNameEnum;
import com.landleaf.homeauto.contact.screen.dto.ContactScreenMqttResponse;
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
public class HomeAutoRequestDispatch {


    public Object dispatch(String params, ContactScreenNameEnum contactScreenNameEnum) {

        log.info("requestName：{},{},messageId:{},familyCode:{}", contactScreenNameEnum.getCode(), contactScreenNameEnum.getName());
        try {
            Object bean = null;
            Method method = null;
            try {
                bean = SpringManager.getApplicationContext().getBean(contactScreenNameEnum.getBeanName());
            } catch (Exception e) {
                log.error(String.format("%s,%s", "未找到具体处理类", e.getMessage()), e);
                throw new BusinessException("未找到具体处理类");
            }
            Object requestBody = null;
            try {
                switch (contactScreenNameEnum.getParamType()) {
                    //object
                    case 1:
                        requestBody = JSON.parseObject(params, contactScreenNameEnum.getParamName());
                        method = bean.getClass().getMethod(contactScreenNameEnum.getMethodName(), new Class[]{contactScreenNameEnum.getParamName()});
                        break;
                    //集合
                    case 2:
                        requestBody = JSON.parseArray(params, contactScreenNameEnum.getParamName());
                        method = bean.getClass().getMethod(contactScreenNameEnum.getMethodName(), new Class[]{List.class});
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                log.error(String.format("%s,%s", "入参转换错误", e.getMessage()), e);
                throw new BusinessException("入参转换错误");
            }
            Object result = ReflectionUtils.invokeMethod(method, bean, requestBody);
            return result;
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw exception;
        }
    }


}
