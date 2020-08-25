package com.landleaf.homeauto.center.device.model.dto.screenapk;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "SmarthomeScreenApkPageDTO", description = "大屏apk列表查询对象")
@Data
public class ScreenApkPageDTO extends BaseQry implements Serializable {

    private static final long serialVersionUID = -6069168206345621158L;
    @ApiModelProperty(value = "版本号(唯一标记)", required = false)
    private String versionCode;
    @ApiModelProperty(value = "应用 名称", required = false)
    private String name;
    @ApiModelProperty(value = "上传者", required = false)
    private String uploadUser;
    @ApiModelProperty(value = "上传时间始", required = false)
    private Date uploadTimeStart;
    @ApiModelProperty(value = "上传时间止", required = false)
    private Date uploadTimeEnd;


}
