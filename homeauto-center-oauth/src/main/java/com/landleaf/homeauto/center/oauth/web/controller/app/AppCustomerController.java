package com.landleaf.homeauto.center.oauth.web.controller.app;


import com.landleaf.homeauto.center.oauth.asyn.IFutureService;
import com.landleaf.homeauto.center.oauth.cache.CustomerCacheProvider;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.common.context.TokenContext;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.*;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "客户基本信息")
    @GetMapping(value = "/userinfo")
    public HomeAutoAppCustomer getCustomerInfo() {
        return customerCacheProvider.getCustomer(TokenContext.getToken().getUserId());
    }

    @ApiOperation(value = "销毁账号", notes = "销毁账号", consumes = "application/json")
    @PostMapping(value = "/destroy")
    public Response destroyCustomer() {
        // TODO
        return returnSuccess();
    }


    @ApiOperation(value = "用户注册App端操作", notes = "用户注册", consumes = "application/json")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Response registerUser(
            @RequestBody CustomerRegisterDTO requestBody) {
        CustomerRegisterResDTO resDTO = homeAutoAppCustomerService.register(requestBody);
        return returnSuccess(resDTO);
    }


    @ApiOperation(value = "忘记密码App端操作", notes = "重置密码", consumes = "application/json")
    @RequestMapping(value = "/forget/password", method = RequestMethod.POST)
    public Response forgetPassword(@RequestBody CustomerForgetPwdDto requestBody) {
        String userId = homeAutoAppCustomerService.forgetPassword(requestBody);
        customerCacheProvider.remove(userId);
        futureService.refreshCustomerCache(userId);
        return returnSuccess();
    }

    @ApiOperation(value = "修改昵称App端操作", notes = "修改昵称", consumes = "application/json")
    @RequestMapping(value = "/modify/nickname", method = RequestMethod.GET)
    public Response modifyNickname(@RequestParam String nickname) {
        String userId = TokenContext.getToken().getUserId();
        customerCacheProvider.remove(userId);
        homeAutoAppCustomerService.modifyNickname(nickname, userId);
        futureService.refreshCustomerCache(userId);
        return returnSuccess();
    }

    @ApiOperation(value = "修改头像App端操作", notes = "修改头像路径，data参数为修改后的头像路径", consumes = "application/json")
    @PostMapping(value = "/header/avatar")
    public Response modifyHeaderImageUrl(@RequestBody CustomerUpdateAvatarReqDTO requestBody) {
        String userId = TokenContext.getToken().getUserId();
        customerCacheProvider.remove(userId);
        homeAutoAppCustomerService.modifyHeaderImageUrl(userId, requestBody.getAvatar());
        futureService.refreshCustomerCache(userId);
        return returnSuccess();
    }

    @ApiOperation(value = "修改密码App端操作", notes = "修改密码", consumes = "application/json")
    @PostMapping(value = "/modify/password")
    public Response modifyPwd(@RequestBody CustomerPwdModifyDTO requestBody) {
        String userId = TokenContext.getToken().getUserId();
        customerCacheProvider.remove(userId);
        homeAutoAppCustomerService.modifyPassword(requestBody, userId);
        futureService.refreshCustomerCache(userId);
        return returnSuccess();
    }

}
