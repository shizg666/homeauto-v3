package com.landleaf.homeauto.center.device.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.filter.AttributeShortCodeConvertFilter;
import com.landleaf.homeauto.center.device.filter.IAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.filter.sys.ISysAttributeOutPutFilter;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.center.device.model.dto.DeviceAttrPrecisionValueDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZDeviceStatusTotalVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.websocket.DeviceStatusMessage;
import com.landleaf.homeauto.common.domain.websocket.MessageEnum;
import com.landleaf.homeauto.common.domain.websocket.MessageModel;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
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
    @Resource
    private List<ISysAttributeOutPutFilter> sysAttributeOutPutFilters;
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
        Long familyId = adapterDeviceStatusUploadDTO.getFamilyId();
        ScreenTemplateDeviceBO templateDeviceBO = contactScreenService.getFamilyDeviceBySn(adapterDeviceStatusUploadDTO.getHouseTemplateId(),
                familyId, deviceSn);
        Integer systemFlag = templateDeviceBO.getSystemFlag();

        Map<String, Object> attrMap = adapterDeviceStatusUploadDTO.getItems().stream().filter(i-> !Objects.isNull(i.getValue())).collect(Collectors.toMap(ScreenDeviceAttributeDTO::getCode, ScreenDeviceAttributeDTO::getValue));
        // 处理设备状态的精度
        Map<String, Object>  resultAttrMap4APP=buildPushData(attrMap,templateDeviceBO.getProductCode(),systemFlag,1);
        Map<String, Object>  resultAttrMap4Applets=buildPushData(attrMap,templateDeviceBO.getProductCode(),systemFlag,2);

        if(resultAttrMap4APP.size()>0){
            pushDeviceToAPP(familyId,templateDeviceBO,resultAttrMap4APP);
        }
        if(resultAttrMap4Applets.size()>0){
            pushDeviceToApplets(familyId,templateDeviceBO,resultAttrMap4Applets);
        }
    }

    /**
     * 构建转换后的属性对象
     * @param attrMap       上传的属性code-value
     * @param productCode   产品编码
     * @param target       推送目标1：app,2:applets
     * @return  转换后的code-value
     */
    private Map<String, Object> buildPushData(Map<String, Object> attrMap, String productCode, Integer systemFlag,int target) {
        if(systemFlag!=null&&systemFlag== FamilySystemFlagEnum.SYS_DEVICE.getType()){
            return buildPush4SystemDevice(attrMap,productCode,target);
        }else {
            return buildPush4NormalOrSubDevice(attrMap,productCode,target);
        }
    }


    /**
     * 构建转换后的属性对象
     * @param attrMap       上传的属性code-value
     * @param productCode   产品编码
     * @param target       推送目标1：app,2:applets
     * @return  转换后的code-value
     */
    private Map<String, Object> buildPush4NormalOrSubDevice(Map<String, Object> attrMap, String productCode, int target) {
        Map<String, Object>  resultAttrMap = Maps.newHashMap();
        List<ScreenProductAttrBO>     functionAttrs= contactScreenService.getDeviceFunctionAttrsByProductCode(productCode);
        Map<String, ScreenProductAttrBO> attrInfoMap = functionAttrs.stream().collect(Collectors.toMap(ScreenProductAttrBO::getAttrCode, i -> i, (v1, v2) -> v2));
        for (String attr : attrMap.keySet()) {
            Object attributeValue = attrMap.get(attr);
            ScreenProductAttrBO attrInfo = attrInfoMap.get(attr);
            if(attrInfo==null){
                log.info("该上报属性:{}非app展示属性,不推送app",attr);
                continue;
            }
            for (IAttributeOutPutFilter filter : attributeOutPutFilters) {
                if (filter.checkFilter(attrInfo)) {
                    if(target==1){
                        attributeValue = filter.appGetStatusHandle(attributeValue, attrInfo);
                    }else {
                        attributeValue = filter.handle(attributeValue, attrInfo);
                    }
                }
            }
            resultAttrMap.put(attr, attributeValue);
        }

        return resultAttrMap;

    }

    private Map<String, Object> buildPush4SystemDevice(Map<String, Object> attrMap, String productCode, int target) {
        Map<String, Object>  resultAttrMap = Maps.newHashMap();
        List<ScreenSysProductAttrBO>     functionAttrs= contactScreenService.getSysDeviceFunctionAttrsByProductCode(productCode);
        Map<String, ScreenSysProductAttrBO> attrInfoMap = functionAttrs.stream().collect(Collectors.toMap(ScreenSysProductAttrBO::getAttrCode, i -> i, (v1, v2) -> v2));
        for (String attr : attrMap.keySet()) {
            Object attributeValue = attrMap.get(attr);
            ScreenSysProductAttrBO attrInfo = attrInfoMap.get(attr);
            if(attrInfo==null){
                log.info("该上报属性:{}非app展示属性,不推送app",attr);
                continue;
            }
            for (ISysAttributeOutPutFilter filter : sysAttributeOutPutFilters) {
                if (filter.checkFilter(attrInfo)) {
                    if(target==1){
                        attributeValue = filter.appGetStatusHandle(attributeValue, attrInfo);
                    }else {
                        attributeValue = filter.handle(attributeValue, attrInfo);
                    }
                }
            }
            resultAttrMap.put(attr, attributeValue);
        }

        return resultAttrMap;

    }

    private void pushDeviceToApplets(Long familyId, ScreenTemplateDeviceBO templateDeviceDO, Map<String, Object> attrMap) {
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
            mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_APPLETS, "*", JSON.toJSONString(new MessageModel(MessageEnum.DEVICE_STATUS, String.valueOf(familyId), deviceStatusMessage)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void pushDeviceToAPP(Long familyId, ScreenTemplateDeviceBO templateDeviceBO, Map<String, Object> attrMap) {
        try {
            DeviceStatusMessage deviceStatusMessage = buildPushStatusCommonProperties(familyId,templateDeviceBO);
            deviceStatusMessage.setAttributes(attrMap);
            mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_APP, "*", JSON.toJSONString(new MessageModel(MessageEnum.DEVICE_STATUS, String.valueOf(familyId), deviceStatusMessage)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private DeviceStatusMessage buildPushStatusCommonProperties(Long familyId, ScreenTemplateDeviceBO templateDeviceBO) {
        DeviceStatusMessage deviceStatusMessage = new DeviceStatusMessage();
        deviceStatusMessage.setFamilyId(familyId);
        deviceStatusMessage.setDeviceId(templateDeviceBO.getId());
        deviceStatusMessage.setCategory(templateDeviceBO.getCategoryCode());
        deviceStatusMessage.setProductCode(templateDeviceBO.getProductCode());
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

    /**
     * 推送设备运行状态统计
     * @param familyId
     * @param total
     */
    public void pushSwitchTotal(Long familyId,List<JZDeviceStatusTotalVO> total) {
        mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_WEBSOCKET_TO_APPLETS, "*", JSON.toJSONString(new MessageModel(MessageEnum.DEVICE_CATEGORY_TOTAL, String.valueOf(familyId), total)));
    }
}
