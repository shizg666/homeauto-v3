package com.landleaf.homeauto.center.device.model.vo.sys_product;

import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author lokiy
 * @since 2021-05-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SysCategoryAttributeDTO", description="")
public class SysCategoryAttributeDTO  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "code")
    private String code;

    @ApiModelProperty(value = "属性类别;多选，值域")
    private Integer type;

    @ApiModelProperty(value = "属性类型 1控制 2只读")
    private Integer nature;

    @ApiModelProperty(value = "属性可选值, 属性是 多选（1）类型 有值")
    private List<SysProductAttributeInfoDTO> infos;


}
