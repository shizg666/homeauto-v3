package com.landleaf.homeauto.center.device.model.vo.device;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 家庭设备
 *
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "DeviceManageQryDTO", description = "设别管理查询对象")
public class FaultManageQryDTO extends BaseQry {

    @ApiModelProperty(value = "楼盘ID",required = true)
    private Long realestateId;

    @ApiModelProperty("房间号列表：楼栋/单元/familyId")
    private List<String> locatePaths;

    @ApiModelProperty("故障信息名称")
    private String faultMsg;


    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

}
