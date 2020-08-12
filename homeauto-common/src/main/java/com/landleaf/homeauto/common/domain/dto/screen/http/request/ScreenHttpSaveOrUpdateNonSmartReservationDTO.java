package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import com.landleaf.homeauto.common.domain.dto.screen.ScreenFamilyNonSmartReservationDTO;
import lombok.Data;

import java.util.List;

/**
 * @ClassName ScreenDeleteNonSmartReservationDTO
 * @Description: 删除自由方舟预约请求
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenHttpSaveOrUpdateNonSmartReservationDTO extends ScreenHttpRequestDTO {


    /**
     * 修改或新增数据集
     */
    List<ScreenFamilyNonSmartReservationDTO> data;
}
