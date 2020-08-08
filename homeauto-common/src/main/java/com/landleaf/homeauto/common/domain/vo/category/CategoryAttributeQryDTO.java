package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@ApiModel(value="CategoryAttributeQryDTO", description="CategoryAttributeQryDTO")
public class CategoryAttributeQryDTO  {



    @ApiModelProperty(value = "品类id")
    private String categoryId;


    @ApiModelProperty(value = "属性id")
    private String attributeId;



}
