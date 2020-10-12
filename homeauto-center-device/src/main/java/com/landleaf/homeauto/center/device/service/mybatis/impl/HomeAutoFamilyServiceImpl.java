package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.enums.FamilyDeliveryStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyUserTypeEnum;
import com.landleaf.homeauto.center.device.excel.importfamily.*;
import com.landleaf.homeauto.center.device.model.bo.FamilyBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.*;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectBuildingUnit;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFamilyMapper;
import com.landleaf.homeauto.center.device.model.vo.*;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilyScenePageVO;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.remote.WebSocketRemote;
import com.landleaf.homeauto.center.device.service.WebSocketMessageService;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.device.family.FamilyAuthStatusDTO;
import com.landleaf.homeauto.common.domain.dto.device.family.TerminalInfoDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.HomeAutoCustomerDTO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 家庭表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
@Slf4j
public class HomeAutoFamilyServiceImpl extends ServiceImpl<HomeAutoFamilyMapper, HomeAutoFamilyDO> implements IHomeAutoFamilyService {

    @Autowired
    private HomeAutoFamilyMapper homeAutoFamilyMapper;

    @Autowired
    private IFamilyUserService iFamilyUserService;

    @Autowired
    private IFamilyRoomService iFamilyRoomService;

    @Autowired
    private IFamilyFloorService iFamilyFloorService;

    @Autowired
    private IFamilyTerminalService iFamilyTerminalService;


    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;

    @Autowired
    private IHomeAutoRealestateService iHomeAutoRealestateService;
    @Autowired
    private IHomeAutoProjectService iHomeAutoProjectService;
    @Autowired
    private IProjectBuildingService iProjectBuildingService;
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;
    @Autowired
    private IProjectBuildingUnitService iProjectBuildingUnitService;
    @Autowired
    private IHouseTemplateFloorService iHouseTemplateFloorService;
    @Autowired
    private IHouseTemplateRoomService iHouseTemplateRoomService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IHouseTemplateTerminalService iHouseTemplateTerminalService;
    @Autowired
    private IHouseTemplateSceneService iHouseTemplateSceneService;
    @Autowired
    private IHouseTemplateSceneActionService iHouseTemplateSceneActionService;
    @Autowired
    private IHvacConfigService iHvacConfigService;
    @Autowired
    private IHvacActionService iHvacActionService;
    @Autowired
    private IHvacPanelActionService iHvacPanelActionService;

    @Autowired
    private IFamilySceneHvacConfigService iFamilySceneHvacConfigService;

    @Autowired
    private IFamilySceneActionService iFamilySceneActionService;

    @Autowired
    private IFamilySceneHvacConfigActionService iFamilySceneHvacConfigActionService;
    @Autowired
    private IFamilySceneHvacConfigActionPanelService iFamilySceneHvacConfigActionPanelService;

    @Autowired
    private IFamilySceneService iFamilySceneService;

    @Autowired
    private IAppService iAppService;

    @Autowired(required = false)
    private UserRemote userRemote;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;

    @Autowired
    private CommonService commonService;


    @Autowired
    private WebSocketMessageService webSocketMessageService;

    @Autowired
    private IFamilyAuthorizationService iFamilyAuthorizationService;



    public static final Integer MASTER_FLAG = 1;

    @Override
    public List<FamilyBO> getFamilyListByUserId(String userId) {
        return homeAutoFamilyMapper.getFamilyByUserId(userId);
    }

    @Override
    public String getWeatherCodeByFamilyId(String familyId) {
        return homeAutoFamilyMapper.getWeatherCodeByFamilyId(familyId);
    }

    @Override
    public FamilyInfoBO getFamilyInfoByTerminalMac(String mac, Integer terminal) {
        return homeAutoFamilyMapper.getFamilyInfoByTerminalMac(mac, terminal);
    }

