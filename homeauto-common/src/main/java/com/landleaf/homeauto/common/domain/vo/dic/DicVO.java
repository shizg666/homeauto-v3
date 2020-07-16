package com.landleaf.homeauto.common.domain.vo.dic;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 字典表视图层对象
 *
 * @author Yujiumin
 * @version 2020/07/13
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DicVO implements Serializable {

    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典值")
    private Object value;

    @ApiModelProperty("字典值类型")
    private String valueType;

    @ApiModelProperty("字典唯一标识")
    private String uniqueCode;

    @ApiModelProperty("所属字典标识")
    private String parent;

    @ApiModelProperty("字典码")
    private String code;

    @ApiModelProperty("父级字典码")
    private String parentCode;

    @ApiModelProperty("描述信息")
    private String desc;

    @ApiModelProperty("系统代码")
    private Integer sysCode;

    @ApiModelProperty("排序值")
    private Integer order;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

}
