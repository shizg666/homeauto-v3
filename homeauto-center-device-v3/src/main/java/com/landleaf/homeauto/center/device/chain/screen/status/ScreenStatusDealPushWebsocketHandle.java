package com.landleaf.homeauto.center.device.chain.screen.status;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.WebSocketMessageService;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
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
 * @ClassName ScreenStatusDealPushWebsocketHandle
 * @Description: 状态数据推送到websocket
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealPushWebsocketHandle extends ScreenStatusDealHandle {
    @Autowired
    private WebSocketMessageService webSocketMessageService;

    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        log.info("状态处理:推送到websocket");
        List<ScreenDeviceAttributeDTO> pushItems = Lists.newArrayList();
        ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
        if (checkCondition(dealComplexBO)) {
            List<String> codes = dealComplexBO.getAttrCategoryBOs().stream().filter(i -> {
                return i.getFunctionType().intValue() == AttrFunctionEnum.FUNCTION_ATTR.getType();
            }).collect(Collectors.toList()).stream()
                    .map(i -> i.getAttrBO()).collect(Collectors.toList()).stream().map(i -> i.getAttrCode()).collect(Collectors.toList());
            AdapterDeviceStatusUploadDTO uploadDTO = dealComplexBO.getUploadDTO();
            List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();
            for (ScreenDeviceAttributeDTO item : items) {
                String code = item.getCode();
                if (!CollectionUtils.isEmpty(codes) && codes.contains(code)) {
                    pushItems.add(item);
                }
            }
        }
        pushWebsocketData(pushItems, dealComplexBO.getUploadDTO(), deviceBO.getDeviceSn());
        nextHandle(dealComplexBO);
    }

    /**
     * 推送到websocket的数据
     *
     * @param pushItems 状态数据
     * @param uploadDTO 原始数据
     */
    private void pushWebsocketData(List<ScreenDeviceAttributeDTO> pushItems,
                                   AdapterDeviceStatusUploadDTO uploadDTO,
                                   String deviceSn) {
        //websocket推送
        if (pushItems.size() > 0) {
            log.info("状态处理:推送到websocket,共计{}条",pushItems.size());
            uploadDTO.setItems(pushItems);
            webSocketMessageService.pushDeviceStatus(uploadDTO, deviceSn);
        }
    }

    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        List<ScreenProductAttrCategoryBO> attrCategoryBOs = dealComplexBO.getAttrCategoryBOs();
        Optional<ScreenProductAttrCategoryBO> any = attrCategoryBOs.stream().filter(i -> {
            return i.getFunctionType().intValue() == AttrFunctionEnum.FUNCTION_ATTR.getType();
        }).findAny();
        return any.isPresent();
    }

    @PostConstruct
    public void init() {
        this.order=5;
        this.handleName=this.getClass().getSimpleName();
    }
}
