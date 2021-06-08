package com.landleaf.homeauto.common.domain.dto.screen.http.request;

import lombok.Data;

/**
 * 大屏apk版本检查
 * @author wenyilu
 */
@Data
public class ScreenHttpApkVersionCheckDTO extends ScreenHttpRequestDTO {


    /**
     * 当前版本号
     */
    private String version;

}
