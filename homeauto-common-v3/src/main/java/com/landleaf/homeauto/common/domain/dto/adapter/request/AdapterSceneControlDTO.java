package com.landleaf.homeauto.common.domain.dto.adapter.request;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import lombok.Data;

/**
 * 场景控制DTO
 * @author wenyilu
 */
@Data
public class AdapterSceneControlDTO extends AdapterMessageBaseDTO {

    /**
     * 场景号
     */
    private Long sceneId;



}
