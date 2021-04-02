package com.landleaf.homeauto.center.device.model.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="RealestateModeUpdateVO", description="RealestateModeUpdateVO")
public class RealestateModeUpdateVO {


    @ApiModelProperty(value = "主键id 修改必传")
    private String id;


    @ApiModelProperty(value = "当前模式")
    private String modeStatus;



}
