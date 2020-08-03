package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IAreaService;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.address.AreaDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 地址表 前端控制器
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/web/area")
@Api(description = "国家省市行政区接口")
public class AreaController extends BaseController {

    @Autowired
    private IAreaService iAreaService;

    @ApiOperation(value = "根据code获得下一级地区列表", notes = "获得下一级地区,data传参为下一级地区的code", consumes = "application/json")
    @GetMapping(value = "/children/{code}")
    public Response<List<AreaDTO>> area(@PathVariable @ApiParam(name="code",value="查询编码（0代表查询所有国家列表）",required=true) String code) {
        List<AreaDTO> areaVOS = iAreaService.getAreaList(code);
        return returnSuccess(areaVOS);
    }

    @ApiOperation(value = "根据code获得下一级地区列表(根据项目过滤)", notes = "获得下一级地区,data传参为下一级地区的code", consumes = "application/json")
    @GetMapping(value = "/children/filter/{code}")
    public Response<List<AreaDTO>> getListAreafilterProject(@PathVariable @ApiParam(name="code",value="查询编码（0代表查询所有国家列表）",required=true) String code) {
        List<AreaDTO> areaVOS = iAreaService.getListAreafilterProject(code);
        return returnSuccess(areaVOS);
    }


}
