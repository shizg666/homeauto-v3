package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import lombok.Data;

/**
 * 大屏apk版本检查
 * @author wenyilu
 */
@Data
public class AdapterHttpApkVersionCheckDTO extends AdapterMessageHttpDTO {


    /**
     * 当前版本号
     */
    private String version;

}
