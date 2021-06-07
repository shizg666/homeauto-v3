package com.landleaf.homeauto.center.device.service.status;

import com.landleaf.homeauto.center.device.handle.upload.ErrorConstant;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceBaseDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceLinkDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceValueDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.FamilyFaultEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @className: AbstractScreenStatusDealErrorHandleService
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/2
 **/
@Slf4j
public abstract class AbstractScreenStatusDealErrorHandleService {

    public IHomeAutoFaultDeviceHavcService havcService;
    public IHomeAutoFaultDeviceLinkService linkService;
    public IHomeAutoFaultDeviceValueService valueService;
    /**
     * 生成故障通用信息
     *
     * @param dealComplexBO 原始数据源
     * @param valueDTO      生成对象
     * @param item          具体属性对象
     */
    void buildErrorCommonDTO(ScreenStatusDealComplexBO dealComplexBO, HomeAutoFaultDeviceBaseDTO valueDTO, ScreenDeviceAttributeDTO item) {
        ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
        ScreenFamilyBO familyBO = dealComplexBO.getFamilyBO();
        valueDTO.setDeviceSn(deviceBO.getDeviceSn());
        valueDTO.setProductCode(deviceBO.getProductCode());
        valueDTO.setFamilyId(BeanUtil.convertString2Long(familyBO.getId()));
        valueDTO.setRealestateId(familyBO.getRealestateId());
        valueDTO.setProjectId(familyBO.getProjectId());
        if (valueDTO instanceof HomeAutoFaultDeviceValueDTO) {
            valueDTO.setFaultMsg(ErrorConstant.VALUE_MSG_ERROR.concat(":").concat(item.getCode()));
        } else if (valueDTO instanceof HomeAutoFaultDeviceLinkDTO) {
            valueDTO.setFaultMsg(ErrorConstant.LINK_MSG_ERROR);
        }
        valueDTO.setFaultStatus(ErrorConstant.STATUS_ERROR_UNRESOLVED);
        valueDTO.setFaultTime(LocalDateTime.now());
    }
    /**
     * 存储故障数据
     *
     * @param havcDTOS  暖通故障数据
     * @param linkDTOS  通信故障数据
     * @param valueDTOS 数值故障数据
     */
    void storeFaultDataToDB(List<HomeAutoFaultDeviceHavcDTO> havcDTOS, List<HomeAutoFaultDeviceLinkDTO> linkDTOS, List<HomeAutoFaultDeviceValueDTO> valueDTOS) {

        if (!CollectionUtils.isEmpty(havcDTOS)) {
            havcService.batchSave(havcDTOS);
            log.info("批量插入havc故障");
        }

        if (!CollectionUtils.isEmpty(linkDTOS)) {
            linkService.batchSave(linkDTOS);
            log.info("批量插入通信故障");
        }
        if (!CollectionUtils.isEmpty(valueDTOS)) {
            valueService.batchSave(valueDTOS);
            log.info("批量插入value故障");
        }
    }

    public String getExistValue(ScreenDeviceInfoStatusDTO familyDeviceInfoStatus, String code,int type) {
        String existValue =null;
        if(familyDeviceInfoStatus==null){
            return existValue;
        }
        Map<Integer, Map<String, String>> currentDetailMap = familyDeviceInfoStatus.getCurrentDetailMap();
        if (currentDetailMap != null && currentDetailMap.size() > 0) {
            Map<String, String> code_value_map = currentDetailMap.get(type);
            if (code_value_map != null && code_value_map.size() > 0) {
                existValue = code_value_map.get(code);
            }
        }
        return existValue;
    }

}
