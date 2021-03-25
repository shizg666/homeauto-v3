package com.landleaf.homeauto.center.device.model.dto.protocol;

import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.protocol.ProtocolAttrTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 协议属性信息
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProtocolAttrInfoVO", description="协议属性信息")
public class ProtocolAttrInfoVO {

    @ApiModelProperty(value = "协议属性主键id （修改必传）")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "通信编码")
    private String code;

    @ApiModelProperty(value = "操作权限 多个以/分隔")
    private String operateAcl;

    @ApiModelProperty(value = "属性类型")
    private Integer type;

    @ApiModelProperty(value = "属性类型Str")
    private String typeStr;

    @ApiModelProperty(value = "品类")
    private String category;

    @ApiModelProperty(value = "品类str")
    private String categoryStr;

    public void setCategory(String category) {
        this.category = category;
        this.categoryStr = CategoryTypeEnum.getInstByType(category) == null?"":CategoryTypeEnum.getInstByType(category).getName();
    }

    public void setType(Integer type) {
        this.type = type;
        this.typeStr = ProtocolAttrTypeEnum.getInstByType(type) == null?"":ProtocolAttrTypeEnum.getInstByType(type).getName();
    }
}
