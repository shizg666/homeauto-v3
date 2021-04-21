package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.RealestateNumProducerMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IBizNumProducerService;
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
public class BizNumProducerServiceImpl extends ServiceImpl<RealestateNumProducerMapper, SequenceProducer> implements IBizNumProducerService {

    public static final String CATEGORY_PREX = "category:";
    public static final String ZERO_PREX_1= "0";
    public static final String ZERO_PREX_2 = "00";

    @Override
    public String getRealestateNum(String citycode) {
        int num = this.getNum(citycode);
        String str;
        if (num <10){
            str = citycode.concat(ZERO_PREX_1).concat(String.valueOf(num));
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
            str = realestateCode.concat(ZERO_PREX_1).concat(String.valueOf(num));
        }else if (num < 100){
            str = String.valueOf(num);
        }else{
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "编码过大请联系管理员");
        }
        return str;
    }

    @Override
    public String getProjectNum(Long templateId) {
        int num = this.getNum(String.valueOf(templateId));
        return String.valueOf(num);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int  getNum(String name) {
        Integer count = this.baseMapper.getNum(name);
        if (count == null){
            SequenceProducer producer = new SequenceProducer();
            producer.setName(name);
            producer.setNum(1);
            save(producer);
            return 1;
        }
        int num = count + 1;
        updateNum(name,num);
        return num;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNum(String name) {
        baseMapper.updateNum(name);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateNum(String name,int num) {
        baseMapper.updateNum(name,num);
    }

    @Override
    public String getProductCode(String categoryCode) {
        int num = this.getNum(CATEGORY_PREX.concat(categoryCode));
        String str = "";
        if (num <10){
            str = categoryCode.concat(ZERO_PREX_2).concat(String.valueOf(num));
        }else if (num < 100){
            str = categoryCode.concat(ZERO_PREX_1).concat(String.valueOf(num));
        }else if (num> 999){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "产品编码过大请联系管理员");
        }
        return str;
    }
}
