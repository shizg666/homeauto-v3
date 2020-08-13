package com.landleaf.homeauto.center.device.model.domain;

import com.landleaf.homeauto.center.device.bean.SmsTemplateConfig;
import com.landleaf.homeauto.center.device.model.constant.ConstForJg;
import com.landleaf.homeauto.center.device.util.SmartHomeCodeGenerator;
import com.landleaf.homeauto.common.constant.CommonConst;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 智能家居验证码领域模型
 *
 * @author Lokiy
 * @date 2019/8/15 18:04
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
     * @param type        验证码类型
     * @param msgType     消息类型
     * @param mobile      手机号
     * @param code        验证码
     * @param tempParaMap 模板参数集合
     * @return 智能家居验证码领域对象
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
     * @param msgType     消息类型
     * @param mobile      手机号码
     * @param tempParaMap 模板参数集合
     * @return 智能家居验证码领域对象
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
     * @param msgType 消息类型
     * @param mobile  手机号
     * @return 智能家居验证码领域对象
     */
    public static ShSmsMsgDomain newShCode(Integer msgType, String mobile) {
        ShSmsMsgDomain shSmsMsg = new ShSmsMsgDomain(mobile);
        shSmsMsg.code = SmartHomeCodeGenerator.codeRandom();
        shSmsMsg.smsMsgType = SmsTemplateConfig.getCodeType(msgType);
        return shSmsMsg;
    }

    /**
     * 构建shCode
     *
     * @param msgType 消息类型
     * @param mobile  手机号
     * @param code    验证码
     * @return 智能家居验证码领域对象
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
     * @param toVerifyCode 待验证的验证码
     * @return 校验结果
     */
    public boolean verifyCode(String toVerifyCode) {
        if (Objects.equals(ConstForJg.SPECIAL_VERIFY_CODE, toVerifyCode)) {
            return true;
        }
        return Objects.equals(code, toVerifyCode);
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
