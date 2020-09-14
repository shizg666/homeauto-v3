package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserCheckout;

/**
 * @author Yujiumin
 * @version 2020/9/14
 */
public interface IFamilyUserCheckoutService extends IService<FamilyUserCheckout> {

    /**
     * 获取用户用户需要切换的家庭
     * @param userId
     * @return
     */
    FamilyUserCheckout getFamilyUserCheckout(String userId);

}
