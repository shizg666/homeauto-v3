package com.landleaf.homeauto.center.device.service.status;

import com.landleaf.homeauto.center.device.model.bo.screen.ScreenStatusDealComplexBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductErrorAttrValueBO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;

/**
 * @className: ScreenStatusDealErrorHandleService
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/2
 **/
public interface ScreenStatusDealErrorHandleService {
    /**
     * @param: dealComplexBO   配置信息
     * @param: item            处理数据值
     * @param: screenProductErrorAttrValueBO   产品故障属性对象
     * @description: 处理故障状态
     * @return: void
     * @author: wyl
     * @date: 2021/6/2
     */
    void handleErrorStatus(ScreenStatusDealComplexBO dealComplexBO, ScreenDeviceAttributeDTO item,
                           ScreenProductErrorAttrValueBO screenProductErrorAttrValueBO, ScreenDeviceInfoStatusDTO familyDeviceInfoStatus);

    /**
     * @param: errorTypeEnum
     * @description: 校验是否满足条件需要处理
     * @return: boolean
     * @author: wyl
     * @date: 2021/6/2
     */
    boolean checkCondition(AttributeErrorTypeEnum errorTypeEnum);
}
