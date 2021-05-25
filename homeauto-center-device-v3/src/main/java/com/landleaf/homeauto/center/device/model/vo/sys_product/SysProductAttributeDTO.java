package com.landleaf.homeauto.center.device.model.vo.sys_product;

import com.landleaf.homeauto.common.domain.vo.category.ProductAttributeInfoDTO;
import com.landleaf.homeauto.common.domain.vo.category.ProductAttributeScopeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 属性字典表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SysProductAttributeDTO", description="系统产品属性对象")
public class SysProductAttributeDTO {

    @NotBlank(message = "属性名称不能为空")
    @ApiModelProperty(value = "属性名称")
    private String name;

    @NotBlank(message = "属性code不能为空")
    @ApiModelProperty(value = "属性code")
    private String code;

    @NotNull(message = "属性类别不能为空")
    @ApiModelProperty(value = "属性类别;多选，值域")
    private Integer type;

    @NotNull(message = "性质不能为空")
    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "属性关联的 品类code 多个以 ，分隔")
    private String categoryList;

//    @ApiModelProperty(value = "属性范围信息，属性是 值域类型（2）有值")
//    private ProductAttributeScopeDTO scope;

    @ApiModelProperty(value = "属性可选值, 属性是 多选（1）类型 有值")
    private List<SysProductAttributeInfoDTO> infos;


}
