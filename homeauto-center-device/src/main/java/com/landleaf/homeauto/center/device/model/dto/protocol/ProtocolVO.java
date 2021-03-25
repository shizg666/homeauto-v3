package com.landleaf.homeauto.center.device.model.dto.protocol;

import com.landleaf.homeauto.common.enums.protocol.ProtocolTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @ClassName ProtocolDTO
 * @Description: TODO
 * @Author shizg
 * @Date 2020/12/28
 * @Version V1.0
 **/
@Data
@ToString
@ApiModel(value="ProtocolVO", description="协议信息")
public class ProtocolVO {

    @ApiModelProperty(value = "协议主键id （修改必传）")
    private String id;

    @ApiModelProperty(value = "协议名称")
    private String name;

    @ApiModelProperty(value = "协议标志")
    private String code;

    @ApiModelProperty(value = "协议场景类型")
    private Integer type;

    @ApiModelProperty(value = "协议场景类型Str")
    private String typeStr;

    public void setType(Integer type) {
        this.type = type;
        this.typeStr = ProtocolTypeEnum.getInstByType(type) == null?"":ProtocolTypeEnum.getInstByType(type).getName();
    }
}
