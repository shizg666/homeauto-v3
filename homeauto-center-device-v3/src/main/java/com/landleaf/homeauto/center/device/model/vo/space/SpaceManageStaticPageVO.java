package com.landleaf.homeauto.center.device.model.vo.space;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "SpaceManageStaticPageVO", description = "空间管理统计VO")
public class SpaceManageStaticPageVO {

    @ApiModelProperty("楼盘ID")
    private Long realestateId;
    @ApiModelProperty("项目ID")
    private Long projectId;
    @ApiModelProperty("楼栋号")
    private String buildingCode;
    @ApiModelProperty("单元数")
    private Integer unitCount;
    @ApiModelProperty("楼层数")
    private Integer floorCount;
    @ApiModelProperty("户数")
    private Integer familyCount;
    @ApiModelProperty("已绑定户数")
    private Integer bindMacCount;
    @ApiModelProperty("未绑定户数")
    private Integer unBindMacCount;


}
