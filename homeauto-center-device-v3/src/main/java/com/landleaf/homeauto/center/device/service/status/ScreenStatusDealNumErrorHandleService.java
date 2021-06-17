package com.landleaf.homeauto.center.device.service.status;

import cn.jiguang.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductErrorAttrValueBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductErrorNumAttrValueBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.center.device.util.FaultValueUtils;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceValueDTO;
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

/**
 * @className: ScreenStatusDealFaultErrorHandleService
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/1
 **/
@Component
public class ScreenStatusDealNumErrorHandleService extends AbstractScreenStatusDealErrorHandleService implements ScreenStatusDealErrorHandleService {
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
        ScreenProductErrorNumAttrValueBO numAttrValue = screenProductErrorAttrValueBO.getNumAttrValue();

        List<HomeAutoFaultDeviceValueDTO> valueDTOS = Lists.newArrayList();

        /**
         * 通信处理:
         *  1。从缓存中获取当前设备二进制故障值；
         *  2.上报值与当前值读取比对，
         *  3.上报值与当前值不等，删除缓存，存储数据库到当前设备信息表。
         */
        // 1.缓存中读取当前设备信息
        ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
        String existValue = getExistValue(familyDeviceInfoStatus, item.getCode(), FamilyFaultEnum.NUM_ERROR.getType());

        // 2.上报值与当前值读取比对，值一样不做存储,其余存储
        compareDetailAndSet(item.getValue(), dealComplexBO, item, familyDeviceInfoStatus, numAttrValue, valueDTOS, existValue);
        storeFaultDataToDB(null, null, valueDTOS);
        // 3.上报值与当前值比较，判定为是异常数据：不包含添加，包含不作处理；判定为非异常数据：包含移除
        compareCurrentAndSet(item.getValue(), familyDeviceInfoStatus, numAttrValue, dealComplexBO.getFamilyBO(), deviceBO, item, existValue);
        // 4.修改设备信息表
        compareInfoAndSet( familyDeviceInfoStatus, dealComplexBO.getFamilyBO(), deviceBO);
    }

    private void compareInfoAndSet(ScreenDeviceInfoStatusDTO familyDeviceInfoStatus, ScreenFamilyBO familyBO, ScreenTemplateDeviceBO deviceBO) {
        // 查询
        long count=contactScreenService.countCurrentFault(familyBO.getId(),deviceBO.getId(),FamilyFaultEnum.NUM_ERROR.getType());
        ScreenDeviceInfoStatusUpdateDTO infoStatusUpdateDTO = ScreenDeviceInfoStatusUpdateDTO.builder()
                .familyId(familyBO.getId())
                .deviceSn(deviceBO.getDeviceSn()).deviceId(deviceBO.getId())
                .categoryCode(deviceBO.getCategoryCode()).productCode(deviceBO.getProductCode())
                .onlineFlag(null).type(FamilyFaultEnum.NUM_ERROR.getType())
                .valueFaultFlag(count>0?CommonConst.NumberConst.INT_TRUE : CommonConst.NumberConst.INT_FALSE)
                .havcFaultFlag(null).build();
        contactScreenService.storeOrUpdateDeviceInfoStatus(infoStatusUpdateDTO);
    }

    private void compareCurrentAndSet(String uploadValue, ScreenDeviceInfoStatusDTO familyDeviceInfoStatus,
                                      ScreenProductErrorNumAttrValueBO numAttrValue, ScreenFamilyBO familyBO,
                                      ScreenTemplateDeviceBO deviceBO, ScreenDeviceAttributeDTO item,
                                      String existValue) {
        //是否需要修改当前值
        // 判定为是异常数据：不包含添加，包含不作处理；判定为非异常数据：包含移除
        String max = numAttrValue.getMax();
        String min = numAttrValue.getMin();
        if (FaultValueUtils.isValueError(uploadValue, min, max)) {
            if (!StringUtils.isEmpty(existValue)) {
                return;
            }
            //存储或修改值
            HomeAutoFaultDeviceCurrentDTO deviceCurrentDTO = HomeAutoFaultDeviceCurrentDTO.builder()
                    .familyId(familyBO.getId())
                    .realestateId(familyBO.getRealestateId()).projectId(familyBO.getProjectId())
                    .deviceId(deviceBO.getId()).deviceSn(deviceBO.getDeviceSn())
                    .productCode(deviceBO.getProductCode()).code(item.getCode()).value(String.valueOf(uploadValue))
                    .type(FamilyFaultEnum.NUM_ERROR.getType()).build();
            contactScreenService.storeOrUpdateCurrentFaultValue(deviceCurrentDTO);
        }else {
            //如果是正常值，要移除
            contactScreenService.removeCurrentFaultValue(familyBO.getId(),
                    deviceBO.getId(),item.getCode(), FamilyFaultEnum.NUM_ERROR.getType());
        }
    }

    private void compareDetailAndSet(String uploadValue, ScreenStatusDealComplexBO dealComplexBO, ScreenDeviceAttributeDTO item, ScreenDeviceInfoStatusDTO familyDeviceInfoStatus, ScreenProductErrorNumAttrValueBO numAttrValue, List<HomeAutoFaultDeviceValueDTO> valueDTOS, String existValue) {
        String max = numAttrValue.getMax();
        String min = numAttrValue.getMin();

        boolean judgeFlag = false;
        if (familyDeviceInfoStatus == null) {
            judgeFlag = true;
        }
        if (!judgeFlag) {
            if (StringUtils.isEmpty(existValue)) {
                judgeFlag = true;
            }
        }
        boolean storeFlag = false;
        if (judgeFlag) {
            storeFlag = true;
        } else {
            // 解析当前存储值
            // 有的话不作操作，无的话添加
            if (StringUtils.isEmpty(existValue)) {
                storeFlag = true;
            }
        }
        if (storeFlag) {
            // 存储
            if (FaultValueUtils.isValueError(uploadValue, min, max)) {
                HomeAutoFaultDeviceValueDTO valueDTO = new HomeAutoFaultDeviceValueDTO();
                valueDTO.setReference(min.concat("-").concat(max));
                valueDTO.setCurrent(uploadValue);
                buildErrorCommonDTO(dealComplexBO, valueDTO, item);
                valueDTOS.add(valueDTO);
            }
        }
    }


    @Override
    public boolean checkCondition(AttributeErrorTypeEnum errorTypeEnum) {
        switch (errorTypeEnum) {
            case VAKUE:
                return true;
            case ERROR_CODE:
            case COMMUNICATE:
            default:
                break;
        }
        return false;
    }
}
