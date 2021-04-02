package com.landleaf.homeauto.center.device.service.common;


import com.landleaf.homeauto.common.domain.dto.email.EmailMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgSmsMsgDTO;

/**
 * 极光业务接口
 */
public interface IJgService {

    /**
     * 发送验证码
     *
     * @param jgMsgDTO
     * @return
     */
    String sendCode(JgMsgDTO jgMsgDTO);

    /**
     * 验值验证码
     *
     * @param jgMsgDTO
     */
    void verifyCode(JgMsgDTO jgMsgDTO);


    /**
     * 通用发送邮件信息
     *
     * @param emailMsgDTO
     * @return
     */
    String sendEmailMsg(EmailMsgDTO emailMsgDTO);

    /**
     * 发送邮箱验证码
     *
     * @param emailMsgDTO
     * @return
     */
    String sendEmailCode(EmailMsgDTO emailMsgDTO);

    /**
     * 验证邮箱验证码
     *
     * @param emailMsgDTO
     */
    void verifyEmailCode(EmailMsgDTO emailMsgDTO);

    /**
     * 发送普通消息模板
     *
     * @param jgSmsMsgDTO
     */
    void sendSmsMsg(JgSmsMsgDTO jgSmsMsgDTO);
}
