package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.RealestateNumProducerMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IRealestateNumProducerService;
import com.landleaf.homeauto.center.device.model.domain.realestate.SequenceProducer;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.exception.BusinessException;
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
public class RealestateNumProducerServiceImpl extends ServiceImpl<RealestateNumProducerMapper, SequenceProducer> implements IRealestateNumProducerService {

    @Override
    public String getRealestateNum(String citycode) {
        int num = this.getNum(citycode);
        String str;
        if (num <10){
            str = citycode.concat("0").concat(String.valueOf(num));
        }else if (num <100){
            str = String.valueOf(num);
        }else{
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "编码过大请联系管理员");
        }
        return str;
    }

    @Override
    public String getProjectNum(String realestateCode) {
        int num = this.getNum(realestateCode);
        String str;
        if (num <10){
            str = realestateCode.concat("0").concat(String.valueOf(num));
        }else if (num < 100){
            str = String.valueOf(num);
        }else{
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "编码过大请联系管理员");
        }
        return str;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int getNum(String name) {
        Integer count = this.baseMapper.getNum(name);
        if (count == null){
            SequenceProducer producer = new SequenceProducer();
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
    public void updateNum(String name) {
        baseMapper.updateNum(name);
    }
}
