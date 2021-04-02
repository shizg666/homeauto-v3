package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.enums.category.AttributeNatureEnum;
import com.landleaf.homeauto.common.enums.category.AttributeTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="AttributeDicDetailVO", description="属性字典详情信息")
public class AttributeDicDetailVO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "属性类别;单选，多选，值域")
    private Integer type;

    @ApiModelProperty(value = "属性类别字符串")
    private String typeStr;

    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "性质类别字符串")
    private String natureStr;

    @ApiModelProperty(value = "属性可选值")
    private List<AttributeInfoDicDTO> infos;

    public void setNature(Integer nature) {
        this.nature = nature;
        this.natureStr = AttributeNatureEnum.getInstByType(nature).getName();
    }

    public void setType(Integer type) {
        this.type = type;
        this.typeStr = AttributeTypeEnum.getInstByType(type).getName();
    }

}
