package com.landleaf.homeauto.common.domain.dto.adapter.request;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import lombok.Data;

/**
 * 配置更新通知DTO
 *
 * @author wenyilu
 */
@Data
public class AdapterConfigUpdateDTO extends AdapterMessageBaseDTO {


    /**
     * 更新操作类型 scene
     * {@link ContactScreenConfigUpdateTypeEnum}
     */
    private String updateType;
}
