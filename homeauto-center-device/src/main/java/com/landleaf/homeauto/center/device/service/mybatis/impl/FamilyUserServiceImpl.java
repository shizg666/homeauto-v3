package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyUserMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 家庭组表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-19
 */
@Service
public class FamilyUserServiceImpl extends ServiceImpl<FamilyUserMapper, FamilyUserDO> implements IFamilyUserService {

    @Override
    public void checkoutFamily(String userId, String familyId) {
        // 把当前用户的当前家庭设为常用家庭
        UpdateWrapper<FamilyUserDO> updateWrapperForCheck = new UpdateWrapper<>();
        updateWrapperForCheck.set("last_checked", 1);
        updateWrapperForCheck.eq("family_id", familyId);
        updateWrapperForCheck.eq("user_id", userId);
        update(updateWrapperForCheck);

        // 把当前用户的其他家庭设备设为不常用家庭
        UpdateWrapper<FamilyUserDO> updateWrapperForUncheck = new UpdateWrapper<>();
        updateWrapperForUncheck.set("last_checked", 0);
        updateWrapperForUncheck.notIn("family_id", familyId);
        updateWrapperForUncheck.eq("user_id", userId);
        update(updateWrapperForCheck);
    }

}
