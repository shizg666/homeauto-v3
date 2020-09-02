package com.landleaf.homeauto.center.device.model.dto.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Lokiy
 * @date 2019/8/13 13:53
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("msg基类")
public class MsgDTO {

    @ApiModelProperty("主键id **新增-非必传  修改-必传**")
    private String id;

    @ApiModelProperty( value = "标题名称", required = true)
    private String name;
}
