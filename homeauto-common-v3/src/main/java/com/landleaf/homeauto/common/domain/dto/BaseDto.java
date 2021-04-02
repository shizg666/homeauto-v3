package com.landleaf.homeauto.common.domain.dto;

import com.landleaf.homeauto.common.domain.BaseDomain;
import com.landleaf.homeauto.common.exception.BusinessException;

/**
 * 所有dto的基类
 *
 * @author hebin
 */
public abstract class BaseDto extends BaseDomain {

    /**
     * 请求的来源
     */
    private String ip;

    /**
     * 校验参数是否合法
     */
    public void check() throws BusinessException {
        return;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
