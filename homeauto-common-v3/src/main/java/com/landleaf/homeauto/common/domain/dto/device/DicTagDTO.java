package com.landleaf.homeauto.common.domain.dto.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author Yujiumin
 * @version 2020/7/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("字典表标签")
public class DicTagDTO {

    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("标签名")
    private String name;

    @ApiModelProperty("标签值")
    private String value;

    @ApiModelProperty("排序值")
    private Integer sort;

    @ApiModelProperty("是否启用")
    private Integer enabled;

    @ApiModelProperty("父级标签ID")
    private Long parent;

    @ApiModelProperty("所属字典的字典码")
    private String dicCode;

    @NotNull(message= "dicId字段不可为空")
    @ApiModelProperty(value = "所属字典的字典ID", required = true)
    private Long dicId;

}
