package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserCheckout;
import com.landleaf.homeauto.center.device.model.mapper.FamilyUserCheckoutMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserCheckoutService;
import org.springframework.stereotype.Service;

/**
 * @author Yujiumin
 * @version 2020/9/14
 */
@Service
public class FamilyUserCheckoutServiceImpl extends ServiceImpl<FamilyUserCheckoutMapper, FamilyUserCheckout> implements IFamilyUserCheckoutService {


    @Override
    public FamilyUserCheckout getFamilyUserCheckout(String userId) {
        return null;
    }
}
