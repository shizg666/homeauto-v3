package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceStatusMapper;
import com.landleaf.homeauto.center.device.model.vo.AttributionVO;
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
    public List<AttributionVO> getDeviceAttributionsById(String deviceId) {
        QueryWrapper<FamilyDeviceStatusDO> familyDeviceStatusQueryWrapper = new QueryWrapper<>();
        familyDeviceStatusQueryWrapper.select("status_name", "status_value");
        familyDeviceStatusQueryWrapper.eq("device_id", deviceId);
        List<FamilyDeviceStatusDO> familyDeviceStatusPoList = list(familyDeviceStatusQueryWrapper);
        List<AttributionVO> attributionVOList = new LinkedList<>();
        for (FamilyDeviceStatusDO deviceStatusPo : familyDeviceStatusPoList) {
            AttributionVO attributionVO = new AttributionVO();
            attributionVO.setAttrName(deviceStatusPo.getStatusName());
            attributionVO.setAttrValue(deviceStatusPo.getStatusValue());
            attributionVOList.add(attributionVO);
        }
        return attributionVOList;
    }
}
