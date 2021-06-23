package com.landleaf.homeauto.contact.screen.dto.payload.http.response;

import lombok.Data;

/**
 * 大屏更新检查返回
 *
 * @author wenyilu
 */
@Data
public class ApkVersionCheckResponsePayload {

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

    /**
     *升级类型(1:用户升级，2：后台升级)
     */
    private Integer upgradeType;

    private String description;


}
