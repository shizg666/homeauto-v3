package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.constant.DeviceNatureEnum;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceBaseInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.device.PanelBO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDeviceDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDeviceUpDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.service.redis.RedisServiceForDeviceStatus;
import com.landleaf.homeauto.center.device.util.RedisKeyUtils;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneDeviceBO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributePrecisionQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.category.PrecisionEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.*;
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

    @Autowired
    private IAppService appService;

    @Autowired
    private IFamilySceneService iFamilySceneService;
    @Autowired
    private IHomeAutoCategoryService iHomeAutoCategoryService;

    @Autowired
    private IFamilyCommonDeviceService familyCommonDeviceService;


    @Override
    public DeviceBO getDeviceById(String id) {
        return listDeviceByIds(Collections.singletonList(id)).get(0);
    }

    @Override
    public List<DeviceBO> listDeviceByIds(List<String> ids) {
        List<DeviceBO> deviceBOList = new LinkedList<>();
        Collection<FamilyDeviceDO> familyDeviceDOList = super.listByIds(ids);
        for (FamilyDeviceDO familyDevice : familyDeviceDOList) {
            DeviceBO deviceBO = new DeviceBO();

            // 1. 设备本身的信息
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
            deviceBO.setFloorNum(familyFloor.getFloor());
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
            deviceBO.setTerminalType(familyTerminal.getType());
            deviceBO.setTerminalMac(familyTerminal.getMac());

            // 8. 设备属性
            List<ProductAttributeDO> productAttributeList = productService.getAttributesByProductId(familyDevice.getProductId());
            List<String> deviceAttributeList = productAttributeList.stream().map(ProductAttributeDO::getCode).collect(Collectors.toList());
            deviceBO.setDeviceAttributeList(deviceAttributeList);

            // 9. 添加进列表
            deviceBOList.add(deviceBO);
        }
        return deviceBOList;
    }

    @Override
    public com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO getDeviceDetailById(String deviceId) {
        return listDeviceDetailByIds(Collections.singletonList(deviceId)).get(0);
    }

    @Override
    public List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO> listDeviceDetailByIds(List<String> ids) {
        List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO> familyDeviceBOList = new LinkedList<>();
        Collection<FamilyDeviceDO> familyDeviceDOList = super.listByIds(ids);
        for (FamilyDeviceDO familyDevice : familyDeviceDOList) {
            com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO familyDeviceBO = new com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO();

            // 1. 设备本身的信息
            familyDeviceBO.setDeviceId(familyDevice.getId());
            familyDeviceBO.setDeviceSn(familyDevice.getSn());
            familyDeviceBO.setDeviceName(familyDevice.getName());

            // 2. 家庭信息
            HomeAutoFamilyDO homeAutoFamily = familyService.getById(familyDevice.getFamilyId());
            familyDeviceBO.setFamilyId(homeAutoFamily.getId());
            familyDeviceBO.setFamilyCode(homeAutoFamily.getCode());

            // 3.房间信息
            FamilyRoomDO familyRoom = roomService.getById(familyDevice.getRoomId());
            familyDeviceBO.setRoomId(familyRoom.getId());
            familyDeviceBO.setRoomName(familyRoom.getName());
            familyDeviceBO.setRoomType(RoomTypeEnum.getInstByType(familyRoom.getType()));

            // 4.楼层信息
            FamilyFloorDO familyFloor = iFamilyFloorService.getById(familyRoom.getFloorId());
            familyDeviceBO.setFloorId(familyFloor.getId());
            familyDeviceBO.setFloorNum(familyFloor.getFloor());
            familyDeviceBO.setFloorName(familyFloor.getName());

            // 5. 产品信息
            HomeAutoProduct homeAutoProduct = productService.getById(familyDevice.getProductId());
            familyDeviceBO.setProductId(homeAutoProduct.getId());
            familyDeviceBO.setProductCode(homeAutoProduct.getCode());
            familyDeviceBO.setProductIcon(homeAutoProduct.getIcon());
            familyDeviceBO.setProductImage(homeAutoProduct.getIcon2());

            // 6. 品类信息
            HomeAutoCategory homeAutoCategory = categoryService.getById(familyDevice.getCategoryId());
            familyDeviceBO.setCategoryId(homeAutoCategory.getId());
            familyDeviceBO.setCategoryCode(homeAutoCategory.getCode());

            // 7. 终端信息
            FamilyTerminalDO familyTerminal = familyTerminalService.getById(familyDevice.getTerminalId());
            familyDeviceBO.setTerminalId(familyTerminal.getId());
            familyDeviceBO.setTerminalType(familyTerminal.getType());
            familyDeviceBO.setTerminalMac(familyTerminal.getMac());

            // 8. 设备属性列表
            List<ProductAttributeDO> productAttributeList = productService.getAttributesByProductId(familyDevice.getProductId());
            List<String> deviceAttributeList = productAttributeList.stream().map(ProductAttributeDO::getCode).collect(Collectors.toList());
            familyDeviceBO.setDeviceAttributeList(deviceAttributeList);

            // 9. 添加进列表
            familyDeviceBOList.add(familyDeviceBO);
        }
        return familyDeviceBOList;
    }

    @Override
    public com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO getByFamilyIdAndDeviceSn(String familyId, String deviceSn) {
        QueryWrapper<FamilyDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        queryWrapper.eq("sn", deviceSn);
        FamilyDeviceDO familyDeviceDO = getOne(queryWrapper);
        return getDeviceDetailById(familyDeviceDO.getId());
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
        FamilyDeviceDO familyDevice = super.getById(deviceId);
        HomeAutoProduct product = productService.getById(familyDevice.getProductId());
        return product.getIcon();
    }

    @Override
    public Object getDeviceStatus(String deviceId, String statusCode) {
        FamilyDeviceDO familyDevice = super.getById(deviceId);
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
        //发送同步消息
        sendDeviceSyncMessage(request.getFamilyId());
    }

    /**
     * 发送家庭设备同步信息
     *
     * @param familyId
     */
    private void sendDeviceSyncMessage(String familyId) {
        AdapterConfigUpdateDTO configUpdateDTO = iFamilySceneService.getSyncConfigInfo(familyId);
        configUpdateDTO.setUpdateType(ContactScreenConfigUpdateTypeEnum.FLOOR_ROOM_DEVICE.code);
        configUpdateDTO.setFamilyId(familyId);
        appService.configUpdateConfig(configUpdateDTO);
    }

    private void addCheck(FamilyDeviceDTO request) {
        String categoryCode = iHomeAutoCategoryService.getCategoryCodeById(request.getCategoryId());
        //暖通新风 一个家庭至多一个设备
        if (CategoryTypeEnum.HVAC.getType().equals(categoryCode) || CategoryTypeEnum.FRESH_AIR.getType().equals(categoryCode)) {
            int count = this.baseMapper.existParam(null, null, request.getFamilyId(), request.getCategoryId());
            if (count > 0) {
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "暖通新风设备最多一个");
            }
        }
        int count = this.baseMapper.existParam(request.getName(), null, request.getFamilyId(), null);
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
        }
        int countSn = this.baseMapper.existParam(null, request.getSn(), request.getFamilyId(), null);
        if (countSn > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备号已存在");
        }
    }

    @Override
    public void update(FamilyDeviceUpDTO request) {
        updateCheck(request);
        FamilyDeviceDO deviceDO = BeanUtil.mapperBean(request, FamilyDeviceDO.class);
        updateById(deviceDO);
        //发送同步消息
        sendDeviceSyncMessage(deviceDO.getFamilyId());
    }

    private void updateCheck(FamilyDeviceUpDTO request) {
        FamilyDeviceDO deviceDO = super.getById(request.getId());
        if (request.getName().equals(deviceDO.getName())) {
            return;
        }
        int count = this.baseMapper.existParam(request.getName(), null, deviceDO.getFamilyId(), null);
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
        }
    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {
        FamilyDeviceDO deviceDO = super.getById(request.getId());
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
        //发送同步消息
        sendDeviceSyncMessage(deviceDO.getFamilyId());
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
        FamilyDeviceDO deviceDO = super.getById(deviceId);
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
        FamilyDeviceDO deviceDO = super.getById(deviceId);
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
        FamilyDeviceDO deviceDO = super.getById(deviceId);
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
        FamilyDeviceDO deviceDO = super.getById(deviceId);
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
            if (!Objects.isNull(productCode) && !Objects.isNull(attributeCode) && !Objects.isNull(value)) {
                AttributePrecisionQryDTO attributePrecisionQryDTO = new AttributePrecisionQryDTO();
                attributePrecisionQryDTO.setProductCode(productCode);
                attributePrecisionQryDTO.setCode(attributeCode);
                List<AttributePrecisionDTO> attributePrecision = productAttributeErrorService.getAttributePrecision(attributePrecisionQryDTO);
                if (!Objects.isNull(attributePrecision) && !CollectionUtil.isEmpty(attributePrecision)) {
                    AttributePrecisionDTO attributePrecisionDTO = attributePrecision.get(0);
                    return PrecisionEnum.getInstByType(attributePrecisionDTO.getPrecision()).parse(value);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return value;
    }


    @Override
    public void sendCommand(FamilyDeviceDO familyDeviceDO, List<ScreenDeviceAttributeDTO> data) {
        String familyId = familyDeviceDO.getFamilyId();
        log.info("获取家庭信息, 家庭ID为:{}", familyId);
        HomeAutoFamilyDO homeAutoFamilyDO = familyService.getById(familyId);

        log.info("获取家庭Master网关/大屏, 家庭ID为:{}", familyId);
        FamilyTerminalDO familyMasterTerminal = familyTerminalService.getMasterTerminal(familyId);

        String productId = familyDeviceDO.getProductId();
        log.info("获取设备产品信息,设备ID为:{}", productId);
        HomeAutoProduct homeAutoProduct = productService.getById(productId);

        log.info("指令信息获取完毕, 准备点火发射");
        AdapterDeviceControlDTO adapterDeviceControlDTO = new AdapterDeviceControlDTO();
        adapterDeviceControlDTO.setFamilyId(familyId);
        adapterDeviceControlDTO.setFamilyCode(homeAutoFamilyDO.getCode());
        adapterDeviceControlDTO.setTerminalType(familyMasterTerminal.getType());
        adapterDeviceControlDTO.setTerminalMac(familyMasterTerminal.getMac());
        adapterDeviceControlDTO.setTime(System.currentTimeMillis());
        adapterDeviceControlDTO.setProductCode(homeAutoProduct.getCode());
        adapterDeviceControlDTO.setDeviceSn(familyDeviceDO.getSn());
        adapterDeviceControlDTO.setData(data);
        AdapterDeviceControlAckDTO adapterDeviceControlAckDTO = appService.deviceWriteControl(adapterDeviceControlDTO);
        if (Objects.isNull(adapterDeviceControlAckDTO)) {
            throw new BusinessException("设备无响应,操作失败");
        } else if (!Objects.equals(adapterDeviceControlAckDTO.getCode(), 200)) {
            throw new BusinessException(adapterDeviceControlAckDTO.getMessage());
        }
    }

    @Override
    public FamilyDeviceDO getSensorDevice(String familyId, CategoryEnum... categoryEnums) {
        // 先将catetoryEnums的type转成字符串列表
        List<String> categories = Arrays.stream(categoryEnums).map(CategoryEnum::getType).map(Functions.toStringFunction()::apply).collect(Collectors.toList());
        QueryWrapper<HomeAutoCategory> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.in("code", categories);
        List<HomeAutoCategory> homeAutoCategoryList = categoryService.list(categoryQueryWrapper);

        // 将查询到的类别id转成字符串列表
        List<String> categoryIds = homeAutoCategoryList.stream().map(HomeAutoCategory::getId).collect(Collectors.toList());

        QueryWrapper<FamilyDeviceDO> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.in("category_id", categoryIds);
        deviceQueryWrapper.eq("family_id", familyId);
        return getOne(deviceQueryWrapper);
    }

    @Override
    public List<ProductAttributeDO> getDeviceAttributes(String deviceId) {
        FamilyDeviceDO familyDeviceDO = super.getById(deviceId);
        String productId = familyDeviceDO.getProductId();
        return productService.getAttributesByProductId(productId);
    }

    @Override
    public List<FamilyDeviceDO> listDeviceByFamilyIdAndNature(String familyId, DeviceNatureEnum deviceNatureEnum) {
        List<HomeAutoProduct> homeAutoProductList = productService.listProductByNature(deviceNatureEnum);
        List<String> productIdList = homeAutoProductList.stream().map(HomeAutoProduct::getId).collect(Collectors.toList());

        QueryWrapper<FamilyDeviceDO> familyDeviceDOQueryWrapper = new QueryWrapper<>();
        familyDeviceDOQueryWrapper.eq("family_id", familyId);
        familyDeviceDOQueryWrapper.in("product_id", productIdList);
        familyDeviceDOQueryWrapper.orderByAsc("create_time");
        return list(familyDeviceDOQueryWrapper);
    }

    @Override
    public List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO> getFamilyDeviceWithIndex(List<FamilyDeviceDO> familyDeviceDOList, List<FamilyCommonDeviceDO> familyCommonDeviceDOList, boolean commonUse) {
        // 常用设备业务对象列表
        List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO> familyDeviceBOListForCommon = new LinkedList<>();

        // 不常用设备业务对象列表
        List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO> familyDeviceBOListForUnCommon = new LinkedList<>();

        // 遍历所有设备, 筛选出常用设备和不常用设备
        for (FamilyDeviceDO familyDeviceDO : familyDeviceDOList) {
            com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO familyDeviceBO = getDeviceDetailById(familyDeviceDO.getId());
            familyDeviceBO.setDevicePosition(String.format("%sF-%s", familyDeviceBO.getFloorNum(), familyDeviceBO.getRoomName()));

            boolean isCommonScene = false;
            for (FamilyCommonDeviceDO familyCommonDeviceDO : familyCommonDeviceDOList) {
                if (Objects.equals(familyCommonDeviceDO.getDeviceId(), familyDeviceDO.getId())) {
                    familyDeviceBO.setDeviceIndex(familyCommonDeviceDO.getSortNo());
                    familyDeviceBOListForCommon.add(familyDeviceBO);
                    isCommonScene = true;
                    break;
                }
            }

            if (!isCommonScene) {
                familyDeviceBOListForUnCommon.add(familyDeviceBO);
            }
        }

        return commonUse ? familyDeviceBOListForCommon : familyDeviceBOListForUnCommon;
    }

    @Override
    public DeviceBaseInfoDTO getDeviceInfo(String familyId, String deviceSn) {
        return this.baseMapper.getDeviceInfo(familyId, deviceSn);
    }

    @Override
    public String getFamilyAlarm(String familyId) {
        return this.baseMapper.getFamilyAlarm(familyId);
    }

}
