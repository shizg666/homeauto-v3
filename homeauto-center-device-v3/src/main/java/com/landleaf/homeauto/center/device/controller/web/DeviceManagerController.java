package com.landleaf.homeauto.center.device.controller.web;

import com.landleaf.homeauto.center.device.model.vo.device.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryBaseInfoVO;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DeviceController
 * @Description: TODO
 * @Author shizg
 * @Date 2021/1/28
 * @Version V1.0
 **/
@RestController
@RequestMapping("/web/device-manage")
@Api(value = "/web/device-manag/", tags = {"设备管理"})
public class DeviceManagerController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @ApiOperation(value = "设备列表查询", consumes = "application/json")
    @PostMapping("list")
    public  Response<BasePageVO<DeviceMangeFamilyPageVO2>> getListDeviceMangeFamilyPage(@RequestBody DeviceManageQryDTO deviceManageQryDTO) {

        //1.先查出familyId列表

        List<Long> familyIds = new ArrayList<>();

        List<Long> familyIds2;



        List<String> locatePaths = deviceManageQryDTO.getLocatePaths();



        if (locatePaths !=null && locatePaths.size()>0){

            for (String path:locatePaths) {

                String[] strings = path.split("/");

                FamilyDTO2 dto2 = new FamilyDTO2();

                if (strings.length == 3) {
                    familyIds.add(Long.valueOf(strings[2]));
                }else if(strings.length ==2) {
                    dto2.setBuildingCode(strings[0]);
                    dto2.setUnitCode(strings[1]);
                }else if (strings.length ==1) {
                    dto2.setBuildingCode(strings[0]);
                }

                    List<Long> ids = iHomeAutoFamilyService.getListIdByRooms(dto2,deviceManageQryDTO.getRealestateId());

                    if (ids.size()>0){
                        familyIds.addAll(ids);
                    }
                }

            //去重
            familyIds2 = familyIds.stream().distinct().collect(Collectors.toList());Collectors.toList();

            } else {

            familyIds2 = null;

        }


        BasePageVO<DeviceMangeFamilyPageVO2> data = iHomeAutoFamilyService.getListDeviceMangeFamilyPage2(familyIds2
        , deviceManageQryDTO.getDeviceName(), deviceManageQryDTO.getPageSize(), deviceManageQryDTO.getPageNum());
        return returnSuccess(data);
    }


    @ApiOperation(value = "家庭设备品类列表查询", consumes = "application/json")
    @GetMapping("list/category")
    public  Response<List<CategoryBaseInfoVO>> getListDeviceCategory(@RequestParam String familyId, @RequestParam Long templateId) {

        //1.先查出familyId列表

        List<CategoryBaseInfoVO> data = iHomeAutoFamilyService.getListDeviceCategory(templateId);

        return returnSuccess(data);
    }

    @ApiOperation(value = "根据家庭品类获取设备列表", consumes = "application/json")
    @GetMapping("list/devices")
    public  Response<List<FamilyDeviceDetailVO>> getListDevice(@RequestParam Long familyId, @RequestParam String  categoryCode) {


        List<FamilyDeviceDetailVO> data = iHomeAutoFamilyService.getListDeviceByCategory(familyId,categoryCode);

        return returnSuccess(data);
    }
}
