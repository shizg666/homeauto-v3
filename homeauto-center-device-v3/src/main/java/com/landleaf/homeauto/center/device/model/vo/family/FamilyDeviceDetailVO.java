package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.common.enums.category.BaudRateEnum;
import com.landleaf.homeauto.common.enums.category.CheckEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyDeviceDetailVO", description="FamilyDeviceDetailVO")
public class FamilyDeviceDetailVO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设备号")
    private String sn;

    @ApiModelProperty(value = "485端口号")
    private String port;


    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "品类名称")
    private String categoryName;

    @ApiModelProperty(value = "通信终端名称")
    private String terminalName;


}
