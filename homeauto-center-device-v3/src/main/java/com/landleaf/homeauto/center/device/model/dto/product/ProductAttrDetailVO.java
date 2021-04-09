package com.landleaf.homeauto.center.device.model.dto.product;

import com.landleaf.homeauto.common.domain.vo.category.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
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
@ApiModel(value="ProductAttrDetailVO", description="ProductAttrDetailVO")
public class ProductAttrDetailVO {

    @ApiModelProperty(value = "属性原始信息")
  private AttributeDicDetailVO dicDetailVO;

    @ApiModelProperty(value = "属性类型 是多选时 产品配置的可选值")
    private List<String> selectAttrCodes;

    @ApiModelProperty(value = "属性是 值域类型（2）有值 属性范围信息")
    private ProductAttributeScopeDTO scope;

}
