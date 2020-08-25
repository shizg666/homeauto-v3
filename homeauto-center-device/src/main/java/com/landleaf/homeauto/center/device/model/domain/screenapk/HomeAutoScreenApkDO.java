package com.landleaf.homeauto.center.device.model.domain.screenapk;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 大屏apk
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoScreenApkDO对象", description="大屏apk")
public class HomeAutoScreenApkDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "apk下载地址")
    private String url;

    @ApiModelProperty(value = "apk存储绝对路径（暂无）")
    private String path;

    @ApiModelProperty(value = "版本号(唯一标记)")
    private String versionCode;

    @ApiModelProperty(value = "本版本内容")
    private String description;

    @ApiModelProperty(value = "上传者")
    private String uploadUser;

    @ApiModelProperty(value = "上传时间")
    private LocalDateTime uploadTime;


}
