package com.landleaf.homeauto.center.oauth.remote;

import com.landleaf.homeauto.common.constance.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.email.EmailMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_DEVICE)
public interface JgRemote {

    @ApiOperation("产生并发送验证码")
    @PostMapping("/mc/web/jg/send-code")
    public Response sendCode(@RequestBody JgMsgDTO jgMsgDTO);

    @ApiOperation("验证验证码")
    @PostMapping("/mc/web/jg/verify-code")
    public Response verifyCode(@RequestBody JgMsgDTO jgMsgDTO);

    /**
     * 通用发送邮件接口
     *
     * @param emailMsgDTO
     * @return
     */
    @PostMapping("/mc/web/jg/send-email")
    Response sendEmail(@RequestBody EmailMsgDTO emailMsgDTO);

    /**
     * 产生并发送验证码
     *
     * @param emailMsgDTO
     * @return
     */
    @PostMapping("/mc/web/jg/send-email-code")
    Response sendEmailCode(@RequestBody EmailMsgDTO emailMsgDTO);

    /**
     * 验证验证码
     *
     * @param emailMsgDTO
     * @return
     */
    @PostMapping("/mc/web/jg/verify-email-code")
    Response verifyEmailCode(@RequestBody EmailMsgDTO emailMsgDTO);

}
