package com.landleaf.homeauto.center.device.model.vo.scene;

import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@Data
@ApiModel(value="SceneFloorVO", description="场景楼层信息")
public class SceneFloorVO {

    @ApiModelProperty("楼层名称")
    private String name;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "房间集合")
    List<SceneRoomVO> rooms;

}
