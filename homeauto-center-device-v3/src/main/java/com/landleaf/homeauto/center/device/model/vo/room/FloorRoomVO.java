package com.landleaf.homeauto.center.device.model.vo.room;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Objects;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/10/14
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("房间视图层对象")
public class FloorRoomVO {

    @ApiModelProperty("楼层ID")
    private String floorId;

    @ApiModelProperty("楼层号")
    private String floorNum;

    @ApiModelProperty("楼层名称")
    private String floorName;

    @ApiModelProperty("房间列表")
    private List<RoomVO> roomList;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FloorRoomVO that = (FloorRoomVO) o;
        return Objects.equal(floorId, that.floorId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(floorId);
    }
}
