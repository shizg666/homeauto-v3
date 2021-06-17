package com.landleaf.homeauto.center.device.model.dto.screenapk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "ProjectScreenUpgradeSaveDTO",description = "新增升级DTO")
@Data
public class ProjectScreenUpgradeSaveDTO implements Serializable {
    private static final long serialVersionUID = 8883758835540686233L;
    @ApiModelProperty(value = "楼盘")
    @NotNull
    private Long realestateId;
    @NotNull
    @ApiModelProperty(value = "项目")
    private Long projectId;
    @NotEmpty
    @ApiModelProperty(value = "apk下载地址")
    private String url;
    @NotEmpty
    @ApiModelProperty(value = "版本号(唯一标记)")
    private String versionCode;

    @ApiModelProperty(value = "本版本内容")
    private String description;
    @NotNull
    @ApiModelProperty(value = "升级类型(1:用户升级，2：后台升级)")
    private Integer upgradeType;
    @NotEmpty
    @ApiModelProperty(value = "文件名")
    private String fileName;
    @NotNull
    @ApiModelProperty(value = "选择房屋:推送路径（最全示例:楼栋/单元/楼/家庭）")
    private List<String> paths;




}
