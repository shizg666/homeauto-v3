package com.landleaf.homeauto.center.device.handle.upload;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusRedisBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAlarmMessageDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.service.WebSocketMessageService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.FaultValueUtils;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceAlarmUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterSecurityAlarmMsgItemDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceLinkDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceValueDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private WebSocketMessageService webSocketMessageService;

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Autowired
    private IProductAttributeErrorService iProductAttributeErrorService;


    @Autowired
    private IHomeAutoFaultDeviceLinkService linkService;

    @Autowired
    private IHomeAutoFaultDeviceValueService valueService;


    @Autowired
    private IFamilyDeviceStatusService iFamilyDeviceStatusService;


    @Autowired
    private IHomeAutoFaultDeviceHavcService havcService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private IHomeAutoAlarmMessageService homeAutoAlarmMessageService;
    @Autowired
    private IHomeAutoGlcWindStatusService homeAutoGlcWindStatusService;

    private static String GLC_WIND_STATUS = "glc_wind_status";

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
                log.info("[大屏上报设备状态消息]:消息编号:[{}],消息体:{}", message.getMessageId(), message);

                //此时设备上报包含暖通故障，需要做判断
                AdapterDeviceStatusUploadDTO uploadDTO = (AdapterDeviceStatusUploadDTO) message;
                dealUploadStatus(uploadDTO);

            } else if (StringUtils.equals(AdapterMessageNameEnum.FAMILY_SECURITY_ALARM_EVENT.getName(), messageName)) {
                if (message != null) {
                    log.info("安防报警上报:{}", message.toString());
                    AdapterDeviceAlarmUploadDTO alarmUploadDTO = (AdapterDeviceAlarmUploadDTO) message;
                    dealAlarmEvent(alarmUploadDTO);
                }

            } else if (StringUtils.equals(AdapterMessageNameEnum.SCREEN_SCENE_SET_UPLOAD.getName(), messageName)) {

            }
        }

    }

    /**
     * 处理安防报警事件
     *
     * @param alarmUploadDTO
     */
    private void dealAlarmEvent(AdapterDeviceAlarmUploadDTO alarmUploadDTO) {
        // 直接存数据库
        List<AdapterSecurityAlarmMsgItemDTO> data = alarmUploadDTO.getData();
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        List<HomeAutoAlarmMessageDO> saveData = data.stream().map(i -> {
            HomeAutoAlarmMessageDO alarmMessageDO = new HomeAutoAlarmMessageDO();
            BeanUtils.copyProperties(i, alarmMessageDO);
            alarmMessageDO.setDeviceId(familyDeviceService.getFamilyAlarm(alarmUploadDTO.getFamilyId()));
            alarmMessageDO.setFamilyId(alarmUploadDTO.getFamilyId());
            alarmMessageDO.setAlarmCancelFlag(0);
            alarmMessageDO.setAlarmContext(i.getContext());
            alarmMessageDO.setAlarmTime(LocalDateTimeUtil.date2LocalDateTime(new Date(i.getAlarmTime())));
            alarmMessageDO.setDeviceSn(alarmUploadDTO.getDeviceSn());
            return alarmMessageDO;
        }).collect(Collectors.toList());

        homeAutoAlarmMessageService.saveBatch(saveData);

    }

    /**
     * 处理上报状态数据
     *
     * @param uploadDTO 状态数据
     */
    private void dealUploadStatus(AdapterDeviceStatusUploadDTO uploadDTO) {
        String productCode = uploadDTO.getProductCode();

        List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();

        List<ScreenDeviceAttributeDTO> pushItems = Lists.newArrayList();

        List<HomeAutoFaultDeviceLinkDTO> linkDTOS = Lists.newArrayList();

        List<HomeAutoFaultDeviceHavcDTO> havcDTOS = Lists.newArrayList();

        List<HomeAutoFaultDeviceValueDTO> valueDTOS = Lists.newArrayList();

        List<DeviceStatusRedisBO> redisBOList = Lists.newArrayList();


        //批量插入设备状态，非故障
        List<DeviceStatusBO> deviceStatusBOList = Lists.newArrayList();



        for (ScreenDeviceAttributeDTO dto : items) {
            try {
                /*******************************处理故障**********************************************/
                AttributeErrorDTO errorDTO = judgeFaultData(dto, productCode);

                if (errorDTO == null) {
                    /****是否是glc特别需要存储的状态数据**********************/
                    if(StringUtils.equals(dto.getCode(),GLC_WIND_STATUS)){
                        log.info("新风特殊状态值,需要另存......");
                        filterSpecialGlcWindStatus(uploadDTO,dto);
                        continue;
                    }
                    // 添加到redis存储最新状态
                    // 添加到数据库状态存储
                    // 推送到websocket
                    redisBOList.add(generateRedisStoreData(uploadDTO, dto));
                    deviceStatusBOList.add(generateDBStoreData(uploadDTO, dto, productCode));
                    pushItems.add(dto);
                } else {
                    HomeAutoFamilyDO homeAutoFamilyDO = iHomeAutoFamilyService.getById(uploadDTO.getFamilyId());
                    String realestateId = homeAutoFamilyDO.getRealestateId();
                    String projectId = homeAutoFamilyDO.getProjectId();
                    log.info("查询AttributeErrorDTO得到返回:{}", errorDTO.toString());
                    // * 产品故障属性表
                    // * 类型为 1 错误码的时候  根据desc字段解析故障（按序号从低到高排序返回）
                    // * 类型为 2 通信故障的时候 默认 0正常 1故障
                    // * 类型为 3 数值故障的时候 根据max和min字段判断是否故障

                    if (dto != null) {
                        log.info("收到上传的故障信息:{}", dto.toString());
                    }
                    Integer type = errorDTO.getType();
                    if (type.intValue() == AttributeErrorTypeEnum.ERROR_CODE.getType().intValue()) {
                        havcDTOS.addAll(generateHavFaultData(errorDTO, uploadDTO, productCode, realestateId, projectId, dto));
                    } else if (type.intValue() == AttributeErrorTypeEnum.COMMUNICATE.getType()) {
                        //如果通讯故障为1，则入库
                        if (dto.getValue().equals(ErrorConstant.LINK_CODE_ERROR)) {
                            linkDTOS.add(generateLinkFaultData(uploadDTO, productCode, realestateId, projectId, dto));
                        }
                    } else if (type.intValue() == AttributeErrorTypeEnum.VAKUE.getType()) {
                        String max = errorDTO.getMax();
                        String min = errorDTO.getMin();
                        String current = dto.getValue();
                        if (FaultValueUtils.isValueError(current, min, max)) {
                            valueDTOS.add(generateValueFaultData(current, min, max, uploadDTO, productCode, realestateId, projectId, dto));
                        } else {
                            //如果数值正常则进行正常处理
                            redisBOList.add(generateRedisStoreData(uploadDTO, dto));
                            deviceStatusBOList.add(generateDBStoreData(uploadDTO, dto, productCode));
                            pushItems.add(dto);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("这个数据处理有问题啊!code:{}",dto.getCode(),e);
            }
        }
        pushWebsocketData(pushItems, uploadDTO);
        storeStatusToDB(deviceStatusBOList);
        storeStatusToRedis(redisBOList);
        storeFaultDataToDB(havcDTOS, linkDTOS, valueDTOS);
    }

    /**
     * 处理是否是特别需要存储的glc状态数据
     * @param uploadDTO
     * @param dto
     */
    private void filterSpecialGlcWindStatus(AdapterDeviceStatusUploadDTO uploadDTO, ScreenDeviceAttributeDTO dto) {
        /**索引：地址起始：长度
         * 3:0:43
         * 4:50:62
         * 5:200:52
         * 6:300:37
         * 先不管它，直接存储
         */
        homeAutoGlcWindStatusService.saveRecord(uploadDTO.getFamilyId(),uploadDTO.getDeviceSn(),uploadDTO.getProductCode(),
                dto.getCode(),dto.getValue());

    }

    /**
     * 存储故障数据
     *
     * @param havcDTOS  暖通故障数据
     * @param linkDTOS  通信故障数据
     * @param valueDTOS 数值故障数据
     */
    private void storeFaultDataToDB(List<HomeAutoFaultDeviceHavcDTO> havcDTOS, List<HomeAutoFaultDeviceLinkDTO> linkDTOS, List<HomeAutoFaultDeviceValueDTO> valueDTOS) {
        //故障批量入库
        log.info("havcDTOS.size()={},linkDTOS.size()={},valueDTOS.size()=",
                havcDTOS.size(), linkDTOS.size(), valueDTOS.size());

        if (havcDTOS.size() > 0) {
            havcService.batchSave(havcDTOS);
            log.info("批量插入havc故障");
        }

        if (linkDTOS.size() > 0) {
            linkService.batchSave(linkDTOS);
            log.info("批量插入通信故障");
        }
        if (valueDTOS.size() > 0) {
            valueService.batchSave(valueDTOS);
            log.info("批量插入value故障");
        }
    }

    /**
     * 存储最新数据到redis
     *
     * @param redisBOList redis中存储的状态数据
     */
    private void storeStatusToRedis(List<DeviceStatusRedisBO> redisBOList) {
        if (redisBOList.size() > 0) {
            //状态 存储到redis中  以attributeCode为key最小维度, value值为String
            for (DeviceStatusRedisBO bo : redisBOList) {
                //存储缓存
                redisUtils.set(bo.getKey(), bo.getStatusValue());
            }
        }
    }

    /**
     * 存储状态数据到数据库
     *
     * @param deviceStatusBOList 状态数据
     */
    private void storeStatusToDB(List<DeviceStatusBO> deviceStatusBOList) {
        log.info("==>> 准备批量插入deviceStatusBOList.length = {}：", deviceStatusBOList.size());
        //批量插入正常状态
        if (deviceStatusBOList.size() > 0) {
            log.info("插入数据:{}", JSON.toJSONString(deviceStatusBOList));
            iFamilyDeviceStatusService.insertBatchDeviceStatus(deviceStatusBOList);

            log.info("<<== 批量量插入 iFamilyDeviceStatusService.insertBatchDeviceStatus(deviceStatusBOList)完毕");
        }
    }

    /**
     * 推送到websocket的数据
     *
     * @param pushItems 状态数据
     * @param uploadDTO 原始数据
     */
    private void pushWebsocketData(List<ScreenDeviceAttributeDTO> pushItems, AdapterDeviceStatusUploadDTO uploadDTO) {
        //websocket推送
        if (pushItems.size() > 0) {
            uploadDTO.setItems(pushItems);
            webSocketMessageService.pushDeviceStatus(uploadDTO);
        }
    }

    /**
     * 组装数值故障数据
     *
     * @param current      当前值
     * @param min          允许的最小值
     * @param max          允许的最大值
     * @param uploadDTO    原始数据
     * @param productCode  产品编码
     * @param realestateId 楼盘ID
     * @param projectId    项目ID
     * @param dto          原始单属性状态数据
     * @return
     */
    private HomeAutoFaultDeviceValueDTO generateValueFaultData(String current, String min, String max, AdapterDeviceStatusUploadDTO uploadDTO, String productCode, String realestateId, String projectId, ScreenDeviceAttributeDTO dto) {
        HomeAutoFaultDeviceValueDTO valueDTO = new HomeAutoFaultDeviceValueDTO();
        valueDTO.setReference(min.concat("-").concat(max));
        valueDTO.setCurrent(current);
        valueDTO.setDeviceSn(uploadDTO.getDeviceSn());
        valueDTO.setProductCode(productCode);
        valueDTO.setRealestateId(realestateId);
        valueDTO.setProjectId(projectId);
        valueDTO.setFamilyId(uploadDTO.getFamilyId());
        valueDTO.setFaultMsg(ErrorConstant.VALUE_MSG_ERROR.concat(":").concat(dto.getCode()));
        valueDTO.setFaultStatus(ErrorConstant.STATUS_ERROR_UNRESOLVED);
        valueDTO.setFaultTime(LocalDateTime.now());
        return valueDTO;
    }

    /**
     * 组装通讯故障数据
     *
     * @param uploadDTO
     * @param productCode
     * @param realestateId
     * @param projectId
     * @param dto
     * @return
     */
    private HomeAutoFaultDeviceLinkDTO generateLinkFaultData(AdapterDeviceStatusUploadDTO uploadDTO, String productCode, String realestateId, String projectId, ScreenDeviceAttributeDTO dto) {
        HomeAutoFaultDeviceLinkDTO linkDTO = new HomeAutoFaultDeviceLinkDTO();
        linkDTO.setDeviceSn(uploadDTO.getDeviceSn());
        linkDTO.setProductCode(productCode);
        linkDTO.setProjectId(projectId);
        linkDTO.setRealestateId(realestateId);
        linkDTO.setFamilyId(uploadDTO.getFamilyId());
        linkDTO.setFaultMsg(ErrorConstant.LINK_MSG_ERROR);
        linkDTO.setFaultStatus(ErrorConstant.STATUS_ERROR_UNRESOLVED);
        linkDTO.setFaultTime(LocalDateTime.now());
        return linkDTO;

    }

    /**
     * 组装暖通故障数据
     *
     * @param errorDTO
     * @param uploadDTO
     * @param productCode
     * @param realestateId
     * @param projectId
     * @param dto
     */
    private List<HomeAutoFaultDeviceHavcDTO> generateHavFaultData(AttributeErrorDTO errorDTO, AdapterDeviceStatusUploadDTO uploadDTO, String productCode, String realestateId, String projectId, ScreenDeviceAttributeDTO dto) {
        List<HomeAutoFaultDeviceHavcDTO> havcTempDTOs = Lists.newArrayList();
        List<String> stringList = errorDTO.getDesc();
        Collections.reverse(stringList);
        //如果value转化位16位二进制为1，且跟list对应，则新增故障
        String value = dto.getValue();
        Integer valueInt = Integer.parseInt(value);

        if (valueInt > FaultValueUtils.HVAC_INT_MAX ||
                valueInt < FaultValueUtils.HVAC_INT_MIN ||
                stringList.size() != FaultValueUtils.HVAC_ERROR_STRING_LENGTH) {
            //如果value小于0或者大于65536，或者sringList不为16则返回
            return havcTempDTOs;
        }

        char[] chars = FaultValueUtils.toBinary(valueInt, 16);
        log.info("暖通故障,productCode:{},attributeCode:{},value:{},转换后:{}", productCode, dto.getCode(), value, new String(chars));


        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '1') {
                HomeAutoFaultDeviceHavcDTO havcDTO = new HomeAutoFaultDeviceHavcDTO();
                havcDTO.setDeviceSn(uploadDTO.getDeviceSn());
                havcDTO.setProductCode(productCode);
                havcDTO.setRealestateId(realestateId);
                havcDTO.setProjectId(projectId);
                havcDTO.setFamilyId(uploadDTO.getFamilyId());
                havcDTO.setFaultMsg(stringList.get(i));
                havcDTO.setFaultStatus(ErrorConstant.STATUS_ERROR_UNRESOLVED);
                havcDTO.setFaultTime(LocalDateTime.now());
                havcTempDTOs.add(havcDTO);
            }
        }
        return havcTempDTOs;
    }

    /**
     * 组装存储数据库的状态数据
     *
     * @param uploadDTO
     * @param dto
     * @param productCode
     * @return
     */
    private DeviceStatusBO generateDBStoreData(AdapterDeviceStatusUploadDTO uploadDTO, ScreenDeviceAttributeDTO dto, String productCode) {

        DeviceStatusBO deviceStatusBO = new DeviceStatusBO();
        deviceStatusBO.setDeviceSn(uploadDTO.getDeviceSn());
        deviceStatusBO.setFamilyCode(uploadDTO.getFamilyCode());
        deviceStatusBO.setFamilyId(uploadDTO.getFamilyId());
        deviceStatusBO.setStatusCode(dto.getCode());
        deviceStatusBO.setStatusValue(dto.getValue());
        deviceStatusBO.setProductCode(productCode);

        log.info("deviceStatusBO:{}", deviceStatusBO.toString());
        return deviceStatusBO;
    }

    /**
     * 组装存储redis的状态数据
     *
     * @param uploadDTO
     * @param dto
     */
    private DeviceStatusRedisBO generateRedisStoreData(AdapterDeviceStatusUploadDTO uploadDTO, ScreenDeviceAttributeDTO dto) {
        String familyDeviceStatusStoreKey = String.format(RedisCacheConst.FAMILY_DEVICE_STATUS_STORE_KEY,
                uploadDTO.getFamilyCode(), uploadDTO.getProductCode(),
                uploadDTO.getDeviceSn(), dto.getCode());

        DeviceStatusRedisBO deviceStatusRedisBO = new DeviceStatusRedisBO();
        deviceStatusRedisBO.setKey(familyDeviceStatusStoreKey);
        deviceStatusRedisBO.setStatusValue(dto.getValue());
        return deviceStatusRedisBO;
    }

    /**
     * 判断是否故障数据
     */
    private AttributeErrorDTO judgeFaultData(ScreenDeviceAttributeDTO dto, String productCode) {

        AttributeErrorQryDTO request = new AttributeErrorQryDTO();

        request.setCode(dto.getCode());
        request.setProductCode(productCode);

        AttributeErrorDTO errorDTO = iProductAttributeErrorService.getErrorAttributeInfo(request);
        return errorDTO;

    }


}
