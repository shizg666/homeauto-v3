package com.landleaf.homeauto.common.domain.vo.category;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

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
@ApiModel(value="CategoryAttributeBO22222222", description="CategoryAttributeBO22222222")
public class CategoryAttributeBO22222222 extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "属性名称")
    private String name;

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


    @ApiModelProperty(value = "属性可选值")
    private List<CategoryAttributeInfoDTO22222222> infos;


}
