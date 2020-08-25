package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 家庭表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoFamily对象", description="家庭表")
@TableName("home_auto_family")
public class HomeAutoFamilyDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField("code")
    @ApiModelProperty(value = "编号")
    private String code;

    @TableField("review_status")
    @ApiModelProperty(value = "审核状态0 未审核，1 已审核,2 授权中")
    private Integer reviewStatus;

    @TableField("delivery_status")
    @ApiModelProperty(value = "交付状态0 未交付，1 已交付 2 已激活")
    private Integer deliveryStatus;

    @TableField("template_name")
    @ApiModelProperty(value = "户型")
    private String templateName;

    @TableField("room_no")
    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @TableField("unit_id")
    @ApiModelProperty(value = "单元id")
    private String unitId;

    @TableField("project_id")
    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @TableField("realestate_id")
    @ApiModelProperty(value = "楼盘ID")
    private String realestateId;

    @TableField("building_id")
    @ApiModelProperty(value = "楼栋id")
    private String buildingId;

    @TableField("path")
    @ApiModelProperty(value = "权限路径")
    private String path;

    @TableField("path_name")
    @ApiModelProperty(value = "权限路径名称")
    private String pathName;

    @TableField("review_time")
    @ApiModelProperty(value = "审核时间")
    private LocalDateTime reviewTime;

    @TableField("delivery_time")
    @ApiModelProperty(value = "交付时间")
    private LocalDateTime deliveryTime;

    @TableField("active_time")
    @ApiModelProperty(value = "激活时间")
    private LocalDateTime activeTime;


}
