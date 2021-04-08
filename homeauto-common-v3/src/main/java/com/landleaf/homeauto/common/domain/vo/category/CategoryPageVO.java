package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.category.ProtocolEnum;
import com.landleaf.homeauto.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
@ApiModel(value="CategoryPageVO", description="品类分页对象")
public class CategoryPageVO {


    @ApiModelProperty(value = "主键id（修改必传）")
    private String id;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "产品数量")
    private Integer productCount;

    @ApiModelProperty(value = "状态 0启用 1停用")
    private Integer status;


//    @ApiModelProperty(value = "品类功能属性集合")
//    private List<CategoryAttributeVO> attributes1;
//
//    @ApiModelProperty(value = "品类基本属性集合")
//    private List<CategoryAttributeVO> attributes2;


}
