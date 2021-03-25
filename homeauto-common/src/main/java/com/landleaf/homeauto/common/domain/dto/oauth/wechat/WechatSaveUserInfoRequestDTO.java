package com.landleaf.homeauto.common.domain.dto.oauth.wechat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName WechatSaveUserInfoRequestDTO
 * @Description: WechatSaveUserInfoRequestDTO
 * @Author wyl
 * @Date 2020/7/31
 * @Version V1.0
 **/
@Data
public class WechatSaveUserInfoRequestDTO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像地址(http://)")
    private String avatar;

}
