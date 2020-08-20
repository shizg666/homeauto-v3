package com.landleaf.homeauto.common.domain.vo.project;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 项目操作日志表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectOperationLogVO", description="ProjectOperationLogVO")
public class ProjectOperationLogVO {


    @ApiModelProperty(value = "账户名称")
    private String account;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "操作内容")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
