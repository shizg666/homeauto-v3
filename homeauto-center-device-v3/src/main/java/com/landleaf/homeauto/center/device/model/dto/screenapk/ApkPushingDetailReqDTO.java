package com.landleaf.homeauto.center.device.model.dto.screenapk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenyilu
 */
@ApiModel(value = "ScreenApkUpdatePushingDetailReqDTO",description = "正在推送列表请求参数")
@Data
public class ApkPushingDetailReqDTO  implements Serializable {

    private static final long serialVersionUID = -6065996168005443540L;
    @ApiModelProperty(value = "应用ID")
    private String apkId;

}
