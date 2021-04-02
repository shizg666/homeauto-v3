package com.landleaf.homeauto.common.domain.dto.device.sobot.token;

import com.landleaf.homeauto.common.domain.dto.device.sobot.SobotBaseResponseDTO;
import lombok.Data;

/**
 * @ClassName SobotGetTokenRequestDTO
 * @Description: 请求token入参
 * @Author wyl
 * @Date 2020/8/14
 * @Version V1.0
 **/
@Data
public class SobotTokenResponseDTO extends SobotBaseResponseDTO {

    private SobotTokenResponseItemDTO item;
}
