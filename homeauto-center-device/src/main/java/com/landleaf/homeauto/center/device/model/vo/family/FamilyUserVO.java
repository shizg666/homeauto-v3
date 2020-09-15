package com.landleaf.homeauto.center.device.model.vo.family;

import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.center.device.enums.FamilyDeliveryStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import com.landleaf.homeauto.common.enums.realestate.ProjectTypeEnum;
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
@ApiModel(value="FamilyUserVO", description="用户家庭列表")
public class FamilyUserVO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编号")
    private String code;

    @ApiModelProperty(value = "审核状态0 未审核，1 已审核,2 授权中")
    private Integer reviewStatus;

    @ApiModelProperty(value = "审核状态0 未审核，1 已审核,2 授权中")
    private String reviewStatusStr;

    @ApiModelProperty(value = "交付状态0 未交付，1 已交付 2 已激活")
    private Integer deliveryStatus;

    @ApiModelProperty(value = "交付状态0 未交付，1 已交付 2 已激活")
    private String deliveryStatusStr;


    @ApiModelProperty(value = "所属楼盘")
    private String realestateName;

    @ApiModelProperty(value = "所属项目")
    private String projectName;

    @ApiModelProperty(value = "项目类型")
    private Integer type;

    @ApiModelProperty(value = "项目类型字符串")
    private String typeStr;

    @ApiModelProperty(value = "家庭地址")
    private String pathName;


    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
        this.reviewStatusStr = FamilyReviewStatusEnum.getInstByType(reviewStatus) != null?FamilyReviewStatusEnum.getInstByType(reviewStatus).getName():"";
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
        this.deliveryStatusStr = FamilyDeliveryStatusEnum.getInstByType(deliveryStatus) != null?FamilyDeliveryStatusEnum.getInstByType(reviewStatus).getName():"";
    }

    public void setType(Integer type) {
        this.type = type;
        this.typeStr = ProjectTypeEnum.getInstByType(type)!=null?ProjectTypeEnum.getInstByType(type).getName():"";
    }
}
