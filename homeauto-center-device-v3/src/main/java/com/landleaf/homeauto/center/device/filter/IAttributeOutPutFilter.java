package com.landleaf.homeauto.center.device.filter;

import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrInfoVO;

/**
 * 设备属性输出过滤
 */
public interface IAttributeOutPutFilter {

    /**
     * 判断是否要经过滤器
     * @return
     */
    boolean checkFilter(String attributeId,String attributeCode);

    /**
     * 过滤方法
     */
    Object handle(Object input,String attributeId,String attributeCode);

    /**
     * app获取属性状态方法
     */
    Object appGetStatusHandle(Object input,String attributeId,String attributeCode);
    /**
     * 小程序获取属性展示
     */
    Object handle(Object input, String attributeId, String attributeCode, AppletsAttrInfoVO attrInfoVO);
}
