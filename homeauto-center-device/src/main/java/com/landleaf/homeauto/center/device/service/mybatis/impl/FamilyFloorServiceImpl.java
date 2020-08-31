package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyFloorMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyFloorService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 家庭楼层表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyFloorServiceImpl extends ServiceImpl<FamilyFloorMapper, FamilyFloorDO> implements IFamilyFloorService {

    @Override
    public List<FamilyFloorDO> getFloorByFamilyId(String familyId) {
        QueryWrapper<FamilyFloorDO> queryWrapper = new QueryWrapper<FamilyFloorDO>();
        queryWrapper.eq("family_id", familyId);
        return list(queryWrapper);
    }
}
