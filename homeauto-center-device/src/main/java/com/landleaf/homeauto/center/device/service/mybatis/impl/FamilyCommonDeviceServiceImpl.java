package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyCommonDeviceMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyCommonDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 家庭常用设备表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyCommonDeviceServiceImpl extends ServiceImpl<FamilyCommonDeviceMapper, FamilyCommonDeviceDO> implements IFamilyCommonDeviceService {

    @Override
    public List<FamilyCommonDeviceDO> listByFamilyId(String familyId) {
        QueryWrapper<FamilyCommonDeviceDO> commonDeviceQueryWrapper = new QueryWrapper<>();
        commonDeviceQueryWrapper.eq("family_id", familyId);
        commonDeviceQueryWrapper.isNotNull("device_id");
        commonDeviceQueryWrapper.ne("device_id", "");
        return list(commonDeviceQueryWrapper);
    }

    @Override
    public void deleteFamilyCommonDeviceList(String familyId) {
        QueryWrapper<FamilyCommonDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCommonDeviceList(String familyId, List<String> deviceList) {
        // 1. 删除常用设备
        deleteFamilyCommonDeviceList(familyId);

        // 2. 添加新的常用设备
        List<FamilyCommonDeviceDO> familyCommonDeviceDOList = new LinkedList<>();
        for (int i = 0; i < deviceList.size(); i++) {
            FamilyCommonDeviceDO familyCommonSceneDO = new FamilyCommonDeviceDO();
            familyCommonSceneDO.setFamilyId(familyId);
            familyCommonSceneDO.setDeviceId(deviceList.get(i));
            familyCommonSceneDO.setSortNo(i);
            familyCommonDeviceDOList.add(familyCommonSceneDO);
        }

        // 3. 批量插入
        saveBatch(familyCommonDeviceDOList);
    }
}
