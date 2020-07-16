package com.landleaf.homeauto.common.domain.dto.dic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 字典信息
 *
 * @author Yujiumin
 * @version 2020/07/10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("字典信息")
public class DicDTO {

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典值")
    private String value;

    @ApiModelProperty("字典值类型")
    private String valueType;

    @ApiModelProperty("唯一标识码")
    private String uniqueCode;

    @ApiModelProperty("所属父级标识码")
    private String parent;

    @ApiModelProperty("字典代码")
    private String code;

    @ApiModelProperty("父级字典代码")
    private String parentCode;

    @ApiModelProperty("描述信息")
    private String desc;

    @ApiModelProperty("系统代码")
    private Integer sysCode;

    @ApiModelProperty("排序值")
    private Integer order;

}
