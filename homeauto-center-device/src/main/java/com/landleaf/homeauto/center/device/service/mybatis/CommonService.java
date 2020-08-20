package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.model.domain.address.HomeAutoAddress;

import java.util.List;

/**
 * 公共 服务类
 * </p>
 *
 * @author shizg
 */
public interface CommonService<T> {


    /**
     * 获取当前用户的paths
     *
     * @return
     */
    List<String> getUserPathScope();



}
