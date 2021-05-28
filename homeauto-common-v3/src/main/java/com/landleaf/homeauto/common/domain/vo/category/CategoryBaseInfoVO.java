package com.landleaf.homeauto.common.domain.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@ApiModel(value="CategoryBaseInfoVO", description="品类对象")
public class CategoryBaseInfoVO {


    private static final long serialVersionUID = -1693669149600857204L;
    @ApiModelProperty(value = "品类主键id")
    private Long id;

    @ApiModelProperty(value = "品类编码")
    private String code;

    @ApiModelProperty(value = "品类名称")
    private String name;



}
