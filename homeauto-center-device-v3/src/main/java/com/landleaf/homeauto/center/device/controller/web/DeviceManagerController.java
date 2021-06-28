package com.landleaf.homeauto.center.device.controller.web;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.device.model.vo.device.*;
import com.landleaf.homeauto.center.device.remote.DataRemote;
import com.landleaf.homeauto.center.device.service.AppService;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceInfoStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAttributeDicService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicDetailVO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeInfoDicDTO;
import com.landleaf.homeauto.common.domain.vo.category.CategoryBaseInfoVO;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.web.BaseController;
import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@Api(value = "/web/device-manage/", tags = {"设备管理"})
public class DeviceManagerController extends BaseController {

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Autowired
    private IFamilyDeviceInfoStatusService deviceInfoStatusService;

    @Autowired
    private DataRemote dataRemote;

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


    @ApiOperation(value = "家庭设备列表查询", consumes = "application/json")
    @PostMapping("list2/")
    public  Response<BasePageVO<DeviceMangeFamilyPageVO2>> getListDeviceMangeByFamilyId(@RequestBody DeviceManageQry2DTO deviceManageQryDTO) {
        BasePageVO<DeviceMangeFamilyPageVO2> data = iHomeAutoFamilyService.getListDeviceMangeByFamilyId(deviceManageQryDTO);
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

    @ApiOperation(value = "读取设备状态", notes = "读取设备状态")
    @GetMapping(value = "read/status/basic")
    public Response<List<BasicAttrInfoDTO>> readStatus(@RequestParam Long familyId,@RequestParam  Long templateId,
                                                       @RequestParam String  deviceSn,@RequestParam Long deviceId) {

        List<ScreenProductAttrCategoryBO> attrCategoryBOS = Lists.newArrayList();

         Integer faultFlag = 0;

         Integer onlineFlag = 0;

        FamilyDeviceInfoStatus familyDeviceInfoStatus = deviceInfoStatusService.getFamilyDeviceInfoStatus(familyId,deviceId);

        if (familyDeviceInfoStatus !=null){
            if (familyDeviceInfoStatus.getOnlineFlag() ==1){
                onlineFlag = familyDeviceInfoStatus.getOnlineFlag();
            }
            if (familyDeviceInfoStatus.getHavcFaultFlag() ==1){
                faultFlag = familyDeviceInfoStatus.getHavcFaultFlag();
            }
        }

        List<BasicAttrInfoDTO> attrInfoDTOList = Lists.newArrayList();

        ScreenTemplateDeviceBO deviceBO = iContactScreenService.getFamilyDeviceBySn(templateId,  familyId,  deviceSn);


        if (deviceBO !=null){
            Integer systemFlag = deviceBO.getSystemFlag();

            if (systemFlag == 0 || systemFlag ==1 ){

                attrCategoryBOS = iContactScreenService.getDeviceAttrsByProductCode(deviceBO.getProductCode());

                if (!Collections.isEmpty(attrCategoryBOS)){

                    for (ScreenProductAttrCategoryBO bo:attrCategoryBOS) {


                        ScreenProductAttrBO attrBO = bo.getAttrBO();

                        if (attrBO !=null){

                            String attrCode = attrBO.getAttrCode();

                            AttributeDicDetailVO dicDetailVO = iHomeAutoAttributeDicService.getAttrDetailByCode(attrCode);

                            if (dicDetailVO!=null && dicDetailVO.getType()==1){

                                CurrentQryDTO currentQryDTO = new CurrentQryDTO();
                                currentQryDTO.setCode(attrCode);
                                currentQryDTO.setDeviceSn(deviceSn);
                                currentQryDTO.setFamilyId(familyId);

                                Response<FamilyDeviceStatusCurrent> response = dataRemote.getStatusCurrent(currentQryDTO);

                                if (response!=null && response.isSuccess()){
                                    FamilyDeviceStatusCurrent current = response.getResult();

                                    if (current !=null){
                                        String valueStr = "";

                                        List<AttributeInfoDicDTO> list = dicDetailVO.getInfos().stream().filter(s->
                                            s.getCode().equals(current.getStatusValue())).collect(Collectors.toList());


                                        if (list.size() > 0 ){
                                            valueStr = list.get(0).getName();
                                        }
                                        BasicAttrInfoDTO attrInfoDTO = new BasicAttrInfoDTO();
                                        attrInfoDTO.setCode(attrCode);
                                        attrInfoDTO.setName(dicDetailVO.getName());
                                        attrInfoDTO.setValue(current.getStatusValue());


                                        attrInfoDTO.setValueStr(valueStr);

                                        attrInfoDTO.setFaultFlag(faultFlag);
                                        attrInfoDTO.setOnlineFlag(onlineFlag);

                                        attrInfoDTOList.add(attrInfoDTO);

                                    }
                                }

                            }



                        }

                    }

                }

            }

        }
        return returnSuccess(attrInfoDTOList);

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
    public  Response<List<FamilyHistoryPageVO>> getStatusHistory(@RequestBody HistoryQryDTO historyQryDTO) {

        List<String> codes = historyQryDTO.getCodes();

        List<FamilyHistoryPageVO> pageVOS = Lists.newArrayList();

        Long familyId = historyQryDTO.getFamilyId();
        String deviceSn = historyQryDTO.getDeviceSn();

        BasePageVO<FamilyDeviceStatusHistory> basePageVO = null;

        if (!CollectionUtils.isEmpty(codes)){
            for (String code:codes) {
                HistoryQryDTO2 dto2 = new HistoryQryDTO2();
                BeanUtils.copyProperties(historyQryDTO,dto2);

                dto2.setCode(code);

                Response response2 = dataRemote.getStatusHistory(dto2);

                if (response2!=null && response2.isSuccess()){
                    basePageVO = (BasePageVO<FamilyDeviceStatusHistory>) response2.getResult();

                    List<FamilyDeviceStatusHistory>familyDeviceStatusHistories =  basePageVO.getList();

                    System.out.println(familyDeviceStatusHistories.size());

                    if (!CollectionUtils.isEmpty(familyDeviceStatusHistories)){

                        FamilyHistoryPageVO familyHistoryPageVO = new FamilyHistoryPageVO();

                        List<LocalDateTime> xlist = familyDeviceStatusHistories.stream().map(s->s.getUploadTime()).collect(Collectors.toList());
                        List<String> ylist = familyDeviceStatusHistories.stream().map(s->s.getStatusValue()).collect(Collectors.toList());

                        familyHistoryPageVO.setCode(code);
                        familyHistoryPageVO.setXList(xlist);
                        familyHistoryPageVO.setYList(ylist);

                        familyHistoryPageVO.setPages(basePageVO.getPages());
                        familyHistoryPageVO.setTotal(basePageVO.getTotal());

                        familyHistoryPageVO.setAttributeDetailVO(iHomeAutoAttributeDicService.getAttrDetailByCode(code));

                        pageVOS.add(familyHistoryPageVO);

                    }



                }

            }
        }



        return  returnSuccess(pageVOS);
    }


    @ApiOperation(value = "根据设备属性获取当前数据列表", consumes = "application/json")
    @PostMapping("status/current")
    public  Response<List<FamilyCurrentVO>> getStatusHistory(@RequestBody CurrentQryDTO2 qryDTO2) {
        List<FamilyCurrentVO> list = Lists.newArrayList();

        List<String> codes = qryDTO2.getCodes();


        Long familyId = qryDTO2.getFamilyId();
        String deviceSn = qryDTO2.getDeviceSn();


        if (!CollectionUtils.isEmpty(codes)) {
            for (String code : codes) {
                CurrentQryDTO dto = new CurrentQryDTO();
                BeanUtils.copyProperties(qryDTO2, dto);

                dto.setCode(code);

                Response response = dataRemote.getStatusCurrent(dto);


                if (response != null && response.isSuccess()) {
                    FamilyDeviceStatusCurrent current = (FamilyDeviceStatusCurrent) response.getResult();

                    if (current != null) {
                        FamilyCurrentVO vo = new FamilyCurrentVO();
                        vo.setCode(code);
                        vo.setAttrValue(current.getStatusValue());
                        vo.setAttributeDetailVO(iHomeAutoAttributeDicService.getAttrDetailByCode(code));
                        list.add(vo);
                    }


                }
            }


        }
        return returnSuccess(list);
    }


    }