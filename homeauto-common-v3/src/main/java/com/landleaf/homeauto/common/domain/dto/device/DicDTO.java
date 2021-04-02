package com.landleaf.homeauto.common.domain.dto.device;

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

    @ApiModelProperty("主键ID")
    private String id;

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典码")
    private String code;

    @ApiModelProperty("是否为系统字典码")
    private Integer isSystemCode;

    @ApiModelProperty("启用状态")
    private Integer enabled;

}
