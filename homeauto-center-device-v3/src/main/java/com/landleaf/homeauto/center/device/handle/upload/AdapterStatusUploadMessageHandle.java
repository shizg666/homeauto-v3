package com.landleaf.homeauto.center.device.handle.upload;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.chain.screen.status.ScreenStatusDealChain;
import com.landleaf.homeauto.center.device.chain.screen.status.ScreenStatusDealHandle;
import com.landleaf.homeauto.center.device.filter.sys.SysProductRelatedFilter;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDevicePowerDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAlarmMessageDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAlarmMessageService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceAlarmUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterHVACPowerUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterSecurityAlarmMsgItemDTO;
import com.landleaf.homeauto.common.domain.dto.device.SysProductRelatedRuleDeviceDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.screen.mqtt.ScreenPowerAttributeDTO;
import com.landleaf.homeauto.common.enums.FamilyDeviceAttrConstraintEnum;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private IHomeAutoAlarmMessageService homeAutoAlarmMessageService;
    @Autowired
    private ScreenStatusDealChain screenStatusDealChain;
    @Autowired
    private SysProductRelatedFilter sysProductRelatedFilter;
    @Autowired
    private IContactScreenService contactScreenService;

    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;



    @Override
    @Async("bridgeDealUploadMessageExecute")
    public void update(Observable o, Object arg) {

        AdapterMessageUploadDTO message = (AdapterMessageUploadDTO) arg;
        // 组装数据
        String messageName = message.getMessageName();

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

        }else if (StringUtils.equals(AdapterMessageNameEnum.HVAC_POWER_UPLOAD.getName(), messageName)) {
            log.info("[大屏上报功率状态消息]:消息编号:[{}],消息体:{}", message.getMessageId(), message);

            //此时为功率上报
            AdapterHVACPowerUploadDTO uploadDTO = (AdapterHVACPowerUploadDTO) message;
            dealHVACPower(uploadDTO);

        }

    }

    private void dealHVACPower(AdapterHVACPowerUploadDTO uploadDTO) {

        List<FamilyDevicePowerDO> powerDeviceDOS = Lists.newArrayList();

        List<ScreenPowerAttributeDTO> data = uploadDTO.getItems();
        if (CollectionUtils.isEmpty(data)) {
            return;
        }
        HomeAutoFamilyDO homeAutoFamilyDO = iHomeAutoFamilyService.getById(uploadDTO.getFamilyId());
        Long realestateId2 = homeAutoFamilyDO.getRealestateId();
        Long projectId = homeAutoFamilyDO.getProjectId();

        Long houseTemplateId = uploadDTO.getHouseTemplateId();
        Long familyId = uploadDTO.getFamilyId();
        data.stream().forEach(i -> {

            ScreenTemplateDeviceBO device = contactScreenService.getFamilyDeviceBySn(houseTemplateId,
                    familyId, String.valueOf(uploadDTO.getDeviceSn()));
            LocalDateTime localDateTime = LocalDateTimeUtil.date2LocalDateTime(new Date());

            FamilyDevicePowerDO powerDO = new FamilyDevicePowerDO();
            powerDO.setFamilyId(uploadDTO.getFamilyId());
            powerDO.setStatusCode(i.getAttrTag());
            powerDO.setStatusValue(i.getAttrValue());
            powerDO.setUploadTime(LocalDateTime.ofEpochSecond(i.getPowerTime(),0, ZoneOffset.ofHours(8)));
            powerDO.setDeviceSn(String.valueOf(uploadDTO.getDeviceSn()));
            powerDO.setProductCode(uploadDTO.getProductCode());
            powerDO.setFamilyId(familyId);
            powerDO.setRealestateId(realestateId2);
            powerDO.setProjectId(projectId);
            powerDO.setCategoryCode(device.getCategoryCode());
            powerDeviceDOS.add(powerDO);

        });

        if (powerDeviceDOS.size() > 0) {
            log.info("插入暖通功率数据:{}", JSON.toJSONString(powerDeviceDOS));
            // 发送到异步存储
            mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_DEVICE_TO_CENTER_DATA, RocketMqConst.TAG_POWER_TO_DATA, JSON.toJSONString(powerDeviceDOS));

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
            alarmMessageDO.setFamilyId(alarmUploadDTO.getFamilyId());
            alarmMessageDO.setAlarmCancelFlag(0);
            alarmMessageDO.setAlarmContext(i.getContext());
            alarmMessageDO.setAlarmTime(LocalDateTimeUtil.date2LocalDateTime(new Date(i.getAlarmTime())));
            return alarmMessageDO;
        }).collect(Collectors.toList());

        homeAutoAlarmMessageService.saveBatch(saveData);

    }

    /**
     * 处理上报状态数据
     *
     * @param uploadDTO 状态数据
     */
    public void dealUploadStatus(AdapterDeviceStatusUploadDTO uploadDTO) {
        //获取设备信息
        buildUploadStatusAttr(uploadDTO);
        List<AdapterDeviceStatusUploadDTO> uploadDTOS = Lists.newArrayList(uploadDTO);
        List<AdapterDeviceStatusUploadDTO> relatedUploadStatus = combineRelatedUploadStatus(uploadDTO);
        if(!CollectionUtils.isEmpty(relatedUploadStatus)){
            uploadDTOS.addAll(relatedUploadStatus);
        }
        dealUploadStatus(uploadDTOS);
    }

    /**
     * 处理上报状态数据
     *
     * @param uploadDTOS 状态数据
     */
    public void dealUploadStatus(List<AdapterDeviceStatusUploadDTO> uploadDTOS) {
        for (AdapterDeviceStatusUploadDTO uploadDTO : uploadDTOS) {
            ScreenStatusDealHandle handle = screenStatusDealChain.getHandle();
            ScreenStatusDealComplexBO complexBO = ScreenStatusDealComplexBO.builder().uploadDTO(uploadDTO)
                    .deviceBO(null).attrCategoryBOs(null).build();
            handle.handle0(complexBO);
        }
    }
    /**
     * 填充上报状态信息的属性描述信息
     * @param uploadDTO
     */
    private void buildUploadStatusAttr(AdapterDeviceStatusUploadDTO uploadDTO) {
        Long houseTemplateId = uploadDTO.getHouseTemplateId();
        Long familyId = uploadDTO.getFamilyId();
        ScreenTemplateDeviceBO device = contactScreenService.getFamilyDeviceBySn(houseTemplateId,
                familyId, String.valueOf(uploadDTO.getDeviceSn()));
        uploadDTO.setSystemFlag(device.getSystemFlag());
        List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();
        for (ScreenDeviceAttributeDTO item : items) {
            item.setAttrConstraint(sysProductRelatedFilter.checkAttrConstraint(houseTemplateId,item.getCode(),
                    device.getSystemFlag(),String.valueOf(uploadDTO.getDeviceSn())));
        }
    }


    /**
     * @param: uploadDTO
     * @description:  系统设备间有关联关系的，需做关联状态上报（此逻辑与大屏约定，各自处理）
     * @return: java.util.Collection<? extends com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO>
     * @author: wyl
     * @date: 2021/5/27
     */
    private List<AdapterDeviceStatusUploadDTO> combineRelatedUploadStatus(AdapterDeviceStatusUploadDTO uploadDTO) {

        List<AdapterDeviceStatusUploadDTO> result = Lists.newArrayList();
        Long houseTemplateId = uploadDTO.getHouseTemplateId();
        Integer deviceSn = uploadDTO.getDeviceSn();
        Integer systemFlag = uploadDTO.getSystemFlag();
        List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();

        /**
         * 1、获取每个项目户型下系统本身与系统设备间的关联关系
         */
        for (ScreenDeviceAttributeDTO item : items) {
            Integer attrConstraint = item.getAttrConstraint();
            if(attrConstraint!=null&&attrConstraint!= FamilyDeviceAttrConstraintEnum.NORMAL_ATTR.getType()
            ){
                List<SysProductRelatedRuleDeviceDTO> ruleDeviceDTOS = sysProductRelatedFilter.filterRelatedDevices(houseTemplateId, item.getCode(), systemFlag, String.valueOf(deviceSn));
                if(!CollectionUtils.isEmpty(ruleDeviceDTOS)){
                    result.addAll(buildRelatedUploadStatusDTO(ruleDeviceDTOS, uploadDTO, item));
                }
            }
        }
          return result;

    }

    private List<AdapterDeviceStatusUploadDTO> buildRelatedUploadStatusDTO(List<SysProductRelatedRuleDeviceDTO> ruleDeviceDTOS, AdapterDeviceStatusUploadDTO origin, ScreenDeviceAttributeDTO originItem) {
        List<AdapterDeviceStatusUploadDTO> result = Lists.newArrayList();
        for (SysProductRelatedRuleDeviceDTO ruleDeviceDTO : ruleDeviceDTOS) {
            AdapterDeviceStatusUploadDTO data = new AdapterDeviceStatusUploadDTO();
            BeanUtils.copyProperties(origin,data);
            data.setProductCode(ruleDeviceDTO.getProductCode());
            data.setSystemFlag(ruleDeviceDTO.getSystemFlag());
            data.setDeviceSn(Integer.parseInt(ruleDeviceDTO.getDeviceSn()));
            ScreenDeviceAttributeDTO attributeDTO = new ScreenDeviceAttributeDTO();
            BeanUtils.copyProperties(originItem,attributeDTO);
            List<ScreenDeviceAttributeDTO> items = Lists.newArrayList(attributeDTO);
            data.setItems(items);
            buildUploadStatusAttr(data);
            result.add(data);
        }
        return result;

    }


}
