package com.landleaf.homeauto.center.device.controller.app.smart;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.service.SobotService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeautoFaultReportService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.KvObject;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairDetailDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.RepairAddReqDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.SobotTicketTypeFiledOption;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 故障报修前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@RestController
@RequestMapping("/app/smart/fault-report")
@Slf4j
public class FaultReportController extends BaseController {


    @Autowired
    private IHomeautoFaultReportService homeautoFaultReportService;
    @Autowired
    private IFamilyDeviceService familyDeviceService;
    @Autowired
    private SobotService sobotService;

    @ApiOperation("获取故障内容可选值")
    @GetMapping("/repair-apperance")
    public Response<List<KvObject>> getRepairApperance() {
        List<KvObject> options = Lists.newArrayList();
        List<SobotTicketTypeFiledOption> tmpResult = sobotService.getRepirApperanceOptions();
        if (!CollectionUtils.isEmpty(tmpResult)) {
            options.addAll(tmpResult.stream().map(i -> {
                KvObject data = new KvObject();
                data.setKey(i.getDataName());
                data.setValue(i.getDataValue());
                return data;
            }).collect(Collectors.toList()));
        }
        return returnSuccess(options);
    }

    @ApiOperation("根据家庭获取设备名称")
    @GetMapping("/device-name")
    public Response<Set<KvObject>> getFamilyDeviceName(@RequestParam("familyId") String familyId) {
        Set<KvObject> options = Sets.newHashSet();
        List<FamilyDeviceWithPositionBO> deviceVOS = familyDeviceService.getAllDevices(familyId);
        if (!CollectionUtils.isEmpty(deviceVOS)) {
            options.addAll(deviceVOS.stream().map(i -> {
                KvObject data = new KvObject();
                data.setKey(i.getDeviceName());
                data.setValue(i.getDeviceName());
                return data;
            }).collect(Collectors.toList()));
        }
        return returnSuccess(options);
    }

    @ApiOperation(value = "故障报修详情查询")
    @GetMapping(value = "/detail")
    public Response<AppRepairDetailDTO> getRepairDetail(@RequestParam("repairId") String repairId) {
        AppRepairDetailDTO data = homeautoFaultReportService.getRepairDetail(repairId);
        return returnSuccess(data);
    }

    @ApiOperation(value = "故障报修记录查询")
    @ApiImplicitParam(name = CommonConst.AUTHORIZATION, value = "访问凭据", paramType = "header", required = true)
    @PostMapping(value = "/list")
    public Response<List<AppRepairDetailDTO>> listRepairs() {
        String userId = TokenContext.getToken().getUserId();
        return returnSuccess(homeautoFaultReportService.listRepairs(userId));
    }

    @ApiOperation(value = "创建报修", notes = "创建报修", consumes = "application/json")
    @PostMapping(value = "/add")
    public Response createRepair(@RequestBody RepairAddReqDTO requestBody) {

        homeautoFaultReportService.createRepair(requestBody, TokenContext.getToken().getUserId());
        return returnSuccess();
    }

}
