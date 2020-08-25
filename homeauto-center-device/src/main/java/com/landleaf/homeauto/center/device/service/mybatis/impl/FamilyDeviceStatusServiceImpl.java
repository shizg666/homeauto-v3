package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceStatusMapper;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttributionVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-15
 */
@Service
public class FamilyDeviceStatusServiceImpl extends ServiceImpl<FamilyDeviceStatusMapper, FamilyDeviceStatusDO> implements IFamilyDeviceStatusService {

    @Override
    public List<DeviceAttributionVO> getDeviceAttributionsById(String deviceId) {
        QueryWrapper<FamilyDeviceStatusDO> familyDeviceStatusQueryWrapper = new QueryWrapper<>();
        familyDeviceStatusQueryWrapper.eq("device_id", deviceId);
        return handleResult(familyDeviceStatusQueryWrapper);
    }

    @Override
    public List<FamilyDeviceStatusDO> getDeviceAttributionStatusById(String deviceId) {
        QueryWrapper<FamilyDeviceStatusDO> familyDeviceStatusQueryWrapper = new QueryWrapper<>();
        familyDeviceStatusQueryWrapper.eq("device_id", deviceId);
        return list(familyDeviceStatusQueryWrapper);
    }

    @Override
    public List<DeviceAttributionVO> getDeviceAttributionsBySn(String deviceSn) {
        QueryWrapper<FamilyDeviceStatusDO> familyDeviceStatusQueryWrapper = new QueryWrapper<>();
        familyDeviceStatusQueryWrapper.eq("device_sn", deviceSn);
        return handleResult(familyDeviceStatusQueryWrapper);
    }

    @Override
    public void insertBatchDeviceStatus(List<DeviceStatusBO> deviceStatusBOList) {
        for (DeviceStatusBO deviceStatusBO : deviceStatusBOList) {
            FamilyDeviceStatusDO familyDeviceStatusDO = new FamilyDeviceStatusDO();

        }
    }

    /**
     * 将设备属性抽离出来
     *
     * @param queryWrapper 查询条件
     * @return 设备属性列表
     */
    private List<DeviceAttributionVO> handleResult(QueryWrapper<FamilyDeviceStatusDO> queryWrapper) {
        List<FamilyDeviceStatusDO> familyDeviceStatusPoList = list(queryWrapper);
        List<DeviceAttributionVO> deviceAttributionVOList = new LinkedList<>();
        for (FamilyDeviceStatusDO deviceStatusPo : familyDeviceStatusPoList) {
            DeviceAttributionVO deviceAttributionVO = new DeviceAttributionVO();
            deviceAttributionVO.setAttrName(deviceStatusPo.getStatusCode());
            deviceAttributionVO.setAttrValue(deviceStatusPo.getStatusValue());
            deviceAttributionVOList.add(deviceAttributionVO);
        }
        return deviceAttributionVOList;
    }
}
