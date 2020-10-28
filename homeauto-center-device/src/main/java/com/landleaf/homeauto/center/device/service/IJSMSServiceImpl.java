package com.landleaf.homeauto.center.device.service;

import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.bean.SmsTemplateConfig;
import com.landleaf.homeauto.center.device.model.SmsMsgType;
import com.landleaf.homeauto.center.device.util.JSMSUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Description 1.0
 * @Author zhanghongbin
 * @Date 2020/9/8 12:19
 */
@Service
public class IJSMSServiceImpl implements IJSMSService{


    @Override
    public void groupAddUser(String projectName, String username, String mobile,String toMobile) {
        SmsMsgType smsMsgType = SmsTemplateConfig.getCodeType(3);//获取新增家庭用户的模板

        if (!StringUtil.isBlank(mobile)) {

            Map<String, String> tempPara = Maps.newHashMap();
            tempPara.put("projectName",projectName);
            tempPara.put("username",username);
            tempPara.put("mobile",mobile);

            JSMSUtils.sendSmsCode(toMobile, smsMsgType.getTempId(),tempPara );
        }

    }
}
