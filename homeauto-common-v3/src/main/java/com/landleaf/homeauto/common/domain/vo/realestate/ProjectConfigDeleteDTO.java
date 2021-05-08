package com.landleaf.homeauto.common.domain.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectConfigDeleteDTO", description="项目配置删除对象")
public class ProjectConfigDeleteDTO {


    @ApiModelProperty(value = "主键id")
    private Long id;


    @NotNull(message = "项目id必传")
    @ApiModelProperty(value = "项目id（必传）")
    private Long projectId;

}
