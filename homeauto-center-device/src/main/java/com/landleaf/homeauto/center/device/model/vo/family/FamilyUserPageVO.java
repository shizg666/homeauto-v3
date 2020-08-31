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
@ApiModel(value="FamilyUserPageVO", description="用户家庭列表对象")
public class FamilyUserPageVO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "审核状态0 未审核，1 已审核,2 授权中")
    private Integer reviewStatus;

    @ApiModelProperty(value = "交付状态0 未交付，1 已交付 2 已激活")
    private Integer deliveryStatus;

    @ApiModelProperty(value = "户型")
    private String templateName;

    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @ApiModelProperty(value = "单元id")
    private String unitId;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime reviewTime;

    @ApiModelProperty(value = "交付时间")
    private LocalDateTime deliveryTime;

    @ApiModelProperty(value = "激活时间")
    private LocalDateTime activeTime;


}
