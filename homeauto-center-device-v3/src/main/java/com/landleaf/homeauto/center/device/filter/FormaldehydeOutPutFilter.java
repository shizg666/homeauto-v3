package com.landleaf.homeauto.center.device.filter;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.model.HchoEnum;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrInfoVO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @ClassName FormaldehydeOutPutFilter
 * @Description: 甲醛输出过滤
 * @Author wyl
 * @Date 2021/1/6
 * @Version V2.0
 **/
@Component
public class FormaldehydeOutPutFilter implements IAttributeOutPutFilter {
    @Override
    public boolean checkFilter(ScreenProductAttrBO attrBO) {
        if (StringUtils.equals(attrBO.getAttrCode(), ProductPropertyEnum.HCHO.code())) {
            return true;
        }
        return false;
    }

    @Override
    public Object handle(Object input, ScreenProductAttrBO attrBO) {
        if(Objects.isNull(input)){
            return HchoEnum.LEVEL_0.getLevel();
        }
        return HchoEnum.getAqi(Float.parseFloat(Objects.toString(input)));
    }

    @Override
    public Object appGetStatusHandle(Object input, ScreenProductAttrBO attrBO) {
        return handle(input,attrBO);
    }

    @Override
    public AppletsAttrInfoVO handle(Object input,ScreenProductAttrBO attrBO,AppletsAttrInfoVO attrInfoVO) {
        Object currentValue = null;
        if(Objects.isNull(input)){
            currentValue=HchoEnum.LEVEL_0.getLevel();
        }else {
            currentValue=HchoEnum.getAqi(Float.parseFloat(Objects.toString(input)));
        }
        attrInfoVO.setCurrentValue(currentValue);
        return attrInfoVO;
    }
}
