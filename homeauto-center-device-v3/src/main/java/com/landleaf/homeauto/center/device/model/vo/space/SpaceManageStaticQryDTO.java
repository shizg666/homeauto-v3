package com.landleaf.homeauto.center.device.model.vo.space;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 空間管理統計查詢請求體
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SpaceManageStaticQryDTO", description = "空間管理統計查詢請求體")
public class SpaceManageStaticQryDTO extends BaseQry {

    @ApiModelProperty(value = "楼盘ID",required = true)
    private Long realestateId;
    @ApiModelProperty(value = "项目ID",required = true)
    private Long projectId;
    @ApiModelProperty("楼栋号")
    private String buildingCode;


}
