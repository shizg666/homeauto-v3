package com.landleaf.homeauto.center.device.model.dto.screenapk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "ProjectScreenUpgradeSaveDTO",description = "新增升级DTO")
@Data
public class ProjectScreenUpgradeUpdateDTO implements Serializable {
    private static final long serialVersionUID = 8883758835540686233L;
    @ApiModelProperty(value = "记录ID")
    @NotNull
    private Long id;

    @ApiModelProperty(value = "本版本内容")
    private String description;
    @NotNull
    @ApiModelProperty(value = "选择房屋:推送路径（最全示例:楼盘/项目/楼栋/单元/楼/家庭）修改时只能增不能减")
    private List<String> paths;




}
