package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 家庭维修记录附件 新增请求对象
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Data
public class FamilyRepairRecordEnclosureDTO {

    @ApiModelProperty(value = "附件名")
    private String fileName;

    @ApiModelProperty(value = "附件地址")
    private String url;
    @ApiModelProperty(value = "附件类型")
    private String fileType;


}
