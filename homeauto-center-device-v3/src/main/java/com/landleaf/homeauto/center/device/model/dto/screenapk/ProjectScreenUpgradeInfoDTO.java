package com.landleaf.homeauto.center.device.model.dto.screenapk;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "ProjectScreenUpgradeInfoDTO",description = "upgrade详情")
@Data
public class ProjectScreenUpgradeInfoDTO implements Serializable {
    private static final long serialVersionUID = 8883758835540686233L;

    @ApiModelProperty(value = "记录ID")
    private Long id;
    @ApiModelProperty(value = "楼盘")
    private Long realestateId;
    @ApiModelProperty(value = "楼盘名称")
    private String realestateName;
    @ApiModelProperty(value = "项目")
    private Long projectId;
    @ApiModelProperty(value = "项目")
    private String projectName;
    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime uploadTime;
    @ApiModelProperty(value = "apk下载地址")
    private String url;
    @ApiModelProperty(value = "版本号(唯一标记)")
    private String versionCode;
    @ApiModelProperty(value = "本版本内容")
    private String description;
    @ApiModelProperty(value = "升级类型(1:用户升级，2：后台升级)")
    private Integer upgradeType;
    @ApiModelProperty(value = "升级类型(1:用户升级，2：后台升级)")
    private String upgradeTypeName;
    @ApiModelProperty(value = "文件名")
    private String fileName;
    @ApiModelProperty(value = "选择房屋:推送路径（最全示例:楼盘/项目/楼栋/单元/楼/家庭）")
    private List<String> paths;
    @ApiModelProperty(value = "已升级户数")
    private Integer upgradeCount;
    @ApiModelProperty(value = "未升级户数")
    private Integer noUpgradeCount;




}
