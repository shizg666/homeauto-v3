package com.landleaf.homeauto.center.device.bean;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.device.model.domain.SmsMsgType;
import com.landleaf.homeauto.center.device.model.mapper.MsgTemplateMapper;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.po.device.email.MsgTemplate;
import com.landleaf.homeauto.common.enums.jg.SecondTimeUnitEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 生成不同的短信模板类
 *
 * @author wenyilu
 */
@Component
@Slf4j
public class SmsTemplateConfig {

    private static Map<Integer, SmsMsgType> SMS_TYPE_HOLDER = new ConcurrentHashMap<>();

    private MsgTemplateMapper msgTemplateMapper;

    @PostConstruct
    private void initSmsTemplate() {
        log.error("我是最新版本--------------------");
        //初始化消息模板
        QueryWrapper<MsgTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("temp_type",1);
        List<MsgTemplate> msgTemplates = msgTemplateMapper.selectList(new LambdaQueryWrapper<MsgTemplate>().eq(MsgTemplate::getTempType, 1));
        msgTemplates.forEach(mt -> {
            SmsMsgType.SmsMsgTypeBuilder builder = SmsMsgType.builder();
            builder.tempId(mt.getTempId());
            builder.msgType(mt.getMsgType());
            builder.msgContent(mt.getMsgContent());
            builder.ttl(mt.getTtl()).secondTimeUnitType(SecondTimeUnitEnum.getSecondTimeUnitEnum(mt.getSecondUnitType()));
            SmsMsgType smsMsgType = builder.build();
            SMS_TYPE_HOLDER.put(mt.getMsgType(), smsMsgType);
        });
    }

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

    @Autowired(required = false)
    public void setMsgTemplateMapper(MsgTemplateMapper msgTemplateMapper) {
        this.msgTemplateMapper = msgTemplateMapper;
    }
}
