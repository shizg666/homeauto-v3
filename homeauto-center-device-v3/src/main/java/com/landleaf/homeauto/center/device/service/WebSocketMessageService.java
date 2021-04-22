package com.landleaf.homeauto.center.device.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.enums.AttrAppFlagEnum;
import com.landleaf.homeauto.center.device.filter.AttributeShortCodeConvertFilter;
import com.landleaf.homeauto.center.device.filter.IAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.DeviceAttrPrecisionValueDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.websocket.DeviceStatusMessage;
import com.landleaf.homeauto.common.domain.websocket.MessageEnum;
import com.landleaf.homeauto.common.domain.websocket.MessageModel;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Yujiumin
 * @version 2020/9/4
 */
@Service
@Slf4j
public class WebSocketMessageService {

    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Resource
    private List<IAttributeOutPutFilter> attributeOutPutFilters;
    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private AttributeShortCodeConvertFilter attributeShortCodeConvertFilter;
    @Autowired
    private IContactScreenService contactScreenService;
    /**
     * 推送设备的状态信息
     *
     * @param adapterDeviceStatusUploadDTO 设备状态信息
     * @param deviceSn
     */
    public void pushDeviceStatus(AdapterDeviceStatusUploadDTO adapterDeviceStatusUploadDTO, String deviceSn) {
        String familyId = adapterDeviceStatusUploadDTO.getFamilyId();
        TemplateDeviceDO templateDeviceDO = familyService.getDeviceByDeviceCode(BeanUtil.convertString2Long(familyId), deviceSn);
        List<ScreenProductAttrBO> functionAttrs = contactScreenService.getDeviceFunctionAttrsByProductCode(templateDeviceDO.getProductCode());
        Map<String, ScreenProductAttrBO> attrInfoMap = functionAttrs.stream().collect(Collectors.toMap(ScreenProductAttrBO::getAttrCode, i -> i, (v1, v2) -> v2));
        // 处理设备状态的精度
        Map<String, Object> attrMap = adapterDeviceStatusUploadDTO.getItems().stream().filter(i-> !Objects.isNull(i.getValue())).collect(Collectors.toMap(ScreenDeviceAttributeDTO::getCode, ScreenDeviceAttributeDTO::getValue));
        Map<String, Object>  resultAttrMap = Maps.newHashMap();
        for (String attr : attrMap.keySet()) {
            Object attributeValue = attrMap.get(attr);
            ScreenProductAttrBO attrInfo = attrInfoMap.get(attr);
            if(attrInfo==null){
                log.info("该上报属性:{}非app展示属性,不推送app",attr);
                continue;
            }
            for (IAttributeOutPutFilter filter : attributeOutPutFilters) {
                if (filter.checkFilter(attrInfo)) {
                    attributeValue = filter.handle(attributeValue, attrInfo);
                }
            }
            resultAttrMap.put(attr, attributeValue);
        }
        if(resultAttrMap.size()>0){
            pushDeviceToAPP(familyId,templateDeviceDO,resultAttrMap);
            pushDeviceToApplets(familyId,templateDeviceDO,resultAttrMap);
        }
    }

    private void pushDeviceToApplets(String familyId, TemplateDeviceDO templateDeviceDO, Map<String, Object> attrMap) {
        try {
            DeviceStatusMessage deviceStatusMessage = buildPushStatusCommonProperties(familyId,templateDeviceDO);
            Map<String,Object> convertAttrMap = Maps.newHashMapWithExpectedSize(16);
            for (String s : attrMap.keySet()) {
                Object o = attrMap.get(s);
                convertAttrMap.put(s,o);
                if(o instanceof DeviceAttrPrecisionValueDTO){
                    DeviceAttrPrecisionValueDTO o1 = (DeviceAttrPrecisionValueDTO) o;
                    convertAttrMap.put(s,o1.getCurrentValue());
                }
            }
            deviceStatusMessage.setAttributes(convertAttrMap);
            mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_APPLETS, "*", JSON.toJSONString(new MessageModel(MessageEnum.DEVICE_STATUS, familyId, deviceStatusMessage)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void pushDeviceToAPP(String familyId, TemplateDeviceDO templateDeviceDO, Map<String, Object> attrMap) {
        try {
            DeviceStatusMessage deviceStatusMessage = buildPushStatusCommonProperties(familyId,templateDeviceDO);
            deviceStatusMessage.setAttributes(attrMap);
            mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_APP, "*", JSON.toJSONString(new MessageModel(MessageEnum.DEVICE_STATUS, familyId, deviceStatusMessage)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private DeviceStatusMessage buildPushStatusCommonProperties(String familyId, TemplateDeviceDO templateDeviceDO) {
        DeviceStatusMessage deviceStatusMessage = new DeviceStatusMessage();
        deviceStatusMessage.setFamilyId(familyId);
        deviceStatusMessage.setDeviceId(String.valueOf(templateDeviceDO.getId()));
        deviceStatusMessage.setCategory(templateDeviceDO.getCategoryCode());
        return deviceStatusMessage;
    }



    /**
     * 推送家庭授权信息
     *
     * @param familyId 家庭ID
     * @param status   状态信息
     */
    public void pushFamilyAuth(String familyId, Integer status) {
        Map<String, Integer> data = Maps.newHashMap();
        data.put("status", status);
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_APP, "*", JSON.toJSONString(new MessageModel(MessageEnum.FAMILY_AUTH, familyId, data)));
    }


    public static void main(String[] args) {
        boolean number = NumberUtils.isNumber("1.1008");
        System.out.println(number);
    }

}
