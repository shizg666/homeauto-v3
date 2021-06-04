package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateSceneActionConfig;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.center.device.model.mapper.TemplateDeviceMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttrInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceBaseInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.device.PanelBO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.FloorRoomBaseVO;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.redis.RedisUtils;
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
 * 户型设备表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Service
public class HouseTemplateDeviceServiceImpl extends ServiceImpl<TemplateDeviceMapper, TemplateDeviceDO> implements IHouseTemplateDeviceService {

    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;
    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHomeAutoFamilyService familyService;
    @Autowired
    private IHomeAutoProductService productService;
    @Autowired
    IDicTagService iDicTagService;
    @Autowired
    private IContactScreenService iContactScreenService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private IdService idService;
    @Autowired
    private ITemplateSceneActionConfigService iTemplateSceneActionConfigService;

    @Autowired
    private ITemplateOperateService iTemplateOperateService;

    public static final String FAMILY_NUM = "99998";
    public static final String FAMILY_USER_NUM = "99997";
    //设备总数
    public static final String DEVICE_NUM = "99996";
    //大屏在线数
//    public static final String SCREEN_ONLINE_NUM = "99995";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TemplateDeviceDO add(TemplateDeviceAddDTO request) {
        Boolean gateWayFalg = iProjectHouseTemplateService.isGateWayProject(request.getHouseTemplateId());
        addCheck(request, gateWayFalg);
        TemplateDeviceDO deviceDO = BeanUtil.mapperBean(request, TemplateDeviceDO.class);
        HomeAutoProduct product = iHomeAutoProductService.getById(request.getProductId());
        deviceDO.setCategoryCode(product.getCategoryCode());
        deviceDO.setProductCode(product.getCode());
        if (!gateWayFalg) {
            //非网关项目自动生成设备号
            deviceDO.setSn(String.valueOf(idService.getSegmentId(CommonConst.HOMEAUTO_DEVICE_SN)));
        }
        save(deviceDO);
        iTemplateOperateService.sendEvent(TemplateOperateEvent.builder().templateId(request.getHouseTemplateId()).typeEnum(ContactScreenConfigUpdateTypeEnum.FLOOR_ROOM_DEVICE).build());
        return deviceDO;
    }

    private void addCheck(TemplateDeviceAddDTO request, Boolean gateWayFalg) {
        checkName(request.getName(), request.getRoomId());
        //有网管的设备编号是手动输入的需要校验
        if (gateWayFalg) {
            if (StringUtil.isEmpty(request.getSn())) {
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备号不能为空");
            }
            checkSn(request.getSn(), request.getHouseTemplateId());
        }

    }

    private void checkSn(String sn, Long houseTemplateId) {
        CheckDeviceParamBO param = new CheckDeviceParamBO();
        param.setHouseTemplateId(houseTemplateId);
        param.setSn(sn);
        int countSn = this.baseMapper.existParamCheck(param);
        if (countSn > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备号已存在");
        }
    }

    private void checkName(String name, Long roomId) {
        CheckDeviceParamBO param = new CheckDeviceParamBO();
        param.setRoomId(roomId);
        param.setName(name);
        int count = this.baseMapper.existParamCheck(param);
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备名称已存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(TemplateDeviceAddDTO request) {
        updateCheck(request);
        TemplateDeviceDO deviceDO = BeanUtil.mapperBean(request, TemplateDeviceDO.class);
        deviceDO.setCategoryCode(iHomeAutoProductService.getCategoryCodeById(request.getProductId()));
        deviceDO.setProductCode(iHomeAutoProductService.getProductCodeById(request.getProductId()));
        updateById(deviceDO);
        iTemplateOperateService.sendEvent(TemplateOperateEvent.builder().templateId(request.getHouseTemplateId()).typeEnum(ContactScreenConfigUpdateTypeEnum.FLOOR_ROOM_DEVICE).build());
    }


    private void updateCheck(TemplateDeviceAddDTO request) {
        TemplateDeviceDO deviceDO = getById(request.getId());
        if (!request.getName().equals(deviceDO.getName())) {
            checkName(request.getName(), request.getRoomId());
        }
        //有网管的设备编号是手动输入的需要校验
        if (iProjectHouseTemplateService.isGateWayProject(request.getHouseTemplateId())) {
            if (StringUtil.isEmpty(request.getSn())) {
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "设备号不能为空");
            }
            if (!request.getSn().equals(deviceDO.getSn())) {
                checkSn(request.getSn(), request.getHouseTemplateId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        TemplateDeviceDO deviceDO = getById(request.getId());
        removeById(request.getId());
        iTemplateSceneActionConfigService.remove(new LambdaQueryWrapper<TemplateSceneActionConfig>().eq(TemplateSceneActionConfig::getDeviceId, request.getId()));

        iTemplateOperateService.sendEvent(TemplateOperateEvent.builder().templateId(deviceDO.getHouseTemplateId()).typeEnum(ContactScreenConfigUpdateTypeEnum.FLOOR_ROOM_DEVICE).build());
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
    public List<String> getListPanel(String templateId) {
        return this.baseMapper.getListPanel(templateId);
    }

    @Override
    public List<SelectedVO> getListPanelSelects(String templateId) {
        List<PanelBO> panelBOS = this.baseMapper.getListPanelSelects(templateId);
        if (CollectionUtils.isEmpty(panelBOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<SelectedVO> selectedVOS = panelBOS.stream().map(panel -> {
            return new SelectedVO(panel.getFloorName().concat(panel.getRoomName()), panel.getSn());
        }).collect(Collectors.toList());
        return selectedVOS;
    }

    @Override
    public List<SceneHvacDeviceVO> getListHvacInfo(String templateId) {
        return this.baseMapper.getListHvacInfo(templateId);
    }

    @Override
    public AttributeScopeVO getPanelSettingTemperature(String templateId) {
        return this.baseMapper.getPanelSettingTemperature(templateId);
    }


    @Override
    public List<SceneDeviceVO> getListDeviceScene(Long templateId) {

        List<SceneDeviceVO> floorVOS = this.baseMapper.getListDevice(templateId);
        if (CollectionUtils.isEmpty(floorVOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<SceneDeviceVO> result = Lists.newArrayListWithExpectedSize(floorVOS.size());
        List<Long> productIds = floorVOS.stream().map(SceneDeviceVO::getProductId).collect(Collectors.toList());
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceAttributeInfo(Lists.newArrayList(productIds));
        if (CollectionUtils.isEmpty(attributes)) {
            return floorVOS;
        }
        Map<Long, List<SceneDeviceAttributeVO>> map = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));
        floorVOS.forEach(obj -> {
            if (map.containsKey(obj.getProductId())) {
                obj.setAttributes(map.get(obj.getProductId()));
                result.add(obj);
            }
        });
        return result;
    }

    @Override
    public HouseFloorRoomListVO getListFloorRooms(Long templateId) {
        HouseFloorRoomListVO result = new HouseFloorRoomListVO();
        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId, templateId).select(TemplateRoomDO::getName, TemplateRoomDO::getFloor));
        if (CollectionUtils.isEmpty(roomDOS)) {
            return result;
        }
        List<FloorRoomBaseVO> rooms = roomDOS.stream().map(o -> {
            return FloorRoomBaseVO.builder().floor(o.getFloor()).name(o.getName()).build();
        }).collect(Collectors.toList());
        result.setRooms(rooms);
        Set<String> floors = roomDOS.stream().map(o -> o.getFloor().concat("楼")).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(floors)) {
            return result;
        }
        result.setFloors(Lists.newArrayList(floors));
        return result;
    }

    @Override
    public boolean existByProductId(Long productId) {
        int count = this.count(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getProductId, productId).last("limit 1"));
        return count > 0;
    }

    /**
     * 根据户型查询设备
     *
     * @param templateId 户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO>
     * @author wenyilu
     * @date 2021/1/5 15:58
     */
    @Override
    public List<TemplateDeviceDO> getTemplateDevices(Long templateId) {
        QueryWrapper<TemplateDeviceDO> familyDeviceDOQueryWrapper = new QueryWrapper<>();
        familyDeviceDOQueryWrapper.eq("house_template_id", templateId);
        familyDeviceDOQueryWrapper.orderByAsc("create_time");
        return list(familyDeviceDOQueryWrapper);
    }

    /**
     * 获取带索引的设备信息
     *
     * @param familyId
     * @param templateId
     * @param templateDevices
     * @param familyCommonDeviceDOList
     * @param commonUse
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date 2021/1/5 16:02
     */
    @Override
    public List<FamilyDeviceBO> getFamilyDeviceWithIndex(Long familyId, Long templateId, List<TemplateDeviceDO> templateDevices, List<FamilyCommonDeviceDO> familyCommonDeviceDOList, boolean commonUse) {
        // 常用设备业务对象列表
        List<FamilyDeviceBO> familyDeviceBOListForCommon = new LinkedList<>();

        // 不常用设备业务对象列表
        List<FamilyDeviceBO> familyDeviceBOListForUnCommon = new LinkedList<>();

        // 遍历所有设备, 筛选出常用设备和不常用设备
        for (TemplateDeviceDO templateDeviceDO : templateDevices) {
            FamilyDeviceBO familyDeviceBO = detailDeviceById(templateDeviceDO.getId(), familyId, templateId);
            familyDeviceBO.setDevicePosition(String.format("%sF-%s", familyDeviceBO.getFloorNum(), familyDeviceBO.getRoomName()));
            familyDeviceBO.setDeviceSn(templateDeviceDO.getSn());
            boolean isCommonScene = false;
            if (!CollectionUtils.isEmpty(familyCommonDeviceDOList)) {
                for (FamilyCommonDeviceDO familyCommonDeviceDO : familyCommonDeviceDOList) {
                    if (Objects.equals(familyCommonDeviceDO.getDeviceId(), templateDeviceDO.getId())) {
                        familyDeviceBO.setDeviceIndex(familyCommonDeviceDO.getSortNo());
                        familyDeviceBOListForCommon.add(familyDeviceBO);
                        isCommonScene = true;
                        break;
                    }
                }
            }

            if (!isCommonScene) {
                familyDeviceBOListForUnCommon.add(familyDeviceBO);
            }
        }

        return commonUse ? familyDeviceBOListForCommon : familyDeviceBOListForUnCommon;
    }

    /**
     * 获取家庭某个设备信息详情
     *
     * @param deviceId
     * @return com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO
     * @author wenyilu
     * @date 2021/1/5 16:07
     */
    @Override
    public FamilyDeviceBO detailDeviceById(Long deviceId, Long familyId, Long templateId) {
        List<FamilyDeviceBO> familyDeviceBOList = listDeviceDetailByIds(Collections.singletonList(deviceId), familyId, templateId);
        if (!CollectionUtil.isEmpty(familyDeviceBOList)) {
            return familyDeviceBOList.get(0);
        }
        return null;
    }

    /**
     * 获取家庭某个房间下设备列表详情
     *
     * @param familyId   家庭ID
     * @param roomId     房间ID
     * @param templateId 户型ID
     * @param showApp    设备在app是否展示（0：否，1：是）
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date 2021/1/6 9:38
     */
    @Override
    public List<FamilyDeviceBO> getFamilyRoomDevices(Long familyId, Long roomId, Long templateId, Integer showApp) {
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_template_id", templateId);
        if(roomId!=null){
            queryWrapper.eq("room_id", roomId);
        }
        List<TemplateDeviceDO> deviceDOS = list(queryWrapper);
        if (CollectionUtil.isEmpty(deviceDOS)) {
            return Lists.newArrayList();
        }
        return listDeviceDetailByIds(deviceDOS.stream().map(i -> {
            return i.getId();
        }).collect(Collectors.toList()), familyId, templateId);
    }

    /**
     * 根据户型统计设备数量
     *
     * @param templateIds 户型ID集合
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.project.CountBO>
     * @author wenyilu
     * @date 2021/1/6 10:02
     */
    @Override
    public List<CountBO> getCountByTemplateIds(List<Long> templateIds) {
        List<CountBO> countBOS = this.baseMapper.getCountByTemplateIds(templateIds);
        if (CollectionUtils.isEmpty(countBOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return countBOS;
    }

    @Override
    public List<TemplateDevicePageVO> getListByTemplateId(Long templateId) {
        return this.baseMapper.getListByTemplateId(templateId);
    }

    @Override
    public List<TemplateDevicePageVO> getListByRoomId(Long roomId) {
        return this.baseMapper.getListByRoomId(roomId);
    }

    @Override
    public TemplateDeviceDO getDeviceByTemplateAndCode(Long templateId, String deviceSn) {
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_template_id", templateId);
        queryWrapper.eq("sn", deviceSn);
        return getOne(queryWrapper);
    }

    @Override
    public TemplateDeviceDetailVO detailById(String deviceId) {
//        TemplateDeviceDetailVO detailVO = this.baseMapper.detailById(deviceId);
//        TemplateRoomDO roomDO = iHouseTemplateRoomService.getById(detailVO.getRoomId());
////        detailVO.setControlArea(roomDO.getCode().concat("-").concat(roomDO.getName()));
//        DicTagPO dic = iDicTagService.getOne(new LambdaQueryWrapper<DicTagPO>().eq(DicTagPO::getDicCode, "app_ui").eq(DicTagPO::getValue, detailVO.getUiCode()).select(DicTagPO::getName));
//        detailVO.setUiCode(dic == null ? "" : dic.getName());
//        List<DeviceAttrInfo> attrInfos = iDeviceAttrInfoService.list(new LambdaQueryWrapper<DeviceAttrInfo>().eq(DeviceAttrInfo::getDeviceId, deviceId).select(DeviceAttrInfo::getCode, DeviceAttrInfo::getName));
//        if (!CollectionUtils.isEmpty(attrInfos)) {
//            List<TemplateDeviceAttrVO> attrs = BeanUtil.mapperList(attrInfos, TemplateDeviceAttrVO.class);
//            detailVO.setAttrs(attrs);
//        }
        return null;
    }

    @Override
    public List<SelectedVO> getSelectByTempalteId(String tempalteId) {
        List<TemplateDeviceDO> deviceDOS = list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId, tempalteId).select(TemplateDeviceDO::getId, TemplateDeviceDO::getName));
        if (CollectionUtils.isEmpty(deviceDOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<SelectedVO> selectedVOS = deviceDOS.stream().map(device -> {
            return new SelectedVO(device.getName(), String.valueOf(device.getId()));
        }).collect(Collectors.toList());
        return selectedVOS;
    }

    @Override
    public List<SelectedVO> getSelectDeviceError(String tempalteId) {
        List<DeviceBaseInfoDTO> deviceBaseInfoDTOS = this.baseMapper.getSelectDeviceError(tempalteId);
        if (CollectionUtils.isEmpty(deviceBaseInfoDTOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        Set<String> dataSet = Sets.newHashSet();
        deviceBaseInfoDTOS.forEach(ojb -> {
            dataSet.add(ojb.getProductName().concat("-").concat(ojb.getRoomName()));
        });
        List<SelectedVO> data = dataSet.stream().map(obj -> {
            return new SelectedVO(obj, obj);
        }).collect(Collectors.toList());
        return data;
    }

    @Override
    public TemplateDeviceDO getDeviceByTemplateAndAttrCode(String templateId, String attrCode) {
        return this.baseMapper.getDeviceByTemplateAndAttrCode(templateId, attrCode);
    }

    @Override
    public BasePageVO<TemplateDevicePageVO> getListPageByTemplateId(String templateId, Integer pageNum, Integer pageSize) {
//        PageHelper.startPage(pageNum, pageSize, true);
//        List<TemplateDevicePageVO> data = this.baseMapper.getListByTemplateId(templateId);
//        if (CollectionUtils.isEmpty(data)) {
//            PageInfo pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
//            return BeanUtil.mapperBean(pageInfo, BasePageVO.class);
//        }
//        PageInfo pageInfo = new PageInfo(data);
//        BasePageVO<TemplateDevicePageVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
//        return resultData;
        return null;
    }

    @Override
    public List<HomeDeviceStatistics> getDeviceStatistics(HomeDeviceStatisticsQry request) {
//
        return null;
    }

    @Override
    public void errorAttrInfoCache(DeviceOperateEvent event) {
//        //新增设备处理
//        if (1 == event.getType()) {
//            iDeviceAttrInfoService.errorAttrInfoCacheAdd(event);
//        } else if (2 == event.getType()) {
//            //修改
//            iDeviceAttrInfoService.errorAttrInfoCacheDelete(event);
//        } else if (3 == event.getType()) {
//            //删除
//            iDeviceAttrInfoService.errorAttrInfoCacheDelete(event);
//        }
    }

//    @Override
//    public TemplateDeviceCacheDTO getBaseDeviceCache(String deviceId) {
//        String jsonObject = (String) redisUtils.get(String.format(RedisCacheConst.DEVICE_BASE_INFO,deviceId));
//        if (!StringUtil.isEmpty(jsonObject)){
//            TemplateDeviceCacheDTO infoDTO = JSON.parseObject(jsonObject, TemplateDeviceCacheDTO.class);
//            return infoDTO;
//        }
//        TemplateDeviceDO deviceDO = getById(deviceId);
//        TemplateDeviceCacheDTO result = saveDeviceCache(deviceDO);
//        return result;
//    }

    @Override
    public DeviceAttrInfoCacheBO getDeviceAttrCache(String templateId, String attrCode) {
//        String jsonObject = (String) redisUtils.get(String.format(RedisCacheConst.DEVICE_ATTR_INFO,
//                templateId, attrCode));
//        if (!StringUtil.isEmpty(jsonObject)) {
//            DeviceAttrInfoCacheBO infoDTO = JSON.parseObject(jsonObject, DeviceAttrInfoCacheBO.class);
//            return infoDTO;
//        }
//        DeviceAttrInfoCacheBO infoCacheBO = iDeviceAttrInfoService.getAndSaveAttrInfoCache(templateId, attrCode);
        return null;
    }

    @Override
    public List<TemplateDeviceDO> listByTemplateId(Long templateId) {
        return list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId, templateId));
    }

    @Override
    public List<TotalCountBO> getDeviceNumGroupByRoom(Long templateId) {
        return this.baseMapper.getDeviceNumGroupByRoom(templateId);
    }

    @Override
    public TemplateDeviceDO getDeviceByIdOrDeviceSn(Long houseTemplateId, Long deviceId, String deviceSn) {
        if (deviceId == null && StringUtils.isEmpty(deviceSn)) {
            throw new BusinessException("必須有一个");
        }
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_template_id", houseTemplateId);
        if (deviceId != null) {
            queryWrapper.eq("id", deviceId);
        }
        if (!StringUtils.isEmpty(deviceSn)) {
            queryWrapper.eq("sn", deviceSn);
        }
        return getOne(queryWrapper);
    }

    @Override
    public List<DeviceAttrInfoDTO> getDeviceAttrsInfo(Long deviceId) {
        Long productId = this.getProdcutIdByDeviceId(deviceId);
        List<DeviceAttrInfoDTO> actions = this.baseMapper.getDeviceAttrsInfo(productId);
        if (CollectionUtils.isEmpty(actions)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return actions;
    }

    @Override
    public Long getProdcutIdByDeviceId(Long deviceId) {
        return this.baseMapper.getProdcutIdByDeviceId(deviceId);
    }

    @Override
    public String getProdcutCodeByDeviceId(Long deviceId) {
        return this.baseMapper.getProdcutCodeByDeviceId(deviceId);
    }

    @Override
    public List<CountLongBO> totalGroupByProductIds(List<Long> productIds) {

        return this.baseMapper.totalGroupByProductIds(productIds);
    }

    @Override
    public TemplateDeviceDO getSystemDevice(Long templateId) {
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_template_id", templateId);
        queryWrapper.eq("system_flag", FamilySystemFlagEnum.SYS_DEVICE.getType());
        List<TemplateDeviceDO> list = list(queryWrapper);

        return !CollectionUtils.isEmpty(list) ? list.get(0) : null;
    }

    @Override
    public List<TemplateDeviceDO> getSystemDevices(Long houseTemplateId,Integer... systemFlags) {
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_template_id", houseTemplateId);
        if(systemFlags!=null){
            queryWrapper.in("system_flag",Arrays.asList(systemFlags));
        }
       return list(queryWrapper);

    }

    @Override
    public Integer checkDeviceType(Long templateId) {
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_template_id",templateId);
        List<TemplateDeviceDO> list = list(queryWrapper);
        Integer result = 0;
        if(!CollectionUtils.isEmpty(list)){
            long systemCount = list.stream().filter(i -> i.getSystemFlag() == FamilySystemFlagEnum.SYS_DEVICE.getType()).count();
            result=systemCount>0?result|1:result;
            long normalCount = list.stream().filter(i -> i.getSystemFlag() == FamilySystemFlagEnum.NORMAL_DEVICE.getType()).count();
            result=normalCount>0?result|2:result;
        }
        return result;
    }


    /**
     * 批量获取家庭设备详情信息
     *
     * @param deviceIds  设备IDs
     * @param familyId   家庭ID
     * @param templateId 户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date 2021/1/12 11:15
     */
    @Override
    public List<FamilyDeviceBO> listDeviceDetailByIds(List<Long> deviceIds, Long familyId, Long templateId) {
        List<FamilyDeviceBO> familyDeviceBOList = new LinkedList<>();
        Collection<TemplateDeviceDO> deviceDOS = super.listByIds(deviceIds);
        for (TemplateDeviceDO deviceDO : deviceDOS) {
            FamilyDeviceBO familyDeviceBO = new FamilyDeviceBO();
            // 1. 设备本身的信息
            familyDeviceBO.setDeviceId(String.valueOf(deviceDO.getId()));
            familyDeviceBO.setDeviceName(deviceDO.getName());
            familyDeviceBO.setSystemFlag(deviceDO.getSystemFlag());
            // 2. 家庭信息
            HomeAutoFamilyDO homeAutoFamily = familyService.getById(familyId);
            familyDeviceBO.setFamilyId(String.valueOf(homeAutoFamily.getId()));
            familyDeviceBO.setFamilyCode(homeAutoFamily.getCode());

            // 3.房间信息
            TemplateRoomDO roomDO = iHouseTemplateRoomService.getById(deviceDO.getRoomId());
            familyDeviceBO.setRoomId(String.valueOf(roomDO.getId()));
            familyDeviceBO.setRoomName(roomDO.getName());
            familyDeviceBO.setRoomType(RoomTypeEnum.getInstByType(roomDO.getType()));
            // 4 楼层信息
            familyDeviceBO.setFloorId(roomDO.getFloor());
            familyDeviceBO.setFloorName(roomDO.getFloor());
            familyDeviceBO.setFloorNum(roomDO.getFloor());

            // 5. 产品信息
            HomeAutoProduct homeAutoProduct = productService.getById(deviceDO.getProductId());
//            familyDeviceBO.setProductId(homeAutoProduct.getId());
            familyDeviceBO.setProductCode(homeAutoProduct.getCode());
            familyDeviceBO.setProductIcon(homeAutoProduct.getIcon());
            familyDeviceBO.setProductImage(homeAutoProduct.getIcon2());

            // 6. 品类信息
            familyDeviceBO.setCategoryCode(homeAutoProduct.getCategoryCode());

            // 8. 设备属性列表
            List<ScreenProductAttrBO> deviceAttrInfos = iContactScreenService.getDeviceFunctionAttrsByProductCode(deviceDO.getProductCode());

            List<String> deviceAttributeList = deviceAttrInfos.stream().map(ScreenProductAttrBO::getAttrCode).collect(Collectors.toList());

            familyDeviceBO.setDeviceAttributeList(deviceAttributeList);

            // 9. 添加进列表
            familyDeviceBOList.add(familyDeviceBO);
        }
        return familyDeviceBOList;
    }

}
