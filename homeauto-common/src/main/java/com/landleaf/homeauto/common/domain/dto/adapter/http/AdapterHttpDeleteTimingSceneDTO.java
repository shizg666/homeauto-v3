package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenDeleteNonSmartReservationDTO
 * @Description: 删除定时场景配置请求
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class AdapterHttpDeleteTimingSceneDTO extends AdapterMessageHttpDTO {


    /**
     * 定时配置Id
     */
    private List<String> ids;
}
