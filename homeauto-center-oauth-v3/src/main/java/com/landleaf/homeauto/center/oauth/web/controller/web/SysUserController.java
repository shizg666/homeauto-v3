package com.landleaf.homeauto.center.oauth.web.controller.web;


import cn.hutool.crypto.digest.BCrypt;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.cache.UserInfoCacheProvider;
import com.landleaf.homeauto.center.oauth.remote.DeviceRemote;
import com.landleaf.homeauto.center.oauth.service.ISysUserService;
import com.landleaf.homeauto.center.oauth.service.ITokenService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.web.context.TokenContext;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.email.EmailMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.sysuser.*;
import com.landleaf.homeauto.common.domain.po.oauth.*;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.enums.email.EmailMsgTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 后台账号表 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/auth/sys-user")
@Api(value = "/auth/sys-user", tags = {"后台账号操作"})
public class SysUserController extends BaseController {

    @Autowired
    private UserInfoCacheProvider userInfoCacheProvider;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private DeviceRemote deviceRemote;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ITokenService tokenService;

    @ApiOperation(value = "验证码校验", notes = "验证码校验")
    @GetMapping(value = "/check/code")
    public Response<SysUserCheckCodeResDTO> checkCode(SysUserCheckCodeDTO sysUserCheckCodeDTO) {
        return returnSuccess(sysUserService.checkCode(sysUserCheckCodeDTO.getType(),sysUserCheckCodeDTO.getCode(),sysUserCheckCodeDTO.getAccount()));
    }
    @ApiOperation(value = "基本信息", notes = "基本信息")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping(value = "/userinfo")
    public SysUser getSysUserInfo() {
        return userInfoCacheProvider.getUserInfo(TokenContext.getToken().getUserId());
    }

//    @ApiOperation(value = "根据名称模糊查询", notes = "根据名称模糊查询")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @GetMapping(value = "/userinfo/name")
//    public List<SysUser> getSysUserByName(@RequestParam("name") String name) {
//        return sysUserService.getSysUserByName(name);
//    }
//
//    @ApiOperation(value = "查看账号", notes = "查看账号")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @RequestMapping(value = "/personal/information", method = RequestMethod.GET)
//    public Response getPersonalInformation() {
//        String userId = TokenContext.getToken().getUserId();
//        return returnSuccess(sysUserService.getPersonalInformation(userId));
//    }
//
//    @ApiOperation(value = "头像修改", notes = "头像修改")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("/personal/avatar")
//    public Response updateAvatar(@RequestBody SysUserUpdateAvatarReqDTO requstBody) {
//        userInfoCacheProvider.remove(requstBody.getId());
//        boolean updateAvatar = sysUserService.updateAvatar(requstBody.getId(), requstBody.getAvatar());
//        userInfoCacheProvider.getUserInfo(requstBody.getId());
//        return returnSuccess();
//    }

//    @ApiOperation(value = "个人资料（账号名称/手机号）修改", notes = "个人资料（账号名称/手机号）修改")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("/personal/update")
//    Response updatePersonalInfo(@RequestBody SysPersonalUpdateReqDTO requstBody) {
//        userInfoCacheProvider.remove(requstBody.getUserId());
//        sysUserService.updatePersonalInfo(requstBody.getUserId(), requstBody.getMobile(), requstBody.getCode(), requstBody.getName());
//        userInfoCacheProvider.getUserInfo(requstBody.getUserId());
//        return returnSuccess();
//    }

//    @ApiOperation(value = "重置密码")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping(value = "/personal/resetPwd")
//    public Response resetPersonalPwd(@RequestBody SysRestPasswordReqDTO requestBody) {
//        userInfoCacheProvider.remove(requestBody.getId());
//        String newPassword = requestBody.getNewPassword();
//        String userId = requestBody.getId();
//        String oldPassword = requestBody.getOldPassword();
//        sysUserService.resetPersonalPwd(userId, newPassword, oldPassword);
//        userInfoCacheProvider.getUserInfo(requestBody.getId());
//        return returnSuccess();
//    }

