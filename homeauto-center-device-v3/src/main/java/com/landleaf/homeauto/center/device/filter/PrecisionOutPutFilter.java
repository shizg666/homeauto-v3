package com.landleaf.homeauto.center.device.filter;

import cn.jiguang.common.utils.StringUtils;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.domain.category.ProductAttributeInfoScope;
import com.landleaf.homeauto.center.device.model.dto.DeviceAttrPrecisionValueDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrInfoVO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrPrecisionVO;
import com.landleaf.homeauto.center.device.util.NumberUtils;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import com.landleaf.homeauto.common.enums.category.PrecisionEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Objects;

/**
 * @ClassName PrecisionOutPutFilter
 * @Description: 数度输出过滤
 * @Author wyl
 * @Date 2021/1/6
 * @Version V2.0
 **/
@Component
public class PrecisionOutPutFilter implements IAttributeOutPutFilter {
    @Autowired
    private FormaldehydeOutPutFilter formaldehydeOutPutFilter;
    @Autowired
    private VocOutPutFilter vocOutPutFilter;

    @Override
    public boolean checkFilter(ScreenProductAttrBO attrBO) {
        if (attrBO != null && attrBO.getAttrValue().getType().intValue() == AttributeTypeEnum.VALUE.getType()
                && !formaldehydeOutPutFilter.checkFilter(attrBO)
                && !vocOutPutFilter.checkFilter(attrBO)) {
            return true;
        }
        return false;
    }

    @Override
    public Object handle(Object input, ScreenProductAttrBO attrBO) {
        Object currentValue = Objects.isNull(input) ? 0 : input;
        ProductAttributeInfoScope precision = attrBO.getAttrValue().getNumValue();
        if (precision != null) {
            Integer precision1 = precision.getPrecision();
            // 有精度
            if (!Objects.isNull(input) && !Objects.isNull(precision1)) {
                try {
                    currentValue = PrecisionEnum.getInstByType(precision.getPrecision()).parse(input);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            //有最大值，有最小值
            if (StringUtils.isNotEmpty(precision.getMax()) || StringUtils.isNotEmpty(precision.getMin())) {

                DeviceAttrPrecisionValueDTO.DeviceAttrPrecisionValueDTOBuilder builder = DeviceAttrPrecisionValueDTO.builder();
                builder.maxValue(NumberUtils.parse(precision.getMax(), Float.class))
                        .minValue(NumberUtils.parse(precision.getMin(), Float.class))
                        .step(precision.getStep());

                builder.currentValue(currentValue != null ? NumberUtils.parse(currentValue, Float.class) : NumberUtils.parse(precision.getMin(), Float.class));

                return builder.build();
            }
        }
        return currentValue;
    }

    @Override
    public Object appGetStatusHandle(Object input, ScreenProductAttrBO attrBO) {
        return handle(input, attrBO);
    }

    @Override
    public AppletsAttrInfoVO handle(Object input, ScreenProductAttrBO attrBO, AppletsAttrInfoVO attrInfoVO) {
        Object currentValue = Objects.isNull(input) ? 0 : input;
        ProductAttributeInfoScope precision = attrBO.getAttrValue().getNumValue();
        if (precision != null) {
            Integer precision1 = precision.getPrecision();
            // 有精度
            if (!Objects.isNull(input) && !Objects.isNull(precision1)) {
                try {
                    currentValue = PrecisionEnum.getInstByType(precision.getPrecision()).parse(input);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            AppletsAttrPrecisionVO precisionVO = new AppletsAttrPrecisionVO();
            BeanUtils.copyProperties(precision, precisionVO);
            attrInfoVO.setPrecision(precisionVO);
            attrInfoVO.setValueType(2);
        }
        attrInfoVO.setCurrentValue(currentValue);
        return attrInfoVO;
    }
}
