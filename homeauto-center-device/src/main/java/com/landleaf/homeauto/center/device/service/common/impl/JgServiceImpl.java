package com.landleaf.homeauto.center.device.service.common.impl;

import com.landleaf.homeauto.center.device.service.SmsSecurityService;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForEmailMessage;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForSmartHomeCode;
import com.landleaf.homeauto.center.device.model.domain.EmailMsg;
import com.landleaf.homeauto.center.device.eventbus.event.EmailMsgEvent;
import com.landleaf.homeauto.center.device.eventbus.event.SendCodeEvent;
import com.landleaf.homeauto.center.device.model.domain.ShSmsMsgDomain;
import com.landleaf.homeauto.center.device.eventbus.publisher.EmailMsgEventPublisher;
import com.landleaf.homeauto.center.device.eventbus.publisher.SendCodeEventPublisher;
import com.landleaf.homeauto.center.device.service.common.IJgService;
import com.landleaf.homeauto.center.device.util.JSMSUtils;
import com.landleaf.homeauto.center.device.service.SimpleMailService;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.email.EmailMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgSmsMsgDTO;
import com.landleaf.homeauto.common.enums.email.EmailMsgTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.exception.JgException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 极光业务类
 *
 * @author Lokiy
 * @date 2019/8/15 17:04
 */
@Slf4j
@Service
@AllArgsConstructor
public class JgServiceImpl implements IJgService {

    private RedisServiceForSmartHomeCode redisServiceForSmartHomeCode;

    private RedisServiceForEmailMessage redisServiceForEmailMessage;

    private SendCodeEventPublisher sendCodeEventPublisher;

    private EmailMsgEventPublisher emailMsgEventPublisher;

    private SmsSecurityService smsSecurityService;

    @Override
    public String sendCode(JgMsgDTO jgMsgDTO) {
        // 1. 校验该手机号是否已经发送过验证码
        smsSecurityService.checkMobileHasSend(jgMsgDTO.getMobile());
        // 2. 校验该手机号的验证码是否还有效
        smsSecurityService.checkCodeValid(jgMsgDTO.getCodeType(), jgMsgDTO.getMobile());
        // 3. 构建领域对象
        ShSmsMsgDomain shCode = ShSmsMsgDomain.newShCode(jgMsgDTO.getCodeType(), jgMsgDTO.getMobile());
        // 4. 获取模板参数
        Map<String, String> tempPara = JSMSUtils.getCodeTempPara(shCode.code(), shCode.getTtlWithSecond());
        // 5. 发送短信验证码（大概三秒延迟，发送失败会直接抛异常)
        String messageId = JSMSUtils.sendSmsCode(shCode.mobile(), shCode.smsMsgType().getTempId(), tempPara);
        // 6. 通知其他事件
        sendCodeEventPublisher.asyncPublish(new SendCodeEvent(shCode, messageId));
        return shCode.code();
    }

    @Override
    public void verifyCode(JgMsgDTO jgMsgDTO) {
        String codeFromRedis = redisServiceForSmartHomeCode.hgetCodeByMobile(jgMsgDTO.getCodeType(), jgMsgDTO.getMobile());
        ShSmsMsgDomain shCode = ShSmsMsgDomain.buildShCode(jgMsgDTO.getCodeType(), jgMsgDTO.getMobile(), codeFromRedis);
        if (!shCode.verifyCode(jgMsgDTO.getCode())) {
            // 验证失败
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_JG_CODE_VERIFY_ERROR);
        }
    }

    @Override
    public void sendSmsMsg(JgSmsMsgDTO jgSmsMsgDTO) {
        // 1. 发送短信
        ShSmsMsgDomain shSmsMsg = ShSmsMsgDomain.newShMsg(jgSmsMsgDTO.getMsgType(), jgSmsMsgDTO.getMobile(), jgSmsMsgDTO.getTempParaMap());
        String messageId = JSMSUtils.sendSmsCode(shSmsMsg.mobile(), shSmsMsg.smsMsgType().getTempId(), shSmsMsg.tempParaMap());
        // 2. 事件通知
        sendCodeEventPublisher.asyncPublish(new SendCodeEvent(shSmsMsg, messageId, false));
    }

    @Override
    public String sendEmailMsg(EmailMsgDTO emailMsgDTO) {
        //优先看验证码-生成
        if (EmailMsgTypeEnum.EMAIL_CODE.getType().equals(emailMsgDTO.getEmailMsgType())) {
            sendEmailCode(emailMsgDTO);
        }

        EmailMsg emailMsg = EmailMsg.buildEmailMsg(
                emailMsgDTO.getEmailMsgType(),
                emailMsgDTO.getEmail(),
                emailMsgDTO.getMsg())
                .fillSubject(emailMsgDTO.getSubject())
                .fillMsg("{{xml}}");
        //发送邮件
        SimpleMailService.sendToUser(emailMsg.email(),
                emailMsg.emailMsgType().getMsgSubject(),
                emailMsg.msg());
        EmailMsgEvent emailMsgEvent = new EmailMsgEvent(emailMsg);
        emailMsgEventPublisher.asyncPublish(emailMsgEvent);
        return emailMsg.msg();
    }


    @Override
    public String sendEmailCode(EmailMsgDTO emailMsgDTO) {
        //创建领域对象
        EmailMsg emailMsg = EmailMsg.newEmailCode(
                emailMsgDTO.getEmailMsgType(),
                emailMsgDTO.getEmail())
                .fillMsg("{{code}}")
                .fillTtl("{{ttl}}");
        //发送邮件
        SimpleMailService.sendToUser(emailMsg.email(),
                emailMsg.emailMsgType().getMsgSubject(),
                emailMsg.msg());
        EmailMsgEvent emailMsgEvent = new EmailMsgEvent(emailMsg);
        emailMsgEventPublisher.asyncPublish(emailMsgEvent);
        return emailMsg.msg();
    }


    @Override
    public void verifyEmailCode(EmailMsgDTO emailMsgDTO) {
        EmailMsg emailMsg = EmailMsg
                .buildEmailMsg(emailMsgDTO.getEmailMsgType(), emailMsgDTO.getEmail(),
                        redisServiceForEmailMessage.hgetCodeByEmail(emailMsgDTO.getEmailMsgType(), emailMsgDTO.getEmail()));
        boolean flag = emailMsg.verifyCode(emailMsgDTO.getMsg());
        if (!flag) {
            throw new BusinessException(ErrorCodeEnumConst.ERROR_CODE_MC_EMAIL_CODE_NOT_ERROR);
        }
    }

    /**
     * 判断是否发送过了
     *
     * @param jgMsgDTO
     * @return true-发送过 验证码还未过期 false-验证码失效了
     */
    private void checkJgMsgDTO(JgMsgDTO jgMsgDTO) {
        String code = redisServiceForSmartHomeCode.hgetCodeByMobile(jgMsgDTO.getCodeType(), jgMsgDTO.getMobile());
        if (code != null) {
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_MC_JG_CODE_NOT_EXPIRE);
        }
    }

}
