package com.landleaf.homeauto.center.device.model.vo.realestate;

import com.landleaf.homeauto.center.device.enums.EnergyModeEnum;
import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 楼盘表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="RealestateModeQryDTO", description="RealestateModeQryDTO")
public class RealestateModeQryDTO extends BaseQry {

    @ApiModelProperty(value = "楼盘名称")
    private String name;


    @ApiModelProperty(value = "楼盘名称")
    private List<String> ids;


}
