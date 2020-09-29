package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceBO;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.device.PanelBO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDeviceDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDeviceUpDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.device.util.RedisKeyUtils;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneDeviceBO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.category.PrecisionEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 家庭设备表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Slf4j
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
    private IFamilyFloorService iFamilyFloorService;

    @Autowired
    private IFamilyTerminalService familyTerminalService;

    @Autowired
    private IHomeAutoCategoryService categoryService;

    @Autowired
    private IFamilySceneActionService iFamilySceneActionService;

    @Autowired
    private IFamilySceneHvacConfigService iFamilySceneHvacConfigService;

    @Autowired
    private IFamilySceneHvacConfigActionService iFamilySceneHvacConfigActionService;

    @Autowired
    private IFamilySceneHvacConfigActionPanelService iFamilySceneHvacConfigActionPanelService;

    @Autowired
    private IProductAttributeErrorService productAttributeErrorService;

    @Override
    public DeviceBO getDeviceById(String id) {
        DeviceBO deviceBO = new DeviceBO();
        // 1. 设备本身的信息
        FamilyDeviceDO familyDevice = getById(id);
        deviceBO.setDeviceId(familyDevice.getId());
        deviceBO.setDeviceSn(familyDevice.getSn());

        // 2. 家庭信息
        HomeAutoFamilyDO homeAutoFamily = familyService.getById(familyDevice.getFamilyId());
        deviceBO.setFamilyId(homeAutoFamily.getId());
        deviceBO.setFamilyCode(homeAutoFamily.getCode());

        // 3.房间信息
        FamilyRoomDO familyRoom = roomService.getById(familyDevice.getRoomId());
        deviceBO.setRoomId(familyRoom.getId());
        deviceBO.setRoomName(familyRoom.getName());
        deviceBO.setRoomType(RoomTypeEnum.getInstByType(familyRoom.getType()));

        // 4.楼层信息
        FamilyFloorDO familyFloor = iFamilyFloorService.getById(familyRoom.getFloorId());
        deviceBO.setFloorId(familyFloor.getId());
        deviceBO.setFloorName(familyFloor.getName());

        // 5. 产品信息
        HomeAutoProduct homeAutoProduct = productService.getById(familyDevice.getProductId());
        deviceBO.setProductId(homeAutoProduct.getId());
        deviceBO.setProductCode(homeAutoProduct.getCode());
        deviceBO.setProductIcon(homeAutoProduct.getIcon());

        // 6. 品类信息
        HomeAutoCategory homeAutoCategory = categoryService.getById(familyDevice.getCategoryId());
        deviceBO.setCategoryId(homeAutoCategory.getId());
        deviceBO.setCategoryCode(homeAutoCategory.getCode());

        // 7. 终端信息
        FamilyTerminalDO familyTerminal = familyTerminalService.getById(familyDevice.getTerminalId());
        deviceBO.setTerminalId(familyTerminal.getId());
        deviceBO.setTerminalMac(familyTerminal.getMac());

        // 8. 设备属性
        List<ProductAttributeDO> productAttributeList = productService.getAttributesByProductId(familyDevice.getProductId());
        List<String> deviceAttributeList = productAttributeList.stream().map(ProductAttributeDO::getCode).collect(Collectors.toList());
        deviceBO.setDeviceAttributeList(deviceAttributeList);
        return deviceBO;
    }

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
        FamilyDeviceDO familyDevice = getById(deviceId);
        HomeAutoProduct product = productService.getById(familyDevice.getProductId());
        return product.getIcon();
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
        FamilyDeviceDO familyDevice = getById(deviceId);
        String familyCode = familyService.getById(familyDevice.getFamilyId()).getCode();
        String productCode = productService.getById(familyDevice.getProductId()).getCode();
        String deviceSn = familyDevice.getSn();
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
        QueryWrapper<HomeAutoCategory> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("code", String.valueOf(CategoryEnum.PANEL_TEMP.getType()));
        HomeAutoCategory category = categoryService.getOne(categoryQueryWrapper);

        QueryWrapper<HomeAutoProduct> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("category_id", category.getId());
        List<HomeAutoProduct> productList = productService.list(productQueryWrapper);
        List<String> productIds = productList.stream().map(HomeAutoProduct::getId).collect(Collectors.toList());

        QueryWrapper<FamilyDeviceDO> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("room_id", roomId);
        deviceQueryWrapper.in("product_id", productIds);
        return getOne(deviceQueryWrapper);
    }

    @Override
    public List<FamilyDeviceBO> getDeviceInfoListByRoomId(String roomId) {
        return familyDeviceMapper.getDeviceListByRoomId(roomId);
    }

    @Override
    public DeviceSensorBO getHchoSensor(String familyId) {
        log.info("getHchoSensor(String familyId) 入参为:{}", familyId);
        log.info("获取家庭的甲醛传感器");
        DeviceSensorBO deviceSensorBO = getSensor(familyId, CategoryEnum.HCHO_SENSOR);
        log.info("甲醛传感器的值为:{}", deviceSensorBO);
        return deviceSensorBO;
    }

    @Override
    public DeviceSensorBO getPm25Sensor(String familyId) {
        return getSensor(familyId, CategoryEnum.PM25_SENSOR);
    }

    @Override
    public DeviceSensorBO getAllParamSensor(String familyId) {
        return getSensor(familyId, CategoryEnum.ALL_PARAM_SENSOR);
    }

    @Override
    public DeviceSensorBO getSensor(String familyId, CategoryEnum... categoryEnums) {
        DeviceSensorBO deviceSensorBO = familyDeviceMapper.getDeviceSensorBO(familyId, categoryEnums);
        if (!Objects.isNull(deviceSensorBO)) {
            List<ProductAttributeDO> attributes = productService.getAttributes(deviceSensorBO.getProductCode());
            List<String> attributeList = attributes.stream().map(ProductAttributeDO::getCode).collect(Collectors.toList());
            deviceSensorBO.setAttributeList(attributeList);
        }
        return deviceSensorBO;
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
        return familyDeviceMapper.getDeviceByFamilyIdAndCategory(familyId, String.valueOf(category));
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
        int count = this.baseMapper.existParam(request.getName(), null, request.getFamilyId());
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
        }
        int countSn = this.baseMapper.existParam(null, request.getSn(), request.getFamilyId());
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
        int count = this.baseMapper.existParam(request.getName(), null, deviceDO.getFamilyId());
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
        }
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        FamilyDeviceDO deviceDO = getById(request.getId());
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoGT(deviceDO.getRoomId(), deviceDO.getSortNo());
        if (!CollectionUtils.isEmpty(sortNoBOS)) {
            sortNoBOS.forEach(obj -> {
                obj.setSortNo(obj.getSortNo() - 1);
            });
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
        boolean hvacFlag = productService.getHvacFlagById(deviceDO.getProductId());
        if (hvacFlag) {
            deleteHvacConfig(deviceDO);
        } else {
            deleteDeviceAction(deviceDO);
        }
        removeById(request.getId());
    }

    /**
     * 非暖通配置
     *
     * @param deviceDO
     */
    private void deleteDeviceAction(FamilyDeviceDO deviceDO) {
        String categoryCode = categoryService.getCategoryCodeById(deviceDO.getCategoryId());
        if (CategoryTypeEnum.TEMPERATURE_PANEL.getType().equals(categoryCode)) {
            iFamilySceneHvacConfigActionPanelService.remove(new LambdaQueryWrapper<FamilySceneHvacConfigActionPanel>().eq(FamilySceneHvacConfigActionPanel::getDeviceSn, deviceDO.getSn()).eq(FamilySceneHvacConfigActionPanel::getFamilyId, deviceDO.getFamilyId()));
        } else {
            iFamilySceneActionService.remove(new LambdaQueryWrapper<FamilySceneActionDO>().eq(FamilySceneActionDO::getDeviceSn, deviceDO.getSn()).eq(FamilySceneActionDO::getFamilyId, deviceDO.getFamilyId()));
        }
    }

    /**
     * 删除暖通设备配置
     */
    private void deleteHvacConfig(FamilyDeviceDO deviceDO) {
        List<String> hvacConfigIds = iFamilySceneHvacConfigService.getListIds(deviceDO.getSn(), deviceDO.getFamilyId());
        if (CollectionUtils.isEmpty(hvacConfigIds)) {
            return;
        }
        iFamilySceneHvacConfigService.removeByIds(hvacConfigIds);
        List<String> hvacActionIds = iFamilySceneHvacConfigActionService.getListIds(hvacConfigIds);
        if (CollectionUtils.isEmpty(hvacActionIds)) {
            return;
        }
        iFamilySceneHvacConfigActionService.removeByIds(hvacActionIds);
        iFamilySceneHvacConfigActionPanelService.remove(new LambdaQueryWrapper<FamilySceneHvacConfigActionPanel>().in(FamilySceneHvacConfigActionPanel::getHvacActionId, hvacActionIds));
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
    public HomeAutoCategory getDeviceCategory(String deviceSn, String familyId) {
        QueryWrapper<FamilyDeviceDO> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("sn", deviceSn);
        deviceQueryWrapper.eq("family_id", familyId);
        FamilyDeviceDO familyDevice = getOne(deviceQueryWrapper);
        HomeAutoProduct product = productService.getById(familyDevice.getProductId());
        return categoryService.getById(product.getCategoryId());
    }

    @Override
    public HomeAutoProduct getDeviceProduct(String deviceSn, String familyId) {
        QueryWrapper<FamilyDeviceDO> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("sn", deviceSn);
        deviceQueryWrapper.eq("family_id", familyId);
        FamilyDeviceDO familyDevice = getOne(deviceQueryWrapper, true);
        HomeAutoProduct product = productService.getById(familyDevice.getProductId());
        return product;
    }

    @Override
    public List<String> getListPanel(String familyId) {
        return this.baseMapper.getListPanel(familyId);
    }

    @Override
    public List<SceneHvacDeviceVO> getListHvacInfo(String familyId) {
        return this.baseMapper.getListHvacInfo(familyId);
    }

    @Override
    public AttributeScopeVO getPanelSettingTemperature(String familyId) {
        return this.baseMapper.getPanelSettingTemperature(familyId);
    }

    @Override
    public List<SceneFloorVO> getListdeviceInfo(String familyId) {
        List<SceneFloorVO> floorVOS = this.baseMapper.getListdeviceInfo(familyId);
        if (CollectionUtils.isEmpty(floorVOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        Set<String> deviceIds = Sets.newHashSet();
        for (SceneFloorVO floor : floorVOS) {
            if (CollectionUtils.isEmpty(floor.getRooms())) {
                continue;
            }
            for (SceneRoomVO room : floor.getRooms()) {
                if (CollectionUtils.isEmpty(room.getDevices())) {
                    continue;
                }
                room.getDevices().forEach(device -> {
                    deviceIds.add(device.getProductId());
                });
            }
        }
        //获取产品属性信息
        List<SceneDeviceAttributeVO> attributes = productService.getListdeviceAttributeInfo(Lists.newArrayList(deviceIds));
        if (CollectionUtils.isEmpty(attributes)) {
            return floorVOS;
        }
        Map<String, List<SceneDeviceAttributeVO>> map = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));
        for (SceneFloorVO floor : floorVOS) {
            if (CollectionUtils.isEmpty(floor.getRooms())) {
                continue;
            }
            for (SceneRoomVO room : floor.getRooms()) {
                if (CollectionUtils.isEmpty(room.getDevices())) {
                    continue;
                }
                room.getDevices().forEach(device -> {
                    if (map.containsKey(device.getProductId())) {
                        device.setAttributes(map.get(device.getProductId()));
                    }
                });
            }
        }
        return floorVOS;
    }

    @Override
    public List<SelectedVO> getListPanelSelects(String familyId) {
        List<PanelBO> panelBOS = this.baseMapper.getListPanelSelects(familyId);
        if (CollectionUtils.isEmpty(panelBOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<SelectedVO> selectedVOS = panelBOS.stream().map(panel -> {
            return new SelectedVO(panel.getFloorName().concat(panel.getRoomName()), panel.getSn());
        }).collect(Collectors.toList());
        return selectedVOS;
    }

    @Override
    public List<SceneDeviceVO> getListDevice(String familyId) {
        List<SceneDeviceVO> floorVOS = this.baseMapper.getListDevice(familyId);
        if (CollectionUtils.isEmpty(floorVOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> productIds = floorVOS.stream().map(SceneDeviceVO::getProductId).collect(Collectors.toList());
        List<SceneDeviceAttributeVO> attributes = productService.getListdeviceAttributeInfo(Lists.newArrayList(productIds));
        if (CollectionUtils.isEmpty(attributes)) {
            return floorVOS;
        }
        Map<String, List<SceneDeviceAttributeVO>> map = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));
        floorVOS.forEach(obj -> {
            if (map.containsKey(obj.getProductId())) {
                obj.setAttributes(map.get(obj.getProductId()));
            }
        });
        return floorVOS;
    }

    @Override
    public HouseFloorRoomListDTO getListFloorRooms(String familyId) {
        HouseFloorRoomListDTO result = new HouseFloorRoomListDTO();
        List<String> floors = iFamilyFloorService.getListNameByFamilyId(familyId);
        if (CollectionUtils.isEmpty(floors)) {
            return result;
        }
        result.setFloors(floors);
        List<String> rooms = roomService.getListNameByFamilyId(familyId);
        result.setRooms(rooms);
        return result;
    }

    @Override
    public List<SyncSceneDeviceBO> getListSyncSceneDevice(String familyId, List<String> deviceSns) {
        return this.baseMapper.getListSyncSceneDevice(familyId, deviceSns);
    }

    @Override
    public FamilyDeviceDO getFamilyDevice(String familyId, String deviceSn) {
        QueryWrapper<FamilyDeviceDO> familyDeviceQueryWrapper = new QueryWrapper<>();
        familyDeviceQueryWrapper.eq("family_id", familyId);
        familyDeviceQueryWrapper.eq("sn", deviceSn);
        FamilyDeviceDO familyDevice = getOne(familyDeviceQueryWrapper);
        return familyDevice;
    }

    @Override
    public FamilyDeviceDO getFamilyHvacDevice(String familyId) {
        QueryWrapper<HomeAutoProduct> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("hvac_flag", 1);
        List<HomeAutoProduct> productList = productService.list(productQueryWrapper);
        List<String> idList = productList.stream().map(HomeAutoProduct::getId).collect(Collectors.toList());

        QueryWrapper<FamilyDeviceDO> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.in("product_id", idList);
        deviceQueryWrapper.eq("family_id", familyId);
        deviceQueryWrapper.orderByAsc("sort_no");

        FamilyDeviceDO familyDeviceDO = getOne(deviceQueryWrapper);
        return familyDeviceDO;
    }

    @Override
    public Object handleParamValue(String productCode, String attributeCode, Object value) {
        try {
            AttributePrecisionQryDTO attributePrecisionQryDTO = new AttributePrecisionQryDTO();
            attributePrecisionQryDTO.setProductCode(productCode);
            attributePrecisionQryDTO.setCode(attributeCode);
            List<AttributePrecisionDTO> attributePrecision = productAttributeErrorService.getAttributePrecision(attributePrecisionQryDTO);
            if (!Objects.isNull(attributePrecision) && !CollectionUtil.isEmpty(attributePrecision)) {
                AttributePrecisionDTO attributePrecisionDTO = attributePrecision.get(0);
                return PrecisionEnum.getInstByType(attributePrecisionDTO.getPrecision()).parse(value);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }
}
