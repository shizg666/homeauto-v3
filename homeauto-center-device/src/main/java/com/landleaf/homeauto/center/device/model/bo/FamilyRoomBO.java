package com.landleaf.homeauto.center.device.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yujiumin
 * @version 2020/8/24
 */
@Data
@Deprecated
@NoArgsConstructor
public class FamilyRoomBO {

    private String floorId;

    private String floorName;

    private String roomId;

    private String roomName;

    private String roomPicUrl;

}
