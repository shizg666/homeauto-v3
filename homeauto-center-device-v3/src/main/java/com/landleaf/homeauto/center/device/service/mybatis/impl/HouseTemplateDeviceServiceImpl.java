package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.enums.AttrAppFlagEnum;
import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrBit;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrPrecision;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrSelect;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.center.device.model.mapper.TemplateDeviceMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceBaseInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.device.PanelBO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.service.IContactScreenService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.po.device.DicTagPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import net.bytebuddy.asm.Advice;
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
    private IDeviceAttrInfoService iDeviceAttrInfoService;
    @Autowired
    private IHomeAutoProductService productService;
    @Autowired
    private IDeviceAttrBitService iDeviceAttrBitService;
    @Autowired
    private IDeviceAttrPrecisionService iDeviceAttrPrecisionService;
    @Autowired
    private IDeviceAttrSelectService iDeviceAttrSelectService;
    @Autowired IDicTagService iDicTagService;
    @Autowired
    private IContactScreenService iContactScreenService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private IdService idService;

    public static final String FAMILY_NUM = "99998";
    public static final String FAMILY_USER_NUM = "99997";
    //设备总数
    public static final String DEVICE_NUM = "99996";
    //大屏在线数
//    public static final String SCREEN_ONLINE_NUM = "99995";

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TemplateDeviceDO add(TemplateDeviceAddDTO request) {
        addCheck(request);
        TemplateDeviceDO deviceDO = BeanUtil.mapperBean(request, TemplateDeviceDO.class);
        String categoryCode = iHomeAutoProductService.getCategoryCodeById(request.getProductId());
        deviceDO.setCategoryCode(categoryCode);
        deviceDO.setId(idService.getSegmentId());
        save(deviceDO);
        return deviceDO;
    }


    private void deleteDeviceCache(String deviceId) {
        redisUtils.del(String.format(RedisCacheConst.DEVICE_BASE_INFO,
                deviceId));
    }



    private void addCheck(TemplateDeviceAddDTO request) {
        HomeAutoProduct product = iHomeAutoProductService.getById(request.getProductId());
        String categoryCode = product.getCategoryCode();
        //暖通新风 一个家庭至多一个设备
        if (CategoryTypeEnum.HVAC.getType().equals(categoryCode) || CategoryTypeEnum.FRESH_AIR.getType().equals(categoryCode)) {
            CheckDeviceParamBO param = new CheckDeviceParamBO();
            param.setHouseTemplateId(request.getHouseTemplateId());
            param.setCategoryCode(categoryCode);
            int count = this.baseMapper.existParamCheck(param);
            if (count > 0) {
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "暖通新风设备最多一个");
            }
        }
        checkName(request.getName(),request.getRoomId());
        //有网管的设备编号是手动输入的需要校验
        if (iProjectHouseTemplateService.isGateWayProject(request.getHouseTemplateId())){
            checkSn(request.getSn(),request.getHouseTemplateId());
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
        TemplateDeviceDO device = getById(request.getId());
        String categoryCode = iHomeAutoProductService.getCategoryCodeById(request.getProductId());
        deviceDO.setCategoryCode(categoryCode);
        updateById(deviceDO);
    }

    private void removeAttr(String deviceId) {
        iDeviceAttrInfoService.remove(new LambdaQueryWrapper<DeviceAttrInfo>().eq(DeviceAttrInfo::getDeviceId,deviceId));
        iDeviceAttrPrecisionService.remove(new LambdaQueryWrapper<DeviceAttrPrecision>().eq(DeviceAttrPrecision::getDeviceId,deviceId));
        iDeviceAttrSelectService.remove(new LambdaQueryWrapper<DeviceAttrSelect>().eq(DeviceAttrSelect::getDeviceId,deviceId));
        iDeviceAttrBitService.remove(new LambdaQueryWrapper<DeviceAttrBit>().eq(DeviceAttrBit::getDeviceId,deviceId));
    }

    private void updateCheck(TemplateDeviceAddDTO request) {
        TemplateDeviceDO deviceDO = getById(request.getId());
        if (!request.getName().equals(deviceDO.getName())) {
            checkName(request.getName(),request.getRoomId());
        }
        //有网管的设备编号是手动输入的需要校验
        if (iProjectHouseTemplateService.isGateWayProject(request.getHouseTemplateId())){
            if (!request.getSn().equals(deviceDO.getSn())) {
                checkSn(request.getSn(),request.getHouseTemplateId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        removeById(request.getId());
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
    public List<TemplateDevicePageVO> getListByRoomId(String roomId) {
        return this.baseMapper.getListByRoomId(roomId);
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
    public List<SceneFloorVO> getListdeviceInfo(String templateId) {
        List<SceneFloorVO> floorVOS = this.baseMapper.getListdeviceInfo(templateId);
        if (CollectionUtils.isEmpty(floorVOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        Set<String> productIds = Sets.newHashSet();
        for (SceneFloorVO floor : floorVOS) {
            if (CollectionUtils.isEmpty(floor.getRooms())) {
                continue;
            }
            for (SceneRoomVO room : floor.getRooms()) {
                if (CollectionUtils.isEmpty(room.getDevices())) {
                    continue;
                }
                room.getDevices().forEach(device -> {
                    productIds.add(device.getProductId());
                });
            }
        }
        //获取产品属性信息
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceAttributeInfo(Lists.newArrayList(productIds));
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
    public List<SceneDeviceVO> getListDevice(String templateId) {
        List<SceneDeviceVO> floorVOS = this.baseMapper.getListDevice(templateId);
        if (CollectionUtils.isEmpty(floorVOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> productIds = floorVOS.stream().map(SceneDeviceVO::getProductId).collect(Collectors.toList());
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceAttributeInfo(Lists.newArrayList(productIds));
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
    public HouseFloorRoomListDTO getListFloorRooms(String templateId) {
        HouseFloorRoomListDTO result = new HouseFloorRoomListDTO();
        List<String> rooms = iHouseTemplateRoomService.getListNameByTemplateId(templateId);
        result.setRooms(rooms);
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
     * @param showApp  app是否展示（0：否，1：是）
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO>
     * @author wenyilu
     * @date 2021/1/5 15:58
     */
    @Override
    public List<TemplateDeviceDO> getTemplateDevices(String templateId,Integer showApp) {
        QueryWrapper<TemplateDeviceDO> familyDeviceDOQueryWrapper = new QueryWrapper<>();
        familyDeviceDOQueryWrapper.eq("house_template_id", templateId);
        if(showApp!=null){
            familyDeviceDOQueryWrapper.eq("show_app", showApp);
        }
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
    public List<FamilyDeviceBO> getFamilyDeviceWithIndex(String familyId, String templateId, List<TemplateDeviceDO> templateDevices, List<FamilyCommonDeviceDO> familyCommonDeviceDOList, boolean commonUse) {
        // 常用设备业务对象列表
        List<FamilyDeviceBO> familyDeviceBOListForCommon = new LinkedList<>();

        // 不常用设备业务对象列表
        List<FamilyDeviceBO> familyDeviceBOListForUnCommon = new LinkedList<>();

        // 遍历所有设备, 筛选出常用设备和不常用设备
        for (TemplateDeviceDO templateDeviceDO : templateDevices) {
            FamilyDeviceBO familyDeviceBO = detailDeviceById(templateDeviceDO.getId(), familyId, templateId);
            familyDeviceBO.setDevicePosition(String.format("%sF-%s", familyDeviceBO.getFloorNum(), familyDeviceBO.getRoomName()));

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
    public FamilyDeviceBO detailDeviceById(String deviceId, String familyId, String templateId) {
        List<FamilyDeviceBO> familyDeviceBOList = listDeviceDetailByIds(Collections.singletonList(deviceId), familyId, templateId);
        if (!CollectionUtil.isEmpty(familyDeviceBOList)) {
            return familyDeviceBOList.get(0);
        }
        return null;
    }
    /**
     *  获取家庭某个房间下设备列表详情
     * @param familyId    家庭ID
     * @param roomId      房间ID
     * @param templateId  户型ID
     * @param showApp     设备在app是否展示（0：否，1：是）
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date  2021/1/6 9:38
     */
    @Override
    public List<FamilyDeviceBO> getFamilyRoomDevices(String familyId, String roomId,String templateId,Integer showApp) {
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId);
        if(showApp!=null){
            queryWrapper.eq("show_app", showApp);
        }
        List<TemplateDeviceDO> deviceDOS = list(queryWrapper);
        if(CollectionUtil.isEmpty(deviceDOS)){
            return Lists.newArrayList();
        }
        return listDeviceDetailByIds(deviceDOS.stream().map(TemplateDeviceDO::getId).collect(Collectors.toList()),familyId,templateId);
    }
    /**
     * 根据户型统计设备数量
     * @param templateIds   户型ID集合
     * @param showApp   app是否展示（0：否，1：是）
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.project.CountBO>
     * @author wenyilu
     * @date  2021/1/6 10:02
     */
    @Override
    public List<CountBO> getCountByTemplateIds(List<String> templateIds,Integer showApp) {
        List<CountBO> countBOS = this.baseMapper.getCountByTemplateIds(templateIds,showApp);
        if (CollectionUtils.isEmpty(countBOS)) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        return countBOS;
    }

    @Override
    public List<TemplateDevicePageVO> getListByTemplateId(String templateId) {
        return this.baseMapper.getListByTemplateId(templateId);
    }

    @Override
    public TemplateDeviceDO getDeviceByTemplateAndCode(String templateId, String deviceCode) {
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_template_id",templateId);
        queryWrapper.eq("code",deviceCode);
        return getOne(queryWrapper);
    }

    @Override
    public TemplateDeviceDetailVO detailById(String deviceId) {
        TemplateDeviceDetailVO detailVO = this.baseMapper.detailById(deviceId);
        TemplateRoomDO roomDO = iHouseTemplateRoomService.getById(detailVO.getRoomId());
//        detailVO.setControlArea(roomDO.getCode().concat("-").concat(roomDO.getName()));
        DicTagPO dic = iDicTagService.getOne(new LambdaQueryWrapper<DicTagPO>().eq(DicTagPO::getDicCode,"app_ui").eq(DicTagPO::getValue,detailVO.getUiCode()).select(DicTagPO::getName));
        detailVO.setUiCode(dic==null?"":dic.getName());
        List<DeviceAttrInfo> attrInfos = iDeviceAttrInfoService.list(new LambdaQueryWrapper<DeviceAttrInfo>().eq(DeviceAttrInfo::getDeviceId,deviceId).select(DeviceAttrInfo::getCode,DeviceAttrInfo::getName));
        if (!CollectionUtils.isEmpty(attrInfos)){
            List<TemplateDeviceAttrVO> attrs = BeanUtil.mapperList(attrInfos,TemplateDeviceAttrVO.class);
            detailVO.setAttrs(attrs);
        }
        return detailVO;
    }

    @Override
    public List<SelectedVO> getSelectByTempalteId(String tempalteId) {
        List<TemplateDeviceDO> deviceDOS = list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,tempalteId).select(TemplateDeviceDO::getId,TemplateDeviceDO::getName));
        if (CollectionUtils.isEmpty(deviceDOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<SelectedVO> selectedVOS = deviceDOS.stream().map(device -> {
            return new SelectedVO(device.getName(), device.getId());
        }).collect(Collectors.toList());
        return selectedVOS;
    }

    @Override
    public List<SelectedVO> getSelectDeviceError(String tempalteId) {
        List<DeviceBaseInfoDTO> deviceBaseInfoDTOS = this.baseMapper.getSelectDeviceError(tempalteId);
        if (CollectionUtils.isEmpty(deviceBaseInfoDTOS)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        Set<String> dataSet = Sets.newHashSet();
        deviceBaseInfoDTOS.forEach(ojb->{
            dataSet.add(ojb.getProductName().concat("-").concat(ojb.getRoomName()));
        });
        List<SelectedVO> data =dataSet.stream().map(obj-> {
            return new SelectedVO(obj,obj);
        }).collect(Collectors.toList());
        return data;
    }

    @Override
    public TemplateDeviceDO getDeviceByTemplateAndAttrCode(String templateId, String attrCode) {
        return this.baseMapper.getDeviceByTemplateAndAttrCode(templateId,attrCode);
    }

    @Override
    public BasePageVO<TemplateDevicePageVO> getListPageByTemplateId(String templateId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize, true);
        List<TemplateDevicePageVO> data = this.baseMapper.getListByTemplateId(templateId);
        if (CollectionUtils.isEmpty(data)) {
            PageInfo pageInfo = new PageInfo(Lists.newArrayListWithCapacity(0));
            return BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        }
        PageInfo pageInfo = new PageInfo(data);
        BasePageVO<TemplateDevicePageVO> resultData = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
        return resultData;
    }

    @Override
    public List<HomeDeviceStatistics> getDeviceStatistics(HomeDeviceStatisticsQry request) {
        //设备数量统计

        //户型id集合
        List<String> templateIds = null;
        //用户统计
        int countuser = 0;
        //设备总数量
        Integer deviceNum = 0;
        List<HomeDeviceStatistics> result = Lists.newArrayList();
        //获取在线设备
        int screenCount = iContactScreenService.getOnlineScreenNum();
        result.add(HomeDeviceStatistics.builder().categoryCode(CategoryTypeEnum.SCREEN.getParentCode()).onlineCount(screenCount).name((CategoryTypeEnum.SCREEN.getParentName())).sort(CategoryTypeEnum.SCREEN.getParentSort()).build());


        if (!StringUtil.isEmpty(request.getProjectIds())){
            String[] ss = request.getProjectIds().split(",");
            List<String> ids = Lists.newArrayList(ss);
            templateIds = this.baseMapper.getTemplateIdsByPtojectIds(ids);
            countuser = this.baseMapper.getCountFamilyUser(null,ids);
        }else if (!StringUtil.isEmpty(request.getRealestateId())){
            templateIds = this.baseMapper.getTemplateIdsByRealestateId(request.getRealestateId());
            countuser = this.baseMapper.getCountFamilyUser(request.getRealestateId(),null);
        }else {
            templateIds = this.baseMapper.getTemplateIdsByPtojectIds(null);
            countuser = this.baseMapper.getCountFamilyUser(null,null);
        }

        if(CollectionUtils.isEmpty(templateIds)){
            result.add(HomeDeviceStatistics.builder().categoryCode(DEVICE_NUM).count(deviceNum).name("设备数量").build());
            result.add(HomeDeviceStatistics.builder().categoryCode(FAMILY_USER_NUM).count(countuser).name("用户数量").build());
            return result;
        }
        //户型id集合去重
        Set<String> templateIdsSet = templateIds.stream().collect(Collectors.toSet());

        //户型家庭数量统计
        List<HomeDeviceStatisticsBO> familyStatistics = this.baseMapper.getFamilyStatistics(templateIds);
        if (CollectionUtils.isEmpty(familyStatistics)){
            result.add(HomeDeviceStatistics.builder().categoryCode(DEVICE_NUM).count(deviceNum).name("设备数量").build());
            result.add(HomeDeviceStatistics.builder().categoryCode(FAMILY_USER_NUM).count(countuser).name("用户数量").build());
            return result;
        }
        //品类 parentcode--设备数量
        Map<String,Integer> dataMap = Maps.newHashMap();
        List<HomeDeviceStatisticsBO> deviceStatistics = this.baseMapper.getDeviceStatistics(templateIds);
        if (CollectionUtils.isEmpty(deviceStatistics)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        Map<String,List<HomeDeviceStatisticsBO>> deviceMap = deviceStatistics.stream().collect(Collectors.groupingBy(HomeDeviceStatisticsBO::getTemplateId));
        //户型id-----家庭数量
        Map<String,Integer> familyCount = familyStatistics.stream().collect(Collectors.toMap(HomeDeviceStatisticsBO::getTemplateId,HomeDeviceStatisticsBO::getCount));
//        int familyNum = 0;
//        for (Map.Entry<String, Integer> e : familyCount.entrySet()) {
//            String k = e.getKey();
//            Integer v = e.getValue();
//            familyNum = familyNum + v;
//        }
        for (Map.Entry<String, List<HomeDeviceStatisticsBO>> entry : deviceMap.entrySet()) {
            String templateId = entry.getKey();
            List<HomeDeviceStatisticsBO> deviceList = entry.getValue();
            if (!templateIdsSet.contains(templateId)) {
                continue;
            }
            for (HomeDeviceStatisticsBO obj : deviceList) {
                if (familyCount.containsKey(templateId)) {
                    if (CategoryTypeEnum.getInstByType(obj.getCategoryCode()) == null){
                        continue;
                    }
                    String categoryParentCode = CategoryTypeEnum.getInstByType(obj.getCategoryCode()).getParentCode();
                    int countTep = obj.getCount() * familyCount.get(templateId);
//                        familyNum += countTep;
                    Integer count = dataMap.get(categoryParentCode);
                    if (Objects.isNull(count)) {
                        dataMap.put(categoryParentCode, countTep);
                    } else {
                        dataMap.put(categoryParentCode, count + countTep);
                    }
                }
            }
        }

        for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
            String categoryParentCode = entry.getKey();
            Integer count = entry.getValue();
            deviceNum = deviceNum + count;
            //大屏的放在第一个设置数量
            if (CategoryTypeEnum.SCREEN.getParentCode().equals(categoryParentCode)){
                result.get(0).setCount(deviceNum);
            }else {
                result.add(HomeDeviceStatistics.builder().categoryCode(categoryParentCode).count(count).name(CategoryTypeEnum.getParentNameByParentType(categoryParentCode)).sort(CategoryTypeEnum.getParentSortByParentType(categoryParentCode)).build());
            }
        }
//        result.add(HomeDeviceStatistics.builder().categoryCode(FAMILY_NUM).count(familyNum).name("家庭数量").build());
        result.add(HomeDeviceStatistics.builder().categoryCode(DEVICE_NUM).count(deviceNum).name("设备数量").build());
//        result.add(HomeDeviceStatistics.builder().categoryCode(SCREEN_ONLINE_NUM).count(screenOnlineNum).name("大屏在线数量").build());
        result.add(HomeDeviceStatistics.builder().categoryCode(FAMILY_USER_NUM).count(countuser).name("用户数量").build());

        return result;
    }

    @Override
    public void errorAttrInfoCache(DeviceOperateEvent event) {
        //新增设备处理
        if (1 ==event.getType()){
            iDeviceAttrInfoService.errorAttrInfoCacheAdd(event);
        }else if(2 == event.getType()){
            //修改
            iDeviceAttrInfoService.errorAttrInfoCacheDelete(event);
        }else if(3 == event.getType()){
            //删除
            iDeviceAttrInfoService.errorAttrInfoCacheDelete(event);
        }
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
        String jsonObject = (String) redisUtils.get(String.format(RedisCacheConst.DEVICE_ATTR_INFO,
                templateId,attrCode));
        if (!StringUtil.isEmpty(jsonObject)){
            DeviceAttrInfoCacheBO infoDTO = JSON.parseObject(jsonObject, DeviceAttrInfoCacheBO.class);
            return infoDTO;
        }
        DeviceAttrInfoCacheBO infoCacheBO = iDeviceAttrInfoService.getAndSaveAttrInfoCache(templateId,attrCode);
        return infoCacheBO;
    }

    @Override
    public List<TemplateDeviceDO> listByTemplateId(String templateId) {
        return list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,templateId));
    }

    @Override
    public List<TotalCountBO> getDeviceNumGroupByRoom(Long templateId) {
        return null;
    }


    /**
     *  批量获取家庭设备详情信息
     * @param deviceIds   设备IDs
     * @param familyId    家庭ID
     * @param templateId  户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date 2021/1/12 11:15
     */
    @Override
    public List<FamilyDeviceBO> listDeviceDetailByIds(List<String> deviceIds, String familyId, String templateId) {
        List<FamilyDeviceBO> familyDeviceBOList = new LinkedList<>();
        Collection<TemplateDeviceDO> deviceDOS = super.listByIds(deviceIds);
        for (TemplateDeviceDO deviceDO : deviceDOS) {
            FamilyDeviceBO familyDeviceBO = new FamilyDeviceBO();
            // 1. 设备本身的信息
            familyDeviceBO.setDeviceId(deviceDO.getId());
            familyDeviceBO.setDeviceCode(deviceDO.getCode());
            familyDeviceBO.setDeviceName(deviceDO.getName());
            familyDeviceBO.setDeviceCode(deviceDO.getCode());
            familyDeviceBO.setUiCode(deviceDO.getUiCode());
            // 2. 家庭信息
            HomeAutoFamilyDO homeAutoFamily = familyService.getById(familyId);
            familyDeviceBO.setFamilyId(homeAutoFamily.getId());
            familyDeviceBO.setFamilyCode(homeAutoFamily.getCode());

            // 3.房间信息
            TemplateRoomDO roomDO = iHouseTemplateRoomService.getById(deviceDO.getRoomId());
            familyDeviceBO.setRoomId(roomDO.getId());
            familyDeviceBO.setRoomName(roomDO.getName());
            familyDeviceBO.setRoomType(RoomTypeEnum.getInstByType(roomDO.getType()));


            // 5. 产品信息
            HomeAutoProduct homeAutoProduct = productService.getById(deviceDO.getProductId());
//            familyDeviceBO.setProductId(homeAutoProduct.getId());
            familyDeviceBO.setProductCode(homeAutoProduct.getCode());
            familyDeviceBO.setProductIcon(homeAutoProduct.getIcon());
            familyDeviceBO.setProductImage(homeAutoProduct.getIcon2());

            // 6. 品类信息
            familyDeviceBO.setCategoryCode(homeAutoProduct.getCategoryCode());

            // 8. 设备属性列表
            List<DeviceAttrInfo> deviceAttrInfos = iDeviceAttrInfoService.getAttributesByDeviceId(deviceDO.getId(),null, AttrAppFlagEnum.ACTIVE.getCode());

            List<String> deviceAttributeList = deviceAttrInfos.stream().map(DeviceAttrInfo::getCode).collect(Collectors.toList());

            familyDeviceBO.setDeviceAttributeList(deviceAttributeList);

            // 9. 添加进列表
            familyDeviceBOList.add(familyDeviceBO);
        }
        return familyDeviceBOList;
    }
}
