package com.landleaf.homeauto.center.device.chain.screen.status;

import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusRedisBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName ScreenStatusDealStoreRedisHandle
 * @Description: 状态数据存储redis的handle
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealStoreRedisHandle extends ScreenStatusDealHandle {

    @Autowired
    private RedisUtils redisUtils;
    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        log.info("状态处理:存储缓存");
        if (checkCondition(dealComplexBO)) {
            List<String> functionCodes = dealComplexBO.getAttrCategoryBOs().stream().filter(i -> i.getFunctionType().intValue() == AttrFunctionEnum.FUNCTION_ATTR.getType()).collect(Collectors.toList()).stream()
                    .map(i -> i.getAttrBO()).collect(Collectors.toList()).stream().map(i -> i.getAttrCode()).collect(Collectors.toList());
            AdapterDeviceStatusUploadDTO uploadDTO = dealComplexBO.getUploadDTO();
            List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();
            for (ScreenDeviceAttributeDTO item : items) {
                String code = item.getCode();
                if (!CollectionUtils.isEmpty(functionCodes) && functionCodes.contains(code)) {
                    String familyDeviceStatusStoreKey = String.format(RedisCacheConst.FAMILY_DEVICE_STATUS_STORE_KEY,
                            uploadDTO.getFamilyCode(),
                            dealComplexBO.getDeviceBO().getDeviceSn(), code);
                    DeviceStatusRedisBO deviceStatusRedisBO = new DeviceStatusRedisBO();
                    deviceStatusRedisBO.setKey(familyDeviceStatusStoreKey);
                    deviceStatusRedisBO.setStatusValue(item.getValue());
                    redisUtils.set(deviceStatusRedisBO.getKey(), deviceStatusRedisBO.getStatusValue());
                }
            }
        }
        nextHandle(dealComplexBO);
    }


    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        List<ScreenProductAttrCategoryBO> attrCategoryBOs = dealComplexBO.getAttrCategoryBOs();
        Optional<ScreenProductAttrCategoryBO> any = attrCategoryBOs.stream().filter(i -> i.getFunctionType().intValue() == AttrFunctionEnum.FUNCTION_ATTR.getType()).findAny();
        return any.isPresent();
    }

    @PostConstruct
    public void init() {
        this.order=4;
        this.handleName=this.getClass().getSimpleName();
    }
}
