package com.landleaf.homeauto.common.domain.dto.adapter.upload;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenDeviceAlarmUploadDTO
 * @Description: 设备报警上报DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class AdapterDeviceAlarmUploadDTO extends AdapterMessageUploadDTO {
    /**
     * 详细信息
     */
    List<AdapterSecurityAlarmMsgItemDTO> data;

}
