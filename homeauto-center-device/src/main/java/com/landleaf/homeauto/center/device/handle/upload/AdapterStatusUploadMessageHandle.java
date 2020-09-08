package com.landleaf.homeauto.center.device.handle.upload;

import cn.hutool.core.util.NumberUtil;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.util.FaultValueUtils;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceValueDTO;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.service.DeviceStatusPushService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceLinkDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Adapter模块状态上报处理类
 *
 * @author zhanghongbin
 */
@Component
@Slf4j
public class AdapterStatusUploadMessageHandle implements Observer {


    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private DeviceStatusPushService deviceStatusPushService;

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Autowired
    private IProductAttributeErrorService iProductAttributeErrorService;

    @Autowired
    private IHomeAutoFaultDeviceHavcService iHomeAutoFaultDeviceHavcService;

    @Autowired
    private IHomeAutoFaultDeviceLinkService linkService;

    @Autowired
    private IHomeAutoFaultDeviceValueService valueService;


    @Autowired
    private IFamilyDeviceStatusService iFamilyDeviceStatusService;


    @Autowired
    private IHomeAutoFaultDeviceHavcService havcService;


    @Override
    @Async("bridgeDealUploadMessageExecute")
    public void update(Observable o, Object arg) {

        AdapterMessageUploadDTO message = (AdapterMessageUploadDTO) arg;
        // 走下面处理逻辑
        Integer terminalType = message.getTerminalType();
        // 组装数据
        String messageName = message.getMessageName();
        if (terminalType != null && TerminalTypeEnum.SCREEN.getCode().intValue() == terminalType.intValue()) {

            if (StringUtils.equals(AdapterMessageNameEnum.DEVICE_STATUS_UPLOAD.getName(), messageName)) {
                //此时设备上报包含暖通故障，需要做判断

                AdapterDeviceStatusUploadDTO uploadDTO = (AdapterDeviceStatusUploadDTO) message;
                String productCode = uploadDTO.getProductCode();
                List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();

                List<ScreenDeviceAttributeDTO> pushItems = Lists.newArrayList();

                List<HomeAutoFaultDeviceLinkDTO> linkDTOS = Lists.newArrayList();

                List<HomeAutoFaultDeviceHavcDTO> havcDTOS = Lists.newArrayList();

                List<HomeAutoFaultDeviceValueDTO> valueDTOS = Lists.newArrayList();

                HomeAutoFamilyDO homeAutoFamilyDO = iHomeAutoFamilyService.getById(uploadDTO.getFamilyId());

                //批量插入设备状态，非故障
                List<DeviceStatusBO> deviceStatusBOList = Lists.newArrayList();


                String realestateId = homeAutoFamilyDO.getRealestateId();
                String projectId = homeAutoFamilyDO.getProjectId();


                for (ScreenDeviceAttributeDTO dto : items) {
                    AttributeErrorQryDTO request = new AttributeErrorQryDTO();

                    request.setCode(dto.getCode());
                    request.setProductCode(productCode);

                    AttributeErrorDTO errorDTO = iProductAttributeErrorService.getErrorAttributeInfo(request);
                    //返回为null代表是设备状态，不为null为故障
                    if (errorDTO == null) {

                        //状态 存储到redis中  以attributeCode为key最小维度, value值为String

                        String familyDeviceStatusStoreKey = String.format(RedisCacheConst.FAMILY_DEVICE_STATUS_STORE_KEY,
                                uploadDTO.getFamilyCode(), uploadDTO.getProductCode(), uploadDTO.getDeviceSn(), dto.getCode());
                        redisUtils.set(familyDeviceStatusStoreKey, dto.getValue());

                        DeviceStatusBO deviceStatusBO = new DeviceStatusBO();
                        deviceStatusBO.setDeviceSn(uploadDTO.getDeviceSn());
                        deviceStatusBO.setFamilyCode(uploadDTO.getFamilyCode());
                        deviceStatusBO.setFamilyId(uploadDTO.getFamilyId());
                        deviceStatusBO.setStatusCode(dto.getCode());
                        deviceStatusBO.setStatusValue(dto.getValue());

                        deviceStatusBOList.add(deviceStatusBO);


                        pushItems.add(dto);//将要推送的状态加到列表


                    } else {
                        // * 产品故障属性表
                        // *类型为 1错误码的时候  根据desc字段解析故障（按序号从低到高排序返回）
                        // * 型为 2 通信故障的时候 默认 0正常 1故障
                        // * 型为 3 数值故障的时候 根据max和min字段判断是否故障
                        Integer type = errorDTO.getType();
                        if (type == AttributeErrorTypeEnum.ERROR_CODE.getType()) {
                            List<String> stringList = errorDTO.getDesc();

                            List<HomeAutoFaultDeviceHavcDTO> havcTempDTOs = Lists.newArrayList();

                            for (String s : stringList) {
                                HomeAutoFaultDeviceHavcDTO havcDTO = new HomeAutoFaultDeviceHavcDTO();

                                havcDTO.setDeviceSn(uploadDTO.getDeviceSn());
                                havcDTO.setProductCode(productCode);
                                havcDTO.setRealestateId(realestateId);
                                havcDTO.setProjectId(projectId);
                                havcDTO.setFamilyId(uploadDTO.getFamilyId());
                                havcDTO.setFaultMsg(s);
                                havcDTO.setFaultStatus(ErrorConstant.LINK_CODE_ERROR);
                                havcDTO.setFaultTime(LocalDateTime.now());
                                havcTempDTOs.add(havcDTO);
                            }

                            havcDTOS.addAll(havcTempDTOs);


                        } else if (type == AttributeErrorTypeEnum.COMMUNICATE.getType()) {

                            HomeAutoFaultDeviceLinkDTO linkDTO = new HomeAutoFaultDeviceLinkDTO();
                            linkDTO.setDeviceSn(uploadDTO.getDeviceSn());
                            linkDTO.setProductCode(productCode);
                            linkDTO.setRealestateId(realestateId);
                            linkDTO.setProjectId(projectId);
                            linkDTO.setFamilyId(uploadDTO.getFamilyId());
                            linkDTO.setFaultMsg(ErrorConstant.LINK_MSG_ERROR);
                            linkDTO.setFaultStatus(ErrorConstant.LINK_CODE_ERROR);
                            linkDTO.setFaultTime(LocalDateTime.now());

                            linkDTOS.add(linkDTO);


                        } else if (type == AttributeErrorTypeEnum.VAKUE.getType()) {



                            String max = errorDTO.getMax();
                            String min = errorDTO.getMin();
                            String current = dto.getValue();
                            if (FaultValueUtils.isValueError(current,min,max)){

                                HomeAutoFaultDeviceValueDTO valueDTO = new HomeAutoFaultDeviceValueDTO();
                                valueDTO.setReference(min.concat("-").concat(max));
                                valueDTO.setCurrent(current);

                                valueDTO.setDeviceSn(uploadDTO.getDeviceSn());
                                valueDTO.setProductCode(productCode);
                                valueDTO.setRealestateId(realestateId);
                                valueDTO.setProjectId(projectId);
                                valueDTO.setFamilyId(uploadDTO.getFamilyId());
                                valueDTO.setFaultTime(LocalDateTime.now());
                                valueDTOS.add(valueDTO);

                            }


                        }
                    }

                }


                //websocket推送

                uploadDTO.setItems(pushItems);
                deviceStatusPushService.pushDeviceStatus(uploadDTO);

                log.info("[大屏上报设备状态消息]:消息编号:[{}],消息体:{}",
                        message.getMessageId(), message);

                //批量插入正常状态
                if (deviceStatusBOList.size() > 0) {
                    iFamilyDeviceStatusService.insertBatchDeviceStatus(deviceStatusBOList);
                }


                //故障批量入库


                if (havcDTOS.size() > 0) {
                    havcService.batchSave(havcDTOS);
                }


                if (linkDTOS.size() > 0) {

                    linkService.batchSave(linkDTOS);
                }


                if (valueDTOS.size() > 0) {

                    valueService.batchSave(valueDTOS);
                }
                /**
                 * 1、状态推给app
                 * 2、最新状态存储--redis 结构：属性code级 ， familyCode:deviceSn:AttributeCode
                 * 3、数据库存储（功能属性、故障属性[暖通故障、数值故障、通信故障]）
                 * 4、故障
                 * 5、全关全开
                 */


            } else if (StringUtils.equals(AdapterMessageNameEnum.FAMILY_SECURITY_ALARM_EVENT.getName(), messageName)) {


            } else if (StringUtils.equals(AdapterMessageNameEnum.SCREEN_SCENE_SET_UPLOAD.getName(), messageName)) {

            }
        }

    }


}
