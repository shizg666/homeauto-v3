package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.center.device.enums.FamilyDeliveryStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="FamilyPageVO", description="家庭列表对象")
public class FamilyPageVO {

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

    @ApiModelProperty(value = "审核状态")
    private String reviewStatusStr;

    @ApiModelProperty(value = "交付状态")
    private String deliveryStatusStr;

    @ApiModelProperty(value = "户型")
    private String templateName;

    @ApiModelProperty(value = "房间号")
    private String roomNo;

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
        this.reviewStatusStr = FamilyReviewStatusEnum.getInstByType(reviewStatus) != null?FamilyReviewStatusEnum.getInstByType(reviewStatus).getName():"";
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        this.deliveryStatusStr = FamilyDeliveryStatusEnum.getInstByType(deliveryStatus) != null?FamilyDeliveryStatusEnum.getInstByType(deliveryStatus).getName():"";
    }
}
