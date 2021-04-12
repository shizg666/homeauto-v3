package com.landleaf.homeauto.center.device.filter;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.model.HchoEnum;
import com.landleaf.homeauto.center.device.model.VocEnum;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrInfoVO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @ClassName VocOutPutFilter
 * @Description: VOC输出过滤
 * @Author wyl
 * @Date 2021/1/6
 * @Version V2.0
 **/
@Component
public class VocOutPutFilter implements IAttributeOutPutFilter {
    @Override
    public boolean checkFilter(ScreenProductAttrBO attrBO) {
        if (StringUtils.equals(attrBO.getAttrCode(), ProductPropertyEnum.VOC.code())) {
            return true;
        }
        return false;
    }

    @Override
    public Object handle(Object input,ScreenProductAttrBO attrBO) {
        if(Objects.isNull(input)){
            return VocEnum.LEVEL_0.getLevel();
        }
        return VocEnum.getAqi(Float.parseFloat(Objects.toString(input)));
    }

    @Override
    public Object appGetStatusHandle(Object input,ScreenProductAttrBO attrBO) {
        return handle( input, attrBO);
    }

    @Override
    public AppletsAttrInfoVO handle(Object input,ScreenProductAttrBO attrBO, AppletsAttrInfoVO attrInfoVO) {
        Object currentValue = null;
        if(Objects.isNull(input)){
            currentValue= VocEnum.LEVEL_0.getLevel();
        }else {
            currentValue=VocEnum.getAqi(Float.parseFloat(Objects.toString(input)));
        }
        attrInfoVO.setCurrentValue(currentValue);
        return attrInfoVO;
    }
}
