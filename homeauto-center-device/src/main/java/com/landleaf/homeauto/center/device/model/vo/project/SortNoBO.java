package com.landleaf.homeauto.center.device.model.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型楼层表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SortNoBO", description="排序")
@Builder
public class SortNoBO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "序号")
    private int sortNo;
}
