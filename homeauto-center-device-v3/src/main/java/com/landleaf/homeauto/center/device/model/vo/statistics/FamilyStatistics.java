package com.landleaf.homeauto.center.device.model.vo.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyStatistics", description="FamilyStatistics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FamilyStatistics {

    @ApiModelProperty(value = "品类")
    private Long familyId;

    @ApiModelProperty(value = "户型id")
    private Long templateId;





}
