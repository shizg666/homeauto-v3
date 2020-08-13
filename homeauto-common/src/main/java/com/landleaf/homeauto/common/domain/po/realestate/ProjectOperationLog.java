package com.landleaf.homeauto.common.domain.po.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目操作日志表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProjectOperationLog对象", description="项目操作日志表")
public class ProjectOperationLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账户名称")
    private String account;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "操作内容")
    private String name;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "参数")
    private String params;


}
