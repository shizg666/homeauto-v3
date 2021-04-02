package com.landleaf.homeauto.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/10/24 0024
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("富文本图片返回对象")
public class RichTextImageResponse implements Serializable {


    public static final Integer SUCCESS_ERRNO = 0;
    public static final Integer FAILURE_ERRNO = -1;

    @ApiModelProperty("错误编码")
    private Integer errno;

    @ApiModelProperty("图片地址")
    private List<String> data;
}
