package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDeviceDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDeviceUpDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.device.util.RedisKeyUtils;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
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
    public FamilyDeviceDO getRoomPanel(String roomId) {
        return null;
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

    @Override
    public void add(FamilyDeviceDTO request) {
        addCheck(request);
        FamilyDeviceDO deviceDO = BeanUtil.mapperBean(request, FamilyDeviceDO.class);
        int count = count(new LambdaQueryWrapper<FamilyDeviceDO>().eq(FamilyDeviceDO::getRoomId, request.getRoomId()));
        deviceDO.setSortNo(count + 1);
        save(deviceDO);
    }

    private void addCheck(FamilyDeviceDTO request) {
        int count = this.baseMapper.existParam(request.getName(), null, request.getRoomId());
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
        }
        int countSn = this.baseMapper.existParam(null, request.getSn(), request.getRoomId());
        if (countSn > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备号已存在");
        }
    }

    @Override
    public void update(FamilyDeviceUpDTO request) {
        updateCheck(request);
        FamilyDeviceDO deviceDO = BeanUtil.mapperBean(request, FamilyDeviceDO.class);
        updateById(deviceDO);
    }

    private void updateCheck(FamilyDeviceUpDTO request) {
        FamilyDeviceDO deviceDO = getById(request.getId());
        if (request.getName().equals(deviceDO.getName())) {
            return;
        }
        int count = this.baseMapper.existParam(request.getName(), null, request.getRoomId());
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
        }
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        //todo 删除场景逻辑
        FamilyDeviceDO roomDO = getById(request.getId());
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoGT(roomDO.getRoomId(), roomDO.getSortNo());
        if (!CollectionUtils.isEmpty(sortNoBOS)) {
            sortNoBOS.forEach(obj -> {
                obj.setSortNo(obj.getSortNo() - 1);
            });
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
        removeById(request.getId());
    }

    @Override
    public List<FamilyDevicePageVO> getListByRoomId(String roomId) {
        return this.baseMapper.getListByRoomId(roomId);
    }

    @Override
    public List<CountBO> countDeviceByRoomIds(List<String> roomIds) {
        if (CollectionUtils.isEmpty(roomIds)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<CountBO> data = this.baseMapper.countDeviceByRoomIds(roomIds);
        if (CollectionUtils.isEmpty(data)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public void moveUp(String deviceId) {
        FamilyDeviceDO deviceDO = getById(deviceId);
        int sortNo = deviceDO.getSortNo();
        if (sortNo == 1) {
            return;
        }
        String updateId = this.getBaseMapper().getIdBySort(sortNo - 1, deviceDO.getRoomId());
        if (StringUtil.isBlank(updateId)) {
            return;
        }
        List<SortNoBO> sortNoBOS = Lists.newArrayListWithCapacity(2);
        sortNoBOS.add(SortNoBO.builder().id(deviceId).sortNo(sortNo - 1).build());
        sortNoBOS.add(SortNoBO.builder().id(updateId).sortNo(sortNo).build());
        this.baseMapper.updateBatchSort(sortNoBOS);
    }

    @Override
    public void moveDown(String deviceId) {
        FamilyDeviceDO deviceDO = getById(deviceId);
        int sortNo = deviceDO.getSortNo();
        String updateId = this.getBaseMapper().getIdBySort(sortNo + 1, deviceDO.getRoomId());
        if (StringUtil.isBlank(updateId)) {
            return;
        }
        List<SortNoBO> sortNoBOS = Lists.newArrayListWithCapacity(2);
        sortNoBOS.add(SortNoBO.builder().id(deviceId).sortNo(sortNo + 1).build());
        sortNoBOS.add(SortNoBO.builder().id(updateId).sortNo(sortNo).build());
        this.baseMapper.updateBatchSort(sortNoBOS);
    }

    @Override
    public void moveTop(String deviceId) {
        FamilyDeviceDO deviceDO = getById(deviceId);
        int sortNo = deviceDO.getSortNo();
        if (sortNo == 1) {
            return;
        }
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoLT(deviceDO.getRoomId(), sortNo);
        if (!CollectionUtils.isEmpty(sortNoBOS)) {
            sortNoBOS.forEach(obj -> {
                obj.setSortNo(obj.getSortNo() + 1);
            });
            SortNoBO sortNoBO = SortNoBO.builder().id(deviceDO.getId()).sortNo(1).build();
            sortNoBOS.add(sortNoBO);
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
    }

    @Override
    public void moveEnd(String deviceId) {
        FamilyDeviceDO deviceDO = getById(deviceId);
        int sortNo = deviceDO.getSortNo();
        int count = count(new LambdaQueryWrapper<FamilyDeviceDO>().eq(FamilyDeviceDO::getRoomId, deviceDO.getRoomId()));
        if (count == sortNo) {
            return;
        }
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoGT(deviceDO.getRoomId(), sortNo);
        if (!CollectionUtils.isEmpty(sortNoBOS)) {
            sortNoBOS.forEach(obj -> {
                obj.setSortNo(obj.getSortNo() - 1);
            });
            SortNoBO sortNoBO = SortNoBO.builder().id(deviceDO.getId()).sortNo(count).build();
            sortNoBOS.add(sortNoBO);
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
    }

    @Override
    public HomeAutoCategory getDeviceCategory(String deviceSn) {
        QueryWrapper<FamilyDeviceDO> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("sn", deviceSn);
        FamilyDeviceDO familyDeviceDO = getOne(deviceQueryWrapper, true);
        HomeAutoProduct product = productService.getById(familyDeviceDO.getProductId());
        return categoryService.getById(product.getCategoryId());
    }

}
