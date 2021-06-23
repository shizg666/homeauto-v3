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
public class HistoryQryDTO extends BaseQry {

    @ApiModelProperty(value = "家庭ID",required = true)
    private Long familyId;

    @ApiModelProperty("属性编码列表")
    private List<String> code;

    @ApiModelProperty("开始结束时间")
    private List<String> uploadTimes;

    @ApiModelProperty("设备名称")
    private String diviceSn;


}
