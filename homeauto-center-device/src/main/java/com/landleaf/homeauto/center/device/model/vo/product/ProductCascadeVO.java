package com.landleaf.homeauto.center.device.model.vo.product;

import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeInfoVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="ProductCascadeVO", description="ProductCascadeVO")
public class ProductCascadeVO {

    @ApiModelProperty("产品id")
    private String id;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("类别code")
    private String categoryCode;

    @ApiModelProperty("类别名称")
    private String categoryName;

    @ApiModelProperty("品牌名称")
    private String brandName;

    @ApiModelProperty("型号")
    private String model;

}
