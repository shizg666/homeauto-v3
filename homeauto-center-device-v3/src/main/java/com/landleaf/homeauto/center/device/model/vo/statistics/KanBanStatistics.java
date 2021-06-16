package com.landleaf.homeauto.center.device.model.vo.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@ApiModel(value="KanBanStatistics", description="看板")
@EqualsAndHashCode(callSuper = false)
public class KanBanStatistics {

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "数量")
    private Integer count ;

    @ApiModelProperty(value = "名称")
    private String name;
//
//    @ApiModelProperty(value = "排序")
//    private Integer sort;



}
