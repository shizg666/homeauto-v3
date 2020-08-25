package com.landleaf.homeauto.center.device.service.bridge;

/**
 * @Description 提供给APP相关操作接口
 * @Author zhanghongbin
 * @Date 2020/8/25 16:11
 */
public interface IAppService {

    /**
     * 场景控制
     * @return
     */
    boolean familySceneControl();

    /**
     * 设备控制
     * @return
     */
    boolean deviceWriteControl();

    /**
     * 状态读取
     * @return
     */
    boolean deviceStatusRead();

    /**
     * 配置更新
     * @return
     */
    boolean configUpdate();
}
