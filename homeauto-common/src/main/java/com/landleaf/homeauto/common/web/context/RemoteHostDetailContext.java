package com.landleaf.homeauto.common.web.context;


import com.landleaf.homeauto.common.domain.RemoteHostDetail;

/**
 * 相关远程设备存储
 */
public class RemoteHostDetailContext {

    /**
     * 用threadLocal存储当前远程主机信息
     */
    private static final ThreadLocal<RemoteHostDetail> USER_HOLDER = new ThreadLocal<>();

    /**
     * 获取当前远程主机信息
     */
    public static RemoteHostDetail getRemoteHostDetail() {
        return USER_HOLDER.get();
    }

    /**
     * 设置当前远程主机信息
     */
    public static void setRemoteHostDetail(RemoteHostDetail remoteHostDetail) {
        USER_HOLDER.set(remoteHostDetail);
    }

    /**
     * 清除远程主机信息
     */
    public static void remove() {
        USER_HOLDER.remove();
    }
}
