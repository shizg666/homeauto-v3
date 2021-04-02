package com.landleaf.homeauto.common.domain.dto.device.sobot.extendfields;

import com.landleaf.homeauto.common.domain.dto.device.sobot.SobotBaseResponseDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SobotExtendFieldsResponseDTO
 * @Description: 请求自定义字段
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotExtendFieldsResponseDTO extends SobotBaseResponseDTO {

    private List<SobotExtendFieldDTO> items;
}
