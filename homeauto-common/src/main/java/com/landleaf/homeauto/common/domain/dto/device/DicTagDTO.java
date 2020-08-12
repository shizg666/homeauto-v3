package com.landleaf.homeauto.common.domain.dto.device;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String id;

    @ApiModelProperty("标签名")
    private String name;

    @ApiModelProperty("标签值")
    private String value;

    @ApiModelProperty("排序值")
    private Integer sort;

    @ApiModelProperty("是否启用")
    private Integer enabled;

    @ApiModelProperty("父级标签ID")
    private String parent;

    @ApiModelProperty("所属字典的字典码")
    private String dicCode;

    @ApiModelProperty(value = "所属字典的字典ID", required = true)
    private String dicId;

}
