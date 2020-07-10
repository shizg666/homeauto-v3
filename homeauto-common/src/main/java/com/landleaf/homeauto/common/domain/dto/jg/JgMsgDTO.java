package com.landleaf.homeauto.common.domain.dto.jg;

import com.landleaf.homeauto.common.domain.KvObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/8/15 17:01
 * @description:
 */
@Data
@ToString
@ApiModel("验证码入参")
public class JgMsgDTO {

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    /**
     * 发送验证码时,不用传
     * 校验验证码时,必传
     */
    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty(value = "短信类型", required = true)
    @NotBlank(message = "短信类型不能为空")
    private Integer codeType;


    @ApiModelProperty("替换类型键值对")
    private List<KvObject> tempParaList;
}
