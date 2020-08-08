package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
@ApiModel(value="CategoryAttributeVO", description="CategoryAttributeVO")
public class CategoryAttributeVO {

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "属性code")
    private String code;

    @ApiModelProperty(value = "属性类别;单选，多选，值域")
    private Integer type;

    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;

    @ApiModelProperty(value = "属性可选值")
    private List<SelectedVO> infos;

    @ApiModelProperty(value = "选中的值")
    private List<String> selectInfo;

    @ApiModelProperty(value = "精度 类型是值域的时候有值")
    private Integer precision;

    @ApiModelProperty(value = "属性范围最大值 类型是值域的时候有值")
    private String max;

    @ApiModelProperty(value = "属性范围最小值 类型是值域的时候有值")
    private String min;

    @ApiModelProperty(value = "属性范围步幅 类型是值域的时候有值")
    private String step;





}
