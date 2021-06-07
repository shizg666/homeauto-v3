package com.landleaf.homeauto.center.device.model.domain.screenapk;

import java.time.LocalDateTime;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProjectScreenUpgrade对象", description="")
public class ProjectScreenUpgrade extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "apk下载地址")
    private String url;

    @ApiModelProperty(value = "apk存储绝对路径（暂无）")
    private String path;

    @ApiModelProperty(value = "版本号(唯一标记)")
    private String versionCode;

    @ApiModelProperty(value = "本版本内容")
    private String description;

    @ApiModelProperty(value = "上传时间")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "楼盘")
    private Long realestateId;

    @ApiModelProperty(value = "项目")
    private Long projectId;

    @ApiModelProperty(value = "升级类型(1:用户升级，2：后台升级)")
    private Integer upgradeType;

    @ApiModelProperty(value = "文件名")
    private String fileName;


}
