package com.landleaf.homeauto.common.domain.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectConfigDeleteBatchDTO", description="项目配置删除对象")
public class ProjectConfigDeleteBatchDTO {


    @ApiModelProperty(value = "家庭主键id集合")
    private List<Long> ids;


    @NotEmpty(message = "项目id必传")
    @ApiModelProperty(value = "项目id（必传）")
    private Long projectId;

}
