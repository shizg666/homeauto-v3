package com.landleaf.homeauto.center.device.model.bo.screen;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 家庭信息
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value = "ScreenFamilyBO", description = "家庭信息")
public class ScreenFamilyBO {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "启用状态0 开启，1 禁用")
    private Integer enableStatus;

    @ApiModelProperty(value = "户型主键id")
    private String templateId;

    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @ApiModelProperty(value = "楼盘ID")
    private String realestateId;

    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @ApiModelProperty(value = "权限路径")
    private String path;

    @ApiModelProperty(value = "权限路径名称")
    private String pathName;

    @ApiModelProperty(value = "激活时间")
    private LocalDateTime activeTime;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "大屏通信Mac")
    private String screenMac;


}
