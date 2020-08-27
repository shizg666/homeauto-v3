package com.landleaf.homeauto.center.device.handle.upload;

import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
    private IHomeAutoFamilyService familyService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    @Async("bridgeDealUploadMessageExecute")
    public void update(Observable o, Object arg) {

        AdapterMessageUploadDTO message = (AdapterMessageUploadDTO) arg;
        // 走下面处理逻辑
        Integer terminalType = message.getTerminalType();
        // 组装数据
        String messageName = message.getMessageName();
        if (terminalType != null && TerminalTypeEnum.SCREEN.getCode().intValue() == terminalType.intValue()
                && StringUtils.equals(AdapterMessageNameEnum.DEVICE_STATUS_UPLOAD.getName(), messageName)) {
            FamilyInfoBO familyInfoBO = null;
            try {
                familyInfoBO = familyService.getFamilyInfoByTerminalMac(message.getTerminalMac(), message.getTerminalType());
            } catch (Exception e) {
                log.error("[大屏上报设备状态消息]获取家庭信息异常,[终端地址]:{}", message.getTerminalMac());
                return;
            }
            if (familyInfoBO == null) {
                log.error("[大屏上报设备状态消息]家庭不存在,[终端地址]:{}", message.getTerminalMac());
                return;
            }
            AdapterDeviceStatusUploadDTO uploadDTO = (AdapterDeviceStatusUploadDTO) message;
            uploadDTO.setFamilyId(familyInfoBO.getFamilyId());
            uploadDTO.setFamilyCode(familyInfoBO.getFamilyCode());
            //发布消息出去
            try {

                log.info("[大屏上报设备状态消息]:消息编号:[{}],消息体:{}",
                        message.getMessageId(), message);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            //状态 存储到redis中  以attributeCode为key最小维度, value值为String



            for (ScreenDeviceAttributeDTO dto : uploadDTO.getItems()) {
                String familyDeviceStatusStoreKey = String.format(RedisCacheConst.FAMILY_DEVICE_STATUS_STORE_KEY,
                        uploadDTO.getFamilyCode(), uploadDTO.getProductCode(), uploadDTO.getDeviceSn(), dto.getCode());
                redisUtils.set(familyDeviceStatusStoreKey,dto.getValue());
            }
            /**
             * 1、状态推给app
             * 2、最新状态存储--redis 结构：属性code级 ， familyCode:deviceSn:AttributeCode
             * 3、数据库存储（功能属性、故障属性[暖通故障、数值故障、通信故障]）
             * 4、故障
             * 5、全关全开
             */


        }

    }
}
