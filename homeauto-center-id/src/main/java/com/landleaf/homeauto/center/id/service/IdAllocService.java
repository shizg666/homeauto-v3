package com.landleaf.homeauto.center.id.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.id.common.model.domain.IdAlloc;

import java.util.List;

public interface IdAllocService extends IService<IdAlloc> {

    List<IdAlloc> getAllLeafAllocs();

    /**
     * 更新最大ID并返回当前页面的号段
     * @param tag
     * @return
     */
    IdAlloc updateMaxIdAndGetIdAlloc(String tag);

    /**
     * 按自定义步数修改MaxId并返回号段
     * @param leafAlloc
     * @return
     */
    IdAlloc updateMaxIdByCustomStepAndGetLeafAlloc(IdAlloc leafAlloc);

    /**
     * 获取所有的业务标记
     * @return
     */
    List<String> getAllTags();
}
