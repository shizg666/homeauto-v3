package com.landleaf.homeauto.center.device.model.dto.screenapk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wenyilu
 */
@ApiModel(value = "ScreenApkUpdatePushingDetailResDTO", description = "正在推送记录返回对象")
@Data
public class ApkPushingDetailResDTO implements Serializable {

    private static final long serialVersionUID = -147129826724486657L;
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "推送记录Id")
    private String apkUpdateId;

    @ApiModelProperty(value = "家庭名称")
    private String familyName;

    @ApiModelProperty(value = "家庭编号")
    private String  familyCode;

    @ApiModelProperty(value = "家庭地址")
    private String pathName;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;

    @ApiModelProperty(value = "更新状态（1：更新中；2：下发成功；3：下发失败）")
    private Integer status;
    @ApiModelProperty(value = "更新状态（1：更新中；2：下发成功；3：下发失败）")
    private String statusName;



}
