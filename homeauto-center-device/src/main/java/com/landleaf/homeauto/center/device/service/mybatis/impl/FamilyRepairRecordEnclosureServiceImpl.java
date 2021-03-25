package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyRepairRecordEnclosure;
import com.landleaf.homeauto.center.device.model.mapper.FamilyRepairRecordEnclosureMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRepairRecordEnclosureService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 家庭维修记录附件 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Service
public class FamilyRepairRecordEnclosureServiceImpl extends ServiceImpl<FamilyRepairRecordEnclosureMapper, FamilyRepairRecordEnclosure> implements IFamilyRepairRecordEnclosureService {

    @Override
    public List<FamilyRepairRecordEnclosure> getByRecordId(String repairId) {
        QueryWrapper<FamilyRepairRecordEnclosure> queryWrapper = new QueryWrapper<FamilyRepairRecordEnclosure>();
        queryWrapper.eq("family_repair_id", repairId);
        return list(queryWrapper);
    }
}
