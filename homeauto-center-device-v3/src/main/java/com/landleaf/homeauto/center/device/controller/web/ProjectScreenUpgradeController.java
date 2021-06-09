package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.dto.screenapk.*;
import com.landleaf.homeauto.center.device.remote.FileRemote;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectScreenUpgradeService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.file.FileVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-07
 */
@RestController
@Api(value = "/project-screen-upgrade", tags = {"项目ota升级"})
@RequestMapping("/project-screen-upgrade")
public class ProjectScreenUpgradeController extends BaseController {
    @Autowired
    private IProjectScreenUpgradeService projectScreenUpgradeService;
    @Autowired
    private FileRemote fileRemote;

    @PostMapping("/upload")
    @ApiOperation(value = "apk上传", notes = "apk上传", produces = "multipart/form-data")
    public Response apkUpload(@RequestParam("file") MultipartFile file) throws Exception {
        FileVO fileVO = new FileVO();
        fileVO.setFile(file);
        return fileRemote.apkUpload(fileVO);
    }

    @ApiOperation(value = "升级")
    @PostMapping("/upgrade")
    public Response upgrade (@RequestParam ("detailId")Long detailId) {
        projectScreenUpgradeService.upgrade(detailId);
        return returnSuccess();
    }
    @ApiOperation(value = "查看详情-条件")
    @PostMapping("/detail")
    public Response<BasePageVO<ProjectScreenUpgradeInfoDetailDTO>> getInfoDetail (@RequestBody ProjectScreenUpgradeDetailPageDTO requestDTO) {
        return returnSuccess(projectScreenUpgradeService.getInfoDetail(requestDTO));
    }

    @ApiOperation(value = "新增升级")
    @PostMapping("save")
    public Response save(@RequestBody @Valid ProjectScreenUpgradeSaveDTO requestBody) {
        projectScreenUpgradeService.saveUpgrade(requestBody);
        return returnSuccess();
    }

    @ApiOperation(value = "修改应用")
    @PostMapping("update")
    public Response update(@RequestBody ProjectScreenUpgradeUpdateDTO requestBody) {
        projectScreenUpgradeService.updateUpgrade(requestBody);
        return returnSuccess();
    }

    @ApiOperation(value = "列表")
    @PostMapping(value = "/page")
    public Response<BasePageVO<ProjectScreenUpgradeInfoDTO>> pageList(@RequestBody ProjectScreenUpgradePageDTO requestBody) {
        return returnSuccess(projectScreenUpgradeService.pageList(requestBody));
    }


}
