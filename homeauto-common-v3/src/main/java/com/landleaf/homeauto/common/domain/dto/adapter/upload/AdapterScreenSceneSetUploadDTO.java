package com.landleaf.homeauto.common.domain.dto.adapter.upload;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import lombok.Data;

/**
 * 大屏执行场景上报DTO
 *
 * @author wenyilu
 */
@Data
public class AdapterScreenSceneSetUploadDTO extends AdapterMessageUploadDTO {

    /**
     * 当前场景号
     */
    private Long sceneId;
}
