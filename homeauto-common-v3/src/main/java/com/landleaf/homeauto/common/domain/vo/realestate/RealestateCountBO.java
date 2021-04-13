package com.landleaf.homeauto.common.domain.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="RealestateCountBO", description="RealestateCountBO")
public class RealestateCountBO {

    @ApiModelProperty(value = "楼盘id")
    private Long realestateId;

    @ApiModelProperty(value = "数量")
    private Integer count;

}
