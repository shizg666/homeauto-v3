package com.landleaf.homeauto.center.id.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.id.common.model.domain.IdAlloc;
import com.landleaf.homeauto.center.id.mapper.IdAllocMapper;
import com.landleaf.homeauto.center.id.service.IdAllocService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IdAllocServiceImpl extends ServiceImpl<IdAllocMapper, IdAlloc> implements IdAllocService {


    @Override
    public List<IdAlloc> getAllLeafAllocs() {

        return this.getBaseMapper().getAllLeafAllocs();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public IdAlloc updateMaxIdAndGetIdAlloc(String tag) {
        this.getBaseMapper().updateMaxId(tag);
        return this.getBaseMapper().getIdAlloc(tag);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public IdAlloc updateMaxIdByCustomStepAndGetLeafAlloc(IdAlloc idAlloc) {
        this.baseMapper.updateMaxIdByCustomStep(idAlloc.getStep(),idAlloc.getBizTag());
        return this.baseMapper.getIdAlloc(idAlloc.getBizTag());
    }

    @Override
    public List<String> getAllTags() {
        return this.baseMapper.getAllTags();
    }
}
