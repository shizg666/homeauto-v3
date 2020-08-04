package com.landleaf.homeauto.common.domain.dto.screen.payload.request.config;

import com.landleaf.homeauto.common.domain.dto.screen.payload.ContactScreenFamilyRoom;
import lombok.Data;

import java.util.List;

/**
 * 房间信息更新payload
 *
 * @author wenyilu
 */
@Data
public class ContactScreenFamilyRoomRequestReplyPayload {

    /**
     * 数据集合
     */
    private List<ContactScreenFamilyRoom> items;

}
