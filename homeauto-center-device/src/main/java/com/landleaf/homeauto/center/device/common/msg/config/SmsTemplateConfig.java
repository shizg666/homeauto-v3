package com.landleaf.homeauto.center.device.common.msg.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.landleaf.homeauto.center.device.domain.msg.jg.SmsMsgType;
import com.landleaf.homeauto.center.device.mapper.msg.MsgTemplateMapper;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.po.device.email.MsgTemplate;
import com.landleaf.homeauto.common.enums.jg.SecondTimeUnitEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 生成不同的短信模板类
 */
@Component
public class SmsTemplateConfig {

    private static Map<Integer, SmsMsgType> SMS_TYPE_HOLDER = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private MsgTemplateMapper msgTemplateMapper;


    @PostConstruct
    private void initSmsTemplate() {
        //初始化消息模板
        List<MsgTemplate> msgTemplates = msgTemplateMapper.selectList(
                new LambdaQueryWrapper<MsgTemplate>()
                        .eq(MsgTemplate::getTempType, 1));
        msgTemplates.forEach(mt ->
                SMS_TYPE_HOLDER.put(mt.getMsgType(),
                        SmsMsgType.builder()
                                .msgType(mt.getMsgType())
                                .tempId(mt.getTempId())
                                .msgContent(mt.getMsgContent())
                                .ttl(mt.getTtl())
                                .secondTimeUnitType(SecondTimeUnitEnum.getSecondTimeUnitEnum(mt.getSecondUnitType()))
                                .build()));
    }

//
//    @Bean
//    public SmsMsgType loginCodeType(
//            @Value("${mc.jg.login.temp-id}") Integer tempId,
//            @Value("${mc.jg.login.ttl}") Integer ttl){
//        SmsMsgType codeType = new SmsMsgType(JgSmsTypeEnum.REGISTER_LOGIN.getType(), tempId, ttl);
//        SMS_TYPE_HOLDER.put(JgSmsTypeEnum.REGISTER_LOGIN.getType(), codeType);
//        return codeType;
//    }
//
//    @Bean
//    public SmsMsgType resetCodeType(
//            @Value("${mc.jg.reset.temp-id}") Integer tempId,
//            @Value("${mc.jg.reset.ttl}") Integer ttl){
//        SmsMsgType codeType = new SmsMsgType(JgSmsTypeEnum.RESET.getType(), tempId, ttl);
//        SMS_TYPE_HOLDER.put(JgSmsTypeEnum.RESET.getType(), codeType);
//        return codeType;
//    }

    /**
     * 根据codeType获取CodeType
     *
     * @param codeType
     * @return
     */
    public static SmsMsgType getCodeType(Integer codeType) {

        SmsMsgType smsMsgType = SMS_TYPE_HOLDER.get(codeType);
        if (smsMsgType == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()), "无信息配置模板");
        }
        return smsMsgType;
    }


}
