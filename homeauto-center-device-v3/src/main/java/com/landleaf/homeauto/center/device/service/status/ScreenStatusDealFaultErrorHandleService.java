package com.landleaf.homeauto.center.device.service.status;

import com.alibaba.druid.util.StringUtils;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductErrorAttrValueBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductErrorCodeAttrValueBO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.center.device.util.FaultValueUtils;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.HomeAutoFaultDeviceCurrentDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceFaultCurrentDetailDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.FamilyFaultEnum;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @className: ScreenStatusDealFaultErrorHandleService
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/1
 **/
@Slf4j
@Component
public class ScreenStatusDealFaultErrorHandleService extends AbstractScreenStatusDealErrorHandleService implements ScreenStatusDealErrorHandleService {
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
    public void handleErrorStatus(ScreenStatusDealComplexBO dealComplexBO, ScreenDeviceAttributeDTO item,
                                  ScreenProductErrorAttrValueBO screenProductErrorAttrValueBO,
                                  ScreenDeviceInfoStatusDTO familyDeviceInfoStatus) {
        List<HomeAutoFaultDeviceHavcDTO> havTempDTOs = Lists.newArrayList();
        List<ScreenProductErrorCodeAttrValueBO> codeAttrValues = screenProductErrorAttrValueBO.getCodeAttrValue();
        codeAttrValues.sort(Comparator.comparing(ScreenProductErrorCodeAttrValueBO::getSortNo));
        List<String> errorValList = codeAttrValues.stream().map(i -> i.getVal()).collect(Collectors.toList());
        Collections.reverse(errorValList);
        //如果value转化位16位二进制为1，且跟list对应，则新增故障
        Integer uploadValue =  new BigDecimal(item.getValue()).intValue();
       
        if (uploadValue > FaultValueUtils.HVAC_INT_MAX ||
                uploadValue < FaultValueUtils.HVAC_INT_MIN ||
                errorValList.size() != FaultValueUtils.HVAC_ERROR_STRING_LENGTH) {
            //如果value小于0或者大于65536，或者stringList不为16则返回
            return;
        }
        char[] uploadValueBinary = FaultValueUtils.toBinary(uploadValue, 16);
        log.info("二进制故障,productCode:{},attrCode:{},value:{},转换后:{}", screenProductErrorAttrValueBO.getProductCode(),
                item.getCode(), uploadValue, new String(uploadValueBinary));
        /**
         * 故障处理:
         *  1。从缓存中获取当前设备二进制故障值；
         *  2.上报值与当前值逐字节读取比对，上报值为0不作处理，上报值为1，比对当前值，当前值为0则插入故障，为1则不处理；
         *  3.上报值与当前值不等，删除缓存，存储数据库到当前设备信息表。
         */
        // 1.缓存中读取当前设备信息 familyDeviceInfoStatus
        // 2.逐个字节比对,生成需要存储故障数据
        compareDetailAndSet(uploadValueBinary, errorValList, dealComplexBO, item, familyDeviceInfoStatus, havTempDTOs);
        storeFaultDataToDB(havTempDTOs, null, null);
        // 3.上报值与当前值比较，存储当前故障值
        compareCurrentAndSet(uploadValue, familyDeviceInfoStatus, dealComplexBO.getFamilyBO(), dealComplexBO.getDeviceBO(),item.getCode());
        // 4.修改设备信息表
        compareInfoAndSet(uploadValue, familyDeviceInfoStatus, dealComplexBO.getFamilyBO(), dealComplexBO.getDeviceBO(),item.getCode());
    }


    private void compareInfoAndSet(Integer uploadValue, ScreenDeviceInfoStatusDTO familyDeviceInfoStatus, ScreenFamilyBO familyBO, ScreenTemplateDeviceBO deviceBO, String code) {
        String existValue = getExistValue(familyDeviceInfoStatus, code, FamilyFaultEnum.HAVC_ERROR.getType());

        if (uploadValue != null) {
            if (!Objects.isNull(familyDeviceInfoStatus) && !StringUtils.isEmpty(existValue)&&
                    uploadValue.intValue() == Integer.parseInt(existValue)) {
                return;
            }
            ScreenDeviceInfoStatusUpdateDTO infoStatusUpdateDTO = ScreenDeviceInfoStatusUpdateDTO.builder()
                    .familyId(familyBO.getId())
                    .deviceSn(deviceBO.getDeviceSn()).deviceId(deviceBO.getId())
                    .categoryCode(deviceBO.getCategoryCode()).productCode(deviceBO.getProductCode())
                    .onlineFlag(null).valueFaultFlag(null).type(FamilyFaultEnum.HAVC_ERROR.getType())
                    .havcFaultFlag(uploadValue.intValue() > 0 ? CommonConst.NumberConst.INT_TRUE :
                            CommonConst.NumberConst.INT_FALSE).build();
            contactScreenService.storeOrUpdateDeviceInfoStatus(infoStatusUpdateDTO);
        }

    }

