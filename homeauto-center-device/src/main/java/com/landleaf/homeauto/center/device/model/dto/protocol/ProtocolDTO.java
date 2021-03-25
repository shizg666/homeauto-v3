package com.landleaf.homeauto.center.device.model.dto.protocol;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName ProtocolDTO
 * @Description: TODO
 * @Author shizg
 * @Date 2020/12/28
 * @Version V1.0
 **/
@Data
@ToString
@ApiModel(value="ProtocolDTO", description="协议信息")
public class ProtocolDTO {

    @ApiModelProperty(value = "协议主键id （修改必传）")
    private String id;

    @NotBlank
    @ApiModelProperty(value = "协议名称")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "协议标志")
    private String code;

    @NotNull
    @ApiModelProperty(value = "协议场景类型")
    private Integer type;

}
