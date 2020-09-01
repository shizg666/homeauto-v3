package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 自定义场景联动组
 *
 * @author Yujiumin
 * @version 2020/9/1
 */
@Data
@NoArgsConstructor
@ApiModel("自定义场景联动组")
public class CustomSceneLinkageGroupVO {

    @ApiModelProperty("联动组名称")
    private String groupName;

    @ApiModelProperty("联动组触发条件")
    private String executeModeName;

    @ApiModelProperty("联动组触发条件值")
    private String executeModeValue;

    @ApiModelProperty("执行动作")
    private List<ExecuteAction> executeActions;

    @Data
    @NoArgsConstructor
    @ApiModel("场景联动组执行动作")
    static class ExecuteAction {

        @ApiModelProperty("房间名")
        private String roomName;

        @ApiModelProperty("运行参数")
        private String value;

    }

}
