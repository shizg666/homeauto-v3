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

    @TableField("enable_status")
    @ApiModelProperty(value = "启用状态0 开启，1 禁用")
    private Integer enableStatus;

    @TableField("template_id")
    @ApiModelProperty(value = "户型主键id")
    private String templateId;

    @TableField("room_no")
    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @TableField("unit_code")
    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @TableField("project_id")
    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @TableField("realestate_id")
    @ApiModelProperty(value = "楼盘ID")
    private String realestateId;

    @TableField("building_code")
    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @TableField("path")
    @ApiModelProperty(value = "权限路径")
    private String path;

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



}
