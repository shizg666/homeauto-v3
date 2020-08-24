package com.landleaf.homeauto.center.device.model.vo.project;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

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
@ApiModel(value="HouseTemplateCopyDTO", description="HouseTemplateCopyDTO")
public class HouseTemplateCopyDTO {
    @ApiModelProperty(value = "户型id")
    private String houseTemplateId;

    @ApiModelProperty(value = "户型名称")
    private String name;

    @ApiModelProperty(value = "项目id")
    private String projectId;


}
