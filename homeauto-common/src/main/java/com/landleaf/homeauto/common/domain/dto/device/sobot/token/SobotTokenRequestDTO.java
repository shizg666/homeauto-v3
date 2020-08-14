package com.landleaf.homeauto.common.domain.dto.device.sobot.token;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SobotGetTokenRequestDTO
 * @Description: 请求token入参
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotTokenRequestDTO {

    @ApiModelProperty(value = "接口凭证Id", name = "appid", required = true, dataType = "String")
    private String appid;

    @ApiModelProperty(value = "时间戳", name = "create_time", required = true, dataType = "String", example = "时间戳,秒，例如 2019-09-25 15:49:33 的时间戳1569397773")
    private String create_time;

    @ApiModelProperty(value = "签名", name = "sign", required = true, dataType = "String", example = "md5(appid+create_time+app_key) sign签名,app_key为秘钥")
    private String sign;
}
