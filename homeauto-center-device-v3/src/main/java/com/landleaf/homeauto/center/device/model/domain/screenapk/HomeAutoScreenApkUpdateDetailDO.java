package com.landleaf.homeauto.center.device.model.domain.screenapk;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 大屏apk更新记录详情
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("home_auto_screen_apk_update_detail")
@ApiModel(value="HomeAutoScreenApkUpdateDetailDO对象", description="大屏apk更新记录详情")
public class HomeAutoScreenApkUpdateDetailDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推送记录Id")
    private String apkUpdateId;

    @ApiModelProperty(value = "家庭Id")
    private String familyId;

    @ApiModelProperty(value = "apk记录id")
    private String apkId;
    @ApiModelProperty(value = "apk名称")
    private String apkName;

    @ApiModelProperty(value = "更新状态（1：更新中；2：更新成功；3：更新失败）")
    private Integer status;

    @ApiModelProperty(value = "响应时间")
    private LocalDateTime resTime;

    @ApiModelProperty(value = "结果信息")
    private String resMsg;

    @ApiModelProperty(value = "apk版本号(此值为判定是否推送成功依据)")
    private String apkVersion;
    @ApiModelProperty(value = "所属楼盘")
    private String realestateId;
    @ApiModelProperty(value = "所属项目")
    private String projectId;
    @ApiModelProperty(value = "推送时间")
    private LocalDateTime pushTime;


}
