package com.landleaf.homeauto.center.device.handle.upload;

import com.landleaf.homeauto.center.device.chain.screen.status.ScreenStatusDealChain;
import com.landleaf.homeauto.center.device.chain.screen.status.ScreenStatusDealHandle;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAlarmMessageDO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoAlarmMessageService;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceAlarmUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterSecurityAlarmMsgItemDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
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
        ScreenStatusDealHandle handle = screenStatusDealChain.getHandle();
        ScreenStatusDealComplexBO complexBO = ScreenStatusDealComplexBO.builder().uploadDTO(uploadDTO)
                .deviceBO(null).attrCategoryBOs(null).build();
        handle.handle0(complexBO);
    }


}
