package com.landleaf.homeauto.center.device.model.bo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Data
@NoArgsConstructor
@ApiModel("房间简单视图对象")
public class FamilySimpleRoomBO {

    private String roomId;

    private String roomName;

    private String roomPicUrl;

}
