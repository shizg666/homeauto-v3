package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 家庭表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@Accessors(chain = true)
@ApiModel(value="PathBO", description="家庭path对象")
public class PathBO {

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "path")
    private String path;

    @ApiModelProperty(value = "pathName")
    private String pathName;

    @ApiModelProperty(value = "户型名称")
    private String templateName;

    @ApiModelProperty(value = "面积")
    private String area;


    @NotEmpty(message = "单元id不能为空")
    @ApiModelProperty(value = "单元id")
    private String unitId;

    @NotEmpty(message = "项目Id不能为空")
    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @NotEmpty(message = "楼盘ID不能为空")
    @ApiModelProperty(value = "楼盘ID")
    private String realestateId;

    @NotEmpty(message = "楼栋id不能为空")
    @ApiModelProperty(value = "楼栋id")
    private String buildingId;



}
