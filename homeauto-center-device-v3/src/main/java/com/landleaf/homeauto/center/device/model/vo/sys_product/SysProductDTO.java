package com.landleaf.homeauto.center.device.model.vo.sys_product;

import com.landleaf.homeauto.common.domain.BaseEntity2;
import com.landleaf.homeauto.common.domain.vo.category.ProductAttributeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
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
@ApiModel(value="SysProductDTO", description="系统产品表")
public class SysProductDTO  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id 修改必传")
    private Long id;

    @ApiModelProperty(value = "系统产品code 后台自动生成")
    private String code;

    @ApiModelProperty(value = "系统产品名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "关联的品类")
    List<SysProductCategoryDTO> categorys;

    @ApiModelProperty(value = "系统产品功能属性")
    List<SysProductAttributeDTO> attributesFunc;

    @ApiModelProperty(value = "系统产品基本属性")
    List<SysProductAttributeDTO> attributesBase;

//    @ApiModelProperty(value = "修改标志 0 不可以修改 1 可修改")
//    private Integer updateFalg;



}
