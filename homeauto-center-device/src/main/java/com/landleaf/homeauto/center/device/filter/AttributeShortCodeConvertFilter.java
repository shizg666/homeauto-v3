package com.landleaf.homeauto.center.device.filter;

import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
import com.landleaf.homeauto.center.device.service.mybatis.IDeviceAttrInfoService;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName FormaldehydeOutPutFilter
 * @Description: 甲醛输出过滤
 * @Author wyl
 * @Date 2021/1/6
 * @Version V2.0
 **/
@Component
public class AttributeShortCodeConvertFilter{

    @Autowired
    private IDeviceAttrInfoService deviceAttrInfoService;

    public String convert(String deviceId,String shortCode) {
        List<DeviceAttrInfo> attributes = deviceAttrInfoService.getAttributesByDeviceId(deviceId, null, null);
        Optional<DeviceAttrInfo> optional = attributes.stream().filter(i -> {
            String attributeCode = i.getCode();
            if (StringUtils.isNotEmpty(attributeCode) && attributeCode.contains(CommonConst.SymbolConst.UNDER_LINE)) {
                String[] split = attributeCode.split(CommonConst.SymbolConst.UNDER_LINE);
                if (StringUtils.equals(shortCode, split[split.length - 1])) {
                    return true;
                }
            }
            return false;
        }).findFirst();
        if(optional.isPresent()){
            return optional.get().getCode();
        }
       throw new BusinessException(ErrorCodeEnumConst.ATTRIBUTE_CODE_NOT_FOUND);
    }

    public String convert(String attributeCode){
        String shortCode= attributeCode;
        if(StringUtils.isNotEmpty(attributeCode)&&attributeCode.contains(CommonConst.SymbolConst.UNDER_LINE)){
            String[] split = attributeCode.split(CommonConst.SymbolConst.UNDER_LINE);
            shortCode= split[split.length-1];
        }
        return shortCode;
    }
}
