package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.excel.importfamily.HouseTemplateConfig;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.*;
import com.landleaf.homeauto.center.device.model.mapper.ProjectHouseTemplateMapper;
import com.landleaf.homeauto.center.device.model.vo.family.TemplateSelectedVO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateCountBO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 项目户型表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class ProjectHouseTemplateServiceImpl extends ServiceImpl<ProjectHouseTemplateMapper, ProjectHouseTemplate> implements IProjectHouseTemplateService {

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

    @Override
    public void add(ProjectHouseTemplateDTO request) {
        addCheck(request);
        ProjectHouseTemplate template = BeanUtil.mapperBean(request,ProjectHouseTemplate.class);
        save(template);
    }

    private void addCheck(ProjectHouseTemplateDTO request) {
        int count = count(new LambdaQueryWrapper<ProjectHouseTemplate>().eq(ProjectHouseTemplate::getName,request.getName()).eq(ProjectHouseTemplate::getProjectId,request.getProjectId()));
        if (count > 0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户型已存在");
        }
    }

    @Override
    public void update(ProjectHouseTemplateDTO request) {
        updateCheck(request);
        ProjectHouseTemplate template = BeanUtil.mapperBean(request,ProjectHouseTemplate.class);
        updateById(template);
    }

    private void updateCheck(ProjectHouseTemplateDTO request) {
        ProjectHouseTemplate template = getById(request.getId());
        if (template.getName().equals(request.getName())){
            return;
        }
        addCheck(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        removeById(request.getId());
        //删除户型配置
        iHouseTemplateTerminalService.remove(new LambdaQueryWrapper<TemplateTerminalDO>().eq(TemplateTerminalDO::getHouseTemplateId,request.getId()));
        iHouseTemplateFloorService.remove(new LambdaQueryWrapper<TemplateFloorDO>().eq(TemplateFloorDO::getHouseTemplateId,request.getId()));
        iHouseTemplateRoomService.remove(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId,request.getId()));
        iHouseTemplateDeviceService.remove(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,request.getId()));
        iHouseTemplateSceneService.deleteByTempalteId(request.getId());
    }

    @Override
    public List<HouseTemplatePageVO> getListByProjectId(String id) {
        List<HouseTemplatePageVO> data = this.baseMapper.getListByProjectId(id);
        if (CollectionUtils.isEmpty(data)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        return data;
    }

    @Override
    public HouseTemplateDetailVO getDeatil(String id) {
        List<TemplateFloorDetailVO> floors = iHouseTemplateFloorService.getListFloorDetail(id);
        HouseTemplateDetailVO detailVO = new HouseTemplateDetailVO();
        detailVO.setFloors(floors);
        return detailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copy(ProjectHouseTemplateDTO request) {
        ProjectHouseTemplate template = getById(request.getId());

        if (template == null){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "户型id不存在");
        }
        //生成新的户型
        template.setId(IdGeneratorUtil.getUUID32());
        addCheck(request);
        template.setName(request.getName());
        template.setArea(request.getArea());

        List<TemplateFloorDO> floorDOS = iHouseTemplateFloorService.list(new LambdaQueryWrapper<TemplateFloorDO>().eq(TemplateFloorDO::getHouseTemplateId,request.getId()).select(TemplateFloorDO::getFloor,TemplateFloorDO::getName,TemplateFloorDO::getId));
        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId,request.getId()).select(TemplateRoomDO::getName,TemplateRoomDO::getFloorId,TemplateRoomDO::getHouseTemplateId,TemplateRoomDO::getType,TemplateRoomDO::getSortNo,TemplateRoomDO::getIcon,TemplateRoomDO::getId));
        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId,request.getId()));
        List<TemplateTerminalDO> terminalDOS = iHouseTemplateTerminalService.list(new LambdaQueryWrapper<TemplateTerminalDO>().eq(TemplateTerminalDO::getHouseTemplateId,request.getId()));


        Map<String, String> floorMap = copyFloor(floorDOS,template.getId());
        Map<String, String> roomMap = copyRoom(roomDOS,floorMap,template.getId());
        Map<String, String> terminalMap = copyTerminal(terminalDOS,template.getId());
        copyDevice(deviceDOS,roomMap,terminalMap,template.getId());


        //场景主信息
        List<HouseTemplateScene> templateScenes = iHouseTemplateSceneService.list(new LambdaQueryWrapper<HouseTemplateScene>().eq(HouseTemplateScene::getHouseTemplateId, request.getId()));
        if (CollectionUtils.isEmpty(templateScenes)){
            return;
        }
        Map<String, String> sceneMap = copyScene(templateScenes, template.getId());

        //场景非暖通设备配置
        List<HouseTemplateSceneAction> sceneActions = iHouseTemplateSceneActionService.list(new LambdaQueryWrapper<HouseTemplateSceneAction>().eq(HouseTemplateSceneAction::getHouseTemplateId, request.getId()));
        copySceneAction(sceneMap,sceneActions,template.getId());

        //场景暖通设备配置
        List<HvacConfig> configs = iHvacConfigService.list(new LambdaQueryWrapper<HvacConfig>().eq(HvacConfig::getHouseTemplateId, request.getId()));
        Map<String, String> hvacConfigMap = copyHvacConfig(sceneMap,configs, template.getId());

        //场景暖通设备动作配置
        List<HvacAction> hvacActions = iHvacActionService.list(new LambdaQueryWrapper<HvacAction>().eq(HvacAction::getHouseTemplateId, request.getId()));
        Map<String, String> hvacActionMap = copyHvacAction(sceneMap,hvacConfigMap,hvacActions,template.getId());

        //场景暖通面板动作配置
        List<HvacPanelAction> panelActions = iHvacPanelActionService.list(new LambdaQueryWrapper<HvacPanelAction>().eq(HvacPanelAction::getHouseTemplateId, request.getId()));

        copyHvacPanelAction(sceneMap,hvacActionMap,template.getId(),panelActions);


        save(template);
    }

    /**
     * 面板动作保存
     * @param sceneMap
     * @param hvacActionMap
     * @param houseTemplateId
     * @param panelActions
     */
    private void copyHvacPanelAction(Map<String, String> sceneMap, Map<String, String> hvacActionMap, String houseTemplateId, List<HvacPanelAction> panelActions) {
        if (CollectionUtils.isEmpty(panelActions)){
            return;
        }
        panelActions.forEach(sceneAction->{
            sceneAction.setId(IdGeneratorUtil.getUUID32());
            sceneAction.setSceneId(sceneMap.get(sceneAction.getSceneId()));
            sceneAction.setHvacActionId(hvacActionMap.get(sceneAction.getHvacActionId()));
            sceneAction.setHouseTemplateId(houseTemplateId);
        });
        iHvacPanelActionService.saveBatch(panelActions);
    }


    /**
     * 暖通动作配置
     * @param sceneMap
     * @param hvacConfigMap
     * @param hvacActions
     * @return
     */
    private Map<String, String> copyHvacAction(Map<String, String> sceneMap, Map<String, String> hvacConfigMap, List<HvacAction> hvacActions,String houseTemplateId) {
        if (CollectionUtils.isEmpty(hvacActions)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> hvacActionMap = Maps.newHashMapWithExpectedSize(hvacActions.size());
        hvacActions.forEach(hvacAction->{
            String id = hvacAction.getId();
            hvacAction.setId(IdGeneratorUtil.getUUID32());
            hvacAction.setSceneId(sceneMap.get(hvacAction.getSceneId()));
            hvacAction.setHvacConfigId(hvacConfigMap.get(hvacAction.getHvacConfigId()));
            hvacAction.setHouseTemplateId(houseTemplateId);
            hvacActionMap.put(id,hvacAction.getId());

        });
        iHvacActionService.saveBatch(hvacActions);
        return  hvacActionMap;
    }


    /**
     * 暖通配置保存
     * @param sceneMap
     * @param configs
     * @param houseTemplateId
     * @return
     */
    private Map<String, String> copyHvacConfig(Map<String, String> sceneMap, List<HvacConfig> configs, String houseTemplateId) {
        if (CollectionUtils.isEmpty(configs)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> hvacConfigMap = Maps.newHashMapWithExpectedSize(configs.size());
        configs.forEach(hvacConfig->{
            String id = hvacConfig.getId();
            hvacConfig.setId(IdGeneratorUtil.getUUID32());
            hvacConfig.setHouseTemplateId(houseTemplateId);
            hvacConfig.setSceneId(sceneMap.get(hvacConfig.getSceneId()));
            hvacConfigMap.put(id,hvacConfig.getId());
        });
        iHvacConfigService.saveBatch(configs);
        return  hvacConfigMap;
    }

    /**
     * 非暖通场景动作保存
     * @param sceneMap
     * @param sceneActions
     */
    private void copySceneAction(Map<String, String> sceneMap, List<HouseTemplateSceneAction> sceneActions,String houseTemplateId) {
        if (CollectionUtils.isEmpty(sceneActions)){
            return;
        }
        sceneActions.forEach(sceneAction->{
            sceneAction.setId(IdGeneratorUtil.getUUID32());
            sceneAction.setSceneId(sceneMap.get(sceneAction.getSceneId()));
            sceneAction.setHouseTemplateId(houseTemplateId);
        });
        iHouseTemplateSceneActionService.saveBatch(sceneActions);
    }

    /**
     * 场景主信息复制
     * @param templateScenes
     * @param houseTemplateId
     * @return
     */
    private Map<String, String> copyScene(List<HouseTemplateScene> templateScenes, String houseTemplateId) {

        Map<String, String> sceneMap = Maps.newHashMapWithExpectedSize(templateScenes.size());
        templateScenes.forEach(templateScene->{
            String sceneId = templateScene.getId();
            templateScene.setId(IdGeneratorUtil.getUUID32());
            sceneMap.put(sceneId,templateScene.getId());
            templateScene.setHouseTemplateId(houseTemplateId);
        });
        iHouseTemplateSceneService.saveBatch(templateScenes);
        return sceneMap;
    }


    @Override
    public List<TemplateSelectedVO> getListSelectByProjectId(String projectId) {
        return this.baseMapper.getListSelectByProjectId(projectId);
    }

    @Override
    public Map<String, Integer> countByProjectIds(List<String> projectIds) {
        if (CollectionUtils.isEmpty(projectIds)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        List<CountBO> countList = this.baseMapper.countByProjectIds(projectIds);
        if (CollectionUtils.isEmpty(countList)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, Integer> count = countList.stream().collect(Collectors.toMap(CountBO::getId, CountBO::getCount));
        return count;
    }

    @Override
    public List<String> getListHoustTemplateNames(String projectId) {
        return this.baseMapper.getListHoustTemplateNames(projectId);
    }

    @Override
    public HouseTemplateConfig getImportTempalteConfig(String templateId) {
        List<TemplateFloorDO> floorDOS = iHouseTemplateFloorService.list(new LambdaQueryWrapper<TemplateFloorDO>().eq(TemplateFloorDO::getHouseTemplateId, templateId).select(TemplateFloorDO::getFloor, TemplateFloorDO::getName, TemplateFloorDO::getId));
        List<TemplateRoomDO> roomDOS = iHouseTemplateRoomService.list(new LambdaQueryWrapper<TemplateRoomDO>().eq(TemplateRoomDO::getHouseTemplateId, templateId).select(TemplateRoomDO::getName, TemplateRoomDO::getFloorId, TemplateRoomDO::getHouseTemplateId, TemplateRoomDO::getType, TemplateRoomDO::getSortNo, TemplateRoomDO::getIcon, TemplateRoomDO::getId));
        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.list(new LambdaQueryWrapper<TemplateDeviceDO>().eq(TemplateDeviceDO::getHouseTemplateId, templateId));
        List<TemplateTerminalDO> terminalDOS = iHouseTemplateTerminalService.list(new LambdaQueryWrapper<TemplateTerminalDO>().eq(TemplateTerminalDO::getHouseTemplateId, templateId).orderByAsc(TemplateTerminalDO::getCreateTime));

        //场景主信息
        List<HouseTemplateScene> templateScenes = iHouseTemplateSceneService.list(new LambdaQueryWrapper<HouseTemplateScene>().eq(HouseTemplateScene::getHouseTemplateId, templateId));

        //场景非暖通设备配置
        List<HouseTemplateSceneAction> sceneActions = iHouseTemplateSceneActionService.list(new LambdaQueryWrapper<HouseTemplateSceneAction>().eq(HouseTemplateSceneAction::getHouseTemplateId, templateId));

        //场景暖通设备配置
        List<HvacConfig> configs = iHvacConfigService.list(new LambdaQueryWrapper<HvacConfig>().eq(HvacConfig::getHouseTemplateId, templateId));

        //场景暖通设备动作配置
        List<HvacAction> hvacActions = iHvacActionService.list(new LambdaQueryWrapper<HvacAction>().eq(HvacAction::getHouseTemplateId, templateId));

        //场景暖通面板动作配置
        List<HvacPanelAction> panelActions = iHvacPanelActionService.list(new LambdaQueryWrapper<HvacPanelAction>().eq(HvacPanelAction::getHouseTemplateId, templateId));

        HouseTemplateConfig config = new HouseTemplateConfig();
        config.setFloorDOS(floorDOS);
        config.setDeviceDOS(deviceDOS);
        config.setRoomDOS(roomDOS);
        config.setTerminalDOS(terminalDOS);

        config.setTemplateScenes(templateScenes);
        config.setSceneActions(sceneActions);
        config.setConfigs(configs);
        config.setHvacActions(hvacActions);
        config.setPanelActions(panelActions);
        return config;
    }

    @Override
    public String getTemplateArea(String templateId) {
        return this.baseMapper.getTemplateArea(templateId);
    }

    private Map<String, String> copyTerminal(List<TemplateTerminalDO> terminalDOS, String houseTemplateId) {
        if (CollectionUtils.isEmpty(terminalDOS)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> terminalMap = Maps.newHashMapWithExpectedSize(terminalDOS.size());
        List<TemplateTerminalDO> data = Lists.newArrayListWithCapacity(terminalDOS.size());
        terminalDOS.forEach(terminal->{
            String terminalId = IdGeneratorUtil.getUUID32();
            terminalMap.put(terminal.getId(),terminalId);
            terminal.setId(terminalId);
            terminal.setHouseTemplateId(houseTemplateId);
            data.add(terminal);
        });
        iHouseTemplateTerminalService.saveBatch(data);
        return terminalMap;
    }

    private void copyDevice(List<TemplateDeviceDO> deviceDOS, Map<String, String> roomMap, Map<String, String> terminalMap,String houseTemplateId) {
//        Map<String, String> deviceMap = Maps.newHashMapWithExpectedSize(deviceDOS.size());
        if (CollectionUtils.isEmpty(deviceDOS)){
            return ;
        }
        List<TemplateDeviceDO> data = Lists.newArrayListWithCapacity(deviceDOS.size());
        deviceDOS.forEach(device->{
            String deviceId = IdGeneratorUtil.getUUID32();
            device.setId(deviceId);
            device.setHouseTemplateId(houseTemplateId);
            device.setRoomId(roomMap.get(device.getRoomId()));
            device.setTerminalId(terminalMap.get(device.getTerminalId()));
            data.add(device);
        });
        iHouseTemplateDeviceService.saveBatch(data);
    }

    private Map<String, String> copyRoom(List<TemplateRoomDO> roomDOS, Map<String, String> floorMap, String houseTemplateId) {
        if (CollectionUtils.isEmpty(roomDOS)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> roomMap = Maps.newHashMapWithExpectedSize(roomDOS.size());
        List<TemplateRoomDO> data = Lists.newArrayListWithCapacity(roomDOS.size());
        roomDOS.forEach(room->{
            String roomId = IdGeneratorUtil.getUUID32();
            roomMap.put(room.getId(),roomId);
            room.setId(roomId);
            room.setHouseTemplateId(houseTemplateId);
            room.setFloorId(floorMap.get(room.getFloorId()));
            data.add(room);
        });
        iHouseTemplateRoomService.saveBatch(data);
        return roomMap;
    }

    private Map<String, String> copyFloor(List<TemplateFloorDO> floorDOS, String houseTemplateId) {
        if (CollectionUtils.isEmpty(floorDOS)){
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, String> floorMap = Maps.newHashMapWithExpectedSize(floorDOS.size());
        List<TemplateFloorDO> data = Lists.newArrayListWithCapacity(floorDOS.size());
        floorDOS.forEach(floor->{
            String floorId = IdGeneratorUtil.getUUID32();
            floorMap.put(floor.getId(),floorId);
            floor.setId(floorId);
            floor.setHouseTemplateId(houseTemplateId);
            data.add(floor);
        });
        iHouseTemplateFloorService.saveBatch(data);
        return floorMap;
    }

}
