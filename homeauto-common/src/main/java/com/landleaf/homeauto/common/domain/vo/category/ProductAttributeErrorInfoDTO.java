package com.landleaf.homeauto.common.domain.vo.category;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProductAttributeErrorInfo对象", description="")
public class ProductAttributeErrorInfoDTO {


    @ApiModelProperty(value = "故障值")
    private String desc;


    @ApiModelProperty(value = "故障属性id")
    private String errorAttributeId;


    @ApiModelProperty(value = "序号")
    private Integer sortNo;


}
