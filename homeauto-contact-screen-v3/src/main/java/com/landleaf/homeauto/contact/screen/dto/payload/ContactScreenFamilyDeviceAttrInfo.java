package com.landleaf.homeauto.contact.screen.dto.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wenyilu
 * @ClassName 设备信息
 **/
@Data
public class ContactScreenFamilyDeviceAttrInfo {

    @ApiModelProperty(value = "属性編碼")
    private String attrCode;

    @ApiModelProperty(value = "属性值类别;1:多选，2:值域")
    private Integer attrValueType;

    @ApiModelProperty(value = "属性约束;0:普通属性，1:系统属性，2：关联系统属性")
    private Integer attrConstraint;

    @ApiModelProperty(value = "值类别为多选时，可选值列表")
    private List<ContactScreenDeviceAttributeInfo> selectValues;
    @ApiModelProperty(value = "值类别为值域时，值域对象")
    private ContactScreenDeviceAttributeInfoScope numValue;


}
