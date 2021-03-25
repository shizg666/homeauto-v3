package com.landleaf.homeauto.center.device.filter;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrPrecision;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrSelect;
import com.landleaf.homeauto.center.device.model.dto.DeviceAttrPrecisionValueDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppEnumAttrInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrSelectVO;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrInfoService;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrPrecisionService;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrSelectService;
import com.landleaf.homeauto.common.enums.category.PrecisionEnum;
import com.landleaf.homeauto.common.enums.protocol.ProtocolAttrValTypeEnum;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @ClassName EnumOutPutFilter
 * @Description: 枚举输出过滤
 * @Author wyl
 * @Date 2021/1/6
 * @Version V2.0
 **/
@Component
public class EnumOutPutFilter implements IAttributeOutPutFilter {
    @Autowired
    private IDeviceAttrInfoService deviceAttrInfoService;
    @Autowired
    private IDeviceAttrSelectService deviceAttrSelectService;

    @Override
    public boolean checkFilter(String attributeId, String attributeCode) {
        DeviceAttrInfo attrInfo = deviceAttrInfoService.getById(attributeId);
        if (attrInfo != null && attrInfo.getValueType().intValue() == ProtocolAttrValTypeEnum.SELECT.getCode()) {
                return true;
        }
        return false;
    }

    @Override
    public Object handle(Object input, String attributeId, String attributeCode) {

        if(Objects.isNull(input)){
            List<DeviceAttrSelect> selects = deviceAttrSelectService.getByAttribute(attributeId);
            String value = CollectionUtils.isEmpty(selects) ? null : selects.get(0).getValue();

            return NumberUtils.isNumber(value)? new BigDecimal(value).setScale(0,BigDecimal.ROUND_DOWN).intValue():value;
        }

        return input;
    }

    @Override
    public Object appGetStatusHandle(Object input, String attributeId, String attributeCode) {
        AppEnumAttrInfoVO appEnumAttrInfoVO = new AppEnumAttrInfoVO();
        appEnumAttrInfoVO.setCurrentValue(input);
        List<DeviceAttrSelect> selects = deviceAttrSelectService.getByAttribute(attributeId);
        if(!CollectionUtil.isEmpty(selects)){
            appEnumAttrInfoVO.setSelects(selects.stream().map(i->{
                return i.getValue();
            }).collect(Collectors.toList()));
        }
        if(Objects.isNull(input)){
            String value = CollectionUtils.isEmpty(selects) ? null : selects.get(0).getValue();
            appEnumAttrInfoVO.setCurrentValue(NumberUtils.isNumber(value)? new BigDecimal(value).setScale(0,BigDecimal.ROUND_DOWN).intValue():value);
        }
        return appEnumAttrInfoVO;
    }

    @Override
    public AppletsAttrInfoVO handle(Object input, String attributeId, String attributeCode, AppletsAttrInfoVO attrInfoVO) {
        attrInfoVO.setCurrentValue(input);
        List<DeviceAttrSelect> selects = deviceAttrSelectService.getByAttribute(attributeId);
        if(!CollectionUtil.isEmpty(selects)){
            attrInfoVO.setSelects(selects.stream().map(i->{
                AppletsAttrSelectVO appletsAttrSelectVO = new AppletsAttrSelectVO();
                BeanUtils.copyProperties(i,appletsAttrSelectVO);
                return appletsAttrSelectVO;
            }).collect(Collectors.toList()));
        }
        if(Objects.isNull(input)){
            String value = CollectionUtils.isEmpty(selects) ? null : selects.get(0).getValue();
            attrInfoVO.setCurrentValue(NumberUtils.isNumber(value)? new BigDecimal(value).setScale(0,BigDecimal.ROUND_DOWN).intValue():value);
        }
        attrInfoVO.setValueType(1);
        return attrInfoVO;
    }
}
