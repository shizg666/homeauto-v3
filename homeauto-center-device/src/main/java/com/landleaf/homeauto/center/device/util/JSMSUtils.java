package com.landleaf.homeauto.center.device.util;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.JSMSClient;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.common.model.SMSPayload;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.KvObject;
import com.landleaf.homeauto.common.exception.JgException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 极光短信帮助类
 *
 * @author wenyilu
 */
@Component
public class JSMSUtils {

    private static JSMSClient jsmsClient;

    @Autowired
    public void setJsmsClient(@Value("${homeauto.jg.app-key}") String appKey, @Value("${homeauto.jg.master-secret}") String masterSecret) {
        JSMSUtils.jsmsClient = new JSMSClient(masterSecret, appKey);
    }

    public static String sendDefaultSmsCode(String mobile, Integer tempId) throws APIConnectionException, APIRequestException {
        SMSPayload smsPayload = SMSPayload.newBuilder()
                .setMobileNumber(mobile)
                .setTempId(tempId)
                .build();
        SendSMSResult smsResult = jsmsClient.sendTemplateSMS(smsPayload);
        return smsResult.getMessageId();
    }

    /**
     * 发送短信验证码
     *
     * @param mobile   手机号
     * @param tempId   模板ID
     * @param tempPara 模板参数
     * @return 消息ID
     */
    public static String sendSmsCode(String mobile, Integer tempId, Map<String, String> tempPara) {
        SMSPayload smsPayload = SMSPayload.newBuilder().setMobileNumber(mobile).setTempId(tempId).setTempPara(tempPara).build();
        SendSMSResult smsResult;
        try {
            smsResult = jsmsClient.sendTemplateSMS(smsPayload);
        } catch (APIConnectionException | APIRequestException e) {
            e.printStackTrace();
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_MC_JG_SEND_ERROR);
        }
        return smsResult.getMessageId();
    }

    /**
     * 获取模板参数
     *
     * @param code 验证码
     * @param ttl  过期时间
     * @return
     */
    public static Map<String, String> getCodeTempPara(String code, Integer ttl) {
        Map<String, String> tempPara = new HashMap<>(4);
        tempPara.put("code", code);
        tempPara.put("time", String.valueOf(ttl));
        return tempPara;
    }


    public static Map<String, String> getCodeTempPara(List<KvObject> kvList) {
        Map<String, String> tempPara = new HashMap<>(16);
        kvList.forEach(kv -> tempPara.put(kv.getKey(), kv.getValue()));
        return tempPara;
    }

}
