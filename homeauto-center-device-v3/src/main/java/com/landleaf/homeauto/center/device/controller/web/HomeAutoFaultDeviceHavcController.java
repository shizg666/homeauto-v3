package com.landleaf.homeauto.center.device.controller.web;


import com.landleaf.homeauto.center.device.model.vo.device.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 暖通故障控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/fault/havc")
@Api(value = "/fault/havc", tags = {"暖通故障请求接口"})
public class HomeAutoFaultDeviceHavcController extends BaseController {

    @Autowired
    private IHomeAutoFaultDeviceHavcService homeAutoFaultDeviceHavcService;

    @Autowired
    private IHomeAutoFamilyService familyService;

    @PostMapping("/batch/save")
    public Response<Void> batchSave(@RequestBody List<HomeAutoFaultDeviceHavcDTO> data) {

        homeAutoFaultDeviceHavcService.batchSave(data);
        return returnSuccess();
    }

    @ApiOperation(value = "故障列表查询", consumes = "application/json")
    @PostMapping("list")
    public  Response<BasePageVO<FaultMangeFamilyPageVO>> getListFaultMangeFamilyPage(@RequestBody FaultManageQryDTO qryDTO) {

        //1.先查出familyId列表

        List<Long> familyIds = new ArrayList<>();

        List<Long> familyIds2;



        List<String> locatePaths = qryDTO.getLocatePaths();



        List<String> timeRang = qryDTO.getFaultTime();
        String startTime = null;
        String endTime = null;
        if (!org.springframework.util.CollectionUtils.isEmpty(timeRang) && timeRang.size() == 2) {
            startTime = timeRang.get(0);
            endTime = timeRang.get(1);
        }


        if (locatePaths !=null && locatePaths.size()>0){

            for (String path:locatePaths) {

                String[] strings = path.split("/");

                FamilyDTO2 dto2 = new FamilyDTO2();

                if (strings.length == 3) {
                    familyIds.add(Long.valueOf(strings[2]));
                }else if(strings.length ==2) {
                    dto2.setBuildingCode(strings[0]);
                    dto2.setUnitCode(strings[1]);

                    List<Long> ids = familyService.getListIdByRooms(dto2,qryDTO.getRealestateId());

                    if (ids.size()>0){
                        familyIds.addAll(ids);
                    }
                }else if (strings.length ==1) {
                    dto2.setBuildingCode(strings[0]);

                    List<Long> ids = familyService.getListIdByRooms(dto2,qryDTO.getRealestateId());

                    if (ids.size()>0){
                        familyIds.addAll(ids);
                    }
                }


            }

            //去重
            familyIds2 = familyIds.stream().distinct().collect(Collectors.toList());

        } else {

            familyIds2 = null;

        }





        BasePageVO<FaultMangeFamilyPageVO> data = familyService.getListFaultMangeFamilyPage2(familyIds2
                , qryDTO.getFaultMsg(), startTime,endTime, qryDTO.getPageSize(), qryDTO.getPageNum());
        return returnSuccess(data);
    }

}
