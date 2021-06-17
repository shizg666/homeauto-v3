package com.landleaf.homeauto.center.device.model.dto.screenapk;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wenyilu
 */
@ApiModel(value = "ProjectScreenUpgradeDetailPageDTO", description = "ota升级查询条件")
@Data
public class ProjectScreenUpgradeDetailPageDTO extends BaseQry implements Serializable {

    private static final long serialVersionUID = -6069168206345621158L;
    @ApiModelProperty(value = "ota升级记录ID")
    private Long upgradeId;
    @ApiModelProperty(value = "更新状态（1：未完成；2：已完成）")
    private Integer status;
    @ApiModelProperty(value = "楼盘")
    private Long realestateId;
    @ApiModelProperty(value = "项目")
    private Long projectId;
    @ApiModelProperty(value = "选择房屋:推送路径（最全示例:楼盘/项目/楼栋/单元/楼/家庭）")
    private List<String> paths;


}
