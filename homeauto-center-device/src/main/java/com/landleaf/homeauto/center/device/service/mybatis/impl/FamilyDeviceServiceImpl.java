package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.device.util.RedisKeyUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 家庭设备表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyDeviceServiceImpl extends ServiceImpl<FamilyDeviceMapper, FamilyDeviceDO> implements IFamilyDeviceService {

    @Autowired
    private FamilyDeviceMapper familyDeviceMapper;

    @Autowired
    private RedisServiceForDeviceStatus redisServiceForDeviceStatus;

    @Autowired
    private IHomeAutoProductService productService;

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilyRoomService roomService;

    @Autowired
    private IHomeAutoCategoryService categoryService;

    @Override
    public List<FamilyDeviceWithPositionBO> getAllDevices(String familyId) {
        return familyDeviceMapper.getAllDevicesByFamilyId(familyId);
    }

    @Override
    public List<FamilyDeviceWithPositionBO> getCommonDevices(String familyId) {
        return familyDeviceMapper.getCommonDevicesByFamilyId(familyId);
    }

    @Override
    public List<FamilyDeviceWithPositionBO> getDeviceInfoBySceneId(String sceneId) {
        return familyDeviceMapper.getDeviceInfoByDeviceSn(sceneId);
    }

    @Override
    public boolean existByProductId(String productId) {
        QueryWrapper<FamilyDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        queryWrapper.last("limit 1");
        Integer deviceCount = baseMapper.selectCount(queryWrapper);
        return deviceCount > 0;
    }

    @Override
    public List<CountBO> getCountByProducts(List<String> productIds) {
        return this.baseMapper.getCountByProducts(productIds);
    }

    @Override
    public List<CountBO> getCountByFamilyIds(List<String> familyIds) {
        List<CountBO> countBOS = this.baseMapper.getCountByFamilyIds(familyIds);
        if (CollectionUtils.isEmpty(countBOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return countBOS;
    }

    @Override
    public String getDeviceIconById(String deviceId) {
        FamilyDeviceDO familyDeviceDO = getById(deviceId);
        HomeAutoProduct product = productService.getById(familyDeviceDO.getProductId());
        return product.getIcon();
    }

    @Override
    public String getDevicePositionById(String deviceId) {
        String roomId = getById(deviceId).getRoomId();
        return roomService.getPosition(roomId);
    }

    @Override
    public Object getDeviceStatus(DeviceSensorBO deviceSensorBO, String attributeCode) {
        String familyCode = deviceSensorBO.getFamilyCode();
        String deviceSn = deviceSensorBO.getDeviceSn();
        String productCode = deviceSensorBO.getProductCode();
        return getDeviceStatus(familyCode, productCode, deviceSn, attributeCode);
    }

    @Override
    public Object getDeviceStatus(String deviceId, String statusCode) {
        FamilyDeviceDO familyDeviceDO = getById(deviceId);
        String familyCode = familyService.getById(familyDeviceDO.getFamilyId()).getCode();
        String productCode = productService.getById(familyDeviceDO.getProductId()).getCode();
        String deviceSn = familyDeviceDO.getSn();
        return getDeviceStatus(familyCode, productCode, deviceSn, statusCode);
    }

    @Override
    public Object getDeviceStatus(String familyCode, String productCode, String deviceSn, String attributeCode) {
        return redisServiceForDeviceStatus.getDeviceStatus(RedisKeyUtils.getDeviceStatusKey(familyCode, productCode, deviceSn, attributeCode));
    }

    @Override
    public List<FamilyDeviceDO> getDeviceListByRoomId(String roomId) {
        QueryWrapper<FamilyDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId);
        return list(queryWrapper);
    }

    @Override
    public List<FamilyDeviceBO> getDeviceInfoListByRoomId(String roomId) {
        return familyDeviceMapper.getDeviceListByRoomId(roomId);
    }

    @Override
    public DeviceSensorBO getHchoSensor(String familyId) {
        return familyDeviceMapper.getDeviceSensorBO(familyId, CategoryEnum.HCHO_SENSOR);
    }

    @Override
    public DeviceSensorBO getPm25Sensor(String familyId) {
        return familyDeviceMapper.getDeviceSensorBO(familyId, CategoryEnum.PM25_SENSOR);
    }

    @Override
    public DeviceSensorBO getParamSensor(String familyId) {
        return familyDeviceMapper.getDeviceSensorBO(familyId, CategoryEnum.MULTI_PARAM_SENSOR, CategoryEnum.ALL_PARAM_SENSOR);
    }

    @Override
    public List<FamilyDeviceDO> getDevicesByFamilyId(String familyId) {
        QueryWrapper<FamilyDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        return list(queryWrapper);
    }

    @Override
    public List<SelectedVO> getListHvacByFamilyId(String familyId) {

        return this.baseMapper.getListHvacByFamilyId(familyId);
    }

    @Override
    public void updateDeviceName(FamilyUpdateVO request) {
        FamilyDeviceDO deviceDO = BeanUtil.mapperBean(request, FamilyDeviceDO.class);
        updateById(deviceDO);
    }

    @Override
    public FamilyDeviceDO getFamilyDevice(String familyId, CategoryEnum categoryEnum) {
        Integer category = categoryEnum.getType();
        return familyDeviceMapper.getHvacDeviceByFamilyId(familyId, category);
    }

}
