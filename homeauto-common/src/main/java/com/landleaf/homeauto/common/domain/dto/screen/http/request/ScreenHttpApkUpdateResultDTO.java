package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

/**
 * @ClassName ContactScreenDeviceStatusReadDTO
 * @Description: 读取设备状态DTO
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
public class ScreenHttpApkUpdateResultDTO extends ScreenHttpRequestDTO {


    /**
     * 当前版本号
     */
    private String version;
}
