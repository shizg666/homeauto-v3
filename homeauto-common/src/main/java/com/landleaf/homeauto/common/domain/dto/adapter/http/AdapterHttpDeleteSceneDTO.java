package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
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
public class AdapterHttpDeleteSceneDTO extends AdapterMessageHttpDTO {


    /**
     * 场景Id
     */
    private List<String> sceneIds;
}
