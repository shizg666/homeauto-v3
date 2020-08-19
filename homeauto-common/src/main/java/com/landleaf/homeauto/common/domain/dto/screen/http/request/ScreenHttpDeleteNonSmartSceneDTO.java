package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenDeleteNonSmartReservationDTO
 * @Description: 删除自由方舟场景请求
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenHttpDeleteNonSmartSceneDTO extends ScreenHttpRequestDTO {


    /**
     * 场景Id
     */
    private List<String> sceneIds;
}
