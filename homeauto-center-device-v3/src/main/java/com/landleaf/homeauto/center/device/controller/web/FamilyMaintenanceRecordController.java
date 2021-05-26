package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.enums.MaintenanceTypeEnum;
import com.landleaf.homeauto.center.device.model.dto.FamilyRepairRecordAddDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyRepairRecordUpdateDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenanceAddRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenancePageRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenanceUpdateRequestDTO;
import com.landleaf.homeauto.center.device.model.vo.maintenance.FamilyMaintenanceRecordVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyMaintenanceRecordService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 家庭维保记录 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2021-05-24
 */
@Api(value = "FamilyMaintenanceRecordController", tags = {"家庭维保记录API"})
@RestController
@RequestMapping("/maintenance/family-maintenance-record")
public class FamilyMaintenanceRecordController extends BaseController {

    @Autowired
    private IFamilyMaintenanceRecordService familyMaintenanceRecordService;

    @ApiOperation(value = "維保记录条件查询")
    @PostMapping(value = "/page")
    public Response<BasePageVO<FamilyMaintenanceRecordVO>> pageListMaintenanceRecord(@RequestBody FamilyMaintenancePageRequestDTO requestBody) {
        return returnSuccess(familyMaintenanceRecordService.pageListMaintenanceRecord(requestBody));
    }

    @ApiOperation(value = "維保记录详情查询")
    @GetMapping(value = "/detail")
    public Response<FamilyMaintenanceRecordVO> detail(@RequestParam Long id) {
        return returnSuccess(familyMaintenanceRecordService.detail(id));
    }


    @ApiOperation(value = "新增")
    @PostMapping("add")
    @ResponseBody
    public Response addRecord(@RequestBody @Valid FamilyMaintenanceAddRequestDTO requestDTO) {
        requestDTO.setMaintenanceType(MaintenanceTypeEnum.input.getCode());
        familyMaintenanceRecordService.addRecord(requestDTO);
        return returnSuccess();
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    @ResponseBody
    public Response updateRecord(@RequestBody FamilyMaintenanceUpdateRequestDTO requestDTO) {
        familyMaintenanceRecordService.updateRecord(requestDTO);
        return returnSuccess();
    }

    @ResponseBody
    @ApiOperation(value = "根据主键删除")
    @DeleteMapping("delete")
    public Response delete(@RequestParam("id") Long id) {
        familyMaintenanceRecordService.delete(id);
        return returnSuccess();
    }

}
