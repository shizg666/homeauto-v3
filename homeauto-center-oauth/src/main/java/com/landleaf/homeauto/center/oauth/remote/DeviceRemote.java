package com.landleaf.homeauto.center.oauth.remote;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.email.EmailMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.vo.oauth.FamilyVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ServerNameConst.HOMEAUTO_CENTER_DEVICE)
public interface DeviceRemote {

    @ApiOperation("产生并发送验证码")
    @PostMapping("/device/msg/jg/send-code")
    public Response sendCode(@RequestBody JgMsgDTO jgMsgDTO);

    @ApiOperation("验证验证码")
    @PostMapping("/device/msg/jg/verify-code")
    public Response verifyCode(@RequestBody JgMsgDTO jgMsgDTO);

    /**
     * 通用发送邮件接口
     *
     * @param emailMsgDTO
     * @return
     */
    @PostMapping("/device/msg/jg/send-email")
    Response sendEmail(@RequestBody EmailMsgDTO emailMsgDTO);

    /**
     * 产生并发送验证码
     *
     * @param emailMsgDTO
     * @return
     */
    @PostMapping("/device/msg/jg/send-email-code")
    Response sendEmailCode(@RequestBody EmailMsgDTO emailMsgDTO);

    /**
     * 验证验证码
     *
     * @param emailMsgDTO
     * @return
     */
    @PostMapping("/device/msg/jg/verify-email-code")
    Response verifyEmailCode(@RequestBody EmailMsgDTO emailMsgDTO);



    @ApiOperation("获取家庭列表")
    @PostMapping("/device/smart/family")
    public Response<FamilyVO> getFamily(@RequestParam("userId") String userId);

}
