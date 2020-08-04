package com.landleaf.homeauto.common.domain.po.category;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 属性值字典表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoAttributeInfoDic对象", description="属性值字典表")
public class HomeAutoAttributeInfoDic extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性值名称")
    private String name;

    @ApiModelProperty(value = "属性值code")
    private String code;

    @ApiModelProperty(value = "属性id")
    private String attributeId;

    @ApiModelProperty(value = "排序")
    private Integer orderNum;

}
