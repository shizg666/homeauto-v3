package com.landleaf.homeauto.center.device.service.bridge;

/**
 * @Description 提供给APP相关操作实现类
 * @Author zhanghongbin
 * @Date 2020/8/25 16:11
 */
public class AppServiceImpl implements IAppService{



    @Override
    public boolean familySceneControl() {
        return false;
    }

    @Override
    public boolean deviceWriteControl() {



        return false;
    }

    @Override
    public boolean deviceStatusRead() {
        return false;
    }

    @Override
    public boolean configUpdate() {
        return false;
    }
}
