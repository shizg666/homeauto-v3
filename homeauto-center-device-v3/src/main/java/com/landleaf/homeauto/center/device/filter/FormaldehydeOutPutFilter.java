package com.landleaf.homeauto.center.device.filter;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.center.device.enums.ProductPropertyEnum;
import com.landleaf.homeauto.center.device.model.HchoEnum;
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
    public boolean checkFilter(String attributeId, String attributeCode) {
        // attributeCode  协议_区域_设备_属性
        String[] ss = attributeCode.split("_");
        String attrCode = ss[ss.length - 1];
        if (StringUtils.equals(attrCode, ProductPropertyEnum.HCHO.code())) {
            return true;
        }
        return false;
    }

    @Override
    public Object handle(Object input,String attributeId,String attributeCode) {
        if(Objects.isNull(input)){
            return HchoEnum.LEVEL_0.getLevel();
        }
        return HchoEnum.getAqi(Float.parseFloat(Objects.toString(input)));
    }

    @Override
    public Object appGetStatusHandle(Object input, String attributeId, String attributeCode) {
        return handle(input,attributeId,attributeCode);
    }

    @Override
    public AppletsAttrInfoVO handle(Object input, String attributeId, String attributeCode,AppletsAttrInfoVO attrInfoVO) {
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
