package com.landleaf.homeauto.common.domain.dto.datacollect;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Lokiy
 * @Date 2021/7/28 16:09
 * @Description 同步云端dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("同步云端传输类")
public class SyncCloudDTO {

    @ApiModelProperty("同步类型")
    private String syncType;

    @ApiModelProperty("楼盘id")
    private Long realestateId;

    @ApiModelProperty("压缩数据")
    private String encodeData;
}
