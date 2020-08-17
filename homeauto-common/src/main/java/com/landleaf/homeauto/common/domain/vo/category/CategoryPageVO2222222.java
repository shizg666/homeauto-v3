package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.category.ProtocolEnum;
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
@ApiModel(value="CategoryPageVO", description="品类分页对象")
public class CategoryPageVO2222222 {


    @ApiModelProperty(value = "主键id（修改必传）")
    private String id;

    @ApiModelProperty(value = "品类类型")
    private Integer type;

    @ApiModelProperty(value = "品类类型")
    private String typeStr;

    @ApiModelProperty(value = "性质: 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "性质: 只读，控制")
    private String natureStr;

    @ApiModelProperty(value = "协议")
    private Integer protocol;

    @ApiModelProperty(value = "协议")
    private String protocolStr;

    public void setType(Integer type) {
        this.type = type;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
        this.natureStr = AttributeNatureEnum.getInstByType(nature) != null? AttributeNatureEnum.getInstByType(nature).getName():"";
    }

    public void setProtocol(Integer protocol) {
        this.protocol = protocol;
    }
}
