package com.landleaf.homeauto.center.device.chain.screen.status;

import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusUpdateDTO;
import com.landleaf.homeauto.common.enums.FamilyFaultEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName ScreenStatusDealUpdateOnlineHandle
 * @Description: 状态上报更改设备上下线状态
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealUpdateOnlineHandle extends ScreenStatusDealHandle {
    @Autowired
    private IContactScreenService contactScreenService;
    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        log.info("上线处理:如果设备在上传状态说明设备在线");
        if (checkCondition(dealComplexBO)) {
            // 更新设备状态
            ScreenFamilyBO familyBO = dealComplexBO.getFamilyBO();
            ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
            ScreenDeviceInfoStatusUpdateDTO infoStatusUpdateDTO = ScreenDeviceInfoStatusUpdateDTO.builder()
                    .familyId(familyBO.getId())
                    .deviceSn(deviceBO.getDeviceSn()).deviceId(deviceBO.getId())
                    .categoryCode(deviceBO.getCategoryCode()).productCode(deviceBO.getProductCode())
                    .onlineFlag( CommonConst.NumberConst.INT_TRUE)
                    .valueFaultFlag(null).type(FamilyFaultEnum.LINK_ERROR.getType())
                    .havcFaultFlag(null).build();
            contactScreenService.storeOrUpdateDeviceInfoStatus(infoStatusUpdateDTO);
        }
        nextHandle(dealComplexBO);
    }

    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        List<ScreenProductAttrCategoryBO> attrCategoryBOs = dealComplexBO.getAttrCategoryBOs();
        Optional<ScreenProductAttrCategoryBO> any = attrCategoryBOs.stream().filter(i ->{
            return i.getFunctionType().intValue() == AttrFunctionEnum.FUNCTION_ATTR.getType()||
                    i.getFunctionType().intValue() == AttrFunctionEnum.BASE_ATTR.getType();
        } ).findAny();
        boolean present = any.isPresent();
        if(!present){
            return false;
        }
        ScreenDeviceInfoStatusDTO familyDeviceInfoStatus = contactScreenService.getFamilyDeviceInfoStatus
                (dealComplexBO.getFamilyBO().getId(),dealComplexBO.getDeviceBO().getId());
        if(familyDeviceInfoStatus==null||familyDeviceInfoStatus.getOnlineFlag()==null
                ||familyDeviceInfoStatus.getOnlineFlag()==0){
                // 离线状态或未知状态
             return true;
        }
        return false ;
    }

    @PostConstruct
    public void init() {
        this.order=7;
        this.handleName=this.getClass().getSimpleName();
    }
}
