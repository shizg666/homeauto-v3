package com.landleaf.homeauto.common.domain.po.address;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * <p>
 * 地址表
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoAddress", description="地址表")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeAutoAddress extends BaseEntity {


    private static final long serialVersionUID = -2688108192696614030L;
    @NotBlank(message = "省名称不能为空")
    @ApiModelProperty(value = "省")
    private String province;

    @NotBlank(message = "省编码不能为空")
    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @NotBlank(message = "市名称不能为空")
    @ApiModelProperty(value = "市")
    private String city;

    @NotBlank(message = "市编码不能为空")
    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @NotBlank(message = "区名称不能为空")
    @ApiModelProperty(value = "区")
    private String area;

    @NotBlank(message = "区编码不能为空")
    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @NotBlank(message = "地址名称不能为空")
    @ApiModelProperty(value = "小区/路")
    private String road;

    @NotBlank(message = "国家名称不能为空")
    @ApiModelProperty(value = "国家")
    private String country;

    @NotBlank(message = "国家编码不能为空")
    @ApiModelProperty(value = "国家编码")
    private String countryCode;


    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;

    @ApiModelProperty(value = "路径")
    private String path;

    @ApiModelProperty(value = "路径中文名称")
    private String pathName;

    @ApiModelProperty(value = "配置标志")
    private Integer configFlag;


}
