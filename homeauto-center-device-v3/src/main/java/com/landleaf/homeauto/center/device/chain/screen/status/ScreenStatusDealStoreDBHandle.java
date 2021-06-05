package com.landleaf.homeauto.center.device.chain.screen.status;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName ScreenStatusDealStoreDBHandle
 * @Description: 状态数据存储DB的handle
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealStoreDBHandle extends ScreenStatusDealHandle {
    @Autowired
    private MQProducerSendMsgProcessor mqProducerSendMsgProcessor;

    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        log.info("状态处理:存储数据库");
        List<DeviceStatusBO> deviceStatusBOList = Lists.newArrayList();
        if (checkCondition(dealComplexBO)) {
            ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
            List<ScreenProductAttrCategoryBO> categoryAttrBos = dealComplexBO.getAttrCategoryBOs().stream().filter(i -> {
                return i.getFunctionType().intValue() == AttrFunctionEnum.FUNCTION_ATTR.getType() ||
                        i.getFunctionType().intValue() == AttrFunctionEnum.BASE_ATTR.getType();
            }).collect(Collectors.toList());
            List<String> codes = Lists.newArrayList();
            if(deviceBO.getSystemFlag()!=null&&deviceBO.getSystemFlag()== FamilySystemFlagEnum.SYS_DEVICE.getType()){
                //系统设备
                codes=categoryAttrBos.stream()
                        .map(i -> i.getSysAttrBO()).collect(Collectors.toList()).stream().map(i -> i.getAttrCode()).collect(Collectors.toList());
            }else {
                codes=categoryAttrBos.stream()
                        .map(i -> i.getAttrBO()).collect(Collectors.toList()).stream().map(i -> i.getAttrCode()).collect(Collectors.toList());
            }
            AdapterDeviceStatusUploadDTO uploadDTO = dealComplexBO.getUploadDTO();
            List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();
            for (ScreenDeviceAttributeDTO item : items) {
                String code = item.getCode();
                if (!CollectionUtils.isEmpty(codes) && codes.contains(code)) {
                    List<String> ignoreCodes = dealComplexBO.getIgnoreCodes();
                    if(!CollectionUtils.isEmpty(ignoreCodes)&&ignoreCodes.contains(code)){
                        //值无变化
                        continue;
                    }
                    DeviceStatusBO deviceStatusBO = new DeviceStatusBO();
                    deviceStatusBO.setDeviceSn(deviceBO.getDeviceSn());
                    deviceStatusBO.setFamilyCode(uploadDTO.getFamilyCode());
                    deviceStatusBO.setFamilyId(BeanUtil.convertString2Long(uploadDTO.getFamilyId()));
                    deviceStatusBO.setStatusCode(item.getCode());
                    deviceStatusBO.setStatusValue(item.getValue());
                    deviceStatusBO.setProductCode(deviceBO.getProductCode());
                    deviceStatusBO.setCategoryCode(deviceBO.getCategoryCode());
                    deviceStatusBO.setProjectId(dealComplexBO.getFamilyBO().getProjectId());
                    deviceStatusBO.setRealestateId(dealComplexBO.getFamilyBO().getRealestateId());
                    log.info("deviceStatusBO:{}", deviceStatusBO.toString());
                    deviceStatusBOList.add(deviceStatusBO);
                }
            }
        }
        storeStatusToDB(deviceStatusBOList);
        nextHandle(dealComplexBO);
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
            mqProducerSendMsgProcessor.send(RocketMqConst.TOPIC_CENTER_DEVICE_TO_CENTER_DATA, RocketMqConst.TAG_DEVICE_STATUS_TO_DATA, JSON.toJSONString(deviceStatusBOList));

            log.info("<<== 批量量插入 iFamilyDeviceStatusService.insertBatchDeviceStatus(deviceStatusBOList)完毕");
        }
    }

    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        List<ScreenProductAttrCategoryBO> attrCategoryBOs = dealComplexBO.getAttrCategoryBOs();
        Optional<ScreenProductAttrCategoryBO> any = attrCategoryBOs.stream().filter(i ->{
            return i.getFunctionType().intValue() == AttrFunctionEnum.FUNCTION_ATTR.getType()||
                    i.getFunctionType().intValue() == AttrFunctionEnum.BASE_ATTR.getType();
        } ).findAny();
        //根据缓存存储时的判定，判断是否需要存储 TODO
        return any.isPresent();
    }

    @PostConstruct
    public void init() {
        this.order=6;
        this.handleName=this.getClass().getSimpleName();
    }
}
