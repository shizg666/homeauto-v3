package com.landleaf.homeauto.center.device.model.domain.familydevice;

import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author lokiy
 * @since 2021-06-04
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyDevice对象", description="")
public class FamilyDevice extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭id")
    private Long familyId;

    @ApiModelProperty(value = "家庭code")
    private String familyCode;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品code")
    private String productCode;

    @ApiModelProperty(value = " 楼盘id/项目id/楼栋/单元/家庭id")
    private String path1;

    @ApiModelProperty(value = "楼盘id/楼栋/单元/家庭id")
    private String path2;

    @ApiModelProperty(value = "户型id")
    private Long templateId;

    @ApiModelProperty(value = "房间id")
    private Long roomId;

    @ApiModelProperty(value = "品类code")
    private String categoryCode;

    @ApiModelProperty(value = "暖通故障")
    private Integer havcFaultFlag;

    @ApiModelProperty(value = "数值故障")
    private Integer valueFaultFlag;

    @ApiModelProperty(value = "是否在线")
    private Integer onlineFlag;

    @ApiModelProperty(value = "设备id")
    private Long deviceId;


}
