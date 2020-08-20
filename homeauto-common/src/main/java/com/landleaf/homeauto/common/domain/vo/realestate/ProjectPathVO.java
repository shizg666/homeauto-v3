package com.landleaf.homeauto.common.domain.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 楼盘表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectPathVO", description="ProjectPathVO")
public class ProjectPathVO {

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "楼盘id")
    private String realestateId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "楼盘名称")
    private String realestateName;

    @ApiModelProperty(value = "国家编码")
    private String countryCode;

    @ApiModelProperty(value = "省编码")
    private String provinceCode;

    @ApiModelProperty(value = "区编码")
    private String areaCode;

    @ApiModelProperty(value = "市编码")
    private String cityCode;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "省编")
    private String province;

    @ApiModelProperty(value = "区")
    private String area;

    @ApiModelProperty(value = "市")
    private String city;

}
