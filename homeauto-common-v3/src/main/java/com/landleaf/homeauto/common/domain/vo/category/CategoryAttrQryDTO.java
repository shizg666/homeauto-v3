package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品类表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="CategoryAttrQryDTO", description="品类属性查询对象")
@EqualsAndHashCode(callSuper = false)
public class CategoryAttrQryDTO {

    @ApiModelProperty(value = "品类id主键")
    private String categoryId;

    @ApiModelProperty(value = "属性code")
    private String code;
}
