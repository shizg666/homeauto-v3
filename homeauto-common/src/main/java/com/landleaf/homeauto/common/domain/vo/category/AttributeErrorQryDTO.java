package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 产品故障属性表
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="AttributeErrorQryDTO", description="AttributeErrorQryDTO")
public class AttributeErrorQryDTO {

//    @ApiModelProperty(value = "设备code")
//    private String deviceCode;
    //    @ApiModelProperty(value = "家庭code")
//    private String familyCode;
//@ApiModelProperty(value = "户型id")
//private String tempalteId;

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "设备id")
    private String deviceId;



}
