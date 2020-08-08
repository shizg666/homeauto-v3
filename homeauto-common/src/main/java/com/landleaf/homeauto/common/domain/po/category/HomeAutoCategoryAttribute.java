package com.landleaf.homeauto.common.domain.po.category;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品类属性信息表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("home_auto_category_attribute")
@ApiModel(value="HomeAutoCategoryAttribute对象", description="品类属性信息表")
public class HomeAutoCategoryAttribute extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性名称")
    private String name;


    @ApiModelProperty(value = "属性id")
    private String attributeId;

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "属性类别;单选，多选，值域")
    private Integer type;

    @ApiModelProperty(value = "精度 类型是值域的时候有值")
    private Integer precision;

    @ApiModelProperty(value = "属性范围最大值 类型是值域的时候有值")
    private String max;

    @ApiModelProperty(value = "属性范围最小值 类型是值域的时候有值")
    private String min;

    @ApiModelProperty(value = "属性范围步幅 类型是值域的时候有值")
    private String step;

    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "品类id")
    private String categoryId;


}
