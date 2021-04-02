package com.landleaf.homeauto.center.device.service.mybatis;

import javax.servlet.http.HttpServletResponse;
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


    void setResponseHeader(HttpServletResponse response, String filename);



}
