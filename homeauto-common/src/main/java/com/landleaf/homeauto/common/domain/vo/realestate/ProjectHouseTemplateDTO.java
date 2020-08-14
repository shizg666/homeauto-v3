package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectHouseTemplateDTO", description="ProjectHouseTemplateDTO")
public class ProjectHouseTemplateDTO {
    @ApiModelProperty(value = "主键 修改必传")
    private String id;

    @ApiModelProperty(value = "户型名称")
    private String name;

    @ApiModelProperty(value = "项目id")
    private String projectId;


}
