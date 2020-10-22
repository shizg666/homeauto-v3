package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

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
@ApiModel(value="FamilyAddDTO", description="家庭对象")
public class
FamilyAddDTO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @NotEmpty(message = "户号不能为空")
    @Length(min=1, max=4,message = "不能超过4个字符")
    @ApiModelProperty(value = "户号")
    private String roomNo;

    @ApiModelProperty(value = "户型id")
    private String templateId;

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


    @ApiModelProperty(value = "")
    private String path;

    @ApiModelProperty(value = "")
    private String pathName;



}
