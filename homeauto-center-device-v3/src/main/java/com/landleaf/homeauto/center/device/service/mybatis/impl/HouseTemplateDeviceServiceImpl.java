package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.eventbus.event.DeviceOperateEvent;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateSceneActionConfig;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.center.device.model.mapper.TemplateDeviceMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceSimpleBO;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttrInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceBaseInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.device.PanelBO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.FloorRoomBaseVO;
import com.landleaf.homeauto.center.device.model.vo.statistics.DeviceStatisticsBO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
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
    private IHomeAutoProductService productService;
    @Autowired
    IDicTagService iDicTagService;
    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private IdService idService;
    @Autowired
    private ITemplateSceneActionConfigService iTemplateSceneActionConfigService;
    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;

    @Autowired
    private ITemplateOperateService iTemplateOperateService;
    @Autowired
    private ISysProductCategoryService iSysProductCategoryService;

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
        //系统设备的判断
        sysDeviceCheck(deviceDO);
        save(deviceDO);
        iTemplateOperateService.sendEvent(TemplateOperateEvent.builder().templateId(request.getHouseTemplateId()).typeEnum(ContactScreenConfigUpdateTypeEnum.FLOOR_ROOM_DEVICE).build());
        return deviceDO;
    }

    //系统设备的判断
    private void sysDeviceCheck(TemplateDeviceDO request) {
        if (FamilySystemFlagEnum.SYS_SUB_DEVICE.getType() != request.getSystemFlag()){
            return;
        }
        Long sysPid = iHomeAutoProjectService.getSysPidByTemplateId(request.getProductId());
        if (Objects.isNull(sysPid)){
            return;
        }
        // 0 :1ge  1:代表多个
        int count = iSysProductCategoryService.getCategoryNumBySysPid(sysPid,request.getCategoryCode());
        if (count == 1){
            return;
        }
        if(!this.existCategoryDevice(request.getCategoryCode(),request.getHouseTemplateId())){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "该系统品类已存在");
        }
    }

    /**
     * 判断户型下某个类型设备是否存在
     * @param categoryCode
     * @param houseTemplateId
     * @return
     */
    private boolean existCategoryDevice(String categoryCode, Long houseTemplateId) {
        int count = this.baseMapper.existCategoryDevice(categoryCode,houseTemplateId);
        return count==0?false:true;
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
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceControlAttributeInfo(Lists.newArrayList(productIds));
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
     * 获取家庭某个房间下设备列表详情
     *
     * @param familyId   家庭ID
     * @param roomId     房间ID
     * @param templateId 户型ID
     * @param systemFlag 设备类型{@link FamilySystemFlagEnum}
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyDeviceBO>
     * @author wenyilu
     * @date 2021/1/6 9:38
     */
    @Override
    public List<FamilyDeviceSimpleBO> getFamilyRoomDevices(Long familyId, Long roomId, Long templateId, Integer systemFlag) {
        QueryWrapper<TemplateDeviceDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("house_template_id", templateId);
        if(roomId!=null){
            queryWrapper.eq("room_id", roomId);
        }
        if(systemFlag!=null){
            queryWrapper.eq("system_flag", systemFlag);
        }
        List<TemplateDeviceDO> deviceDOS = list(queryWrapper);
        if (CollectionUtil.isEmpty(deviceDOS)) {
            return Lists.newArrayList();
        }
        deviceDOS = deviceDOS.stream().filter(device -> !CategoryTypeEnum.HOST.getType().equals(device.getCategoryCode())).collect(Collectors.toList());
        List<HomeAutoProduct> allProducts = productService.getAllProducts();
        Map<String, HomeAutoProduct> productMap = allProducts.stream().filter(product -> !CategoryTypeEnum.HOST.getType().equals(product.getCategoryCode())).collect(Collectors.toMap(HomeAutoProduct::getCode, p -> p, (n, o) -> n));

        return deviceDOS.stream().map(i->{
            FamilyDeviceSimpleBO simpleBO = new FamilyDeviceSimpleBO();
            simpleBO.setCategoryCode(i.getCategoryCode());
            simpleBO.setDeviceId(i.getId());
            simpleBO.setDeviceName(i.getName());
            simpleBO.setProductCode(i.getProductCode());
            HomeAutoProduct product = productMap.get(i.getProductCode());
            if(product!=null){
                simpleBO.setProductIcon(product.getIcon());
                simpleBO.setProductImage(product.getIcon2());
            }
            return simpleBO;
        }).collect(Collectors.toList());
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
    public List<SelectedVO> getSelectDeviceError(Long tempalteId) {
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
    public BasePageVO<TemplateDevicePageVO> getListPageByTemplateId(Long templateId, Integer pageNum, Integer pageSize) {
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

    @Override
    public List<TemplateDeviceDO> getListDeviceDOByTeamplateId(Long templateId) {
        List<TemplateDeviceDO> data = list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,templateId).select(TemplateDeviceDO::getId,TemplateDeviceDO::getProductId,TemplateDeviceDO::getProductCode,TemplateDeviceDO::getCategoryCode,TemplateDeviceDO::getName, TemplateDeviceDO::getSn,TemplateDeviceDO::getRoomId));
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public List<TemplateDeviceDO> getListDeviceDOByTeamplateIds(List<Long> templateIds) {
        List<TemplateDeviceDO> data = list(new LambdaQueryWrapper<TemplateDeviceDO>().in(TemplateDeviceDO::getHouseTemplateId,templateIds).select(TemplateDeviceDO::getId,TemplateDeviceDO::getProductId,TemplateDeviceDO::getProductCode,TemplateDeviceDO::getCategoryCode,TemplateDeviceDO::getName, TemplateDeviceDO::getSn,TemplateDeviceDO::getRoomId));
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public List<DeviceStatisticsBO> getListDeviceStatistics(List<Long> templateIds) {
        if(CollectionUtils.isEmpty(templateIds)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return this.baseMapper.getListDeviceStatistics(templateIds);
    }

    @Override
    public TemplateDeviceDO getSensorDeviceSnByTId(Long realestateId) {
        return this.baseMapper.getSensorDeviceSnByTId(realestateId);
    }

    @Override
    public TemplateDeviceDO getHvacByTtemplateId(Long templateId) {
        TemplateDeviceDO deviceDO = getOne(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,templateId).eq(TemplateDeviceDO::getCategoryCode, CategoryTypeEnum.HVAC.getType()).select(TemplateDeviceDO::getId,TemplateDeviceDO::getSn,TemplateDeviceDO::getName,TemplateDeviceDO::getProductId,TemplateDeviceDO::getProductCode));
        return deviceDO;
    }


}
