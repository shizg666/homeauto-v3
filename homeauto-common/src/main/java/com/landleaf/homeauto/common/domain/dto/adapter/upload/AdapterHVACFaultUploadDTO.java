package com.landleaf.homeauto.common.domain.dto.adapter.upload;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageUploadDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenHVACAttributeDTO;
import lombok.Data;

import java.util.List;


/**
 * @ClassName AdapterHVACFaultUploadDTO
 * @Description: 暖通故障上报DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class AdapterHVACFaultUploadDTO extends AdapterMessageUploadDTO {


    /**
     * 具体返回值
     */
    private List<ScreenHVACAttributeDTO> items;
}
