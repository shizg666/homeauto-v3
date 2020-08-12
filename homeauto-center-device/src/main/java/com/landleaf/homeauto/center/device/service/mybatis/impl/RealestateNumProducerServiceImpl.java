package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.RealestateNumProducerMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IRealestateNumProducerService;
import com.landleaf.homeauto.common.domain.po.realestate.RealestateNumProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class RealestateNumProducerServiceImpl extends ServiceImpl<RealestateNumProducerMapper, RealestateNumProducer> implements IRealestateNumProducerService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int getNum(String name) {
        Integer count = this.baseMapper.getNum(name);
        if (count == null){
            RealestateNumProducer producer = new RealestateNumProducer();
            producer.setName(name);
            producer.setNum(1);
            save(producer);
            return 1;
        }
        int num = count + 1;
        updateNum(name);
        return num;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateNum(String name) {
        return this.baseMapper.updateNum(name);
    }
}
