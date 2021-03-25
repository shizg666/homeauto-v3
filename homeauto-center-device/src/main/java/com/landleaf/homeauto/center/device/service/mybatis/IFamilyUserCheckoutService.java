package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserCheckout;

/**
 * @author Yujiumin
 * @version 2020/9/14
 */
public interface IFamilyUserCheckoutService extends IService<FamilyUserCheckout> {

    /**
     * 添加或更新记录
     *
     * @param userId   用户ID
     * @param familyId 家庭ID
     * @return 添加后的家庭信息
     */
    boolean saveOrUpdate(String userId, String familyId);

    /**
     * 获取用户用户需要切换的家庭
     *
     * @param userId
     * @return
     */
    FamilyUserCheckout getByUserId(String userId);

    /**
     * 删除用户家庭记录
     *
     * @param userId
     * @param familyId
     * @return
     */
    void deleteFamilyUserNote(String familyId, String userId);

    /**
     * 删除用户家庭记录
     *
     * @param familyId
     * @return
     */
    void deleteByFamilyId(String familyId);
}
