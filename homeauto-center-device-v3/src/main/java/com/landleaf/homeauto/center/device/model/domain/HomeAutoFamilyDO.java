package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import com.landleaf.homeauto.common.domain.BaseEntity2;
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
public class HomeAutoFamilyDO extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    @ApiModelProperty(value = "名称")
    private String name;

    @TableField("code")
    @ApiModelProperty(value = "编号")
    private String code;

    @TableField("enable_status")
    @ApiModelProperty(value = "启用状态1开启，0禁用")
    private Integer enableStatus;

    @TableField("template_id")
    @ApiModelProperty(value = "户型主键id")
    private Long templateId;

    @TableField("room_no")
    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @TableField("doorplate")
    @ApiModelProperty(value = "门牌号")
    private String doorplate;

    @TableField("unit_code")
    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @TableField("project_id")
    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @TableField("realestate_id")
    @ApiModelProperty(value = "楼盘ID")
    private Long realestateId;

    @TableField("floor")
    @ApiModelProperty(value = "楼层")
    private String floor;

    @TableField("building_code")
    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @TableField("path")
    @ApiModelProperty(value = "权限路径")
    private String path;

    @TableField("path_1")
    @ApiModelProperty(value = "权限路径1 楼盘id/项目id/楼栋/单元/家庭id")
    private String path1;

    @TableField("path_2")
    @ApiModelProperty(value = "权限路径2 楼盘id/楼栋/单元/家庭id")
    private String path2;

    @TableField("path_name")
    @ApiModelProperty(value = "权限路径名称")
    private String pathName;

    @TableField("active_time")
    @ApiModelProperty(value = "激活时间")
    private LocalDateTime activeTime;

    @TableField("ip")
    @ApiModelProperty(value = "ip")
    private String ip;

    @TableField("screen_mac")
    @ApiModelProperty(value = "大屏通信Mac")
    private String screenMac;

    @ApiModelProperty(value = "前缀")
    private String prefix;

    @ApiModelProperty(value = "后缀")
    private String suffix;

    @ApiModelProperty(value = "楼栋名称")
    private String buildingName;

    @ApiModelProperty(value = "单元名称")
    private String unitName;



}
