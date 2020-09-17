package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectHouseTemplateService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 楼栋表 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Controller
@RequestMapping("/web/family/import/")
@Api(value = "/web/family/import/", tags = {"家庭导入接口"})
public class ImportFamilyController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @ApiOperation("获取家庭批量导入模板")
    @ApiImplicitParam(paramType = "header", name = CommonConst.AUTHORIZATION)
    @PostMapping("/download/template")
    public Response downLoadImportTemplate(@RequestBody TemplateQeyDTO request, HttpServletResponse response) {
        iHomeAutoFamilyService.downLoadImportTemplate(request,response);
        return returnSuccess();
    }

    @ApiOperation("工程批量导入")
    @ApiImplicitParam(paramType = "header", name = CommonConst.AUTHORIZATION)
    @PostMapping("/project/import-batch")
    @ResponseBody
    public Response importBatch(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        iHomeAutoFamilyService.importBatch(file,response);
        return returnSuccess();
    }





}
