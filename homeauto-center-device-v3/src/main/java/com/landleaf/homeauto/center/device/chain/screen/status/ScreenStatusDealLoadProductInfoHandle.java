package com.landleaf.homeauto.center.device.chain.screen.status;

import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
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

/**
 * @ClassName ScreenStatusDealHandle
 * @Description: 加载产品信息
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealLoadProductInfoHandle extends ScreenStatusDealHandle {
    @Autowired
    private IContactScreenService contactScreenService;


    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        log.info("状态处理:加载产品信息");
        if (checkCondition(dealComplexBO)) {
            AdapterDeviceStatusUploadDTO uploadDTO = dealComplexBO.getUploadDTO();
            List<ScreenProductAttrCategoryBO> attrCategoryBO=contactScreenService.getDeviceAttrsByProductCode(uploadDTO.getProductCode(),dealComplexBO.getDeviceBO().getSystemFlag());
            if(attrCategoryBO==null){
                log.error("状态处理:产品信息加载为空:家庭：{}，设备号{}",uploadDTO.getFamilyId(),uploadDTO.getDeviceSn());
            }
            dealComplexBO.setAttrCategoryBOs(attrCategoryBO);
        }
        nextHandle(dealComplexBO);
    }

    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        return CollectionUtils.isEmpty(dealComplexBO.getAttrCategoryBOs());
    }

    @PostConstruct
    public void init() {
        this.order=2;
        this.handleName=this.getClass().getSimpleName();
    }
}
