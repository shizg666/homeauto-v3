package com.landleaf.homeauto.center.device.model.dto.screenapk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "ScreenApkUpdateDetailResDTO", description = "大屏apk更新详情列表更新记录返回对象")
@Data
public class ApkUpdateDetailResDTO implements Serializable {


    private static final long serialVersionUID = -6458600077243057162L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "应用名称")
    private String apkName;
    @ApiModelProperty(value = "应用版本")
    private String apkVersionCode;
    @ApiModelProperty(value = "家庭编号")
    private String familyCode;
    @ApiModelProperty(value = "家庭名称")
    private String familyName;
    @ApiModelProperty(value = "所属楼盘")
    private String realestateName;
    @ApiModelProperty(value = "所属项目")
    private String projectName;
    @ApiModelProperty(value = "路径(0/110000/110000/110101/road_code)")
    private String path;
    @ApiModelProperty(value = "家庭地址中文名")
    private String pathName;
    @ApiModelProperty(value = "推送时间")
    private String uploadTimeFormat;
    @ApiModelProperty(value = "推送时间")
    private Date uploadTime;
    @ApiModelProperty(value = "更新状态（1：更新中；2：下发成功；3：下发失败）")
    private Integer status;
    @ApiModelProperty(value = "更新状态（1：更新中；2：下发成功；3：下发失败）")
    private String statusName;
    @ApiModelProperty(value = "房间号")
    private String roomNo;
}
