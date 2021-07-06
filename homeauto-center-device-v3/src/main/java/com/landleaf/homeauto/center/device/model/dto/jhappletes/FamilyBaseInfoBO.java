package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="FamilyBaseInfoBO", description="家庭基本信息对象")
public class FamilyBaseInfoBO {

    @ApiModelProperty(value = "家庭主键id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "启用状态0 开启，1 禁用")
    private Integer enableStatus;

    @ApiModelProperty(value = "户型主键id")
    private Long templateId;

    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    private Long realestateId;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "大屏通信Mac")
    private String screenMac;



}
