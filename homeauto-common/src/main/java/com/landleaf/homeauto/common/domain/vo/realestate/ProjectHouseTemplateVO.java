package com.landleaf.homeauto.common.domain.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
@ApiModel(value="ProjectHouseTemplateVO", description="户型列表对象")
public class ProjectHouseTemplateVO {
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "户型名称")
    private String name;

    @ApiModelProperty(value = "项目id")
    private List<String> projectId;


}
