package com.landleaf.homeauto.center.device.model.vo;

import com.landleaf.homeauto.center.device.model.bo.FamilySimpleRoomBO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Data
@NoArgsConstructor
@ApiModel("家庭房间视图层对象")
public class FamilyRoomVO {

    private String floorId;

    private String floorName;

    private List<FamilySimpleRoomBO> roomList;

}
