package com.landleaf.homeauto.center.device.model.vo.family;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @Length(min=1, max=4,message = "户号不能超过4个字符")
    @ApiModelProperty(value = "户号")
    private String roomNo;


    @ApiModelProperty(value = "户型id")
    private String templateId;

    @NotEmpty(message = "单元code不能为空")
    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @NotEmpty(message = "项目Id不能为空")
    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @NotEmpty(message = "楼盘ID不能为空")
    @ApiModelProperty(value = "楼盘ID")
    private String realestateId;

    @NotEmpty(message = "楼栋code不能为空")
    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "大屏通信Mac")
    private String screenMac;

    @ApiModelProperty(value = "")
    private String path;

    @ApiModelProperty(value = "")
    private String pathName;

    @ApiModelProperty(value = "起停用状态")
    private Integer enableStatus;



}
