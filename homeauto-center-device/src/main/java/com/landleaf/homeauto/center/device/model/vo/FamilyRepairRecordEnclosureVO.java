package com.landleaf.homeauto.center.device.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 家庭维修记录上傳返回对象
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel("家庭维修记录上傳返回对象")
public class FamilyRepairRecordEnclosureVO {


    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "地址")
    private String url;
    @ApiModelProperty(value = "文件类型")
    private String fileType;






}
