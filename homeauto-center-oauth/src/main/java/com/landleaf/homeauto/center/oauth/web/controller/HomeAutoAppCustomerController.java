package com.landleaf.homeauto.center.oauth.web.controller;


import com.landleaf.homeauto.center.oauth.asyn.IFutureService;
import com.landleaf.homeauto.center.oauth.cache.CustomerCacheProvider;
import com.landleaf.homeauto.center.oauth.service.IDestroyCustomerService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.common.constance.DateFormatConst;
import com.landleaf.homeauto.common.context.TokenContext;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.*;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 客户列表（APP） 前端控制器
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/auth/customer")
@Api(value = "/auth/customer", tags = {"App客户操作"})
public class HomeAutoAppCustomerController extends BaseController {

    @Autowired
    private CustomerCacheProvider customerCacheProvider;
    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;
    @Autowired(required = false)
    private IFutureService futureService;
    @Autowired
    private IDestroyCustomerService destroyCustomerService;

    @ApiOperation(value = "销毁账号", notes = "销毁账号", consumes = "application/json")
    @PostMapping(value = "/destroy")
    public Response destroyCustomer() {
        HomeAutoToken token = TokenContext.getToken();
        String userId = token.getUserId();
        return destroyCustomerService.destroyCustomer(userId);
    }

    @GetMapping(value = "/userinfo")
    public HomeAutoAppCustomer getSmarthomeCustomerInfo(@RequestParam("userId") String userId) {
        return customerCacheProvider.getSmarthomeCustomer(userId);
    }

    @ApiOperation(value = "web端查询客户基础信息")
    @GetMapping(value = "/userinfo/web")
    public Response<CustomerInfoDTO> getSmarthomeCustomerInfoForWeb(@RequestParam("userId") String userId) {
        HomeAutoAppCustomer smarthomeCustomer = homeAutoAppCustomerService.getById(userId);
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
        BeanUtils.copyProperties(smarthomeCustomer, customerInfoDTO);
        Date bindTime = customerInfoDTO.getBindTime();
        Date loginTime = customerInfoDTO.getLoginTime();
        if (bindTime != null) {
            customerInfoDTO.setBindTimeFormat(DateFormatUtils.format(bindTime, DateFormatConst.PATTERN_YYYY_MM_DD_HH_MM_SS));
        }
        if (loginTime != null) {
            customerInfoDTO.setLoginTimeFormat(DateFormatUtils.format(loginTime, DateFormatConst.PATTERN_YYYY_MM_DD_HH_MM_SS));
        }
        Integer bindFlag = customerInfoDTO.getBindFlag();
        customerInfoDTO.setBindFlagName(bindFlag != null && bindFlag.intValue() == 1 ? "是" : "否");
        return returnSuccess(customerInfoDTO);
    }

    @PostMapping(value = "/list/ids")
    public Response getListByIds(@RequestBody List<String> userIds) {
        return returnSuccess(homeAutoAppCustomerService.getListByIds(userIds));
    }

    @ApiOperation(value = "客户列表查询web端操作")
    @PostMapping(value = "/page")
    public Response pageListCustomer(@RequestBody CustomerPageReqDTO requestBody) {
        return returnSuccess(homeAutoAppCustomerService.pageListCustomer(requestBody));
    }

    @ApiOperation(value = "修改客户web端操作", notes = "修改客户", consumes = "application/json")
    @PostMapping(value = "/update")
    public Response updateCustomer(@RequestBody CustomerUpdateReqDTO requestBody) {
        customerCacheProvider.remove(requestBody.getId());
        homeAutoAppCustomerService.updateCustomer(requestBody);
        //更新用户相关缓存
        futureService.refreshCustomerCache(requestBody.getId());
        return returnSuccess();
    }

    @ApiOperation(value = "新增客户web端操作", notes = "新增客户", consumes = "application/json")
    @PostMapping(value = "/add")
    public Response addCustomer(@RequestBody CustomerAddReqDTO requestBody) {
        homeAutoAppCustomerService.addCustomer(requestBody);
        return returnSuccess();
    }

    @ApiOperation(value = "客户绑定工程数增加通知web端操作", notes = "客户绑定工程通知", consumes = "application/json")
    @GetMapping(value = "/bind/project")
    public Response bindProjectNotice(@RequestParam("userId") String userId,
                                      @RequestParam("projectId") String projectId) {
        customerCacheProvider.remove(userId);
        homeAutoAppCustomerService.bindProjectNotice(userId, projectId);
        futureService.refreshCustomerCache(userId);
        return returnSuccess();
    }

    @ApiOperation(value = "客户解绑工程减少通知web端操作", notes = "客户绑定工程通知", consumes = "application/json")
    @PostMapping(value = "/unbind/project")
    public Response unbindProjectNotice(@RequestBody List<String> userIds) {
        userIds.forEach(userId -> {
            customerCacheProvider.remove(userId);
            homeAutoAppCustomerService.unbindProjectNotice(userId);
            futureService.refreshCustomerCache(userId);
        });
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
    @RequestMapping(value = "/forget_password", method = RequestMethod.POST)
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
        homeAutoAppCustomerService.modifyNickname(nickname);
        futureService.refreshCustomerCache(userId);
        return returnSuccess();
    }

    @ApiOperation(value = "修改头像App端操作", notes = "修改头像路径，data参数为修改后的头像路径", consumes = "application/json")
    @PostMapping(value = "/header/img_url")
    public Response modifyHeaderImageUrl(@RequestBody CustomerUpdateAvatarReqDTO requestBody) {
        String userId = TokenContext.getToken().getUserId();
        customerCacheProvider.remove(userId);
        homeAutoAppCustomerService.modifyHeaderImageUrl(requestBody);
        futureService.refreshCustomerCache(userId);
        return returnSuccess();
    }

    @ApiOperation(value = "修改密码App端操作", notes = "修改密码", consumes = "application/json")
    @PostMapping(value = "/modify_pwd")
    public Response modifyPwd(@RequestBody CustomerPwdModifyDTO requestBody) {
        customerCacheProvider.remove(TokenContext.getToken().getUserId());
        homeAutoAppCustomerService.modifyPassword(requestBody);
        futureService.refreshCustomerCache(TokenContext.getToken().getUserId());
        return returnSuccess();
    }

    @ApiOperation(value = "根据用户名或手机号获取客户列表web端操作")
    @GetMapping(value = "/select/list")
    public Response queryCustomerListByQuery(@RequestParam String query) {
        return returnSuccess(homeAutoAppCustomerService.queryCustomerListByQuery(query));
    }


    @ApiOperation("根据名称模糊查询匹配用户列表")
    @GetMapping("/name/list")
    public Response<List<SelectedVO>> getCustomerListByName(@RequestParam(value = "name", required = false) String name) {
        List<SelectedVO> result = homeAutoAppCustomerService.getCustomerListByName(name);
        return returnSuccess(result);
    }

}
