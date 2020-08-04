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
    private String id;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典码")
    private String code;

    @ApiModelProperty("是否为系统字典")
    private Integer isSystemCode;

    @ApiModelProperty("是否启用")
    private Integer enabled;

}