    @ApiOperation(value = "忘记密码", notes = "忘记密码", consumes = "application/json")
    @RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
    public Response forgetPassword(@RequestBody SysUserForgetPasswordDTO requestBody) {
        sysUserService.forgetPwd(requestBody);
        return returnSuccess();
    }

//    @ApiOperation(value = "分页", notes = "分页", consumes = "application/json")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping(value = "/page")
//    public Response<BasePageVO<SysPersonalInformationDTO>> pageListSysUsers(@RequestBody SysUserPageReqDTO requestBody) {
//        BasePageVO<SysPersonalInformationDTO> result = sysUserService.pageListSysUsers(requestBody);
//        return returnSuccess(result);
//    }

//    @ApiOperation(value = "修改系统账号", notes = "修改系统账号", consumes = "application/json")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping(value = "/update")
//    public Response updateSysUser(@RequestBody SysUserUpdateReqDTO requestBody) {
//        //删除缓存
//        userInfoCacheProvider.remove(requestBody.getId());
//        //修改
//        sysUserService.updateSysUser(requestBody);
//        //刷新缓存
//        userInfoCacheProvider.getUserInfo(requestBody.getId());
//
//        return returnSuccess();
//    }

    @ApiOperation(value = "新建系统账号", notes = "新建系统账号", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping(value = "/add")
    public Response addSysUser(@RequestBody SysUserAddReqDTO requestBody) {
        return returnSuccess(sysUserService.addSysUser(requestBody));
    }

//    @ApiOperation(value = "启用/停用", notes = "启用/停用", consumes = "application/json")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping(value = "/update/status")
//    public Response updateStatus(@RequestBody SysUserUpdateStatusReqDTO requestBody) {
//        userInfoCacheProvider.remove(requestBody.getUserId());
//        sysUserService.updateStatus(requestBody);
//        userInfoCacheProvider.getUserInfo(requestBody.getUserId());
//        return returnSuccess();
//    }
//
//    @ApiOperation("根据ids获取系统用户信息")
//    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
//    @PostMapping("/list/user-ids")
//    public Response<List<SysUser>> getSysUserByIds(@RequestBody List<String> ids) {
//        if (CollectionUtils.isEmpty(ids)) {
//            return returnSuccess(Lists.newArrayList());
//        }
//        List<SysUser> sysUsers = (List<SysUser>) sysUserService.listByIds(ids);
//        return returnSuccess(sysUsers);
//    }

//    @ApiOperation("根据名称模糊查询匹配用户列表")
//    @GetMapping("/name/list")
//    public Response<List<SelectedVO>> getUserListByName(@RequestParam(value = "name", required = false) String name) {
//
//        return returnSuccess(sysUserService.getUserListByName(name));
//    }


    @ApiOperation(value = "发送验证码", notes = "发送验证码", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile", value = "手机号", paramType = "query"),
            @ApiImplicitParam(name = "sendType", value = "发送类型（1：登录注册，2：修改密码）", paramType = "query"),
    })
    @GetMapping(value = "/send/verycode")
    public Response sendVeryCode(@RequestParam("mobile") String mobile,
                                 @RequestParam("sendType") Integer sendType) {

        JgMsgDTO jgMsgDTO = new JgMsgDTO();
        jgMsgDTO.setMobile(mobile);
        jgMsgDTO.setCodeType(sendType);
        Response response = deviceRemote.sendCode(jgMsgDTO);
        if(response.isSuccess()){
            return returnSuccess();
        }
        return response;
    }

    @ApiOperation(value = "发送邮箱验证码", notes = "发送邮箱验证码", consumes = "application/json")
    @GetMapping(value = "/send/email/verycode")
    public Response sendEmailVeryCode(@RequestParam("email") String email) {
        EmailMsgDTO emailMsgDTO = new EmailMsgDTO();
        emailMsgDTO.setEmail(email);
        emailMsgDTO.setEmailMsgType(EmailMsgTypeEnum.EMAIL_CODE.getType());
        Response response = deviceRemote.sendEmailCode(emailMsgDTO);
        if(response.isSuccess()){
            return returnSuccess();
        }
        return response;
    }

    @ApiOperation(value = "退出", notes = "退出", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @GetMapping(value = "/logout")
    @ResponseBody
    public Response logout() {
        HomeAutoToken token = TokenContext.getToken();
        String key = String.format(RedisCacheConst.USER_TOKEN, UserTypeEnum.WEB_DEPLOY.getType(), token.getUserId());
        redisUtils.hdel(key, token.getAccessToken());
        return returnSuccess();
    }


    @ApiOperation(value = "删除", notes = "删除", consumes = "application/json")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Response delete(@RequestBody List<String> ids) {
        boolean b = sysUserService.delete(ids);
        userInfoCacheProvider.cacheAllUser();
        for (String id : ids) {
            tokenService.clearSysUserToken(id);
        }
        return returnSuccess();
    }

    public static void main(String[] args) {
        String hashpw = BCrypt.hashpw("123456");
        System.out.println(hashpw);
    }
}
