package com.landleaf.homeauto.center.device.model.vo.device;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 家庭设备
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "DeviceManageQryDTO", description = "设别管理查询对象")
public class DeviceManageQryDTO extends BaseQry {

    @ApiModelProperty("楼盘名称")
    private String realestateName;

    @ApiModelProperty("项目项目")
    private String projectName;

    @ApiModelProperty("房间名称")
    private String familyName;


}
