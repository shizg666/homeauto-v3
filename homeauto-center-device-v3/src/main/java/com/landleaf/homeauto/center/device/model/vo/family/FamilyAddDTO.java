package com.landleaf.homeauto.center.device.model.vo.family;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class
FamilyAddDTO {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "名称(后台自动生成)")
    private String name;

    @ApiModelProperty(value = "编号(后台自动生成)")
    private String code;

    @ApiModelProperty(value = "门牌(后台自动生成)")
    private String doorPlate;

    @NotEmpty(message = "房号不能为空")
    @Length(min=1, max=2,message = "房号最多2个字符")
    @ApiModelProperty(value = "房号")
    private String roomNo;

    @ApiModelProperty(value = "户型id")
    private Long templateId;

    @ApiModelProperty(value = "前缀")
    private String prefix;

    @ApiModelProperty(value = "后缀")
    private String suffix;

    @NotEmpty(message = "楼层不能为空")
    @ApiModelProperty(value = "楼层")
    private String floor;

    @NotEmpty(message = "单元code不能为空")
    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @NotEmpty(message = "项目Id不能为空")
    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @NotEmpty(message = "楼盘ID不能为空")
    @ApiModelProperty(value = "楼盘ID")
    private Long realestateId;

    @NotEmpty(message = "楼栋code不能为空")
    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @ApiModelProperty(value = "后端组装")
    private String path;

    @ApiModelProperty(value = "后端组装")
    private String pathName;

    //
//    @ApiModelProperty(value = "ip")
//    private String ip;
//
//    @ApiModelProperty(value = "大屏通信Mac")
//    private String screenMac;
//
//    @ApiModelProperty(value = "起停用状态")
//    private Integer enableStatus;



}
