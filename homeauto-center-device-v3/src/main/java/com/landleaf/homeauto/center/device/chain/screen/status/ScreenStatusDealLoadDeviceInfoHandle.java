package com.landleaf.homeauto.center.device.chain.screen.status;

import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @ClassName ScreenStatusDealHandle
 * @Description: 加载设备配置文件
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealLoadDeviceInfoHandle extends ScreenStatusDealHandle {
    @Autowired
    private IContactScreenService contactScreenService;

    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        if (checkCondition(dealComplexBO)) {
            // 获取配置
            AdapterDeviceStatusUploadDTO uploadDTO = dealComplexBO.getUploadDTO();
            ScreenTemplateDeviceBO deviceBO = contactScreenService.getFamilyDeviceBySn(uploadDTO.getHouseTemplateId(), uploadDTO.getFamilyId(), uploadDTO.getDeviceSn());
            if(deviceBO==null){
                log.error("状态处理:设备信息加载为空:家庭：{}，设备号{}",uploadDTO.getFamilyId(),uploadDTO.getDeviceSn());
            }
            dealComplexBO.setDeviceBO(deviceBO);
        }
    }

    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        return dealComplexBO.getDeviceBO() == null;
    }
    @PostConstruct
    public void init() {
        this.order=2;
        this.handleName=this.getClass().getSimpleName();
    }
}
