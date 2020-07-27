package com.landleaf.homeauto.center.device.controller;

import com.landleaf.homeauto.center.device.service.common.IJgService;
import com.landleaf.homeauto.common.annotation.ParamCheck;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.email.EmailMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgSmsMsgDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 极光推送接口
 *
 * @author wenyilu
 */
@Slf4j
@RestController
@RequestMapping("/msg/jg")
@AllArgsConstructor
@Api(value = "验证码相关控制类", tags = {"短信/邮件"})
public class JgController extends BaseController {

    private final IJgService jgService;

    @ApiOperation("产生并发送验证码")
    @ParamCheck({"mobile:手机号不能为空", "codeType:验证码类型不能为空"})
    @PostMapping("/send-code")
    public Response sendCode(@RequestBody JgMsgDTO jgMsgDTO) {
        String code = jgService.sendCode(jgMsgDTO);
        return returnSuccess(code, null);
    }

    @ApiOperation("验证验证码")
    @ParamCheck({"mobile:手机号不能为空", "codeType:验证码类型不能为空", "code:验证码不能为空"})
    @PostMapping("/verify-code")
    public Response verifyCode(@RequestBody JgMsgDTO jgMsgDTO) {
        jgService.verifyCode(jgMsgDTO);
        return returnSuccess();
    }

    @ApiOperation("发送模板验证码短信")
    @ParamCheck({"mobile:手机号不能为空", "tempParaMap:模板替换内容不能为空", "msgType:消息类型不能为空"})
    @PostMapping("/send-sms-msg")
    public Response sendSmsMsg(@RequestBody JgSmsMsgDTO jgSmsMsgDTO) {
        jgService.sendSmsMsg(jgSmsMsgDTO);
        return returnSuccess();
    }

    @ApiOperation("通用发送邮件信息")
    @ParamCheck({"email:邮箱不能为空", "emailMsgType:邮件信息类型不能为空"})
    @PostMapping("/send-email")
    public Response sendEmail(@RequestBody EmailMsgDTO emailMsgDTO) {
        String result = jgService.sendEmailMsg(emailMsgDTO);
        return returnSuccess(result, null);
    }

    @ApiOperation("产生并发送邮箱验证码")
    @ParamCheck({"email:邮箱不能为空", "emailMsgType:邮件信息类型不能为空"})
    @PostMapping("/send-email-code")
    public Response sendEmailCode(@RequestBody EmailMsgDTO emailMsgDTO) {
        String code = jgService.sendEmailCode(emailMsgDTO);
        return returnSuccess(code, null);
    }

    @ApiOperation("验证邮箱验证码")
    @ParamCheck({"email:邮箱不能为空", "emailMsgType:邮件信息类型不能为空", "xml:验证码不能为空"})
    @PostMapping("/verify-email-code")
    public Response verifyEmailCode(@RequestBody EmailMsgDTO emailMsgDTO) {
        jgService.verifyEmailCode(emailMsgDTO);
        return returnSuccess();
    }

}
