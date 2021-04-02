package com.landleaf.homeauto.common.mybatis.mp;

import java.util.List;

/**
 * 公共 服务类
 * id生成器，所有引用公共包的项目都要实现该接口
 *
 * @author shizg
 */
public interface IdService {


    /**
     * 批量获取id
     * @return
     */
    List<Long> getListSegmentId(int count);

    /**
     * 获取单个id
     * 默认  biztype  homeauto
     * @return
     */
    long getSegmentId();



}
