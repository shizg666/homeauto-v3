package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserCheckout;
import com.landleaf.homeauto.center.device.model.mapper.FamilyUserCheckoutMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserCheckoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/9/14
 */
@Slf4j
@Service
public class FamilyUserCheckoutServiceImpl extends ServiceImpl<FamilyUserCheckoutMapper, FamilyUserCheckout> implements IFamilyUserCheckoutService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public FamilyUserCheckout saveOrUpdate(String userId, String familyId) {
        FamilyUserCheckout familyUserCheckout = new FamilyUserCheckout();
        familyUserCheckout.setUserId(userId);
        familyUserCheckout.setFamilyId(familyId);
        saveOrUpdate(familyUserCheckout);
        return familyUserCheckout;
    }

    @Override
    public FamilyUserCheckout getFamilyUserCheckout(String userId) {
        QueryWrapper<FamilyUserCheckout> familyUserCheckoutQueryWrapper = new QueryWrapper<>();
        familyUserCheckoutQueryWrapper.eq("user_id", userId);
        return getOne(familyUserCheckoutQueryWrapper);
    }

    @Override
    public void deleteFamilyUserNote(String familyId, String userId) {
        remove(new LambdaQueryWrapper<FamilyUserCheckout>().eq(FamilyUserCheckout::getFamilyId, familyId).eq(FamilyUserCheckout::getUserId, userId));
    }
}
