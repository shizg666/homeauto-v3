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
@ApiModel(value="ProjectScreenUpgradeDetail对象", description="")
public class ProjectScreenUpgradeDetail extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "版本升级id")
    private Long projectUpgradeId;

    @ApiModelProperty(value = "家庭Id")
    private Long familyId;

    @ApiModelProperty(value = "更新状态（1：未完成；2：已完成）")
    private Integer status;

    @ApiModelProperty(value = "响应时间")
    private LocalDateTime resTime;

    @ApiModelProperty(value = "结果信息")
    private String resMsg;

    @ApiModelProperty(value = "apk版本号(此值为判定是否推送成功依据)")
    private String versionCode;

    @ApiModelProperty(value = "所属楼盘")
    private Long realestateId;

    @ApiModelProperty(value = "所属项目")
    private Long projectId;

    @ApiModelProperty(value = "推送时间（以时间判定为当前推送版本）")
    private LocalDateTime pushTime;


}
