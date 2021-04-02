package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 工程批量导入配置对象
 */
@Data
@Accessors(chain = true)
@ApiModel(value="TemplateDataBO", description="下载家庭批量导入模板")
public class TemplateDataBO {


    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "楼盘ID")
    private String realestateId;

    @ApiModelProperty(value = "楼栋id")
    private String buildingId;


    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "模板名称列表")
    private List<String> names;

}
