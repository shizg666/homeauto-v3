package com.landleaf.homeauto.common.domain.dto.device.sobot.datadic;

import com.landleaf.homeauto.common.domain.dto.device.sobot.SobotBaseResponseDTO;
import lombok.Data;

/**
 * @ClassName SobotDataDicResponseDTO
 * @Description: 请求数据字典
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotDataDicResponseDTO extends SobotBaseResponseDTO {

    private SobotDataDicResponseItemDTO item;
}
