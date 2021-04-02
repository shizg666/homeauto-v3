package com.landleaf.homeauto.center.device.model.dto.screenapk;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author wenyilu
 */
@ApiModel(value = "SmarthomeScreenApkUpdatePageDTO", description = "大屏apk更新详情列表查询对象")
@Data
public class ApkUpdateDetailPageDTO extends BaseQry implements Serializable {


    private static final long serialVersionUID = 1382900389372800522L;

    @ApiModelProperty(value = "应用名称")
    private String apkName;
    @ApiModelProperty(value = "版本号")
    private String versionCode;
    @ApiModelProperty(value = "所属项目")
    private String projectId;
    @ApiModelProperty(value = "所属楼盘")
    private String realestateId;
    @ApiModelProperty(value = "推送时间范围")
    private List<String> createTime;

}
