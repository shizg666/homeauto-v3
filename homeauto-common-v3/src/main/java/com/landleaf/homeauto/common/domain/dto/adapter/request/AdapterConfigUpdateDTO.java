package com.landleaf.homeauto.common.domain.dto.adapter.request;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 配置更新通知DTO
 *
 * @author wenyilu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdapterConfigUpdateDTO extends AdapterMessageBaseDTO {


    /**
     * 更新操作类型 scene
     * {@link ContactScreenConfigUpdateTypeEnum}
     */
    private String updateType;
}