    /**
     * @param: uploadValue
     * @param: familyDeviceInfoStatus
     * @param: deviceBO
     * @description: 比较存储当前设备的故障值
     * @return: void
     * @author: wyl
     * @date: 2021/6/1
     */
    private void compareCurrentAndSet(Integer uploadValue, ScreenDeviceInfoStatusDTO familyDeviceInfoStatus,
                                      ScreenFamilyBO familyBO, ScreenTemplateDeviceBO deviceBO, String code) {
        String existValue = getExistValue(familyDeviceInfoStatus, code, FamilyFaultEnum.HAVC_ERROR.getType());
        if (Objects.isNull(familyDeviceInfoStatus) || StringUtils.isEmpty(existValue)||
                uploadValue.intValue() != Integer.parseInt(existValue)) {
            //存储或修改值
            HomeAutoFaultDeviceCurrentDTO deviceCurrentDTO = HomeAutoFaultDeviceCurrentDTO.builder()
                    .familyId(familyBO.getId())
                    .realestateId(familyBO.getRealestateId()).projectId(familyBO.getProjectId())
                    .deviceId(deviceBO.getId()).deviceSn(deviceBO.getDeviceSn())
                    .productCode(deviceBO.getProductCode()).code(code).value(String.valueOf(uploadValue))
                    .type(FamilyFaultEnum.HAVC_ERROR.getType()).build();
            contactScreenService.storeOrUpdateCurrentFaultValue(deviceCurrentDTO);
        }

    }

    /**
     * 比对上报故障值与当前故障，并生成需保存的故障记录
     *
     * @param uploadValueBinary      上报值
     * @param errorValList           故障枚举
     * @param dealComplexBO          原始信息
     * @param item                   当前故障属性信息
     * @param familyDeviceInfoStatus
     * @param havTempDTOs
     */
    private void compareDetailAndSet(char[] uploadValueBinary, List<String> errorValList, ScreenStatusDealComplexBO dealComplexBO,
                                     ScreenDeviceAttributeDTO item, ScreenDeviceInfoStatusDTO familyDeviceInfoStatus,
                                     List<HomeAutoFaultDeviceHavcDTO> havTempDTOs) {
        // 标记是否直接存储故障详情记录，不与当前值比较
        boolean judgeFlag = false;
        if (familyDeviceInfoStatus == null) {
            judgeFlag = true;
        }
        char[] existValueBinary = null;
        if (!judgeFlag) {
            String existValue = getExistValue(familyDeviceInfoStatus, item.getCode(),FamilyFaultEnum.HAVC_ERROR.getType());
            if (existValue == null) {
                judgeFlag = true;
            }
            if (!judgeFlag) {
                existValueBinary = FaultValueUtils.toBinary(Integer.parseInt(existValue), 16);
            }
        }
        for (int i = 0; i < uploadValueBinary.length; i++) {
            boolean storeFlag = false;
            char upload = uploadValueBinary[i];
            if (upload == '1') {
                if (judgeFlag) {
                    // 直接存储吧，不要判断了
                    storeFlag = true;

                } else {
                    // 已有值了，判断下吧
                    if (existValueBinary[i] == '0') {
                        storeFlag = true;
                    }
                }
            }
            if (storeFlag) {
                HomeAutoFaultDeviceHavcDTO havDTO = new HomeAutoFaultDeviceHavcDTO();
                buildErrorCommonDTO(dealComplexBO, havDTO, item);
                havDTO.setFaultMsg(errorValList.get(i));
                havTempDTOs.add(havDTO);
            }
        }
    }

    @Override
    public boolean checkCondition(AttributeErrorTypeEnum errorTypeEnum) {
        switch (errorTypeEnum) {
            case ERROR_CODE:
                return true;
            case COMMUNICATE:
            case VAKUE:
            default:
                break;
        }
        return false;
    }


}
