package com.landleaf.homeauto.center.device.service.status;

import cn.jiguang.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductErrorAttrValueBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductErrorConnectAttrValueBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceLinkDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.HomeAutoFaultDeviceCurrentDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.FamilyFaultEnum;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @className: ScreenStatusDealFaultErrorHandleService
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/1
 **/
@Component
public class ScreenStatusDealLinkErrorHandleService extends AbstractScreenStatusDealErrorHandleService implements ScreenStatusDealErrorHandleService{
    @Autowired
    public void setHavcService(IHomeAutoFaultDeviceHavcService havcService) {
        this.havcService = havcService;
    }
    @Autowired
    public void setLinkService(IHomeAutoFaultDeviceLinkService linkService) {
        this.linkService = linkService;
    }
    @Autowired
    public void setValueService(IHomeAutoFaultDeviceValueService valueService) {
        this.valueService = valueService;
    }
    @Autowired
    private IContactScreenService contactScreenService;
    @Override
    public void handleErrorStatus(ScreenStatusDealComplexBO dealComplexBO, ScreenDeviceAttributeDTO item, ScreenProductErrorAttrValueBO screenProductErrorAttrValueBO, ScreenDeviceInfoStatusDTO familyDeviceInfoStatus) {
        List<HomeAutoFaultDeviceLinkDTO> linkDTOs = Lists.newArrayList();
        ScreenProductErrorConnectAttrValueBO connectAttrValue = screenProductErrorAttrValueBO.getConnectAttrValue();
        /**
         * 通信处理:
         *  1。从缓存中获取当前设备二进制故障值；
         *  2.上报值与当前值读取比对，值一样不做存储,其余存储；
         *  3.上报值与当前值不等，删除缓存，存储数据库到当前设备信息表。
         */
        String existValue = getExistValue(familyDeviceInfoStatus, item.getCode(), FamilyFaultEnum.LINK_ERROR.getType());

        // 1.缓存中读取当前设备信息
        ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
        // 2.上报值与当前值读取比对，值一样不做存储,其余存储
        compareDetailAndSet(Integer.parseInt(item.getValue()), dealComplexBO, item, familyDeviceInfoStatus, connectAttrValue, linkDTOs,existValue);
        storeFaultDataToDB(null, linkDTOs, null);
        // 3.上报值与当前值比较，存储当前在线离线值
        compareCurrentAndSet(Integer.parseInt(item.getValue()),  dealComplexBO.getFamilyBO(), deviceBO,item.getCode(),existValue);
        // 4.修改设备信息表
        compareInfoAndSet(Integer.parseInt(item.getValue()), connectAttrValue, familyDeviceInfoStatus, dealComplexBO.getFamilyBO(), deviceBO,existValue);

    }


    private void compareInfoAndSet(Integer uploadValue, ScreenProductErrorConnectAttrValueBO connectAttrValue, ScreenDeviceInfoStatusDTO familyDeviceInfoStatus, ScreenFamilyBO familyBO, ScreenTemplateDeviceBO deviceBO, String existValue) {

        if (uploadValue != null) {
            if (!Objects.isNull(familyDeviceInfoStatus) && !com.alibaba.druid.util.StringUtils.isEmpty(existValue)&&
                    uploadValue.intValue() == Integer.parseInt(existValue)) {
                return;
            }
            ScreenDeviceInfoStatusUpdateDTO infoStatusUpdateDTO = ScreenDeviceInfoStatusUpdateDTO.builder()
                    .familyId(familyBO.getId())
                    .deviceSn(deviceBO.getDeviceSn()).deviceId(deviceBO.getId())
                    .categoryCode(deviceBO.getCategoryCode()).productCode(deviceBO.getProductCode())
                    .onlineFlag(uploadValue.intValue() == connectAttrValue.getNormalVal().intValue() ? CommonConst.NumberConst.INT_TRUE : CommonConst.NumberConst.INT_FALSE)
                    .valueFaultFlag(null).type(FamilyFaultEnum.LINK_ERROR.getType())
                    .havcFaultFlag(null).build();
            contactScreenService.storeOrUpdateDeviceInfoStatus(infoStatusUpdateDTO);
        }

    }
    private void compareCurrentAndSet(Integer uploadValue, ScreenFamilyBO familyBO, ScreenTemplateDeviceBO deviceBO, String code, String existValue) {
        if (!StringUtils.isEmpty(existValue)&&Integer.parseInt(existValue)==uploadValue.intValue()) {
            return;
        }
        //存储或修改值
        HomeAutoFaultDeviceCurrentDTO deviceCurrentDTO = HomeAutoFaultDeviceCurrentDTO.builder().familyId(familyBO.getId())
                .realestateId(familyBO.getRealestateId()).projectId(familyBO.getProjectId())
                .deviceId(deviceBO.getId()).deviceSn(deviceBO.getDeviceSn())
                .productCode(deviceBO.getProductCode()).code(code).value(String.valueOf(uploadValue))
                .type(FamilyFaultEnum.LINK_ERROR.getType()).build();
        contactScreenService.storeOrUpdateCurrentFaultValue(deviceCurrentDTO);
    }
    private void compareDetailAndSet(Integer uploadValue, ScreenStatusDealComplexBO dealComplexBO, ScreenDeviceAttributeDTO item, ScreenDeviceInfoStatusDTO familyDeviceInfoStatus, ScreenProductErrorConnectAttrValueBO connectAttrValue, List<HomeAutoFaultDeviceLinkDTO> linkDTOs, String existValue) {
        boolean judgeFlag = false;
        if (familyDeviceInfoStatus == null) {
            judgeFlag = true;
        }
        if (!judgeFlag) {
            if (existValue == null) {
                judgeFlag = true;
            }
        }
        boolean storeFlag = false;
        if (judgeFlag) {
            storeFlag = true;
        } else {
            if (uploadValue.intValue() != Integer.parseInt(existValue)) {
                storeFlag = true;
            }
        }
        if (storeFlag) {
            // 存储
            if (connectAttrValue.getUnnormalVal().intValue() == Integer.parseInt(item.getValue())) {
                HomeAutoFaultDeviceLinkDTO linkDTO = new HomeAutoFaultDeviceLinkDTO();
                buildErrorCommonDTO(dealComplexBO, linkDTO, item);
                // 处理通信故障数据
                linkDTOs.add(linkDTO);
            }
        }

    }

    @Override
    public boolean checkCondition(AttributeErrorTypeEnum errorTypeEnum) {
        switch (errorTypeEnum) {
            case COMMUNICATE:
                return true;
            case ERROR_CODE:
            case VAKUE:
            default:
                break;
        }
        return false;
    }
}
