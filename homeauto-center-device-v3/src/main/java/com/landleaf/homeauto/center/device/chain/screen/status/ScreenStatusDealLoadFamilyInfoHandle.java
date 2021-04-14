package com.landleaf.homeauto.center.device.chain.screen.status;

import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName ScreenStatusDealLoadFamilyInfoHandle
 * @Description: 加载家庭信息
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealLoadFamilyInfoHandle extends ScreenStatusDealHandle {
    @Autowired
    private ConfigCacheProvider configCacheProvider;

    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        if (checkCondition(dealComplexBO)) {
            // 获取配置
            AdapterDeviceStatusUploadDTO uploadDTO = dealComplexBO.getUploadDTO();
            ScreenFamilyBO familyInfo = configCacheProvider.getFamilyInfo(BeanUtil.convertString2Long(dealComplexBO.getUploadDTO().getFamilyId()));
            if (familyInfo == null) {
                log.error("状态处理:家庭信息加载为空:家庭：{}，设备号{}", uploadDTO.getFamilyId(), uploadDTO.getDeviceSn());
            }
            dealComplexBO.setFamilyBO(familyInfo);
        }
        nextHandle(dealComplexBO);
    }

    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        return dealComplexBO.getFamilyBO() == null;
    }

    @PostConstruct
    public void init() {
        this.order = -1;
        this.handleName = this.getClass().getSimpleName();
    }
}
