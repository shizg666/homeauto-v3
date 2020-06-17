package com.landleaf.homeauto.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("远程主机信息")
public class RemoteHostDetail {

    @ApiModelProperty("远程主机ip")
    private String ip;

}
