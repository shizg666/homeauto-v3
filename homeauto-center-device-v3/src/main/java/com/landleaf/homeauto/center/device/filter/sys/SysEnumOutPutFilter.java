package com.landleaf.homeauto.center.device.filter.sys;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.util.CollectionUtils;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.center.device.model.domain.sysproduct.SysProductAttributeInfo;
import com.landleaf.homeauto.center.device.model.smart.vo.AppEnumAttrInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrSelectVO;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
public class SysEnumOutPutFilter implements ISysAttributeOutPutFilter {

    @Override
    public boolean checkFilter(ScreenSysProductAttrBO attrBO) {
        if (attrBO != null && attrBO.getAttrValue().getType().intValue() == AttributeTypeEnum.MULTIPLE_CHOICE.getType()) {
            return true;
        }
        return false;
    }

    @Override
    public Object handle(Object input, ScreenSysProductAttrBO attrBO) {

        if (Objects.isNull(input)) {
            List<SysProductAttributeInfo> selects = attrBO.getAttrValue().getSelectValues();
            String value = CollectionUtils.isEmpty(selects) ? null : selects.get(0).getCode();
            return NumberUtils.isNumber(value) ? new BigDecimal(value).setScale(0, BigDecimal.ROUND_DOWN).intValue() : value;
        }

        return input;
    }

    @Override
    public Object appGetStatusHandle(Object input, ScreenSysProductAttrBO attrBO) {
        AppEnumAttrInfoVO appEnumAttrInfoVO = new AppEnumAttrInfoVO();
        appEnumAttrInfoVO.setCurrentValue(input);
        List<SysProductAttributeInfo> selects = attrBO.getAttrValue().getSelectValues();
        if (!CollectionUtil.isEmpty(selects)) {
            appEnumAttrInfoVO.setSelects(selects.stream().map(i -> {
                return i.getCode();
            }).collect(Collectors.toList()));
        }
        if (Objects.isNull(input)) {
            String value = CollectionUtils.isEmpty(selects) ? null : selects.get(0).getCode();
            appEnumAttrInfoVO.setCurrentValue(NumberUtils.isNumber(value) ? new BigDecimal(value).setScale(0, BigDecimal.ROUND_DOWN).intValue() : value);
        }
        return appEnumAttrInfoVO;
    }

    @Override
    public AppletsAttrInfoVO handle(Object input, ScreenSysProductAttrBO attrBO, AppletsAttrInfoVO attrInfoVO) {
        attrInfoVO.setCurrentValue(input);
        List<SysProductAttributeInfo> selects = attrBO.getAttrValue().getSelectValues();
        if (!CollectionUtil.isEmpty(selects)) {
            attrInfoVO.setSelects(selects.stream().map(i -> {
                AppletsAttrSelectVO appletsAttrSelectVO = new AppletsAttrSelectVO();
                appletsAttrSelectVO.setValue(i.getCode());
                appletsAttrSelectVO.setName(i.getName());
                return appletsAttrSelectVO;
            }).collect(Collectors.toList()));
        }
        if (Objects.isNull(input)) {
            String value = CollectionUtils.isEmpty(selects) ? null : selects.get(0).getCode();
            attrInfoVO.setCurrentValue(NumberUtils.isNumber(value) ? new BigDecimal(value).setScale(0, BigDecimal.ROUND_DOWN).intValue() : value);
        }
        attrInfoVO.setValueType(1);
        return attrInfoVO;
    }
}
