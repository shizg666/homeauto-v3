package com.landleaf.homeauto.center.id.service.impl;

import com.landleaf.homeauto.center.id.common.model.Result;
import com.landleaf.homeauto.center.id.service.IdGen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 号段方式
 *
 * @author wenyilu
 */
@Slf4j
@Service("SegmentService")
public class SegmentService {

    @Autowired
    private IdGen segmentIdGenImpl;

    public Result getId(String bizTag) {
        return segmentIdGenImpl.get(bizTag);
    }

}
