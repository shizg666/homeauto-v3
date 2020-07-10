package com.landleaf.homeauto.common.domain.dto.jg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;


/**
 * @author Lokiy
 * @date 2019/9/18 16:12
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel("极光普通短信")
public class JgSmsMsgDTO {

    @ApiModelProperty(value = "手机号", required = true)
    private String mobile;

    @ApiModelProperty(value = "模板替换内容", required = true)
    private Map<String, String> tempParaMap;

    @ApiModelProperty(value = "消息类型", required = true)
    private Integer msgType;
}
