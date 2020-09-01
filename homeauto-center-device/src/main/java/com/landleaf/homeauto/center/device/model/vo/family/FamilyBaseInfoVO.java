package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.center.device.enums.FamilyDeliveryStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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
@Accessors(chain = true)
@ApiModel(value="FamilyBaseInfoVO", description="家庭基本信息对象")
public class FamilyBaseInfoVO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "户号")
    private String roomNo;


    @ApiModelProperty(value = "户型名称")
    private String templateName;

    @ApiModelProperty(value = "楼盘地址")
    private String addressComplete;

    @ApiModelProperty(value = "项目类型")
    private Integer type;

    @ApiModelProperty(value = "项目类型")
    private String typeStr;

    @ApiModelProperty(value = "审核时间")
    private LocalDateTime reviewTime;

    @ApiModelProperty(value = "交付时间")
    private LocalDateTime deliveryTime;

    @ApiModelProperty(value = "激活时间")
    private LocalDateTime activeTime;

    @ApiModelProperty(value = "审核状态0 未审核，1 已审核,2 授权中")
    private Integer reviewStatus;

    @ApiModelProperty(value = "交付状态0 未交付，1 已交付 2 已激活")
    private Integer deliveryStatus;

    @ApiModelProperty(value = "审核状态")
    private String reviewStatusStr;

    @ApiModelProperty(value = "交付状态")
    private String deliveryStatusStr;

    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
        this.reviewStatusStr = FamilyReviewStatusEnum.getInstByType(reviewStatus) != null?FamilyReviewStatusEnum.getInstByType(reviewStatus).getName():"";
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        this.deliveryStatusStr = FamilyDeliveryStatusEnum.getInstByType(deliveryStatus) != null?FamilyDeliveryStatusEnum.getInstByType(deliveryStatus).getName():"";
    }



}
