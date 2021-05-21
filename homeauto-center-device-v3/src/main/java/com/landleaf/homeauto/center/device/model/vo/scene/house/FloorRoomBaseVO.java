package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/7
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel(value="FloorRoomBaseVO", description="FloorRoomBaseVO")
public class FloorRoomBaseVO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("楼层")
    private String floor;







}
