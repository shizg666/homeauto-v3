package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Builder
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="CategoryAttributeInfoVO", description="品类属性和属性值信息对象")
public class CategoryAttributeInfoVO {

    /**
     * 基本属性
     */
    List<CategoryAttributeDTO> attrsInfo1;
    /**
     * 功能属性
     */
    List<CategoryAttributeDTO> attrsInfo2;

}
