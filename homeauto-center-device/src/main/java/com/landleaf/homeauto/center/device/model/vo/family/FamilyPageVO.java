package com.landleaf.homeauto.center.device.model.vo.family;

import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.center.device.model.bo.SimpleFamilyBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="FamilyPageVO", description="FamilyPageVO")
public class FamilyPageVO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String code;

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
