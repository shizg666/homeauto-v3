package com.landleaf.homeauto.center.device.model.domain.screenapk;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 大屏apk更新记录
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("home_auto_screen_apk_update")
@ApiModel(value = "v对象", description = "大屏apk更新记录")
public class HomeAutoScreenApkUpdateDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推送范围可精确到工程ID")
    private String path;

    @ApiModelProperty(value = "apk记录id")
    private String apkId;


}
