package com.landleaf.homeauto.common.domain.dto.screen.http.response;

import lombok.Data;

/**
 * apk更新检查请求返回
 *
 * @author wenyilu
 */
@Data
public class ScreenHttpApkVersionCheckResponseDTO {
    /**
     * 版本号
     */
    private String version;
    /**
     * apk地址
     */
    private String url;
    /**
     * 是否需要更新
     */
    private Boolean updateFlag;

}
