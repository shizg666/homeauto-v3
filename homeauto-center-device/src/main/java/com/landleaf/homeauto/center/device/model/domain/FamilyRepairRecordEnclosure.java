package com.landleaf.homeauto.center.device.model.domain;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 家庭维修记录附件
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="FamilyRepairRecordEnclosure对象", description="家庭维修记录附件")
public class FamilyRepairRecordEnclosure extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "维修记录ID")
    private String familyRepairId;

    @ApiModelProperty(value = "附件地址")
    private String url;
    @ApiModelProperty(value = "附件名")
    private String fileName;
    @ApiModelProperty(value = "附件类型")
    private String fileType;


}
