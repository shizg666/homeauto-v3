package com.landleaf.homeauto.common.domain.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

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
@ApiModel(value="RealestateModeStatusVO", description="RealestateModeStatusVO")
public class RealestateModeStatusVO {



    private static final long serialVersionUID = -1083009607018779779L;

    @ApiModelProperty(value = "主键id 修改必传")
    private String id;

    @ApiModelProperty(value = "楼盘名称")
    private String name;

    @ApiModelProperty(value = "当前模式str")
    private String modeStatusStr;


    @ApiModelProperty(value = "当前模式")
    private Integer modeStatus;



}