    @Override
    public List<MyFamilyInfoVO> getListFamily() {
//        String userId = "5ce32feb4c224b22ad5705bc7accf21d";
        List<MyFamilyInfoVO> infoVOS = this.baseMapper.getListFamilyInfo(TokenContext.getToken().getUserId());
//        List<MyFamilyInfoVO> infoVOS = this.baseMapper.getListFamilyInfo(userId);
        if (CollectionUtils.isEmpty(infoVOS)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<String> familyIds = infoVOS.stream().map(MyFamilyInfoVO::getId).collect(Collectors.toList());
        List<CountBO> roomCount = iFamilyRoomService.getCountByFamilyIds(familyIds);
        List<CountBO> deviceCount = iFamilyDeviceService.getCountByFamilyIds(familyIds);
        List<CountBO> userCount = iFamilyUserService.getCountByFamilyIds(familyIds);
        Map<String, Integer> roomCountMap = roomCount.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        Map<String, Integer> deviceCountMap = deviceCount.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        Map<String, Integer> userCountMap = userCount.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        infoVOS.forEach(info -> {
            if (FamilyUserTypeEnum.MADIN.getType().equals(info.getType())) {
                info.setAdminFlag(1);
            } else {
                info.setAdminFlag(0);
            }
            if (roomCountMap.get(info.getId()) != null) {
                info.setRoomCount(roomCountMap.get(info.getId()));
            }
            if (deviceCountMap.get(info.getId()) != null) {
                info.setDeviceCount(deviceCountMap.get(info.getId()));
            }
            if (userCountMap.get(info.getId()) != null) {
                info.setUserCount(userCountMap.get(info.getId()));
            }
        });
        return infoVOS;
    }

    @Override
    public MyFamilyDetailInfoVO getMyFamilyInfo(String familyId) {
        MyFamilyDetailInfoVO result = new MyFamilyDetailInfoVO();
        List<FloorRoomVO> floors = this.baseMapper.getMyFamilyInfo(familyId);
        if (!CollectionUtils.isEmpty(floors)) {
            result.setFloors(floors);
        }
        List<FamilyUserInfoVO> userInfoVOS = this.baseMapper.getMyFamilyUserInfo(familyId);
        if (!CollectionUtils.isEmpty(userInfoVOS)) {
            List<String> userIds = userInfoVOS.stream().map(FamilyUserInfoVO::getUserId).collect(Collectors.toList());
            Response<List<HomeAutoCustomerDTO>> response = userRemote.getListByIds(userIds);
            if (!response.isSuccess()) {
                log.error("getMyFamilyInfo----userRemote.getListByIds ----获取用户信息失败：{}", response.getErrorMsg());
            }
            List<HomeAutoCustomerDTO> customerDTOS = response.getResult();
            if (CollectionUtils.isEmpty(customerDTOS)) {
                log.error("getMyFamilyInfo----userRemote.getListByIds ----获取用户信息为空：{}", response.toString());
            }
            Map<String, List<HomeAutoCustomerDTO>> collect = customerDTOS.stream().collect(Collectors.groupingBy(HomeAutoCustomerDTO::getId));
            userInfoVOS.forEach(user -> {
                if (FamilyUserTypeEnum.MADIN.getType().equals(user.getType())) {
                    user.setAdminFlag(1);
                } else {
                    user.setAdminFlag(0);
                }
                List<HomeAutoCustomerDTO> list = collect.get(user.getUserId());
                if (!CollectionUtils.isEmpty(list)) {
                    HomeAutoCustomerDTO customerDTO = list.get(0);
                    user.setName(customerDTO == null ? "" : customerDTO.getName());
                }
            });
            result.setUsers(userInfoVOS);
        }

        return result;
    }

    @Override
    public FamilyInfoForSobotDTO getFamilyInfoForSobotById(String familyId) {
        return this.baseMapper.getFamilyInfoForSobotById(familyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(FamilyAddDTO request) {
        addCheck(request);
        request.setId(IdGeneratorUtil.getUUID32());
        String code = buildCode(request);
        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(request, HomeAutoFamilyDO.class);
        familyDO.setCode(code);
        familyDO.setDeliveryStatus(0);
        familyDO.setReviewStatus(0);
        save(familyDO);
        saveTempalteConfig(request.getTemplateId(), familyDO.getId());
    }

    /**
     * 户型配置保存
     *
     * @param templateId
     */
    private void saveTempalteConfig(String templateId, String familyId) {
        List<TemplateFloorDO> floorDOS = iHouseTemplateFloorService.list(new LambdaQueryWrapper<TemplateFloorDO>().eq(TemplateFloorDO::getHouseTemplateId, templateId).select(TemplateFloorDO::getFloor, TemplateFloorDO::getName, TemplateFloorDO::getId));
        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId, templateId).select(TemplateRoomDO::getName, TemplateRoomDO::getFloorId, TemplateRoomDO::getHouseTemplateId, TemplateRoomDO::getType, TemplateRoomDO::getSortNo, TemplateRoomDO::getIcon, TemplateRoomDO::getId));
        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId, templateId));
        List<TemplateTerminalDO> terminalDOS = iHouseTemplateTerminalService.list(new LambdaQueryWrapper<TemplateTerminalDO>().eq(TemplateTerminalDO::getHouseTemplateId, templateId));

        Map<String, String> floorMap = copyFloor(floorDOS, familyId);
        Map<String, String> roomMap = copyRoom(roomDOS, floorMap, familyId);
        Map<String, String> terminalMap = copyTerminal(terminalDOS, familyId);
        copyDevice(deviceDOS, roomMap, terminalMap, familyId);


        //场景主信息
        List<HouseTemplateScene> templateScenes = iHouseTemplateSceneService.list(new LambdaQueryWrapper<HouseTemplateScene>().eq(HouseTemplateScene::getHouseTemplateId, templateId));
        if (CollectionUtils.isEmpty(templateScenes)){
            return;
        }
        Map<String, String> sceneMap = copyScene(templateScenes, familyId);

        //场景非暖通设备配置
        List<HouseTemplateSceneAction> sceneActions = iHouseTemplateSceneActionService.list(new LambdaQueryWrapper<HouseTemplateSceneAction>().eq(HouseTemplateSceneAction::getHouseTemplateId, templateId));
        copySceneAction(sceneMap,sceneActions,familyId);

        //场景暖通设备配置
        List<HvacConfig> configs = iHvacConfigService.list(new LambdaQueryWrapper<HvacConfig>().eq(HvacConfig::getHouseTemplateId, templateId));
        Map<String, String> hvacConfigMap = copyHvacConfig(sceneMap,configs, familyId);

        //场景暖通设备动作配置
        List<HvacAction> hvacActions = iHvacActionService.list(new LambdaQueryWrapper<HvacAction>().eq(HvacAction::getHouseTemplateId, templateId));
        Map<String, String> hvacActionMap = copyHvacAction(sceneMap,hvacConfigMap,hvacActions,familyId);

        //场景暖通面板动作配置
        List<HvacPanelAction> panelActions = iHvacPanelActionService.list(new LambdaQueryWrapper<HvacPanelAction>().eq(HvacPanelAction::getHouseTemplateId, templateId));

