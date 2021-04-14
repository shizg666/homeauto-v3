package com.landleaf.homeauto.center.device.chain.screen.status;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.AttrFunctionEnum;
import com.landleaf.homeauto.center.device.handle.upload.ErrorConstant;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.*;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceHavcService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceLinkService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFaultDeviceValueService;
import com.landleaf.homeauto.center.device.util.FaultValueUtils;
import com.landleaf.homeauto.common.domain.dto.adapter.upload.AdapterDeviceStatusUploadDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceHavcDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceLinkDTO;
import com.landleaf.homeauto.common.domain.dto.device.fault.HomeAutoFaultDeviceValueDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName ScreenStatusDealErrorHandle
 * @Description: 处理故障信息
 * @Author wyl
 * @Date 2021/4/1
 * @Version V1.0
 **/
@Slf4j
@Component
public class ScreenStatusDealErrorHandle extends ScreenStatusDealHandle {
    @Autowired
    private IHomeAutoFaultDeviceHavcService havcService;
    @Autowired
    private IHomeAutoFaultDeviceLinkService linkService;
    @Autowired
    private IHomeAutoFaultDeviceValueService valueService;

    @Override
    public void handle(ScreenStatusDealComplexBO dealComplexBO) {
        List<HomeAutoFaultDeviceLinkDTO> linkDTOS;
        List<HomeAutoFaultDeviceValueDTO> valueDTOS;
        if (checkCondition(dealComplexBO)) {
            Map<String, ScreenProductErrorAttrValueBO> errorValueMap = dealComplexBO.getAttrCategoryBOs().stream().filter(i -> i.getFunctionType().intValue() == AttrFunctionEnum.ERROR_ATTR.getType()).collect(Collectors.toList()).stream()
                    .map(i -> i.getAttrBO()).collect(Collectors.toList()).stream().collect(Collectors.toMap(ScreenProductAttrBO::getAttrCode, ScreenProductAttrBO::getErrorAttrValue, (o, n) -> n));
            AdapterDeviceStatusUploadDTO uploadDTO = dealComplexBO.getUploadDTO();
            List<ScreenDeviceAttributeDTO> items = uploadDTO.getItems();
            for (ScreenDeviceAttributeDTO item : items) {
                String code = item.getCode();
                ScreenProductErrorAttrValueBO screenProductErrorAttrValueBO = errorValueMap.get(code);
                if (screenProductErrorAttrValueBO != null) {
                    AttributeErrorTypeEnum errorTypeEnum = AttributeErrorTypeEnum.getInstByType(screenProductErrorAttrValueBO.getType());
                    switch (errorTypeEnum) {
                        case ERROR_CODE:
                            handleCodeErrorStatus(dealComplexBO, item, screenProductErrorAttrValueBO);
                        case COMMUNICATE:
                            handleConnectErrorStatus(dealComplexBO, item, screenProductErrorAttrValueBO);
                        case VAKUE:
                            handleNumErrorStatus(dealComplexBO, item, screenProductErrorAttrValueBO);
                        default:
                            break;
                    }
                }
            }
        }
        nextHandle(dealComplexBO);
    }

    /**
     * 處理是否有數值故障
     *
     * @param dealComplexBO
     * @param item
     * @param screenProductErrorAttrValueBO
     */
    private void handleNumErrorStatus(ScreenStatusDealComplexBO dealComplexBO, ScreenDeviceAttributeDTO item, ScreenProductErrorAttrValueBO screenProductErrorAttrValueBO) {
        ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
        ScreenProductErrorNumAttrValueBO numAttrValue = screenProductErrorAttrValueBO.getNumAttrValue();
        String max = numAttrValue.getMax();
        String min = numAttrValue.getMin();
        String current = item.getValue();
        List<HomeAutoFaultDeviceValueDTO> valueDTOS = Lists.newArrayList();

        if (FaultValueUtils.isValueError(current, min, max)) {
            HomeAutoFaultDeviceValueDTO valueDTO = new HomeAutoFaultDeviceValueDTO();
            valueDTO.setReference(min.concat("-").concat(max));
            valueDTO.setCurrent(current);
            valueDTO.setDeviceCode(deviceBO.getDeviceSn());
            valueDTO.setProductCode(screenProductErrorAttrValueBO.getProductCode());
            valueDTO.setFamilyId(dealComplexBO.getUploadDTO().getFamilyId());
            valueDTO.setFaultMsg(ErrorConstant.VALUE_MSG_ERROR.concat(":").concat(item.getCode()));
            valueDTO.setFaultStatus(ErrorConstant.STATUS_ERROR_UNRESOLVED);
            valueDTO.setFaultTime(LocalDateTime.now());
            valueDTOS.add(valueDTO);
        }
        storeFaultDataToDB(null, null, valueDTOS);
    }

