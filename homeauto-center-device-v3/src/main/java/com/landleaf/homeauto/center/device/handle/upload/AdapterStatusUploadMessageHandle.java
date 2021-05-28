package com.landleaf.homeauto.center.device.handle.upload;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.chain.screen.status.ScreenStatusDealChain;
import com.landleaf.homeauto.center.device.chain.screen.status.ScreenStatusDealHandle;
import com.landleaf.homeauto.center.device.filter.sys.SysProductRelatedFilter;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAlarmMessageDO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAlarmMessageService;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceAlarmUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterSecurityAlarmMsgItemDTO;
import com.landleaf.homeauto.common.domain.dto.device.SysProductRelatedRuleDeviceDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.FamilyDeviceAttrConstraintEnum;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
     * @param: uploadDTO
     * @description:  系统设备间有关联关系的，需做关联状态上报（此逻辑与大屏约定，各自处理）
     * @return: java.util.Collection<? extends com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO>
     * @author: wyl
     * @date: 2021/5/27
     */
    private List<AdapterDeviceStatusUploadDTO> combineRelatedUploadStatus(AdapterDeviceStatusUploadDTO uploadDTO) {

        List<AdapterDeviceStatusUploadDTO> result = Lists.newArrayList();
        String houseTemplateId = uploadDTO.getHouseTemplateId();
        String familyId = uploadDTO.getFamilyId();
        String deviceSn = uploadDTO.getDeviceSn();
        List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();
         //获取设备信息
        ScreenTemplateDeviceBO device = contactScreenService.getFamilyDeviceBySn(BeanUtil.convertString2Long(houseTemplateId), BeanUtil.convertString2Long(familyId), deviceSn);
        Integer systemFlag = device.getSystemFlag();
        /**
         * 1、获取每个项目户型下系统本身与系统设备间的关联关系
         */
        for (ScreenDeviceAttributeDTO item : items) {
            Integer attrConstraint = sysProductRelatedFilter.checkAttrConstraint(BeanUtil.convertString2Long(houseTemplateId), item.getCode(), systemFlag, deviceSn);
            item.setAttrConstraint(attrConstraint);
            if(attrConstraint!=null&&attrConstraint.intValue()!= FamilyDeviceAttrConstraintEnum.NORMAL_ATTR.getType()
            ){
                List<SysProductRelatedRuleDeviceDTO> ruleDeviceDTOS = sysProductRelatedFilter.filterRelatedDevices(BeanUtil.convertString2Long(houseTemplateId), item.getCode(), systemFlag, deviceSn);
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
            data.setDeviceSn(ruleDeviceDTO.getDeviceSn());
            data.setSystemFlag(ruleDeviceDTO.getSystemFlag());
            ScreenDeviceAttributeDTO attributeDTO = new ScreenDeviceAttributeDTO();
            BeanUtils.copyProperties(originItem,attributeDTO);
            attributeDTO.setAttrConstraint(sysProductRelatedFilter.checkAttrConstraint(BeanUtil.convertString2Long(origin.getHouseTemplateId())
                    ,attributeDTO.getCode(),ruleDeviceDTO.getSystemFlag(),ruleDeviceDTO.getDeviceSn()));
            List<ScreenDeviceAttributeDTO> items = Lists.newArrayList(attributeDTO);
            data.setItems(items);
            result.add(data);
        }
        return result;

    }


}
