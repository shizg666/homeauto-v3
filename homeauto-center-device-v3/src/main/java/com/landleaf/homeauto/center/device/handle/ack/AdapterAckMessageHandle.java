package com.landleaf.homeauto.center.device.handle.ack;

import com.alibaba.fastjson.JSON;
import com.landleaf.homeauto.center.device.filter.sys.SysProductRelatedFilter;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.ack.AckReadStatusExtendService;
import com.landleaf.homeauto.center.device.service.mybatis.IAdapterRequestMsgLogService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterSceneControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;

/**
 * 监听Adapter到APP的响应ack
 * 将响应存储到redis缓存
 *
 * @author zhanghongbin
 */
@Component
@Slf4j
public class AdapterAckMessageHandle implements Observer {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IAdapterRequestMsgLogService adapterRequestMsgLogService;
    @Autowired
    private IContactScreenService contactScreenService;
    @Autowired
    private SysProductRelatedFilter sysProductRelatedFilter;

    @Autowired
    private AckReadStatusExtendService ackReadStatusExtendService;
    @Override
    @Async(value = "bridgeDealAckMessageExecute")
    public void update(Observable o, Object arg) {
        AdapterMessageAckDTO message = (AdapterMessageAckDTO) arg;
            // 组装数据
            String messageName = message.getMessageName();

            String messageId = message.getMessageId();
            String data = null;
            if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_DEVICE_WRITE.getName())) {
                // 控制设备ack   TODO ack时其它逻辑处理
                data =JSON.toJSONString((AdapterDeviceControlAckDTO) message);
            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_DEVICE_STATUS_READ.getName())) {
                // 读取状态
                AdapterDeviceStatusReadAckDTO deviceStatusReadAckDTO = (AdapterDeviceStatusReadAckDTO) message;
                if(deviceStatusReadAckDTO!=null){
                    buildUploadStatusAttr(deviceStatusReadAckDTO);
                    // 走上传状态逻辑
                    ackReadStatusExtendService.triggerUploadStatus(deviceStatusReadAckDTO);
                }
                data =JSON.toJSONString(deviceStatusReadAckDTO);
            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_FAMILY_CONFIG_UPDATE.getName())) {
                data =JSON.toJSONString((AdapterConfigUpdateAckDTO) message);
            } else if (StringUtils.equals(messageName, AdapterMessageNameEnum.TAG_FAMILY_SCENE_SET.getName())) {
                // 控制场景
                data =JSON.toJSONString((AdapterSceneControlAckDTO) message);
            } else {
                return;
            }
           String key = RedisCacheConst.ADAPTER_APP_MSG_WAIT_ACK_PREFIX.concat(messageId);
           redisUtils.set(key, data, RedisCacheConst.COMMON_EXPIRE);
            try {
                adapterRequestMsgLogService.updateRecord(message);
            } catch (Exception e) {
                log.error("更新操作日志异常，装作没看见....");
            }


    }

    /**
     * 填充状态返回信息的属性描述信息
     */
    private void buildUploadStatusAttr(AdapterDeviceStatusReadAckDTO deviceStatusReadAckDTO) {
        try {
            Long houseTemplateId = BeanUtil.convertString2Long(deviceStatusReadAckDTO.getHouseTemplateId());

            Long familyId = BeanUtil.convertString2Long(deviceStatusReadAckDTO.getFamilyId());
            String deviceSn = deviceStatusReadAckDTO.getDeviceSn();
            ScreenTemplateDeviceBO device = contactScreenService.getFamilyDeviceBySn(houseTemplateId,
                    familyId, deviceSn);
            deviceStatusReadAckDTO.setSystemFlag(device.getSystemFlag());
            List<ScreenDeviceAttributeDTO> items = deviceStatusReadAckDTO.getItems();
            for (ScreenDeviceAttributeDTO item : items) {
                item.setAttrConstraint(sysProductRelatedFilter.checkAttrConstraint(houseTemplateId,item.getCode(),
                        device.getSystemFlag(),deviceSn));
            }
        } catch (Exception e) {
            log.error("返回读取状态信息，封装设备类型等异常:{}",e.getMessage());
        }
    }



}