package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttribureDicService;
import com.landleaf.homeauto.common.constance.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicDetailVO;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicPageVO;
import com.landleaf.homeauto.common.domain.vo.category.AttribureDicQryDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 属性字典表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/web/attribute-dic/")
@Api(value = "/web/attribute-dic/", tags = {"属性字典表功能"})
public class AttribureDicController extends BaseController {

    @Autowired
    private IHomeAutoAttribureDicService iHomeAutoAttribureDicService;

    @ApiOperation(value = "新增属性", notes = "新增属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("add")
    public Response add(@RequestBody @Valid AttribureDicDTO request){
        iHomeAutoAttribureDicService.add(request);
        return returnSuccess();
    }

    @ApiOperation(value = "修改属性", notes = "修改属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("update")
    public Response update(@RequestBody  @Valid AttribureDicDTO request){
        iHomeAutoAttribureDicService.update(request);
        return returnSuccess();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @PostMapping("page")
    public Response page(@RequestBody AttribureDicQryDTO request){
        BasePageVO<AttribureDicPageVO> result = iHomeAutoAttribureDicService.pageList(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "查看属性", notes = "查看属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @GetMapping("get/detail/{id}")
    public Response getDetailById(@PathVariable("id") String id){
        AttribureDicDetailVO result= iHomeAutoAttribureDicService.getDetailById(id);
        return returnSuccess(result);
    }

    @ApiOperation(value = "删除属性", notes = "删除属性")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header",required = true)
    @DeleteMapping("delete/{id}")
    public Response deleteById(@PathVariable("id") String id ){
        iHomeAutoAttribureDicService.deleteById(id);
        return returnSuccess();
    }

    @GetMapping("get/types")
    public Response<List<SelectedVO>> getAttributeDicTypes(){
        List<SelectedVO> result = iHomeAutoAttribureDicService.getAttributeDicTypes();
        return returnSuccess(result);
    }

    @GetMapping("get/natures")
    public Response<List<SelectedVO>> getAttributeDicNatures(){
        List<SelectedVO> result = iHomeAutoAttribureDicService.getAttributeDicNatures();
        return returnSuccess(result);
    }

}
