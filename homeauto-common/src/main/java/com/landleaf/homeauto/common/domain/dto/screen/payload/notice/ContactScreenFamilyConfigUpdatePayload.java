package com.landleaf.homeauto.common.domain.dto.screen.payload.notice;

import lombok.Data;

/**
 * 数据更新通知payload
 *
 * @author wenyilu
 */
@Data
public class ContactScreenFamilyConfigUpdatePayload {

    /**
     * 更新项目
     */
    private String name;
    /**
     * 更新操作类型 add/update/delete
     */
    private String operateType;


}
