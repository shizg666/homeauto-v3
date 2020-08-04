package com.landleaf.homeauto.common.domain.dto.screen.payload.request.config;

import com.landleaf.homeauto.common.domain.dto.screen.payload.ContactScreenDeviceAttribute;
import lombok.Data;

import java.util.List;

/**
 * 楼层信息更新payload
 * @author wenyilu
 */
@Data
public class ContactScreenFamilyFloorRequestReplyPayload {

    /**
     * 数据集合
     */
    private List<ContactScreenDeviceAttribute> items;

    /**
     * 操作类型
     */
    private String operateType;

}
