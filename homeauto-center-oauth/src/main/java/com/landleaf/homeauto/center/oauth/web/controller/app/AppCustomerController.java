package com.landleaf.homeauto.center.oauth.web.controller.app;


import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.oauth.asyn.IFutureService;
import com.landleaf.homeauto.center.oauth.cache.CustomerCacheProvider;
import com.landleaf.homeauto.center.oauth.remote.DeviceRemote;
import com.landleaf.homeauto.center.oauth.remote.FileRemote;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.ITokenService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.*;
import com.landleaf.homeauto.common.domain.dto.oauth.sysuser.SysUserCheckCodeResDTO;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.vo.file.FileVO;
import com.landleaf.homeauto.common.enums.jg.JgSmsTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.AppTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.AVATAR_UPLOAD_ERROR;

/**
 * <p>
 * 客户列表（APP） 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/auth/customer/app")
@Api(value = "/auth/customer/app", tags = {"App客户操作"})
public class AppCustomerController extends BaseController {

    @Autowired
    private CustomerCacheProvider customerCacheProvider;
    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;
    @Autowired(required = false)
    private IFutureService futureService;
    @Autowired
    private DeviceRemote deviceRemote;
    @Autowired
    private FileRemote fileRemote;
    @Autowired
    private ITokenService tokenService;


    @ApiOperation(value = "客户基本信息")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @GetMapping(value = "/userinfo")
    public Response<HomeAutoAppCustomer> getCustomerInfo() {
        return returnSuccess(customerCacheProvider.getCustomer(TokenContext.getToken().getUserId()));
    }

    @ApiOperation(value = "销毁账号", notes = "销毁账号", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping(value = "/destroy")
    public Response destroyCustomer() {
        String userId = TokenContext.getToken().getUserId();
        homeAutoAppCustomerService.destroyCustomer(userId);
        customerCacheProvider.remove(userId);
        // 清除token
        // 清除相关token
        tokenService.clearToken(userId, UserTypeEnum.APP);
        tokenService.clearToken(userId, UserTypeEnum.WECHAT);
        return returnSuccess();
    }


    @ApiOperation(value = "用户注册App端操作", notes = "用户注册", consumes = "application/json")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response registerUser(
            @RequestBody CustomerRegisterDTO requestBody) {
        CustomerRegisterResDTO resDTO = homeAutoAppCustomerService.register(requestBody, AppTypeEnum.SMART.getCode());
        return returnSuccess(resDTO);
    }


    @ApiOperation(value = "忘记密码App端操作", notes = "重置密码", consumes = "application/json")
    @RequestMapping(value = "/forget/password", method = RequestMethod.POST)
    public Response forgetPassword(@RequestBody CustomerForgetPwdDto requestBody) {
        String userId = homeAutoAppCustomerService.forgetPassword(requestBody, AppTypeEnum.SMART.getCode());
        customerCacheProvider.remove(userId);
        futureService.refreshCustomerCache(userId);
        // 清除相关token
        tokenService.clearToken(userId, UserTypeEnum.APP);
        tokenService.clearToken(userId, UserTypeEnum.WECHAT);
        return returnSuccess();
    }

    @ApiOperation(value = "修改昵称App端操作", notes = "修改昵称", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @RequestMapping(value = "/modify/nickname", method = RequestMethod.GET)
    public Response modifyNickname(@RequestParam String nickname) {
        String userId = TokenContext.getToken().getUserId();
        customerCacheProvider.remove(userId);
        homeAutoAppCustomerService.modifyNickname(nickname, userId);
        futureService.refreshCustomerCache(userId);
        return returnSuccess();
    }

    @ApiOperation(value = "修改手机号App端操作", notes = "修改手机号", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @RequestMapping(value = "/modify/mobile", method = RequestMethod.POST)
    public Response modifyMobile(@RequestBody CustomerMobileModifyDto requestBody) {
        String userId = TokenContext.getToken().getUserId();
        customerCacheProvider.remove(userId);
        homeAutoAppCustomerService.modifyMobile(requestBody.getMobile(), requestBody.getCode(), userId);
        futureService.refreshCustomerCache(userId);
        return returnSuccess();
    }

    @ApiOperation(value = "头像修改", notes = "头像修改", produces = "multipart/form-data")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping(value = "/header/avatar")
    public Response modifyHeaderImageUrl(@RequestParam("file") MultipartFile file) {
        Map<String, String> data = Maps.newHashMap();
        FileVO fileVO = new FileVO();
        fileVO.setTypeName("app-avatar");
        fileVO.setFile(file);
        Response response = fileRemote.headerAvatarUpload(fileVO);
        if (response.isSuccess()) {
            Map<String, String> result = (Map<String, String>) response.getResult();
            String url = result.get("url");
            CustomerUpdateAvatarReqDTO param = new CustomerUpdateAvatarReqDTO();
            param.setAvatar(url);
            String userId = TokenContext.getToken().getUserId();
            customerCacheProvider.remove(userId);
            homeAutoAppCustomerService.modifyHeaderImageUrl(userId, param.getAvatar());
            futureService.refreshCustomerCache(userId);
            data.put("url", url);
            return returnSuccess(data);
        }
        throw new BusinessException(AVATAR_UPLOAD_ERROR);
    }

    @ApiOperation(value = "修改密码App端操作", notes = "修改密码", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping(value = "/modify/password")
    public Response modifyPwd(@RequestBody CustomerPwdModifyDTO requestBody) {
        String userId = TokenContext.getToken().getUserId();
        customerCacheProvider.remove(userId);
        homeAutoAppCustomerService.modifyPassword(requestBody, userId);
        futureService.refreshCustomerCache(userId);

        return returnSuccess();
    }

    /**
     * 备注一下，app只发一种类型
     *
     * @param mobile
     * @return
     */
    @ApiOperation(value = "获取验证码", notes = "获取验证码", consumes = "application/json")
    @RequestMapping(value = "/send/code", method = RequestMethod.GET)
    public Response verificationCode(@RequestParam(value = "mobile", required = true) String mobile) {

        JgMsgDTO jgMsgDTO = new JgMsgDTO();
        jgMsgDTO.setMobile(mobile);
        jgMsgDTO.setCodeType(JgSmsTypeEnum.REGISTER_LOGIN.getMsgType());
        Response response = deviceRemote.sendCode(jgMsgDTO);
        response.setResult(null);
        return response;
    }

    @ApiOperation(value = "验证码校验", notes = "验证码校验")
    @GetMapping(value = "/check/code")
    public Response<SysUserCheckCodeResDTO> checkCode(CustomerCheckCodeDTO customerCheckCodeDTO) {
        JgMsgDTO jgMsgDTO = new JgMsgDTO();
        jgMsgDTO.setCode(customerCheckCodeDTO.getCode());
        jgMsgDTO.setCodeType(JgSmsTypeEnum.REGISTER_LOGIN.getMsgType());
        jgMsgDTO.setMobile(customerCheckCodeDTO.getMobile());
        Response response = deviceRemote.verifyCode(jgMsgDTO);
        response.setResult(null);
        return response;
    }
}
