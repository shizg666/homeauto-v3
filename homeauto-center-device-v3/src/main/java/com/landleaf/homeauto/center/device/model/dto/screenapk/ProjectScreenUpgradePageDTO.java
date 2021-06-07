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
@ApiModel(value = "ProjectScreenUpgradePageDTO", description = "ota升级查询条件")
@Data
public class ProjectScreenUpgradePageDTO extends BaseQry implements Serializable {

    private static final long serialVersionUID = -6069168206345621158L;
    @ApiModelProperty(value = "楼盘")
    private Long realestateId;
    @ApiModelProperty(value = "项目")
    private Long projectId;
    @ApiModelProperty(value = "上传时间范围")
    private List<String> versionTime;


}
