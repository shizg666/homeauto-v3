package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoCategoryService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryAttributeInfoVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryDTO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryPageVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryQryDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 品类表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/web/category/")
@Api(value = "/web/category/", tags = {"品类接口"})
public class CategoryController extends BaseController {

    @Autowired
    private IHomeAutoCategoryService iHomeAutoCategoryService;


    @ApiOperation(value = "新增/修改类别（修改id必传）", notes = "新增类别")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping("addOrUpdate")
    public Response add(@RequestBody @Valid CategoryDTO request) {
        if (Objects.isNull(request.getId())) {
            iHomeAutoCategoryService.add(request);
        } else {
            iHomeAutoCategoryService.update(request);
        }
        return returnSuccess();
    }


    @ApiOperation(value = "删除类别", notes = "删除类别")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping("delete/{id}")
    public Response update(@PathVariable("id") Long categoryId) {
        iHomeAutoCategoryService.deleteById(categoryId);
        return returnSuccess();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping("page")
    public Response<BasePageVO<CategoryPageVO>> page(@RequestBody CategoryQryDTO request) {
        BasePageVO<CategoryPageVO> result = iHomeAutoCategoryService.pageList(request);
        return returnSuccess(result);
    }

    @ApiOperation(value = "新增品类获取类别下拉列表接口", notes = "获取类别列表接口")
    @GetMapping("get/category-type")
    public Response<List<SelectedVO>> getCategorys() {
        List<SelectedVO> result = iHomeAutoCategoryService.getCategorys();
        return returnSuccess(result);
    }

    @ApiOperation(value = "启用停用", notes = "启用停用")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping("switch-status/{id}")
    public Response switchStatus(@PathVariable("id") Long categoryId) {
        iHomeAutoCategoryService.switchStatus(categoryId);
        return returnSuccess();
    }

    @ApiOperation(value = "新增产品时获取品类下拉列表", notes = "获取协议下拉列表")
    @GetMapping("get/categorys")
    public Response<List<SelectedLongVO>> getCategorysList(){
        List<SelectedLongVO> result = iHomeAutoCategoryService.getListSelectedVO();
        return returnSuccess(result);
    }

    @ApiOperation(value = "新增产品时获取品类关联属性列表", notes = "获取协议下拉列表")
    @GetMapping("get/categorys/attrinfos/{categoryId}")
    public Response<CategoryAttributeInfoVO> getCategorysAttrInfoList(@PathVariable("categoryId")String categoryId){
        CategoryAttributeInfoVO result = iHomeAutoCategoryService.getCategorysAttrInfoList(categoryId);
        return returnSuccess(result);
    }


}