        copyHvacPanelAction(sceneMap,hvacActionMap,familyId,panelActions);

    }

    /**
     * 面板动作保存
     * @param sceneMap
     * @param hvacActionMap
     * @param familyId
     * @param panelActions
     */
    private void copyHvacPanelAction(Map<String, String> sceneMap, Map<String, String> hvacActionMap, String familyId, List<HvacPanelAction> panelActions) {
        if (CollectionUtils.isEmpty(panelActions)){
            return;
        }
        List<FamilySceneHvacConfigActionPanel> configActionPanels = Lists.newArrayListWithExpectedSize(panelActions.size());
        panelActions.forEach(sceneAction->{
            FamilySceneHvacConfigActionPanel configActionPanel = BeanUtil.mapperBean(sceneAction,FamilySceneHvacConfigActionPanel.class);
            configActionPanel.setId(IdGeneratorUtil.getUUID32());
            configActionPanel.setSceneId(sceneMap.get(sceneAction.getSceneId()));
            configActionPanel.setHvacActionId(hvacActionMap.get(sceneAction.getHvacActionId()));
            configActionPanel.setFamilyId(familyId);
            configActionPanels.add(configActionPanel);
        });
        iFamilySceneHvacConfigActionPanelService.saveBatch(configActionPanels);
    }


    /**
     * 暖通动作配置
     * @param sceneMap
     * @param hvacConfigMap
     * @param hvacActions
     * @return
     */
    private Map<String, String> copyHvacAction(Map<String, String> sceneMap, Map<String, String> hvacConfigMap, List<HvacAction> hvacActions,String familyId) {
        if (CollectionUtils.isEmpty(hvacActions)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> hvacActionMap = Maps.newHashMapWithExpectedSize(hvacActions.size());
        List<FamilySceneHvacConfigAction> hvacConfigActions = Lists.newArrayListWithExpectedSize(hvacActions.size());
        hvacActions.forEach(hvacAction->{
            FamilySceneHvacConfigAction sceneHvacConfigAction = BeanUtil.mapperBean(hvacAction,FamilySceneHvacConfigAction.class);
            sceneHvacConfigAction.setId(IdGeneratorUtil.getUUID32());
            sceneHvacConfigAction.setSceneId(sceneMap.get(hvacAction.getSceneId()));
            sceneHvacConfigAction.setHvacConfigId(hvacConfigMap.get(hvacAction.getHvacConfigId()));
            sceneHvacConfigAction.setFamilyId(familyId);
            hvacConfigActions.add(sceneHvacConfigAction);
            hvacActionMap.put(hvacAction.getId(),sceneHvacConfigAction.getId());
        });
        iFamilySceneHvacConfigActionService.saveBatch(hvacConfigActions);
        return  hvacActionMap;
    }

    /**
     * 暖通配置保存
     * @param sceneMap
     * @param configs
     * @param familyId
     * @return
     */
    private Map<String, String> copyHvacConfig(Map<String, String> sceneMap, List<HvacConfig> configs, String familyId) {
        if (CollectionUtils.isEmpty(configs)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> hvacConfigMap = Maps.newHashMapWithExpectedSize(configs.size());
        List<FamilySceneHvacConfig> hvacConfigs = Lists.newArrayListWithExpectedSize(configs.size());
        configs.forEach(hvacConfig->{
            FamilySceneHvacConfig sceneHvacConfig = BeanUtil.mapperBean(hvacConfig,FamilySceneHvacConfig.class);
            sceneHvacConfig.setId(IdGeneratorUtil.getUUID32());
            sceneHvacConfig.setFamilyId(familyId);
            sceneHvacConfig.setSceneId(sceneMap.get(hvacConfig.getSceneId()));
            hvacConfigs.add(sceneHvacConfig);
            hvacConfigMap.put(hvacConfig.getId(),sceneHvacConfig.getId());
        });
        iFamilySceneHvacConfigService.saveBatch(hvacConfigs);
        return  hvacConfigMap;
    }

    /**
     * 非暖通场景动作保存
     * @param sceneMap
     * @param sceneActions
     */
    private void copySceneAction(Map<String, String> sceneMap, List<HouseTemplateSceneAction> sceneActions,String familyId) {
        if (CollectionUtils.isEmpty(sceneActions)){
            return;
        }
        List<FamilySceneActionDO> sceneActionDOS = Lists.newArrayListWithExpectedSize(sceneActions.size());
        sceneActions.forEach(sceneAction->{
            FamilySceneActionDO sceneActionDO = BeanUtil.mapperBean(sceneAction,FamilySceneActionDO.class);
            sceneActionDO.setId(IdGeneratorUtil.getUUID32());
            sceneActionDO.setSceneId(sceneMap.get(sceneAction.getSceneId()));
            sceneActionDO.setFamilyId(familyId);
            sceneActionDO.setProductAttributeCode(sceneAction.getAttributeCode());
            sceneActionDO.setProductAttributeId(sceneAction.getAttributeId());
        });
        iFamilySceneActionService.saveBatch(sceneActionDOS);
    }

    /**
     * 场景主信息复制
     * @param templateScenes
     * @param familyId
     * @return
     */
    private Map<String, String> copyScene(List<HouseTemplateScene> templateScenes, String familyId) {

        Map<String, String> sceneMap = Maps.newHashMapWithExpectedSize(templateScenes.size());
        List<FamilySceneDO> sceneDOS = Lists.newArrayListWithExpectedSize(templateScenes.size());
        templateScenes.forEach(templateScene->{
            FamilySceneDO sceneDO = BeanUtil.mapperBean(templateScene,FamilySceneDO.class);
            sceneDO.setId(IdGeneratorUtil.getUUID32());
            sceneDO.setFamilyId(familyId);
            sceneDOS.add(sceneDO);
            sceneMap.put(templateScene.getId(),sceneDO.getId());
        });
        iFamilySceneService.saveBatch(sceneDOS);
        return sceneMap;
    }


    private Map<String, String> copyTerminal(List<TemplateTerminalDO> terminalDOS, String familyId) {
        if (CollectionUtils.isEmpty(terminalDOS)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> terminalMap = Maps.newHashMapWithExpectedSize(terminalDOS.size());
        List<FamilyTerminalDO> data = Lists.newArrayListWithCapacity(terminalDOS.size());
        terminalDOS.forEach(terminal -> {

            FamilyTerminalDO terminalDO = BeanUtil.mapperBean(terminal, FamilyTerminalDO.class);
            terminalDO.setFamilyId(familyId);
            terminalDO.setId(IdGeneratorUtil.getUUID32());
            terminalMap.put(terminal.getId(), terminalDO.getId());
            data.add(terminalDO);
        });
        iFamilyTerminalService.saveBatch(data);
        return terminalMap;
    }

    private void copyDevice(List<TemplateDeviceDO> deviceDOS, Map<String, String> roomMap, Map<String, String> terminalMap, String familyId) {
        if (CollectionUtils.isEmpty(deviceDOS)) {
            return;
        }
        List<FamilyDeviceDO> data = Lists.newArrayListWithCapacity(deviceDOS.size());
        deviceDOS.forEach(device -> {
            FamilyDeviceDO deviceDO = BeanUtil.mapperBean(device, FamilyDeviceDO.class);
            deviceDO.setId(IdGeneratorUtil.getUUID32());
            deviceDO.setFamilyId(familyId);
            deviceDO.setRoomId(roomMap.get(device.getRoomId()));
            deviceDO.setTerminalId(terminalMap.get(device.getTerminalId()));
            data.add(deviceDO);
        });
        iFamilyDeviceService.saveBatch(data);
    }

    private Map<String, String> copyRoom(List<TemplateRoomDO> roomDOS, Map<String, String> floorMap, String familyId) {
        if (CollectionUtils.isEmpty(roomDOS)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> roomMap = Maps.newHashMapWithExpectedSize(roomDOS.size());
        List<FamilyRoomDO> data = Lists.newArrayListWithCapacity(roomDOS.size());
        roomDOS.forEach(room -> {
            FamilyRoomDO roomDO = BeanUtil.mapperBean(room, FamilyRoomDO.class);
            roomDO.setId(IdGeneratorUtil.getUUID32());
            roomDO.setFamilyId(familyId);
            roomDO.setFloorId(floorMap.get(room.getFloorId()));
            roomMap.put(room.getId(), roomDO.getId());
            data.add(roomDO);
        });
        iFamilyRoomService.saveBatch(data);
        return roomMap;
    }

    private Map<String, String> copyFloor(List<TemplateFloorDO> floorDOS, String familyId) {
        if (CollectionUtils.isEmpty(floorDOS)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> floorMap = Maps.newHashMapWithExpectedSize(floorDOS.size());
        List<FamilyFloorDO> data = Lists.newArrayListWithCapacity(floorDOS.size());
        floorDOS.forEach(floor -> {
            FamilyFloorDO floorDO = BeanUtil.mapperBean(floor, FamilyFloorDO.class);
            floorDO.setId(IdGeneratorUtil.getUUID32());
            floorDO.setFamilyId(familyId);
            floorMap.put(floor.getId(), floorDO.getId());
            data.add(floorDO);
        });
        iFamilyFloorService.saveBatch(data);
        return floorMap;
    }

    /**
     * 生产家庭编号
     *
     * @param request
     * @return
     */
    private String buildCode(FamilyAddDTO request) {
        PathBO realestate = iHomeAutoRealestateService.getRealestatePathInfoById(request.getRealestateId());
        PathBO project = iHomeAutoProjectService.getProjectPathInfoById(request.getProjectId());
        PathBO building = iProjectBuildingService.getBuildingPathInfoById(request.getBuildingId());
        PathBO unit = iProjectBuildingUnitService.getUnitPathInfoById(request.getUnitId());
        String path =project.getPath().concat("/").concat(request.getBuildingId()).concat("/").concat(request.getUnitId()).concat("/").concat(request.getId());
        String pathName = realestate.getPathName().concat("/").concat(project.getName()).concat("/").concat(building.getName()).concat(unit.getName()).concat(request.getRoomNo());
        request.setPath(path);
        request.setPathName(pathName);
        return new StringBuilder().append(realestate.getCode()).append(building.getCode()).append(unit.getCode()).append(request.getRoomNo()).toString();
    }

    @Override
    public void update(FamilyUpdateDTO request) {
        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(request, HomeAutoFamilyDO.class);
        updateById(familyDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        removeById(request.getId());
        iFamilyFloorService.remove(new LambdaQueryWrapper<FamilyFloorDO>().eq(FamilyFloorDO::getFamilyId, request.getId()));
        iFamilyRoomService.remove(new LambdaQueryWrapper<FamilyRoomDO>().eq(FamilyRoomDO::getFamilyId, request.getId()));
        iFamilyDeviceService.remove(new LambdaQueryWrapper<FamilyDeviceDO>().eq(FamilyDeviceDO::getFamilyId, request.getId()));
        iFamilyTerminalService.remove(new LambdaQueryWrapper<FamilyTerminalDO>().eq(FamilyTerminalDO::getFamilyId, request.getId()));
        iFamilyUserService.remove(new LambdaQueryWrapper<FamilyUserDO>().eq(FamilyUserDO::getFamilyId, request.getId()));
        iFamilySceneService.deleteByFamilyId(request.getId());

    }

    @Override
    public List<FamilyPageVO> getListByUnitId(String id) {
        return this.baseMapper.getListByUnitId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void review(FamilyOperateDTO request) {
        HomeAutoFamilyDO familyDO = new HomeAutoFamilyDO();
        familyDO.setId(request.getId());
        familyDO.setReviewStatus(FamilyReviewStatusEnum.REVIEW.getType());
        familyDO.setReviewTime(LocalDateTime.now());
        updateById(familyDO);
        iFamilyAuthorizationService.updateByFamilyId();
        //发授权消息
//        FamilyAuthStatusDTO familyAuthStatusDTO = new FamilyAuthStatusDTO();
//        familyAuthStatusDTO.setFamilyId(request.getId());
//        familyAuthStatusDTO.setStatus(FamilyReviewStatusEnum.AUTHORIZATION.getType());
        webSocketMessageService.pushFamilyAuth(request.getId(),FamilyReviewStatusEnum.AUTHORIZATION.getType());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(FamilyOperateDTO request) {
        HomeAutoFamilyDO familyDO = getById(request.getId());
        if (familyDO == null) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "id不存在");
        }
        if (FamilyDeliveryStatusEnum.DELIVERY.getType().equals(familyDO.getDeliveryStatus())) {
            return;
        }
        if (FamilyReviewStatusEnum.UNREVIEW.getType().equals(familyDO.getReviewStatus())) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "家庭未审核不可交付");
        }

        HomeAutoFamilyDO obj = new HomeAutoFamilyDO();
        obj.setId(request.getId());
        obj.setDeliveryStatus(FamilyDeliveryStatusEnum.DELIVERY.getType());
        obj.setDeliveryTime(LocalDateTime.now());
        updateById(obj);
        iFamilyUserService.deleteOperation(request.getId());
    }

    @Override
    public FamilyDetailVO detail(String familyId) {
        FamilyDetailVO result = new FamilyDetailVO();
        FamilyBaseInfoVO baseInfo = this.baseMapper.getFamilyBaseInfo(familyId);
        List<FamilyFloorDetailVO> floorDetailVOS = this.baseMapper.getFamilyFloorDetail(familyId);
        List<FamilyScenePageVO> scenes = iFamilySceneService.getListScene(familyId);
        result.setBaseInfo(baseInfo);
        result.setFloor(floorDetailVOS);
        result.setScenes(scenes);
        getFamilyConfigVO(familyId, result);
        return result;
    }

    @Override
    public void updateFamilyName(FamilyUpdateVO request) {
        iFamilyUserService.checkAdmin(request.getId());
        HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(request, HomeAutoFamilyDO.class);
        updateById(familyDO);
    }

    @Override
    public List<String> getListIdByPaths(List<String> path) {
        if (CollectionUtils.isEmpty(path)) {
            return Lists.newArrayListWithCapacity(0);
        }
        return this.baseMapper.getListIdByPaths(path);
    }

    @Override
    public FamilyConfigDetailVO getConfigInfo(String familyId) {
        List<FamilyFloorConfigVO> floors = iFamilyFloorService.getListFloorDetail(familyId);
        List<FamilyTerminalPageVO> terminalPageVOS = iFamilyTerminalService.getListByFamilyId(familyId);
        FamilyConfigDetailVO detailVO = new FamilyConfigDetailVO();
        detailVO.setFloors(floors);
        detailVO.setTerminals(terminalPageVOS);
        return detailVO;
    }

    @Override
    public List<FamilyBaseInfoDTO> getBaseInfoByProjectId(String familyId) {
        return this.baseMapper.getBaseInfoByProjectId(familyId);
    }

    @Override
    public List<FamilyBaseInfoDTO> getBaseInfoByPath(List<String> paths) {
        if (CollectionUtils.isEmpty(paths)) {
            return Lists.newArrayListWithCapacity(0);
        }
        return this.baseMapper.getBaseInfoByPath(paths);
    }

    @Override
    public Boolean checkFamilyConfig(String familyId) {
        HomeAutoFamilyDO familyDO = getById(familyId);
        if (FamilyReviewStatusEnum.REVIEW.getType().equals(familyDO.getReviewStatus())) {
            if (!CommonConst.Business.SUPER_ACCOUNT.equals(TokenContext.getToken().getUserId())) {
                return false;
            }
        }
        return true;
    }

    private void getFamilyConfigVO(String familyId, FamilyDetailVO detailVO) {
        List<FamilyTerminalDO> terminalDOS = iFamilyTerminalService.list(new LambdaQueryWrapper<FamilyTerminalDO>()
                .eq(FamilyTerminalDO::getFamilyId, familyId).select(FamilyTerminalDO::getName, FamilyTerminalDO::getMac, FamilyTerminalDO::getMasterFlag, FamilyTerminalDO::getId));
        if (CollectionUtils.isEmpty(terminalDOS)) {
            return;
        }
        FamilyConfigVO result = null;
        List<String> ids = Lists.newArrayListWithExpectedSize(terminalDOS.size());
        List<FamilyConfigVO> configVOS = Lists.newArrayListWithExpectedSize(terminalDOS.size());
        for (FamilyTerminalDO terminal : terminalDOS) {
            if (MASTER_FLAG.equals(terminal.getMasterFlag())) {
                result = BeanUtil.mapperBean(terminal, FamilyConfigVO.class);
                result.setType(1);
                ids.add(terminal.getId());
            } else {
                FamilyConfigVO config = BeanUtil.mapperBean(terminal, FamilyConfigVO.class);
                config.setType(1);
                configVOS.add(config);
                ids.add(terminal.getId());
            }
        }
        if (result == null) {
            return;
        }
        result.setChildren(configVOS);

        if (CollectionUtils.isEmpty(ids)) {
            detailVO.setConfig(result);
            return;
        }
        List<FamilyDeviceDO> devices = iFamilyDeviceService.list(new LambdaQueryWrapper<FamilyDeviceDO>().in(FamilyDeviceDO::getTerminalId, ids).select(FamilyDeviceDO::getSn, FamilyDeviceDO::getTerminalId, FamilyDeviceDO::getName));
        if (CollectionUtils.isEmpty(devices)) {
            detailVO.setConfig(result);
            return;
        }
        Map<String, List<FamilyDeviceDO>> mapData = devices.stream().collect(Collectors.groupingBy(FamilyDeviceDO::getTerminalId));
        if (CollectionUtils.isEmpty(result.getChildren())) {
            if (!CollectionUtils.isEmpty(mapData.get(result.getId()))) {
                result.setChildren(BeanUtil.mapperList(mapData.get(result.getId()), FamilyConfigVO.class));
            }
        } else {
            result.getChildren().forEach(obj -> {
                List<FamilyDeviceDO> familyDeviceDOS = mapData.get(obj.getId());
                if (familyDeviceDOS != null) {
                    obj.setChildren(BeanUtil.mapperList(familyDeviceDOS, FamilyConfigVO.class));
                }
            });
            if (!CollectionUtils.isEmpty(mapData.get(result.getId()))) {
                List<FamilyConfigVO> configs = BeanUtil.mapperList(mapData.get(result.getId()), FamilyConfigVO.class);
                result.getChildren().addAll(configs);
                result.setChildren(result.getChildren());
            }
        }
        detailVO.setConfig(result);
        List<TerminalInfoVO> infoVOS = BeanUtil.mapperList(terminalDOS, TerminalInfoVO.class);
        detailVO.setTerminal(infoVOS);
    }

    private void addCheck(FamilyAddDTO request) {
        int count = this.baseMapper.existRoomNo(request.getRoomNo(),request.getUnitId());
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户号已存在");
        }
    }



    @Override
    public HomeAutoFamilyDO getFamilyByCode(String familyCode) {
        QueryWrapper<HomeAutoFamilyDO> familyQueryWrapper = new QueryWrapper<>();
        familyQueryWrapper.eq("code", familyCode);
        return getOne(familyQueryWrapper);
    }

    @Override
    public FamilyAuthStatusDTO getAuthorizationState(String familyId) {
        FamilyAuthStatusDTO stateObj = new FamilyAuthStatusDTO();
        stateObj.setFamilyId(familyId);
        Integer state = this.baseMapper.getAuthorizationState(familyId);
        stateObj.setStatus(state);
        return stateObj;
    }

    @Override
    public List<FamilyUserVO> getListByUser(String userId) {
        return this.baseMapper.getListByUser(userId);
    }

    @Override
    public void downLoadImportTemplate(TemplateQeyDTO request, HttpServletResponse response) {
        List<List<String>> headList = getListHead(request,null);
        commonService.setResponseHeader(response,request.getTemplateName().concat("模板"));
        try {
            OutputStream os = response.getOutputStream();
            EasyExcel.write(os).head(headList).excelType(ExcelTypeEnum.XLSX).sheet(request.getTemplateName()).registerWriteHandler(new Custemhandler()).registerWriteHandler(getStyleStrategy()).registerWriteHandler(new RowWriteHandler()).doWrite(Lists.newArrayListWithCapacity(0));
        } catch (IOException e) {
            log.error("模板下载失败，原因：{}",e.getMessage());
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()),ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getMsg());
        }
    }

    public  HorizontalCellStyleStrategy getStyleStrategy(){
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为灰色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)18);
        // 字体样式
        headWriteFont.setFontName("Frozen");
        headWriteCellStyle.setWriteFont(headWriteFont);
        //自动换行
        headWriteCellStyle.setWrapped(false);
        // 水平对齐方式
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直对齐方式
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
//        contentWriteCellStyle.setFillPatternType(FillPatternType.SQUARES);
        // 背景白色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)18);
        // 字体样式
        contentWriteFont.setFontName("Calibri");
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }





    @Override
    public void importBatch(MultipartFile file, HttpServletResponse response) throws IOException {
        FamilyImportDataListener listener = new FamilyImportDataListener(iHomeAutoFamilyService,iHomeAutoRealestateService,iHomeAutoProjectService,iProjectBuildingService,iProjectBuildingUnitService,iProjectHouseTemplateService);
        EasyExcel.read(file.getInputStream(), ImportFamilyModel.class, listener).sheet().doRead();
        if (CollectionUtils.isEmpty(listener.getErrorlist())){
            return;
        }
        try {
            String fileName = "失败列表";
            commonService.setResponseHeader(response,fileName);
            OutputStream os = response.getOutputStream();
            List<ImporFamilyResultVO> familyResultVOS = BeanUtil.mapperList(listener.getErrorlist(),ImporFamilyResultVO.class);
            EasyExcel.write(os, ImporFamilyResultVO.class).sheet("失败列表").doWrite(familyResultVOS);
        } catch (IOException e) {
            log.error("模板下载失败，原因：{}",e.getMessage());
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),e.getMessage());
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<ImportFamilyModel> importBatchFamily(List<ImportFamilyModel> dataList, HouseTemplateConfig config) {

        if (CollectionUtils.isEmpty(dataList)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<ImportFamilyModel> result = Lists.newArrayListWithExpectedSize(dataList.size());
        for (ImportFamilyModel data : dataList) {
            try {
                int count = this.baseMapper.existRoomNo(data.getRoomNo(), data.getUnitId());
                if (count > 0) {
                    data.setError(ErrorCodeEnumConst.IMPORT_FAMILY_CHECK.getMsg());
                    result.add(data);
                    continue;
                }
                HomeAutoFamilyDO familyDO = BeanUtil.mapperBean(data, HomeAutoFamilyDO.class);
                save(familyDO);
                saveImportTempalteConfig(data, config);
            } catch (BusinessException e) {
                data.setError(e.getMessage());
                result.add(data);
            } catch (Exception e) {
                log.error("工程导入报错：行数:{} 工程名称：{}，原因：{}", data.getRow(), data.getName(), e.getMessage());
                data.setError(ErrorCodeEnumConst.ERROR_CODE_UNHANDLED_EXCEPTION.getMsg());
                result.add(data);
            }

        }
        return result;
    }

    @Override
    public void syncFamilyConfig(String familyId) {
        AdapterConfigUpdateDTO sceneUpdate = new AdapterConfigUpdateDTO();
        String code = iHomeAutoFamilyService.getFamilyCodeByid(familyId);
        TerminalInfoDTO infoDTO = iFamilyTerminalService.getMasterMacByFamilyid(familyId);
        sceneUpdate.setFamilyId(familyId);
        sceneUpdate.setFamilyCode(code);
        sceneUpdate.setTerminalMac(infoDTO.getMac());
        sceneUpdate.setTerminalType(infoDTO.getType());
        sceneUpdate.setUpdateType(ContactScreenConfigUpdateTypeEnum.SCENE.code);


        AdapterConfigUpdateDTO configUpdate = new AdapterConfigUpdateDTO();
        configUpdate.setFamilyId(familyId);
        configUpdate.setFamilyCode(code);
        configUpdate.setTerminalMac(infoDTO.getMac());
        configUpdate.setTerminalType(infoDTO.getType());
        configUpdate.setUpdateType(ContactScreenConfigUpdateTypeEnum.FLOOR_ROOM_DEVICE.code);

        AdapterConfigUpdateDTO timeUpdate = new AdapterConfigUpdateDTO();
        timeUpdate.setFamilyId(familyId);
        timeUpdate.setFamilyCode(code);
        timeUpdate.setTerminalMac(infoDTO.getMac());
        timeUpdate.setTerminalType(infoDTO.getType());
        timeUpdate.setUpdateType(ContactScreenConfigUpdateTypeEnum.SCENE_TIMING.code);
        iAppService.configUpdateConfig(sceneUpdate);
        iAppService.configUpdateConfig(configUpdate);
        iAppService.configUpdateConfig(timeUpdate);
    }

    @Override
    public String getFamilyCodeByid(String familyId) {
        String key = String.format(RedisCacheConst.FAMILY_ID_CODE,familyId);
        String code = (String) redisUtils.get(key);
        if (!StringUtil.isEmpty(code)){
            return code;
        }
        code = this.baseMapper.getFamilyCodeByid(familyId);
        redisUtils.set(key,code);
        return code;
    }

    @Override
    public void downLoadImportBuildingTemplate(TemplateQeyDTO request, HttpServletResponse response) {
        List<ProjectBuildingUnit> units = iProjectBuildingUnitService.list(new LambdaQueryWrapper<ProjectBuildingUnit>().eq(ProjectBuildingUnit::getBuildingId,request.getBuildingId()).select(ProjectBuildingUnit::getId,ProjectBuildingUnit::getName));
        if (CollectionUtils.isEmpty(units)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"单元为空");
        }
        List<String> names = iHouseTemplateTerminalService.getListByTempalteId(request.getTemplateId());
        if (CollectionUtils.isEmpty(names)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"当前户型没有配置大屏/网关");
        }
        commonService.setResponseHeader(response,request.getTemplateName().concat("模板"));
        try {
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).excelType(ExcelTypeEnum.XLSX).registerWriteHandler(new Custemhandler()).registerWriteHandler(getStyleStrategy()).registerWriteHandler(new RowWriteHandler()).build();
            for (int i = 0; i < units.size(); i++) {
                writeSheet(excelWriter,units.get(i),i,request,names);
            }
            excelWriter.finish();
        } catch (IOException e) {
            log.error("模板下载失败，原因：{}",e.getMessage());
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getCode()),ErrorCodeEnumConst.ERROR_CODE_BUSINESS_EXCEPTION.getMsg());
        }
    }

    @Override
    public void importBuildingBatch(MultipartFile file, HttpServletResponse response) throws IOException {
        ExcelReader excelReader = EasyExcel.read(file.getInputStream()).build();
        List<ReadSheet> sheets = excelReader.excelExecutor().sheetList();
        if(CollectionUtils.isEmpty(sheets)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"解析失败");
        }
        List<ReadSheet> sheetList = Lists.newArrayListWithExpectedSize(sheets.size());
        List<FamilyImportDataListener> listeners = Lists.newArrayListWithExpectedSize(sheets.size());
        for (int i = 0; i < sheets.size(); i++) {
            FamilyImportDataListener listener = new FamilyImportDataListener(iHomeAutoFamilyService,iHomeAutoRealestateService,iHomeAutoProjectService,iProjectBuildingService,iProjectBuildingUnitService,iProjectHouseTemplateService);
            ReadSheet readSheet = EasyExcel.readSheet(i).head(ImportFamilyModel.class).registerReadListener(listener).build();
            sheetList.add(readSheet);
            listeners.add(listener);
        }
        excelReader.read(sheetList);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
        boolean errorFlag = false;
        for (FamilyImportDataListener listener : listeners) {
            if (!CollectionUtils.isEmpty(listener.getErrorlist())){
                errorFlag = true;
                break;
            }
        }
        if (!errorFlag){
            return;
        }
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(),ImporFamilyResultVO.class).excelType(ExcelTypeEnum.XLSX).build();
        String fileName = "失败列表";
        commonService.setResponseHeader(response,fileName);
        for (int i = 0; i < listeners.size(); i++) {
            WriteSheet writeSheet = EasyExcel.writerSheet(i, listeners.get(i).getUnitName()).build();
            List<ImporFamilyResultVO> familyResultVOS = BeanUtil.mapperList(listeners.get(i).getErrorlist(),ImporFamilyResultVO.class);
            excelWriter.write(familyResultVOS, writeSheet);
        }
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();

    }

    @Override
    public List<SelectedVO> getListFamilySelects() {
        List<String> paths = commonService.getUserPathScope();
        return this.baseMapper.getListFamilyByPaths(paths);
    }

    private void writeSheet(ExcelWriter excelWriter, ProjectBuildingUnit projectBuildingUnit, int i, TemplateQeyDTO request,List<String> names) {
        request.setUnitId(projectBuildingUnit.getId());
        List<List<String>> headList = getListHead(request,names);
        WriteSheet writeSheet = EasyExcel.writerSheet(i, projectBuildingUnit.getName()).head(headList).build();
        excelWriter.write(Lists.newArrayListWithCapacity(0), writeSheet);
    }


    private void saveImportTempalteConfig(ImportFamilyModel data, HouseTemplateConfig config) {
        Map<String, String> floorMap = copyFloor(config.getFloorDOS(), data.getId());
        Map<String, String> roomMap = copyRoom(config.getRoomDOS(), floorMap, data.getId());
        Map<String, String> terminalMap = copyImportTerminal(config.getTerminalDOS(), data);
        copyDevice(config.getDeviceDOS(), roomMap, terminalMap, data.getId());
        //场景主信息
        if (CollectionUtils.isEmpty(config.getTemplateScenes())){
            return;
        }
        Map<String, String> sceneMap = copyScene(config.getTemplateScenes(), data.getId());
        //场景非暖通设备配置
        copySceneAction(sceneMap,config.getSceneActions(),data.getId());
        //场景暖通设备配置
        Map<String, String> hvacConfigMap = copyHvacConfig(sceneMap,config.getConfigs(), data.getId());
        //场景暖通设备动作配置
        Map<String, String> hvacActionMap = copyHvacAction(sceneMap,hvacConfigMap,config.getHvacActions(),data.getId());
        //场景暖通面板动作配置
        copyHvacPanelAction(sceneMap,hvacActionMap,data.getId(),config.getPanelActions());
    }

    //导入家庭复制网关信息
    private Map<String, String> copyImportTerminal(List<TemplateTerminalDO> terminalDOS, ImportFamilyModel familyModel) {
        if (CollectionUtils.isEmpty(terminalDOS)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> terminalMap = Maps.newHashMapWithExpectedSize(terminalDOS.size());
        List<FamilyTerminalDO> data = Lists.newArrayListWithCapacity(terminalDOS.size());
        for (int i = 0; i < terminalDOS.size(); i++) {
            FamilyTerminalDO terminalDO = BeanUtil.mapperBean(terminalDOS.get(i), FamilyTerminalDO.class);
            terminalDO.setFamilyId(familyModel.getId());
            terminalDO.setId(IdGeneratorUtil.getUUID32());
            terminalMap.put(terminalDOS.get(i).getId(), terminalDO.getId());
            //终端会按主网关 在前其他按创建时间倒序目前导入只支持4个
            if (i == 0){
                terminalDO.setMac(familyModel.getMac1());
            }else if (i == 1){
                terminalDO.setMac(familyModel.getMac2());
            }else if (i == 2){
                terminalDO.setMac(familyModel.getMac3());
            }else if (i == 3){
                terminalDO.setMac(familyModel.getMac4());
            }
            data.add(terminalDO);
        }
        iFamilyTerminalService.saveBatch(data);
        return terminalMap;
    }




    private List<List<String>> getListHead(TemplateQeyDTO request,List<String> names ) {
        List<List<String>> headList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(names)){
            names = iHouseTemplateTerminalService.getListByTempalteId(request.getTemplateId());
            if (CollectionUtils.isEmpty(names)){
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()),"当前户型没有配置大屏/网关");
            }
        }
        // 表头
        String headStr = request.getTemplateName().concat("-").concat(request.getRealestateId()).concat("-").concat(request.getProjectId()).concat("-").concat(request.getBuildingId()).concat("-").concat(request.getUnitId()).concat("-").concat(request.getTemplateId());
        List<String> headArray = Lists.newArrayListWithExpectedSize(names.size()+2);
        headArray.add("家庭名称");
        headArray.add("户号");
        headArray.addAll(names);
        List<String> headTitle;
        for (String name : headArray) {
            headTitle = Lists.newArrayListWithExpectedSize(2);
            headTitle.add(headStr);
            headTitle.add(name);
            headList.add(headTitle);
        }
        return headList;
    }
}
