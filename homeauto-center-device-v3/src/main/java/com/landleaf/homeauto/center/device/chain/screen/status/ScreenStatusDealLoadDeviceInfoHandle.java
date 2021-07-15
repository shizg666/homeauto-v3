package com.landleaf.homeauto.center.device.chain.screen.status;

import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
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
        if (CategoryTypeEnum.SECURITY_MAINFRAME.getType().equals(dealComplexBO.getDeviceBO().getCategoryCode())){
            log.info("处理安防********************************************");
        }
        log.info("状态处理:加载设备信息");
        if (checkCondition(dealComplexBO)) {
            // 获取配置
            AdapterDeviceStatusUploadDTO uploadDTO = dealComplexBO.getUploadDTO();
            ScreenTemplateDeviceBO deviceBO = contactScreenService.getFamilyDeviceBySn(uploadDTO.getHouseTemplateId(),
                   uploadDTO.getFamilyId(), String.valueOf(uploadDTO.getDeviceSn()));
            if(deviceBO==null){
                log.error("状态处理:设备信息加载为空:家庭：{}，设备号{}",uploadDTO.getFamilyId(),uploadDTO.getDeviceSn());
            }
            dealComplexBO.setDeviceBO(deviceBO);
        }
        nextHandle(dealComplexBO);
    }

    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        return dealComplexBO.getDeviceBO() == null;
    }
    @PostConstruct
    public void init() {
        this.order=1;
        this.handleName=this.getClass().getSimpleName();
    }
}
