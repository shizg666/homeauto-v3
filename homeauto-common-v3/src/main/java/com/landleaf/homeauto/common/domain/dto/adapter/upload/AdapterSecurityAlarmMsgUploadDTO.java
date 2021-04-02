package com.landleaf.homeauto.common.domain.dto.adapter.upload;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import lombok.Data;

import java.util.List;

/**
 * 安防报警DTO
 *
 * @author wenyilu
 */
@Data
public class AdapterSecurityAlarmMsgUploadDTO extends AdapterMessageUploadDTO {

    List<AdapterSecurityAlarmMsgItemDTO> items;

    private String productCode;

}
