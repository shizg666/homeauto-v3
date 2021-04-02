package com.landleaf.homeauto.common.domain.dto.device.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Yujiumin
 * @version 2020/9/10
 */
@Data
@ToString
@NoArgsConstructor
@ApiModel(value="TerminalInfoDTO", description="TerminalInfoDTO")
public class TerminalInfoDTO {

    @ApiModelProperty("mac")
    private String mac;

    @ApiModelProperty("审核状态 (1-已审核 2-授权中)")
    private Integer type;

}
