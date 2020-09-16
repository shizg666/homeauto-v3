package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工程批量导入配置对象
 */
@Data
@Accessors(chain = true)
@ApiModel(value="TemplateQeyDTO", description="下载家庭批量导入模板请求对象")
public class TemplateQeyDTO {


    @NotBlank(message = "项目id不能为空")
    @ApiModelProperty(value = "项目id")
    private String projectId;

    @NotBlank(message = "楼盘ID不能为空")
    @ApiModelProperty(value = "楼盘ID")
    private String realestateId;

    @NotBlank(message = "楼栋id不能为空")
    @ApiModelProperty(value = "楼栋id")
    private String buildingId;

    @NotEmpty(message = "单元id不能为空")
    @ApiModelProperty(value = "单元id")
    private String unitId;

    @NotBlank(message = "户型模板id不能为空")
    @ApiModelProperty(value = "户型模板id")
    private String templateId;

    @NotBlank(message = "户型模板名称不能为空")
    @ApiModelProperty(value = "户型模板名称")
    private String templateName;




}
