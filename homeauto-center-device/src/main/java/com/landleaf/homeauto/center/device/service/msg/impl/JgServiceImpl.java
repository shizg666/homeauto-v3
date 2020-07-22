package com.landleaf.homeauto.center.device.service.msg.impl;

import com.landleaf.homeauto.center.device.common.msg.cache.EmailMsgRedisService;
import com.landleaf.homeauto.center.device.common.msg.cache.ShCodeRedisService;
import com.landleaf.homeauto.center.device.domain.msg.email.EmailMsg;
import com.landleaf.homeauto.center.device.domain.msg.email.EmailMsgEvent;
import com.landleaf.homeauto.center.device.domain.msg.jg.SendCodeEvent;
import com.landleaf.homeauto.center.device.domain.msg.jg.ShSmsMsgDomain;
import com.landleaf.homeauto.center.device.common.msg.config.event.jg.producer.EmailMsgEventPublisher;
import com.landleaf.homeauto.center.device.common.msg.config.event.jg.producer.SendCodeEventPublisher;
import com.landleaf.homeauto.center.device.service.msg.IJgService;
import com.landleaf.homeauto.center.device.util.msg.JsmsUtil;
import com.landleaf.homeauto.center.device.util.msg.email.SimpleMailSender;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.email.EmailMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgSmsMsgDTO;
import com.landleaf.homeauto.common.enums.email.EmailMsgTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.exception.JgException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 极光业务类
 *
 * @author Lokiy, Yujiumin
 * @date 2019/8/15 17:04
 */
@Slf4j
@Service
@AllArgsConstructor
public class JgServiceImpl implements IJgService {

    private ShCodeRedisService shCodeRedisService;

    private EmailMsgRedisService emailMsgRedisService;

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
        Map<String, String> tempPara = JsmsUtil.getCodeTempPara(shCode.code(), shCode.getTtlWithSecond());
        // 5. 发送短信验证码（大概三秒延迟，发送失败会直接抛异常)
        String messageId = JsmsUtil.sendSmsCode(shCode.mobile(), shCode.smsMsgType().getTempId(), tempPara);
        // 6. 通知其他事件
        sendCodeEventPublisher.asyncPublish(new SendCodeEvent(shCode, messageId));
        return shCode.code();
    }

    @Override
    public void verifyCode(JgMsgDTO jgMsgDTO) {
        ShSmsMsgDomain shCode = ShSmsMsgDomain
                .buildShCode(jgMsgDTO.getCodeType(), jgMsgDTO.getMobile(),
                        shCodeRedisService.hgetCodeByMobile(jgMsgDTO.getCodeType(), jgMsgDTO.getMobile()));
        boolean flag = shCode.verifyCode(jgMsgDTO.getCode());
        if (!flag) {
            //验证失败
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_JG_CODE_VERIFY_ERROR);
        }
    }

    @Override
    public void sendSmsMsg(JgSmsMsgDTO jgSmsMsgDTO) {
        ShSmsMsgDomain shSmsMsg = ShSmsMsgDomain.newShMsg(
                jgSmsMsgDTO.getMsgType(),
                jgSmsMsgDTO.getMobile(),
                jgSmsMsgDTO.getTempParaMap());
        //发送短信
        String messageId = JsmsUtil.sendSmsCode(shSmsMsg.mobile(),
                shSmsMsg.smsMsgType().getTempId(),
                shSmsMsg.tempParaMap());
        //事件通知
        SendCodeEvent sendCodeEvent = new SendCodeEvent(shSmsMsg, messageId);
        sendCodeEvent.redisFlag(false);
        sendCodeEventPublisher.asyncPublish(sendCodeEvent);

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
                .fillMsg("{{msg}}");
        //发送邮件
        SimpleMailSender.sendToUser(emailMsg.email(),
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
        SimpleMailSender.sendToUser(emailMsg.email(),
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
                        emailMsgRedisService.hgetCodeByEmail(emailMsgDTO.getEmailMsgType(), emailMsgDTO.getEmail()));
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
        String code = shCodeRedisService.hgetCodeByMobile(jgMsgDTO.getCodeType(), jgMsgDTO.getMobile());
        if (code != null) {
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_MC_JG_CODE_NOT_EXPIRE);
        }
    }

}
