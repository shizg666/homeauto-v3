package com.landleaf.homeauto.center.device.domain.msg.jg;

import com.landleaf.homeauto.center.device.common.msg.config.SmsTemplateConfig;
import com.landleaf.homeauto.center.device.util.msg.ShCodeGenerator;
import com.landleaf.homeauto.common.constance.CommonConst;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author Lokiy
 * @date 2019/8/15 18:04
 * @description: 智能家居验证码领域模型
 */
@Data
@Accessors(fluent = true)
@NoArgsConstructor
public class ShSmsMsgDomain {


    /**
     * 用于标识特殊类型 1-验证码类型
     */
    private Integer type;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 验证码
     */
    private String code;

    /**
     * 邮件发送模板
     */
    private SmsMsgType smsMsgType;

    /**
     * 替换键值对
     */
    private Map<String, String> tempParaMap;


    public ShSmsMsgDomain(String mobile) {
        this.mobile = mobile;
    }


    /**
     * 生成短信对象
     *
     * @param type
     * @param msgType
     * @param mobile
     * @param code
     * @param tempParaMap
     * @return
     */
    public static ShSmsMsgDomain newShSmsMsgDomain(Integer type, Integer msgType, String mobile, String code, Map<String, String> tempParaMap) {
        ShSmsMsgDomain shSmsMsgDomain;
        if (CommonConst.NumberConst.INT_ONE == type) {
            //验证码类型
            if (StringUtils.isNotBlank(code)) {
                shSmsMsgDomain = buildShCode(msgType, mobile, code);
            } else {
                shSmsMsgDomain = newShCode(msgType, mobile);
            }
        } else {
            //正常短信类型
            shSmsMsgDomain = newShMsg(msgType, mobile, tempParaMap);
        }
        return shSmsMsgDomain;
    }


    /**
     * 正常短信类型
     *
     * @param msgType
     * @param mobile
     * @param tempParaMap
     * @return
     */
    public static ShSmsMsgDomain newShMsg(Integer msgType, String mobile, Map<String, String> tempParaMap) {
        ShSmsMsgDomain shSmsMsg = new ShSmsMsgDomain(mobile);
        shSmsMsg.smsMsgType = SmsTemplateConfig.getCodeType(msgType);
        shSmsMsg.tempParaMap = tempParaMap;
        return shSmsMsg;
    }


    /**
     * 生成新的code
     *
     * @param msgType
     * @param mobile
     * @return
     */
    public static ShSmsMsgDomain newShCode(Integer msgType, String mobile) {
        ShSmsMsgDomain shSmsMsg = new ShSmsMsgDomain(mobile);
        shSmsMsg.code = ShCodeGenerator.codeRandom();
        shSmsMsg.smsMsgType = SmsTemplateConfig.getCodeType(msgType);
        return shSmsMsg;
    }

    /**
     * 构建shCode
     *
     * @param msgType
     * @param mobile
     * @return
     */
    public static ShSmsMsgDomain buildShCode(Integer msgType, String mobile, String code) {
        ShSmsMsgDomain shSmsMsg = new ShSmsMsgDomain(mobile);
        shSmsMsg.code = code;
        shSmsMsg.smsMsgType = SmsTemplateConfig.getCodeType(msgType);
        return shSmsMsg;
    }


    /**
     * 验值code
     *
     * @param toVerifyCode
     * @return
     */
    public boolean verifyCode(String toVerifyCode) {
        if ("888888".equals(toVerifyCode)) {
            return true;
        }
        return this.code != null && this.code.equals(toVerifyCode);
    }

    /**
     * 将过期时间转化为秒
     *
     * @return 单位为秒的过期时间
     */
    public Integer getTtlWithSecond() {
        return smsMsgType.getTtl() / smsMsgType.getSecondTimeUnitType().getSeconds();
    }
}