    /**
     * 处理通信类型故障
     *
     * @param dealComplexBO
     * @param item
     * @param screenProductErrorAttrValueBO
     */
    private void handleConnectErrorStatus(ScreenStatusDealComplexBO dealComplexBO, ScreenDeviceAttributeDTO item, ScreenProductErrorAttrValueBO screenProductErrorAttrValueBO) {
        ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
        List<HomeAutoFaultDeviceLinkDTO> linkDTOs = Lists.newArrayList();
        ScreenProductErrorConnectAttrValueBO connectAttrValue = screenProductErrorAttrValueBO.getConnectAttrValue();
        if (connectAttrValue.getNormalVal().intValue() == Integer.parseInt(item.getValue())) {
            HomeAutoFaultDeviceLinkDTO linkDTO = new HomeAutoFaultDeviceLinkDTO();
            linkDTO.setDeviceCode(deviceBO.getDeviceSn());
            linkDTO.setProductCode(screenProductErrorAttrValueBO.getProductCode());
            linkDTO.setFamilyId(dealComplexBO.getUploadDTO().getFamilyId());
            linkDTO.setFaultMsg(ErrorConstant.LINK_MSG_ERROR);
            linkDTO.setFaultStatus(ErrorConstant.STATUS_ERROR_UNRESOLVED);
            linkDTO.setFaultTime(LocalDateTime.now());
            // 处理通信故障数据
            linkDTOs.add(linkDTO);
        }
        storeFaultDataToDB(null, linkDTOs, null);

    }

    /**
     * 故障碼類型故障
     *
     * @param dealComplexBO
     * @param item
     * @param screenProductErrorAttrValueBO
     */
    private void handleCodeErrorStatus(ScreenStatusDealComplexBO dealComplexBO, ScreenDeviceAttributeDTO item, ScreenProductErrorAttrValueBO screenProductErrorAttrValueBO) {
        ScreenTemplateDeviceBO deviceBO = dealComplexBO.getDeviceBO();
        List<HomeAutoFaultDeviceHavcDTO> havcTempDTOs = Lists.newArrayList();
        List<ScreenProductErrorCodeAttrValueBO> codeAttrValues = screenProductErrorAttrValueBO.getCodeAttrValue();
        codeAttrValues.sort(Comparator.comparing(ScreenProductErrorCodeAttrValueBO::getSortNo));
        List<String> errorValList = codeAttrValues.stream().map(i -> i.getVal()).collect(Collectors.toList());
        Collections.reverse(errorValList);
        //如果value转化位16位二进制为1，且跟list对应，则新增故障
        String value = item.getValue();
        Integer valueInt = Integer.parseInt(value);
        if (valueInt > FaultValueUtils.HVAC_INT_MAX ||
                valueInt < FaultValueUtils.HVAC_INT_MIN ||
                errorValList.size() != FaultValueUtils.HVAC_ERROR_STRING_LENGTH) {
            //如果value小于0或者大于65536，或者stringList不为16则返回
            return;
        }
        char[] chars = FaultValueUtils.toBinary(valueInt, 16);
        log.info("暖通故障,productCode:{},attrCode:{},value:{},转换后:{}", screenProductErrorAttrValueBO.getProductCode(), item.getCode(), value, new String(chars));

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '1') {
                HomeAutoFaultDeviceHavcDTO havcDTO = new HomeAutoFaultDeviceHavcDTO();
                havcDTO.setDeviceCode(deviceBO.getDeviceSn());
                havcDTO.setProductCode(screenProductErrorAttrValueBO.getProductCode());
                havcDTO.setFamilyId(dealComplexBO.getUploadDTO().getFamilyId());
                havcDTO.setFaultMsg(errorValList.get(i));
                havcDTO.setFaultStatus(ErrorConstant.STATUS_ERROR_UNRESOLVED);
                havcDTO.setFaultTime(LocalDateTime.now());
                havcTempDTOs.add(havcDTO);
            }
        }
        storeFaultDataToDB(havcTempDTOs, null, null);
    }

    private boolean checkCondition(ScreenStatusDealComplexBO dealComplexBO) {
        List<ScreenProductAttrCategoryBO> attrCategoryBOs = dealComplexBO.getAttrCategoryBOs();
        Optional<ScreenProductAttrCategoryBO> any = attrCategoryBOs.stream().filter(i -> i.getFunctionType().intValue() == AttrFunctionEnum.ERROR_ATTR.getType()).findAny();
        return any.isPresent();
    }

    /**
     * 存储故障数据
     *
     * @param havcDTOS  暖通故障数据
     * @param linkDTOS  通信故障数据
     * @param valueDTOS 数值故障数据
     */
    private void storeFaultDataToDB(List<HomeAutoFaultDeviceHavcDTO> havcDTOS, List<HomeAutoFaultDeviceLinkDTO> linkDTOS, List<HomeAutoFaultDeviceValueDTO> valueDTOS) {
        //故障批量入库
        log.info("havcDTOS.size()={},linkDTOS.size()={},valueDTOS.size()=",
                havcDTOS.size(), linkDTOS.size(), valueDTOS.size());

        if (havcDTOS.size() > 0) {
            havcService.batchSave(havcDTOS);
            log.info("批量插入havc故障");
        }

        if (linkDTOS.size() > 0) {
            linkService.batchSave(linkDTOS);
            log.info("批量插入通信故障");
        }
        if (valueDTOS.size() > 0) {
            valueService.batchSave(valueDTOS);
            log.info("批量插入value故障");
        }
    }

    @PostConstruct
    public void init() {
        this.order=3;
        this.handleName=this.getClass().getSimpleName();
    }
}
