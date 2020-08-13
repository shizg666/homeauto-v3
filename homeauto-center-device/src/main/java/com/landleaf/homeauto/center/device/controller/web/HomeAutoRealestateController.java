package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoRealestateService;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateDeveloperVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateVO;
import com.landleaf.homeauto.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.landleaf.homeauto.common.web.BaseController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 楼盘表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/web/realestate/")
@Api(value = "/web/realestate/", tags = {"楼盘模块"})
public class HomeAutoRealestateController extends BaseController {
    @Autowired
    private IHomeAutoRealestateService iHomeAutoRealestateService;



    @ApiOperation(value = "新增/修改（修改id必传）", notes = "新增")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("addOrUpdate")
    public Response addOrUpdate(@RequestBody @Valid RealestateDTO request){
        if (StringUtil.isEmpty(request.getId())){
            iHomeAutoRealestateService.add(request);
        }else {
            iHomeAutoRealestateService.update(request);
        }
        return returnSuccess();
    }

    @ApiOperation(value = "删除", notes = "新增属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("delete/{id}")
    public Response addOrUpdate(@PathVariable("id") String id){
       iHomeAutoRealestateService.deleteById(id);
        return returnSuccess();
    }


    @ApiOperation(value = "分页查询", notes = "根据id获取楼盘信息")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("page")
    public Response<BasePageVO<RealestateVO>> page(@RequestBody RealestateQryDTO request){
        BasePageVO<RealestateVO> result = iHomeAutoRealestateService.page(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "楼盘下拉列表获取", notes = "楼盘下拉列表获取")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("list/selects")
    public Response<List<SelectedVO>> ListSelects(){
        List<SelectedVO> result = iHomeAutoRealestateService.ListSelects();
        return returnSuccess(result);
    }


    @ApiOperation(value = "楼盘状态下拉列表获取", notes = "楼盘状态下拉列表获取")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("status")
    public Response<List<SelectedIntegerVO>> getStatus(){
        List<SelectedIntegerVO> result = iHomeAutoRealestateService.getRealestateStatus();
        return returnSuccess(result);
    }

    @ApiOperation(value = "根据楼盘id获取楼盘开发商和地址信息", notes = "根据楼盘id获取楼盘开发商和地址信息")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/developer-path/{id}")
    public Response<RealestateDeveloperVO> getDeveloperInfoById(@PathVariable("id" ) String id){
        RealestateDeveloperVO result = iHomeAutoRealestateService.getDeveloperInfoById(id);
        return returnSuccess(result);
    }
}
