package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 属性字典表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Builder
@Data
@Accessors(chain = true)
@ApiModel(value="AttributeRangeDTO", description="属性范围对象")
public class AttributeRangeDTO {

    @ApiModelProperty(value = "属性名称")
    private String max;

    @ApiModelProperty(value = "属性code")
    private String min;

    @ApiModelProperty(value = "属性类别;单选，多选，值域")
    private String step;


}
