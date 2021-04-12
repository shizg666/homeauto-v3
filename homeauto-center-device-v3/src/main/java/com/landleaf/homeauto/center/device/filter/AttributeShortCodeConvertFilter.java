package com.landleaf.homeauto.center.device.filter;

import com.landleaf.homeauto.common.constant.CommonConst;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @ClassName FormaldehydeOutPutFilter
 * @Description: 甲醛输出过滤
 * @Author wyl
 * @Date 2021/1/6
 * @Version V2.0
 **/
@Component
public class AttributeShortCodeConvertFilter {


    public String convert(String deviceId, String shortCode) {
        return shortCode;
    }

    public String convert(String attributeCode) {
        String shortCode = attributeCode;
        if (StringUtils.isNotEmpty(attributeCode) && attributeCode.contains(CommonConst.SymbolConst.UNDER_LINE)) {
            String[] split = attributeCode.split(CommonConst.SymbolConst.UNDER_LINE);
            shortCode = split[split.length - 1];
        }
        return shortCode;
    }
}
