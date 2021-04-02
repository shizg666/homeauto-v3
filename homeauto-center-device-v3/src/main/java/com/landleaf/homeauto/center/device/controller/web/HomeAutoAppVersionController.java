package com.landleaf.homeauto.center.device.controller.web;


import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionQry;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionSaveOrUpdateDTO;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.remote.FileRemote;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAppVersionService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.file.FileVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * web端app版本前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-24
 */
@RestController
@RequestMapping("/web/app-version")
@Api(value = "Web端操作app版本请求", tags = "Web端操作app版本请求相关接口")
public class HomeAutoAppVersionController extends BaseController {

    @Autowired
    private IHomeAutoAppVersionService homeAutoAppVersionService;
    @Autowired
    private FileRemote fileRemote;

    @ApiOperation("获取版本列表")
    @PostMapping("/list")
    public Response<BasePageVO<AppVersionDTO>> list(@RequestBody AppVersionQry appVersionQry) {
        PageInfo<AppVersionDTO> temp = homeAutoAppVersionService.queryAppVersionDTOList(appVersionQry);
        BasePageVO<AppVersionDTO> result = new BasePageVO<>(temp);
        return returnSuccess(result);
    }

    @ApiOperation("获取单个版本信息")
    @GetMapping("/{id}")
    public Response<AppVersionDTO> get(@PathVariable("id") String id) {
        AppVersionDTO result = homeAutoAppVersionService.queryAppVersionDTO(id);
        return returnSuccess(result);
    }

    @ApiOperation("新增版本信息")
    @PostMapping
    public Response save(@RequestBody AppVersionSaveOrUpdateDTO appVersionDTO) {
        homeAutoAppVersionService.saveAppVersion(appVersionDTO);
        return returnSuccess();
    }


    @ApiOperation("根据id修改版本信息")
    @PutMapping
    public Response updateById(@RequestBody AppVersionSaveOrUpdateDTO appVersionDTO) {
        homeAutoAppVersionService.updateAppVersion(appVersionDTO);
        return returnSuccess();
    }


    @ApiOperation("推送")
    @GetMapping("/push/{id}")
    public Response push(@PathVariable("id") String id) {
        homeAutoAppVersionService.updatePushStatus(id);
        return returnSuccess();
    }

    @ApiOperation("删除版本信息")
    @DeleteMapping("/{id}")
    public Response deleteById(@PathVariable("id") String id) {
        homeAutoAppVersionService.deleteAppVersion(id);
        return returnSuccess();
    }


    @ApiOperation("app版本下拉框")
    @GetMapping("/select")
    public Response<List<SelectedVO>> getAppVersionsSelect(@RequestParam(value = "belongApp", defaultValue = "smart") String belongApp) {
        List<SelectedVO> appVersions = homeAutoAppVersionService.getAppVersionsSelect(belongApp);
        return returnSuccess(appVersions);
    }

    /**
     * apk上传
     *
     * @return
     */
    @PostMapping("/apk/upload")
    @ApiOperation(value = "apk上传", notes = "apk上传", produces = "multipart/form-data")
    public Response apkUpload(@RequestParam("file") MultipartFile file) throws Exception {
        FileVO fileVO = new FileVO();
        fileVO.setFile(file);
        return fileRemote.apkUpload(fileVO);
    }

}
