package com.landleaf.homeauto.center.device.filter.sys;

import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.center.device.model.smart.vo.AppletsAttrInfoVO;

/**
 * 设备属性输出过滤
 */
public interface ISysAttributeOutPutFilter {

    /**
     * 判断是否要经过滤器
     *
     * @return
     */
    boolean checkFilter(ScreenSysProductAttrBO attrBO);

    /**
     * 过滤方法
     */
    Object handle(Object input, ScreenSysProductAttrBO attrBO);

    /**
     * app获取属性状态方法
     */
    Object appGetStatusHandle(Object input, ScreenSysProductAttrBO attrBO);

    /**
     * 小程序获取属性展示
     */
    Object handle(Object input, ScreenSysProductAttrBO attrBO, AppletsAttrInfoVO attrInfoVO);
}
