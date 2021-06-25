package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailAttributeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="JZSceneDetailRoomDeviceVO", description="江左场景房间信息")
public class JZSceneDetailRoomDeviceVO {

    @ApiModelProperty(value = "房间名称")
    private String roomName;

    @ApiModelProperty(value = "设备信息")
    private List<JzSceneDetailDeviceVO> devices;

}
