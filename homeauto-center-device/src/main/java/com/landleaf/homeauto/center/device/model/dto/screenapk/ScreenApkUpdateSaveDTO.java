package com.landleaf.homeauto.center.device.model.dto.screenapk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenyilu
 */
@ApiModel(value = "ScreenApkUpdateSaveDTO", description = "大屏apk新增更新记录对象")
@Data
public class ScreenApkUpdateSaveDTO implements Serializable {

    @ApiModelProperty(value = "apkId", required = true)
    private String apkId;

    @ApiModelProperty(value = "推送路径", required = true)
    private String path;


}
