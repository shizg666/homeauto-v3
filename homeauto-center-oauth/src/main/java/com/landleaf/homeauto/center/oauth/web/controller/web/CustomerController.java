package com.landleaf.homeauto.center.oauth.web.controller.web;


import com.landleaf.homeauto.center.oauth.asyn.IFutureService;
import com.landleaf.homeauto.center.oauth.cache.CustomerCacheProvider;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.common.constant.DateFormatConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerAddReqDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerInfoDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerPageReqDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerUpdateReqDTO;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.web.BaseController;
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
@RequestMapping("/auth/customer/web")
@Api(value = "/auth/customer/controller", tags = {"web操作客户"})
public class CustomerController extends BaseController {

    @Autowired
    private CustomerCacheProvider customerCacheProvider;
    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;
    @Autowired(required = false)
    private IFutureService futureService;

    @ApiOperation(value = "销毁账号", notes = "销毁账号", consumes = "application/json")
    @PostMapping(value = "/destroy")
    public Response destroyCustomer() {
        // TODO
        return returnSuccess();
    }


    @ApiOperation(value = "web端客户详情查询")
    @GetMapping(value = "/userinfo")
    public Response<CustomerInfoDTO> getCustomerInfoForWeb(@RequestParam("userId") String userId) {
        HomeAutoAppCustomer customer = homeAutoAppCustomerService.getById(userId);
        CustomerInfoDTO customerInfoDTO = new CustomerInfoDTO();
        BeanUtils.copyProperties(customer, customerInfoDTO);
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

    @ApiOperation(value = "批量获取客户信息")
    @PostMapping(value = "/list/ids")
    public Response getListByIds(@RequestBody List<String> userIds) {
        return returnSuccess(homeAutoAppCustomerService.getListByIds(userIds));
    }

    @ApiOperation(value = "客户列表分页查询web端操作")
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

    /**********************************以下两接口为工程上操作绑定时调用******************************************/
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

    @ApiOperation(value = "根据用户名或手机号获取客户列表web端操作")
    @GetMapping(value = "/select/list")
    public Response queryCustomerListByQuery(@RequestParam String query,
                                             @RequestParam("belongApp") String belongApp) {
        return returnSuccess(homeAutoAppCustomerService.queryCustomerListByQuery(query, belongApp));
    }


    @ApiOperation("根据名称模糊查询匹配用户列表")
    @GetMapping("/name/list")
    public Response<List<SelectedVO>> getCustomerListByName(@RequestParam(value = "name", required = false) String name,
                                                            @RequestParam("belongApp") String belongApp) {
        List<SelectedVO> result = homeAutoAppCustomerService.getCustomerListByName(name, belongApp);
        return returnSuccess(result);
    }

}
