package com.landleaf.homeauto.model.po.device;

import com.landleaf.homeauto.model.po.base.BasePO;
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
public class HomeAutoFamilyPO extends BasePO {

    private static final long serialVersionUID = 1L;

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
