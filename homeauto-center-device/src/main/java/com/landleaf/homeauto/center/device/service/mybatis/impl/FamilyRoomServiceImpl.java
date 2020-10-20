package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.config.ImagePathConfig;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.mapper.FamilyRoomMapper;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 家庭房间表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilyRoomServiceImpl extends ServiceImpl<FamilyRoomMapper, FamilyRoomDO> implements IFamilyRoomService {

    @Autowired
    private FamilyRoomMapper familyRoomMapper;

    @Autowired
    private IFamilyFloorService familyFloorService;

    @Autowired
    private IFamilyDeviceService familyDeviceService;

    @Autowired
    private IHomeAutoCategoryService homeAutoCategoryService;

    @Autowired
    private IHomeAutoProductService productService;

    @Autowired
    private IFamilySceneHvacConfigService familySceneHvacConfigService;

    @Autowired
    private IFamilySceneHvacConfigActionService familySceneHvacConfigActionService;

    @Autowired
    private IFamilySceneHvacConfigActionPanelService familySceneHvacConfigActionPanelService;

    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IFamilySceneService iFamilySceneService;
    @Autowired
    private IAppService iAppService;

    @Autowired
    private ImagePathConfig imagePathConfig;

    @Override
    public List<CountBO> getCountByFamilyIds(List<String> familyIds) {
        List<CountBO> countBOS = this.baseMapper.getCountByFamilyIds(familyIds);
        if (CollectionUtils.isEmpty(countBOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return countBOS;
    }

    @Override
    public List<FamilyRoomDO> getRoom(String familyId) {
        QueryWrapper<FamilyRoomDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        return list(queryWrapper);
    }

    @Override
    public List<FamilyRoomDO> getRoomExcludeWhole(String familyId) {
        QueryWrapper<FamilyRoomDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        queryWrapper.ne("type", RoomTypeEnum.WHOLE.getType());
        return list(queryWrapper);
    }

    @Override
    public void updateRoomName(FamilyUpdateVO request) {
        FamilyRoomDO roomDO = BeanUtil.mapperBean(request, FamilyRoomDO.class);
        updateById(roomDO);
    }

    @Override
    public List<FamilyRoomDO> getHvacSceneRoomList(String sceneId) {
        // 场景配置
        QueryWrapper<FamilySceneHvacConfig> configQueryWrapper = new QueryWrapper<>();
        configQueryWrapper.eq("scene_id", sceneId);
        FamilySceneHvacConfig familySceneHvacConfig = familySceneHvacConfigService.getOne(configQueryWrapper, true);

        // 场景模式配置
        QueryWrapper<FamilySceneHvacConfigAction> configActionQueryWrapper = new QueryWrapper<>();
        configActionQueryWrapper.eq("hvac_config_id", familySceneHvacConfig.getId());
        FamilySceneHvacConfigAction familySceneHvacConfigAction = familySceneHvacConfigActionService.getOne(configActionQueryWrapper, true);

        // 场景模式面板配置
        QueryWrapper<FamilySceneHvacConfigActionPanel> configActionPanelQueryWrapper = new QueryWrapper<>();
        configActionPanelQueryWrapper.eq("hvac_action_id", familySceneHvacConfigAction.getId());
        List<FamilySceneHvacConfigActionPanel> familySceneHvacConfigActionPanelList = familySceneHvacConfigActionPanelService.list(configActionPanelQueryWrapper);
        List<String> deviceSnList = familySceneHvacConfigActionPanelList.stream().map(FamilySceneHvacConfigActionPanel::getDeviceSn).collect(Collectors.toList());

        // 设备缩在的房间
        QueryWrapper<FamilyDeviceDO> familyDeviceQueryWrapper = new QueryWrapper<>();
        familyDeviceQueryWrapper.in("sn", deviceSnList);
        List<FamilyDeviceDO> familyDeviceDOList = familyDeviceService.list(familyDeviceQueryWrapper);
        List<String> roomIdList = familyDeviceDOList.stream().map(FamilyDeviceDO::getRoomId).collect(Collectors.toList());

        return CollectionUtil.list(true, listByIds(roomIdList));
    }

    @Override
    public void add(FamilyRoomDTO request) {
        addCheck(request);
        FamilyRoomDO roomDO = BeanUtil.mapperBean(request, FamilyRoomDO.class);
        int count = count(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getFloorId, request.getFloorId()));
        roomDO.setSortNo(count + 1);
        RoomTypeEnum roomTypeEnum = RoomTypeEnum.getInstByType(request.getType());
        if (roomTypeEnum != null) {
            roomDO.setIcon(imagePathConfig.getContext().concat(roomTypeEnum.getIcon()));
            roomDO.setImgIcon(imagePathConfig.getContext().concat(roomTypeEnum.getImgIcon()));
        }
        save(roomDO);
        //发送同步消息
        sendRoomSyncMessage(request.getFamilyId());
    }

    /**
     * 发送家庭设备同步信息
     *
     * @param familyId
     */
    private void sendRoomSyncMessage(String familyId) {
        AdapterConfigUpdateDTO configUpdateDTO = iFamilySceneService.getSyncConfigInfo(familyId);
        configUpdateDTO.setUpdateType(ContactScreenConfigUpdateTypeEnum.FLOOR_ROOM_DEVICE.code);
        configUpdateDTO.setFamilyId(familyId);
        iAppService.configUpdateConfig(configUpdateDTO);
    }

    private void addCheck(FamilyRoomDTO request) {
        int count = count(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getName, request.getName()).eq(FamilyRoomDO::getFloorId, request.getFloorId()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "房间名称已存在");
        }
    }

    @Override
    public void update(FamilyRoomDTO request) {
        updateCheck(request);
        FamilyRoomDO roomDO = BeanUtil.mapperBean(request, FamilyRoomDO.class);
        RoomTypeEnum roomTypeEnum = RoomTypeEnum.getInstByType(request.getType());
        if (roomTypeEnum != null) {
            roomDO.setIcon(imagePathConfig.getContext().concat(roomTypeEnum.getIcon()));
            roomDO.setImgIcon(imagePathConfig.getContext().concat(roomTypeEnum.getImgIcon()));
        }
        updateById(roomDO);
        //发送同步消息
        sendRoomSyncMessage(roomDO.getFamilyId());
    }

    private void updateCheck(FamilyRoomDTO request) {
        FamilyRoomDO roomDO = getById(request.getId());
        if (request.getName().equals(roomDO.getName())) {
            return;
        }
        addCheck(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        int count = familyDeviceService.count(new LambdaQueryWrapper<FamilyDeviceDO>().eq(FamilyDeviceDO::getRoomId, request.getId()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "房间下有设备已存在");
        }
        FamilyRoomDO roomDO = getById(request.getId());
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoGT(roomDO.getFloorId(), roomDO.getSortNo());
        if (!CollectionUtils.isEmpty(sortNoBOS)) {
            sortNoBOS.forEach(obj -> {
                obj.setSortNo(obj.getSortNo() - 1);
            });
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
        removeById(request.getId());
        //发送同步消息
        sendRoomSyncMessage(roomDO.getFamilyId());
    }

    @Override
    public void moveUp(String roomId) {
        FamilyRoomDO roomDO = getById(roomId);
        int sortNo = roomDO.getSortNo();
        if (sortNo == 1) {
            return;
        }
        String updateId = this.getBaseMapper().getIdBySort(sortNo - 1, roomDO.getFloorId());
        if (StringUtil.isBlank(updateId)) {
            return;
        }
        List<SortNoBO> sortNoBOS = Lists.newArrayListWithCapacity(2);
        sortNoBOS.add(SortNoBO.builder().id(roomId).sortNo(sortNo - 1).build());
        sortNoBOS.add(SortNoBO.builder().id(updateId).sortNo(sortNo).build());
        this.baseMapper.updateBatchSort(sortNoBOS);
    }

    @Override
    public void moveDown(String roomId) {
        FamilyRoomDO roomDO = getById(roomId);
        int sortNo = roomDO.getSortNo();
        String updateId = this.getBaseMapper().getIdBySort(sortNo + 1, roomDO.getFloorId());
        if (StringUtil.isBlank(updateId)) {
            return;
        }
        List<SortNoBO> sortNoBOS = Lists.newArrayListWithCapacity(2);
        sortNoBOS.add(SortNoBO.builder().id(roomId).sortNo(sortNo + 1).build());
        sortNoBOS.add(SortNoBO.builder().id(updateId).sortNo(sortNo).build());
        this.baseMapper.updateBatchSort(sortNoBOS);
    }

    @Override
    public void moveTop(String roomId) {
        FamilyRoomDO roomDO = getById(roomId);
        int sortNo = roomDO.getSortNo();
        if (sortNo == 1) {
            return;
        }
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoLT(roomDO.getFloorId(), sortNo);
        if (!CollectionUtils.isEmpty(sortNoBOS)) {
            sortNoBOS.forEach(obj -> {
                obj.setSortNo(obj.getSortNo() + 1);
            });
            SortNoBO sortNoBO = SortNoBO.builder().id(roomDO.getId()).sortNo(1).build();
            sortNoBOS.add(sortNoBO);
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
    }

    @Override
    public void moveEnd(String roomId) {
        FamilyRoomDO roomDO = getById(roomId);
        int sortNo = roomDO.getSortNo();
        int count = count(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getFloorId, roomDO.getFloorId()));
        if (count == sortNo) {
            return;
        }
        List<SortNoBO> sortNoBOS = this.baseMapper.getListSortNoBoGT(roomDO.getFloorId(), sortNo);
        if (!CollectionUtils.isEmpty(sortNoBOS)) {
            sortNoBOS.forEach(obj -> {
                obj.setSortNo(obj.getSortNo() - 1);
            });
            SortNoBO sortNoBO = SortNoBO.builder().id(roomDO.getId()).sortNo(count).build();
            sortNoBOS.add(sortNoBO);
            this.baseMapper.updateBatchSort(sortNoBOS);
        }
    }

    @Override
    public boolean existPanel(String roomId) {
        QueryWrapper<HomeAutoCategory> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("code", Objects.toString(CategoryEnum.PANEL_TEMP.getType()));
        HomeAutoCategory category = homeAutoCategoryService.getOne(categoryQueryWrapper);

        QueryWrapper<HomeAutoProduct> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.eq("category_id", category.getId());
        List<HomeAutoProduct> productList = productService.list(productQueryWrapper);
        List<String> idList = productList.stream().map(HomeAutoProduct::getId).collect(Collectors.toList());

        QueryWrapper<FamilyDeviceDO> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("room_id", roomId);
        deviceQueryWrapper.in("product_id", idList);
        List<FamilyDeviceDO> deviceDOList = familyDeviceService.list(deviceQueryWrapper);
        return !CollectionUtils.isEmpty(deviceDOList);
    }

    @Override
    public List<String> getListNameByFamilyId(String familyId) {
        return this.baseMapper.getListNameByFamilyId(familyId);
    }

    @Override
    public List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO> listFamilyRoom(String familyId) {
        HomeAutoFamilyDO homeAutoFamilyDO = familyService.getById(familyId);
        QueryWrapper<FamilyRoomDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", familyId);
        List<FamilyRoomDO> familyRoomDOList = list(queryWrapper);

        List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO> familyRoomBOList = new LinkedList<>();
        for (FamilyRoomDO familyRoomDO : familyRoomDOList) {
            com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO familyRoomBO = new com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO();
            // 1. 家庭信息
            familyRoomBO.setFamilyId(homeAutoFamilyDO.getId());
            familyRoomBO.setFamilyCode(homeAutoFamilyDO.getCode());
            familyRoomBO.setFamilyName(homeAutoFamilyDO.getName());

            // 2. 楼层信息
            FamilyFloorDO familyFloorDO = familyFloorService.getById(familyRoomDO.getFloorId());
            familyRoomBO.setFloorId(familyFloorDO.getId());
            familyRoomBO.setFloorName(familyFloorDO.getName());
            familyRoomBO.setFloorNum(familyFloorDO.getFloor());

            // 3. 房间信息
            familyRoomBO.setRoomId(familyRoomDO.getId());
            familyRoomBO.setRoomName(familyRoomDO.getName());
            familyRoomBO.setRoomIcon1(familyRoomDO.getIcon());
            familyRoomBO.setRoomIcon2(familyRoomDO.getImgIcon());
            familyRoomBO.setRoomTypeEnum(RoomTypeEnum.getInstByType(familyRoomDO.getType()));

            familyRoomBOList.add(familyRoomBO);
        }

        return familyRoomBOList;
    }

    @Override
    public List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO> listFloorRoom(String floorId) {
        QueryWrapper<FamilyRoomDO> familyRoomDOQueryWrapper = new QueryWrapper<>();
        familyRoomDOQueryWrapper.eq("floor_id", floorId);
        List<FamilyRoomDO> familyRoomDOList = list(familyRoomDOQueryWrapper);

        List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO> familyRoomBOList = new LinkedList<>();
        for (FamilyRoomDO familyRoomDO : familyRoomDOList) {
            com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO familyRoomBO = new com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO();
            familyRoomBO.setRoomId(familyRoomDO.getId());
            familyRoomBO.setRoomName(familyRoomDO.getName());
            familyRoomBO.setRoomIcon1(familyRoomDO.getIcon());
            familyRoomBO.setRoomIcon2(familyRoomDO.getImgIcon());
            familyRoomBOList.add(familyRoomBO);
        }
        return familyRoomBOList;
    }
}
