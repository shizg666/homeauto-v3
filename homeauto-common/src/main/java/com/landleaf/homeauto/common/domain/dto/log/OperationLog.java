package com.landleaf.homeauto.common.domain.dto.log;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 字典信息
 *
 * @author Yujiumin
 * @version 2020/07/10
 */
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("操作日志对象")
public class OperationLog {

    /**
     * 参数
     */
    private String params;
    /**
     * 操作名称
     */
    private String name;
    /**
     * 操作类型
     */
    private Integer type;
    /**
     * 操作ip
     */
    private String ip;
    /**
     * 返回结果
     */
    private String result;

}
