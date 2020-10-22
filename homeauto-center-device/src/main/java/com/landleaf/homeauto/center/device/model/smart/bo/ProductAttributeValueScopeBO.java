package com.landleaf.homeauto.center.device.model.smart.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 产品属性值域范围业务对象
 *
 * @author Yujiumin
 * @version 2020/10/22
 */
@Data
@ApiModel("产品属性值域范围业务对象")
public class ProductAttributeValueScopeBO {

    private String attributeValue;

    private String minValue;

    private String maxValue;


}
