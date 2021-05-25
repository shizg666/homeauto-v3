package com.landleaf.homeauto.center.device.model.vo.sys_product;

import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 系统产品表
 * </p>
 *
 * @author lokiy
 * @since 2021-05-24
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SysProductCategoryVO", description="SysProductCategoryVO")
public class SysProductCategoryVO {

    @ApiModelProperty(value = "品类code")
    private String categoryCode;

    @ApiModelProperty(value = "品类名称")
    private String categoryName;

    @ApiModelProperty(value = "品类数量 0 1个 1多个")
    private Integer category_num;

    @ApiModelProperty(value = "系统品类功能属性")
    List<SysCategoryAttributeVO> attributesFunc;

    @ApiModelProperty(value = "系统品类基本属性")
    List<SysCategoryAttributeVO> attributesBase;

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
        this.categoryName = CategoryTypeEnum.getInstByType(categoryCode) == null?"":CategoryTypeEnum.getInstByType(categoryCode).getName();
    }
}
