package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.DeviceStatusBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceStatusDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceStatusMapper;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttributionVO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoProductService;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.device.util.RedisKeyUtils;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;

    private IHomeAutoProductService homeAutoProductService;

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
            String familyCode = deviceStatusBO.getFamilyCode();
            String productCode = deviceStatusBO.getProductCode();
            String deviceSn = deviceStatusBO.getDeviceSn();
            String statusCode = deviceStatusBO.getStatusCode();
            String statusValue = deviceStatusBO.getStatusValue();
            String key = RedisKeyUtils.getDeviceStatusKey(familyCode, productCode, deviceSn, statusCode);
            if (Objects.equals(redisServiceForDeviceStatus.getDeviceStatus(key).toString(), statusValue)) {
                // 如果设备的上次状态和上报状态一致,则更新状态的结束时间
                UpdateWrapper<FamilyDeviceStatusDO> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("end_time", LocalDateTime.now());
                updateWrapper.eq("family_code", familyCode);
                updateWrapper.eq("product_code", productCode);
                updateWrapper.eq("device_sn", deviceSn);
                updateWrapper.eq("status_code", statusCode);
                update(updateWrapper);
            } else {
                // 如果设备的上次状态和上报状态不一致,则插入一条新的状态
                FamilyDeviceStatusDO familyDeviceStatusDO = new FamilyDeviceStatusDO();
                familyDeviceStatusDO.setDeviceSn(deviceSn);
                familyDeviceStatusDO.setStatusCode(statusCode);
                familyDeviceStatusDO.setStatusValue(statusValue);
                familyDeviceStatusDO.setFamilyId(deviceStatusBO.getFamilyId());
                familyDeviceStatusDO.setProductCode(productCode);
                familyDeviceStatusDO.setCategoryCode(homeAutoProductService.getCategoryCodeByProductCode(productCode));
                familyDeviceStatusDO.setBeginTime(LocalDateTime.now());
                familyDeviceStatusDO.setEndTime(LocalDateTime.now());
                save(familyDeviceStatusDO);
            }

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

    @Autowired
    public void setRedisUtils(RedisServiceForDeviceStatus redisServiceForDeviceStatus) {
        this.redisServiceForDeviceStatus = redisServiceForDeviceStatus;
    }

    @Autowired
    public void setHomeAutoProductService(IHomeAutoProductService homeAutoProductService) {
        this.homeAutoProductService = homeAutoProductService;
    }
}
