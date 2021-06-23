package com.landleaf.homeauto.center.device.controller.web;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.vo.device.*;
import com.landleaf.homeauto.center.device.service.AppService;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeDicService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicDetailVO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryBaseInfoVO;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.web.BaseController;
import io.jsonwebtoken.lang.Collections;
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


    @Autowired
    private IContactScreenService iContactScreenService;

    @Autowired
    private IHomeAutoAttributeDicService iHomeAutoAttributeDicService;

    @Autowired
    private AppService appService;

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

                    List<Long> ids = iHomeAutoFamilyService.getListIdByRooms(dto2,deviceManageQryDTO.getRealestateId());

                    if (ids.size()>0){
                        familyIds.addAll(ids);
                    }
                }else if (strings.length ==1) {
                    dto2.setBuildingCode(strings[0]);

                    List<Long> ids = iHomeAutoFamilyService.getListIdByRooms(dto2,deviceManageQryDTO.getRealestateId());

                    if (ids.size()>0){
                        familyIds.addAll(ids);
                    }
                }

                }

            //去重
            familyIds2 = familyIds.stream().distinct().collect(Collectors.toList());Collectors.toList();

            } else {

            familyIds2 = null;

        }


        BasePageVO<DeviceMangeFamilyPageVO2> data = iHomeAutoFamilyService.getListDeviceMangeFamilyPage2(familyIds2
        , deviceManageQryDTO.getDeviceName(),deviceManageQryDTO.getCategoryCode(), deviceManageQryDTO.getPageSize(), deviceManageQryDTO.getPageNum());
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

    @ApiOperation(value = "读取设备状态", notes = "读取设备状态", consumes = "application/json")
    @PostMapping(value = "/read/status/{familyId}/{deviceId}")
    public Response<AdapterDeviceStatusReadAckDTO> readStatus(@PathVariable("familyId") String familyId,
                                                              @PathVariable("deviceId") String deviceId) {
        return returnSuccess(appService.readDeviceStatus(BeanUtil.convertString2Long(familyId),
                BeanUtil.convertString2Long(deviceId)));
    }

    @ApiOperation(value = "根据设备编号获取数值型属性列表", consumes = "application/json")
    @GetMapping("device/type")
    public  Response<List<AttrInfoDTO>> getDeviceBasic(@RequestParam Long familyId,@RequestParam  Long templateId, @RequestParam String  deviceSn) {
        List<ScreenProductAttrCategoryBO> attrCategoryBOS = Lists.newArrayList();

        List<AttrInfoDTO> attrInfoDTOList = Lists.newArrayList();

        ScreenTemplateDeviceBO deviceBO = iContactScreenService.getFamilyDeviceBySn(templateId,  familyId,  deviceSn);


        if (deviceBO !=null){
            Integer systemFlag = deviceBO.getSystemFlag();

            if (systemFlag == 0 || systemFlag ==1 ){

                attrCategoryBOS = iContactScreenService.getDeviceAttrsByProductCode(deviceBO.getProductCode());

                if (!Collections.isEmpty(attrCategoryBOS)){

                    for (ScreenProductAttrCategoryBO bo:attrCategoryBOS) {
                        System.out.println(bo.toString());


                            ScreenProductAttrBO attrBO = bo.getAttrBO();

                            if (attrBO !=null){

                                String attrCode = attrBO.getAttrCode();

                                AttributeDicDetailVO dicDetailVO = iHomeAutoAttributeDicService.getAttrDetailByCode(attrCode);

                                if (dicDetailVO!=null && dicDetailVO.getType()==2 && dicDetailVO.getNature()==2){
                                    AttrInfoDTO attrInfoDTO = new AttrInfoDTO();
                                    attrInfoDTO.setCode(attrCode);
                                    attrInfoDTO.setName(dicDetailVO.getName());

                                    attrInfoDTOList.add(attrInfoDTO);
                                }



                        }

                    }

                }

            }

        }

        return returnSuccess(attrInfoDTOList);
    }

    @ApiOperation(value = "根据设备属性获取历史数据列表", consumes = "application/json")
    @PostMapping("status/history")
    public  Response getStatusHistroy(@RequestBody HistoryQryDTO historyQryDTO) {


        return  null;
    }


}
