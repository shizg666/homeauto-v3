package com.landleaf.homeauto.center.device.model.smart.bo;

import com.landleaf.homeauto.common.enums.device.TerminalTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yujiumin
 * @version 2020/10/21
 */
@Data
@ApiModel("家庭终端业务对象")
public class FamilyTerminalBO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("家庭编码")
    private String familyCode;

    @ApiModelProperty("终端ID")
    private String terminalId;

    @ApiModelProperty("终端MAC地址")
    private String terminalMac;

    @ApiModelProperty("终端名称")
    private String terminalName;

    @ApiModelProperty("终端类型")
    private TerminalTypeEnum terminalType;

}
