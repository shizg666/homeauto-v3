package com.landleaf.homeauto.common.util;

import com.landleaf.homeauto.common.domain.RemoteHostDetail;
import com.landleaf.homeauto.common.web.context.RemoteHostDetailContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lokiy
 * @date 2019/8/19 12:07
 * @description:
 */
public class RemoteHostDetailContextUtils {


    /**
     * 获取ip信息
     *
     * @return
     */
    public static String getIp() {
        RemoteHostDetail remoteHostDetail = RemoteHostDetailContext.getRemoteHostDetail();
        if (remoteHostDetail == null || StringUtils.isBlank(remoteHostDetail.getIp())) {
            return "0.0.0.0";
        }
        return remoteHostDetail.getIp();
    }


}
