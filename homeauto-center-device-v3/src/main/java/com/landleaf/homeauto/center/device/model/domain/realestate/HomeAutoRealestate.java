package com.landleaf.homeauto.center.device.model.domain.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 楼盘表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoRealestate对象", description="楼盘表")
public class HomeAutoRealestate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "楼盘名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "开发商code")
    private String developerCode;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "地址path")
    private String path;

    @ApiModelProperty(value = "path名称")
    private String pathName;

    @ApiModelProperty(value = "国家编码")
    private String countryCode;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "市编码")
    private String cityCode;


    @ApiModelProperty(value = "国家编码")
    private String country;

    @ApiModelProperty(value = "省编码")
    private String province;

    @ApiModelProperty(value = "区编码")
    private String area;

    @ApiModelProperty(value = "市编码")
    private String city;

    @ApiModelProperty(value = "权限path")
    private String pathOauth;

    @ApiModelProperty(value = "完整的地址")
    private String addressComplete;

    @ApiModelProperty(value = "当前模式")
    private String modeStatus;


}
