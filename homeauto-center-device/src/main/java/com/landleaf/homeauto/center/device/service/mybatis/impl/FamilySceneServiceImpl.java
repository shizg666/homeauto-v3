package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.domain.*;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateSceneAction;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneMapper;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilySceneDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilySceneDetailQryDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilyScenePageVO;
import com.landleaf.homeauto.center.device.service.bridge.AppServiceImpl;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.sync.*;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 家庭情景表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class FamilySceneServiceImpl extends ServiceImpl<FamilySceneMapper, FamilySceneDO> implements IFamilySceneService {

    @Autowired
    private FamilySceneMapper familySceneMapper;

    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IFamilyTerminalService familyTerminalService;

    @Autowired
    private IAppService appService;

    @Autowired
    private IFamilySceneActionService iFamilySceneActionService;

    @Autowired
    private IFamilySceneHvacConfigService iFamilySceneHvacConfigService;

    @Autowired
    private IFamilySceneHvacConfigActionService iFamilySceneHvacConfigActionService;
    @Autowired
    private IFamilySceneHvacConfigActionPanelService iFamilySceneHvacConfigActionPanelService;

    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;

    @Autowired
    private IFamilyDeviceService iFamilyDeviceService;

    @Autowired
    private IAppService iAppService;

    //app操作
    public static final Integer OPEARATE_FLAG_APP = 1;
    public static final Integer ROOM_FLAG = 0;
    //是否是暖通
    public static final Integer HVAC_FLAG_NO = 0;
    public static final Integer HVAC_FLAG_YES = 1;

    //是否是默认场景 0否 1是
    public static final Integer SCENE_DEFAULT = 1;
    public static final Integer SCENE_UNDEFAULT = 0;

    @Override
    public List<FamilySceneBO> getAllSceneList(String familyId) {
        return familySceneMapper.getAllScenesByFamilyId(familyId);
    }

    @Override
    public List<FamilySceneBO> getCommonSceneList(String familyId) {
        return familySceneMapper.getCommonScenesByFamilyId(familyId);
    }

    @Override
    public List<FamilySceneDO> getFamilyScenesBySceneId(String sceneId) {
        FamilySceneDO familySceneDO = getById(sceneId);
        QueryWrapper<FamilySceneDO> familySceneQueryWrapper = new QueryWrapper<>();
        familySceneQueryWrapper.eq("family_id", familySceneDO.getFamilyId());
        return list(familySceneQueryWrapper);
    }

    @Override
    public void notifyConfigUpdate(String familyId, ContactScreenConfigUpdateTypeEnum typeEnum) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        FamilyTerminalDO familyTerminalDO = familyTerminalService.getMasterTerminal(familyDO.getId());

        AdapterConfigUpdateDTO adapterConfigUpdateDTO = new AdapterConfigUpdateDTO();
        adapterConfigUpdateDTO.setFamilyId(familyDO.getId());
        adapterConfigUpdateDTO.setFamilyCode(familyDO.getCode());
        adapterConfigUpdateDTO.setTerminalType(familyTerminalDO.getType());
        adapterConfigUpdateDTO.setTerminalMac(familyTerminalDO.getMac());
        adapterConfigUpdateDTO.setTime(System.currentTimeMillis());
        adapterConfigUpdateDTO.setUpdateType(typeEnum.code);
        appService.configUpdateConfig(adapterConfigUpdateDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(FamilySceneDTO request) {
        checkPanel(request);
        addCheck(request);
        FamilySceneDO scene = BeanUtil.mapperBean(request, FamilySceneDO.class);
        scene.setId(IdGeneratorUtil.getUUID32());
        if(SCENE_UNDEFAULT.equals(request.getDefaultFlag())){
            //不是默认场景 场景编号=id
            scene.setSceneNo(scene.getId());
        }
        save(scene);
        request.setId(scene.getId());
        saveDeviceAction(request);
        saveHvacAction(request);
    }

    private void saveHvacAction(FamilySceneDTO request) {
        List<SceneHvacConfigDTO> hvacConfigDTOs = request.getHvacConfigDTOs();
        if (CollectionUtils.isEmpty(hvacConfigDTOs)) {
            return;
        }
        List<FamilySceneHvacConfig> configs = Lists.newArrayListWithCapacity(hvacConfigDTOs.size());
        hvacConfigDTOs.forEach(obj -> {
            FamilySceneHvacConfig sceneHvacConfig = BeanUtil.mapperBean(obj, FamilySceneHvacConfig.class);
            sceneHvacConfig.setId(IdGeneratorUtil.getUUID32());
            sceneHvacConfig.setSceneId(request.getId());
            sceneHvacConfig.setFamilyId(request.getFamilyId());
            obj.setId(sceneHvacConfig.getId());
            obj.setSceneId(request.getId());
            configs.add(sceneHvacConfig);
        });
        iFamilySceneHvacConfigService.saveBatch(configs);
        List<FamilySceneHvacConfigAction> saveHvacs = Lists.newArrayList();
        List<FamilySceneHvacConfigActionPanel> panelActions = Lists.newArrayList();
        for (SceneHvacConfigDTO config : hvacConfigDTOs) {
            if (CollectionUtils.isEmpty(config.getHvacActionDTOs())) {
                continue;
            }
            for (SceneHvacActionDTO hvacActionDTO : config.getHvacActionDTOs()) {
                FamilySceneHvacConfigAction hvacAction = BeanUtil.mapperBean(hvacActionDTO, FamilySceneHvacConfigAction.class);
                hvacAction.setHvacConfigId(config.getId());
                hvacAction.setId(IdGeneratorUtil.getUUID32());
                hvacAction.setSceneId(request.getId());
                hvacAction.setFamilyId(request.getFamilyId());
                saveHvacs.add(hvacAction);
                List<FamilySceneHvacConfigActionPanel> panels = null;
                if (ROOM_FLAG.equals(hvacAction.getRoomFlag())) {
                    //不是分室控制查询所有房间面板
                    panels = getListPanel(hvacAction, request.getFamilyId());
                } else {
                    List<SceneHvacPanelActionDTO> actionDTOS = hvacActionDTO.getPanelActionDTOs();
                    if (CollectionUtils.isEmpty(actionDTOS)) {
                        continue;
                    }
                    panels = BeanUtil.mapperList(actionDTOS, FamilySceneHvacConfigActionPanel.class);
                }
                if (!CollectionUtils.isEmpty(panels)) {
                    panels.forEach(panel -> {
                        panel.setId(IdGeneratorUtil.getUUID32());
                        panel.setHvacActionId(hvacAction.getId());
                        panel.setFamilyId(request.getFamilyId());
                    });
                }
                panelActions.addAll(panels);
            }
        }
        if (!CollectionUtils.isEmpty(saveHvacs)) {
            iFamilySceneHvacConfigActionService.saveBatch(saveHvacs);
        }
        if (!CollectionUtils.isEmpty(panelActions)) {
            iFamilySceneHvacConfigActionPanelService.saveBatch(panelActions);
        }
    }

    /**
     * 场景暖通配置非分室控制查询所有面板信息
     *
     * @param hvacAction
     * @param familyId
     * @return
     */
    private List<FamilySceneHvacConfigActionPanel> getListPanel(FamilySceneHvacConfigAction hvacAction, String familyId) {
        List<String> panels = iFamilyDeviceService.getListPanel(familyId);
        if (CollectionUtils.isEmpty(panels)) {
            return Lists.newArrayListWithCapacity(0);
        }
        List<FamilySceneHvacConfigActionPanel> hvacPanelActions = Lists.newArrayListWithCapacity(panels.size());
        panels.forEach(panelSn -> {
            FamilySceneHvacConfigActionPanel panelAction = BeanUtil.mapperBean(hvacAction, FamilySceneHvacConfigActionPanel.class);
            panelAction.setDeviceSn(panelSn);
            panelAction.setId(null);
            hvacPanelActions.add(panelAction);
        });
        return hvacPanelActions;
    }

    /**
     * 保存设备动作信息
     *
     * @param request
     */
    private void saveDeviceAction(FamilySceneDTO request) {
        if (CollectionUtils.isEmpty(request.getDeviceActions())) {
            return;
        }
        String sceneId = request.getId();
        List<FamilySceneActionDO> actionList = Lists.newArrayList();
        request.getDeviceActions().forEach(device -> {
            List<SceneDeviceActionDetailDTO> deviceInfos = device.getInfos();
            if (!CollectionUtils.isEmpty(deviceInfos)) {
                deviceInfos.forEach(deviceInfo -> {
                    FamilySceneActionDO sceneAction = BeanUtil.mapperBean(deviceInfo, FamilySceneActionDO.class);
                    sceneAction.setDeviceSn(device.getDeviceSn());
                    sceneAction.setSceneId(sceneId);
                    sceneAction.setFamilyId(request.getFamilyId());
                    sceneAction.setProductAttributeId(deviceInfo.getAttributeId());
                    sceneAction.setProductAttributeCode(deviceInfo.getAttributeCode());
                    actionList.add(sceneAction);
                });
            }
        });
        iFamilySceneActionService.saveBatch(actionList);
    }

    private void addCheck(FamilySceneDTO request) {
        if(SCENE_DEFAULT.equals(request.getDefaultFlag())){
            if ( StringUtil.isEmpty(request.getSceneNo())){
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "默认场景编号不能为空");
            }
            int count1 = count(new LambdaQueryWrapper<FamilySceneDO>().eq(FamilySceneDO::getSceneNo, request.getSceneNo()).eq(FamilySceneDO::getFamilyId, request.getFamilyId()));
            if (count1 > 0) {
                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "场景编号已存在");
            }
        }
        int count = count(new LambdaQueryWrapper<FamilySceneDO>().eq(FamilySceneDO::getName, request.getName()).eq(FamilySceneDO::getFamilyId, request.getFamilyId()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "场景名称已存在");
        }
    }

    /**
     * 面板配置
     * @param request
     */
    private void checkPanel(FamilySceneDTO request) {
        List<SceneHvacConfigDTO> hvacConfigDTOS = request.getHvacConfigDTOs();
        if (CollectionUtils.isEmpty(hvacConfigDTOS)) {
            return;
        }
        for (SceneHvacConfigDTO config : hvacConfigDTOS) {
            if (CollectionUtils.isEmpty(config.getHvacActionDTOs())) {
                continue;
            }
            List<SceneHvacActionDTO> hvacActionDTOS = config.getHvacActionDTOs();
            for (SceneHvacActionDTO hvacActionDTO : hvacActionDTOS) {
                if (CollectionUtils.isEmpty(hvacActionDTO.getPanelActionDTOs())) {
                    continue;
                }
                List<SceneHvacPanelActionDTO> panelActionDTOS = hvacActionDTO.getPanelActionDTOs();
                List<String> paleIds = panelActionDTOS.stream().map(SceneHvacPanelActionDTO::getDeviceSn).collect(Collectors.toList());
                List<String> paleIds2 = paleIds.stream().distinct().collect(Collectors.toList());
                if (paleIds.size() != paleIds2.size()) {
                    throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "面板配置重复");
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FamilySceneDTO request) {
        updateCheck(request);
        FamilySceneDO scene = BeanUtil.mapperBean(request, FamilySceneDO.class);
        updateById(scene);
        deleteAction(request.getId());
        saveDeviceAction(request);
        saveHvacAction(request);
    }

    /**
     * 删除场景动作配置
     *
     * @param sceneId
     */
    private void deleteAction(String sceneId) {
        //删除暖通配置
        iFamilySceneHvacConfigService.remove(new LambdaQueryWrapper<FamilySceneHvacConfig>().eq(FamilySceneHvacConfig::getSceneId, sceneId));
        iFamilySceneHvacConfigActionPanelService.remove(new LambdaQueryWrapper<FamilySceneHvacConfigActionPanel>().eq(FamilySceneHvacConfigActionPanel::getSceneId, sceneId));
        iFamilySceneHvacConfigActionService.remove(new LambdaQueryWrapper<FamilySceneHvacConfigAction>().eq(FamilySceneHvacConfigAction::getSceneId, sceneId));
        //删除非暖通配置
        iFamilySceneActionService.remove(new LambdaQueryWrapper<FamilySceneActionDO>().eq(FamilySceneActionDO::getSceneId, sceneId));
    }

    private void updateCheck(FamilySceneDTO request) {
        checkPanel(request);
        FamilySceneDO scene = getById(request.getId());
        if (scene.getName().equals(request.getName())) {
            return;
        }
        int count = count(new LambdaQueryWrapper<FamilySceneDO>().eq(FamilySceneDO::getName, request.getName()).eq(FamilySceneDO::getFamilyId, request.getFamilyId()));
        if (count > 0) {
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "场景名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        removeById(request.getId());
        deleteAction(request.getId());
    }

    @Override
    public void updateAppOrScreenFlag(SwitchSceneUpdateFlagDTO request) {
        FamilySceneDO scene = new FamilySceneDO();
        scene.setId(request.getId());
        if (OPEARATE_FLAG_APP.equals(request.getType())) {
            scene.setUpdateFlagApp(request.getUpdateFlag());
        } else {
            scene.setUpdateFlagScreen(request.getUpdateFlag());
        }
        updateById(scene);
    }

    @Override
    public List<FamilyScenePageVO> getListScene(String familyId) {
        return this.baseMapper.getListScene(familyId);
    }

    @Override
    public WebSceneDetailDTO getSceneDetail(FamilySceneDetailQryDTO request) {
        WebSceneDetailDTO detailDTO = this.baseMapper.getSceneDetail(request.getId());
        if (detailDTO == null) {
            return null;
        }
        //非暖通配置信息
        List<WebSceneDetailDeviceActionVO> deviceActions = getDeviceCinfig(request);
        //暖通配置信息
        List<WebSceneDetailHvacConfigVO> hvacActions = gethvacCinfig(request);
        detailDTO.setDeviceActions(deviceActions);
        detailDTO.setHvacConfigDTOs(hvacActions);
        return detailDTO;
    }

    @Override
    public List<SyncSceneInfoDTO> getListSyncScene(String familyId) {
        List<SyncSceneInfoDTO> scenes = this.baseMapper.getListSyncScene(familyId);
        if (CollectionUtils.isEmpty(scenes)) {
            return null;
        }
        List<String> deviceSns = Lists.newArrayList();
        //获取非暖通配置设备号
        List<String> deviceSnList = iFamilySceneActionService.getListDeviceSn(familyId);
        if (!CollectionUtils.isEmpty(deviceSnList)) {
            deviceSns.addAll(deviceSnList);
        }

        //获取暖通配置
        List<SyncSceneHvacAtionBO> hvacActions = iFamilySceneActionService.getListSceneHvacAction(familyId);
        if (!CollectionUtils.isEmpty(hvacActions)) {
            List<String> ids = hvacActions.stream().map(SyncSceneHvacAtionBO::getSn).collect(Collectors.toList());
            deviceSns.addAll(ids);
        }
        //获取面板配置
        List<FamilySceneHvacConfigActionPanel> panelActionDTOS = iFamilySceneHvacConfigActionPanelService.getListSyncPanelAction(familyId);
        if (!CollectionUtils.isEmpty(panelActionDTOS)) {
            List<String> ids = panelActionDTOS.stream().map(FamilySceneHvacConfigActionPanel::getDeviceSn).collect(Collectors.toList());
            deviceSns.addAll(ids);
        }

        Map<String, String> productCodeMap = null;
        if (!CollectionUtils.isEmpty(deviceSns)) {
            List<String> disdeviceSns = deviceSns.stream().distinct().collect(Collectors.toList());
            List<SyncSceneDeviceBO> deviceBOS = iFamilyDeviceService.getListSyncSceneDevice(familyId, disdeviceSns);
            if (!CollectionUtils.isEmpty(deviceBOS)) {
                productCodeMap = deviceBOS.stream().collect(Collectors.toMap(SyncSceneDeviceBO::getSn, SyncSceneDeviceBO::getProductCode));
            }
        }


        //组装暖通配置
        Map<String, List<SyncSceneDTO>> mapHvac = buildHvacData(hvacActions, panelActionDTOS, productCodeMap);
        if (mapHvac == null) {
            return scenes;
        }

        for (SyncSceneInfoDTO scene : scenes) {
            if (CollectionUtils.isEmpty(scene.getActions())) {
                //为空说明没有非暖通的配置
                scene.setActions(mapHvac.get(scene.getId()));
            } else {
                for (SyncSceneDTO device : scene.getActions()) {
                    device.setProductTag(productCodeMap.get(device.getDeviceSn()));
                    device.setHvacTag(HVAC_FLAG_NO);
                }
                if (!CollectionUtils.isEmpty(mapHvac.get(scene.getId()))) {
                    scene.getActions().addAll(mapHvac.get(scene.getId()));
                }
            }
        }
        return scenes;
    }

    @Override
    public void getSyncInfo(String familyId) {
        //发送同步消息
        iAppService.configUpdateConfig(new AdapterConfigUpdateDTO(ContactScreenConfigUpdateTypeEnum.SCENE.code));
        iAppService.configUpdateConfig(new AdapterConfigUpdateDTO(ContactScreenConfigUpdateTypeEnum.FLOOR_ROOM_DEVICE.code));
    }

    @Override
    public void deleteByFamilyId(String familyId) {
        this.remove(new LambdaQueryWrapper<FamilySceneDO>().eq(FamilySceneDO::getFamilyId, familyId));
        //删除暖通配置
        iFamilySceneHvacConfigService.remove(new LambdaQueryWrapper<FamilySceneHvacConfig>().eq(FamilySceneHvacConfig::getFamilyId, familyId));
        iFamilySceneHvacConfigActionPanelService.remove(new LambdaQueryWrapper<FamilySceneHvacConfigActionPanel>().eq(FamilySceneHvacConfigActionPanel::getFamilyId, familyId));
        iFamilySceneHvacConfigActionService.remove(new LambdaQueryWrapper<FamilySceneHvacConfigAction>().eq(FamilySceneHvacConfigAction::getFamilyId, familyId));
        //删除非暖通配置
        iFamilySceneActionService.remove(new LambdaQueryWrapper<FamilySceneActionDO>().eq(FamilySceneActionDO::getFamilyId, familyId));
    }

    private Map<String, List<SyncSceneDTO>> buildHvacData(List<SyncSceneHvacAtionBO> hvacActions, List<FamilySceneHvacConfigActionPanel> panelActionDTOS, Map<String, String> productCodeMap) {
        if (CollectionUtils.isEmpty(hvacActions)) {
            return Maps.newHashMapWithExpectedSize(0);
        }
        Map<String, List<SyncSceneDTO>> sceneMap = Maps.newLinkedHashMapWithExpectedSize(hvacActions.size());

        Map<String, List<FamilySceneHvacConfigActionPanel>> panelMap = null;
        if (!CollectionUtils.isEmpty(panelActionDTOS)) {
            panelMap = panelActionDTOS.stream().collect(Collectors.groupingBy(FamilySceneHvacConfigActionPanel::getHvacActionId));
        }
        //按场景分组
        Map<String, List<SyncSceneHvacAtionBO>> scenedataMap = hvacActions.stream().collect(Collectors.groupingBy(SyncSceneHvacAtionBO::getSceneId));

        for (Map.Entry<String, List<SyncSceneHvacAtionBO>> sceneEntry : scenedataMap.entrySet()) {

            //按设备分组
            Map<String, List<SyncSceneHvacAtionBO>> deviceDataMap = sceneEntry.getValue().stream().collect(Collectors.groupingBy(SyncSceneHvacAtionBO::getSn));
            for (Map.Entry<String, List<SyncSceneHvacAtionBO>> deviceEntry : deviceDataMap.entrySet()) {
                String deviceSn = deviceEntry.getKey();
                List<SyncSceneHvacAtionBO> sceneHvacAtionBOList = deviceEntry.getValue();
                //设备信息
                SyncSceneDTO sceneDTO = new SyncSceneDTO();
                sceneDTO.setDeviceSn(deviceSn);
                sceneDTO.setProductTag(productCodeMap.get(deviceSn));
                sceneDTO.setHvacTag(HVAC_FLAG_YES);
                List<SyncSceneActionDTO> attrs = Lists.newArrayListWithExpectedSize(1);
                SyncSceneActionDTO sceneActionDTO = new SyncSceneActionDTO();
                //系统开关
                sceneActionDTO.setAttrValue(sceneHvacAtionBOList.get(0).getSwitchVal());
                sceneActionDTO.setAttrTag(sceneHvacAtionBOList.get(0).getSwitchCode());
                attrs.add(sceneActionDTO);
                sceneDTO.setAttrs(attrs);
                List<SyncSceneDTO> syncSceneDTOS = sceneMap.get(sceneEntry.getKey());
                if (CollectionUtils.isEmpty(syncSceneDTOS)) {
                    syncSceneDTOS = Lists.newArrayListWithCapacity(hvacActions.size());
                    sceneMap.put(sceneEntry.getKey(), syncSceneDTOS);
                }
                syncSceneDTOS.add(sceneDTO);
                //暖通动作
                //hvacList 集合
                List<SyncSceneHvacActionDTO> syncSceneHvacActionDTOS = Lists.newArrayList();
                sceneDTO.setHvacList(syncSceneHvacActionDTOS);
                for (SyncSceneHvacAtionBO sceneHvacAtionBO : sceneHvacAtionBOList) {
                    if (StringUtil.isEmpty(sceneHvacAtionBO.getActionId())) {
                        continue;
                    }
                    SyncSceneHvacActionDTO hvacActionDTO = new SyncSceneHvacActionDTO();
                    List<SyncSceneActionDTO> attrsList = Lists.newArrayListWithExpectedSize(2);
                    //模式
                    SyncSceneActionDTO sceneActionDTO1 = new SyncSceneActionDTO();
                    sceneActionDTO1.setAttrTag(sceneHvacAtionBO.getModeCode());
                    sceneActionDTO1.setAttrValue(sceneHvacAtionBO.getModeVal());
                    //风量
                    if (!StringUtil.isEmpty(sceneHvacAtionBO.getWindCode())) {
                        SyncSceneActionDTO sceneActionDTO2 = new SyncSceneActionDTO();
                        sceneActionDTO2.setAttrTag(sceneHvacAtionBO.getWindCode());
                        sceneActionDTO2.setAttrValue(sceneHvacAtionBO.getWindVal());
                        attrsList.add(sceneActionDTO2);
                    }
                    attrsList.add(sceneActionDTO1);
                    hvacActionDTO.setAttrs(attrsList);
                    syncSceneHvacActionDTOS.add(hvacActionDTO);

                    if (panelMap == null || !panelMap.containsKey(sceneHvacAtionBO.getActionId())) {
                        continue;
                    }
                    //面板
                    List<SyncScenePanelActionDTO> temPanel = Lists.newArrayList();
                    for (FamilySceneHvacConfigActionPanel panle : panelMap.get(sceneHvacAtionBO.getActionId())) {
                        SyncScenePanelActionDTO syncScenePanel = new SyncScenePanelActionDTO();
                        syncScenePanel.setDeviceSn(panle.getDeviceSn());
                        syncScenePanel.setProductTag(productCodeMap.get(panle.getDeviceSn()));

                        //面板开关
                        List<SyncSceneActionDTO> attrsPanel = Lists.newArrayListWithExpectedSize(1);
                        SyncSceneActionDTO sceneActionDTOP1 = new SyncSceneActionDTO();
                        sceneActionDTOP1.setAttrValue(panle.getSwitchVal());
                        sceneActionDTOP1.setAttrTag(panle.getSwitchCode());
                        //面板温度
                        SyncSceneActionDTO sceneActionDTOP2 = new SyncSceneActionDTO();
                        sceneActionDTOP2.setAttrValue(panle.getTemperatureVal());
                        sceneActionDTOP2.setAttrTag(panle.getTemperatureCode());
                        attrsPanel.add(sceneActionDTOP1);
                        attrsPanel.add(sceneActionDTOP2);
                        syncScenePanel.setAttrs(attrsPanel);
                        temPanel.add(syncScenePanel);
                    }
                    hvacActionDTO.setTemPanel(temPanel);
                }


            }
        }


//        for (SyncSceneHvacAtionBO sceneHvacAtionBO : hvacActions) {
//            //设备信息
//            SyncSceneDTO sceneDTO = new SyncSceneDTO();
//            sceneDTO.setSn(sceneHvacAtionBO.getSn());
//            sceneDTO.setProductTag(productCodeMap.get(sceneHvacAtionBO.getSn()));
//            sceneDTO.setHvacTag(HVAC_FLAG_YES);
//            List<SyncSceneActionDTO> attrs = Lists.newArrayListWithExpectedSize(1);
//            SyncSceneActionDTO sceneActionDTO = new SyncSceneActionDTO();
//            //系统开关
//            sceneActionDTO.setAttrValue(sceneHvacAtionBO.getSwitchVal());
//            sceneActionDTO.setAttrTag(sceneHvacAtionBO.getSwitchCode());
//            attrs.add(sceneActionDTO);
//            sceneDTO.setAttrs(attrs);
//            List<SyncSceneDTO> syncSceneDTOS = sceneMap.get(sceneHvacAtionBO.getSceneId());
//            if (CollectionUtils.isEmpty(syncSceneDTOS)){
//                syncSceneDTOS = Lists.newArrayListWithCapacity(hvacActions.size());
//                sceneMap.put(sceneHvacAtionBO.getSceneId(),syncSceneDTOS);
//            }
//            syncSceneDTOS.add(sceneDTO);
//
//            if (StringUtil.isEmpty(sceneHvacAtionBO.getActionId())){
//                continue;
//            }
//
//            //暖通动作
//
//            //hvacList 集合
//            List<SyncSceneHvacActionDTO> syncSceneHvacActionDTOS = Lists.newArrayList();
//            SyncSceneHvacActionDTO hvacActionDTO = new SyncSceneHvacActionDTO();
//            List<SyncSceneActionDTO> attrsList = Lists.newArrayListWithExpectedSize(2);
//            //模式
//            SyncSceneActionDTO sceneActionDTO1 = new SyncSceneActionDTO();
//            sceneActionDTO1.setAttrTag(sceneHvacAtionBO.getModeCode());
//            sceneActionDTO1.setAttrValue(sceneHvacAtionBO.getModeVal());
//            //风量
//            SyncSceneActionDTO sceneActionDTO2 = new SyncSceneActionDTO();
//            sceneActionDTO2.setAttrTag(sceneHvacAtionBO.getWindCode());
//            sceneActionDTO2.setAttrValue(sceneHvacAtionBO.getWindVal());
//            attrsList.add(sceneActionDTO1);
//            attrsList.add(sceneActionDTO2);
//            hvacActionDTO.setAttrs(attrsList);
//            syncSceneHvacActionDTOS.add(hvacActionDTO);
//            sceneDTO.setHvacList(syncSceneHvacActionDTOS);
//            if (panelMap == null || !panelMap.containsKey(sceneHvacAtionBO.getActionId())){
//                continue;
//            }
//            //面板
//            List<SyncScenePanelActionDTO> temPanel =  Lists.newArrayList();
//            panelMap.get(sceneHvacAtionBO.getActionId()).forEach(panle->{
//                SyncScenePanelActionDTO syncScenePanel = new SyncScenePanelActionDTO();
//                syncScenePanel.setSn(panle.getDeviceSn());
//                syncScenePanel.setProductTag(productCodeMap.get(panle.getDeviceSn()));
//
//                //面板开关
//                List<SyncSceneActionDTO> attrsPanel = Lists.newArrayListWithExpectedSize(1);
//                SyncSceneActionDTO sceneActionDTOP1 = new SyncSceneActionDTO();
//                sceneActionDTOP1.setAttrValue(panle.getSwitchVal());
//                sceneActionDTOP1.setAttrTag(panle.getSwitchCode());
//                //面板温度
//                SyncSceneActionDTO sceneActionDTOP2 = new SyncSceneActionDTO();
//                sceneActionDTOP2.setAttrValue(panle.getTemperatureVal());
//                sceneActionDTOP2.setAttrTag(panle.getTemperatureCode());
//                attrsPanel.add(sceneActionDTOP1);
//                attrsPanel.add(sceneActionDTOP2);
//                syncScenePanel.setAttrs(attrsPanel);
//                temPanel.add(syncScenePanel);
//            });
//            hvacActionDTO.setTemPanel(temPanel);
//
//        }
        return sceneMap;
    }

    //获取暖通配置
//    private void getSceneHvacAction(String familyId, List<SyncSceneInfoDTO> scenes) {
//        List<SyncSceneHvacAtionBO> actions = iFamilySceneActionService.getListSceneHvacAction(familyId);
//        if (CollectionUtils.isEmpty(actions)){
//            return;
//        }
//
//        scenes.forEach(scene->{
//            List<FamilySceneActionDO> actionDOS = mapData.get(scene.getId());
//            if (!CollectionUtils.isEmpty(actionDOS)){
//                actionDOS.forEach(action->{
//                    FamilySceneActionDO
//                });
//            }
//        });
//    }

    /**
     * 暖通配置信息
     *
     * @param request
     * @return
     */
    private List<WebSceneDetailHvacConfigVO> gethvacCinfig(FamilySceneDetailQryDTO request) {
        List<WebSceneDetailHvacConfigVO> hvacConfigVOS = this.baseMapper.getListhvacCinfig(request.getId());
        if (CollectionUtils.isEmpty(hvacConfigVOS)) {
            return null;
        }
        List<SceneHvacDeviceVO> hvacDeviceVOS = iFamilyDeviceService.getListHvacInfo(request.getFamilyId());
        if (CollectionUtils.isEmpty(hvacDeviceVOS)) {
            return null;
        }
        List<WebSceneDetailHvacConfigVO> result = Lists.newArrayListWithExpectedSize(hvacDeviceVOS.size());
        Map<String, List<WebSceneDetailHvacConfigVO>> map = hvacConfigVOS.stream().collect(Collectors.groupingBy(WebSceneDetailHvacConfigVO::getDeviceSn));
        for (SceneHvacDeviceVO hvacDeviceVO : hvacDeviceVOS) {
            WebSceneDetailHvacConfigVO hvacConfigVO = BeanUtil.mapperBean(hvacDeviceVO, WebSceneDetailHvacConfigVO.class);
            if (CollectionUtils.isEmpty(map.get(hvacDeviceVO.getDeviceSn()))) {
                continue;
            }
            hvacConfigVO.setSwitchVal(map.get(hvacDeviceVO.getDeviceSn()).get(0).getSwitchVal());
            hvacConfigVO.setHvacActionDTOs(map.get(hvacDeviceVO.getDeviceSn()).get(0).getHvacActionDTOs());
            result.add(hvacConfigVO);
        }
        return result;
    }

    /**
     * 非暖通配置信息
     *
     * @param request
     * @return
     */
    private List<WebSceneDetailDeviceActionVO> getDeviceCinfig(FamilySceneDetailQryDTO request) {
        List<WebSceneDetailDeviceActionBO> detailDeviceActionVOS = this.baseMapper.getSceneDeviceAction(request);
        if (CollectionUtils.isEmpty(detailDeviceActionVOS)) {
            return null;
        }
        List<String> productIds = detailDeviceActionVOS.stream().map(WebSceneDetailDeviceActionBO::getProductId).collect(Collectors.toList());
        //获取产品属性信息
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceAttributeInfo(Lists.newArrayList(productIds));
        Map<String, List<SceneDeviceAttributeVO>> map = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));

        List<WebSceneDetailDeviceActionVO> result = Lists.newArrayListWithExpectedSize(detailDeviceActionVOS.size());
        for (WebSceneDetailDeviceActionBO device : detailDeviceActionVOS) {
            WebSceneDetailDeviceActionVO deviceActionVO = BeanUtil.mapperBean(device, WebSceneDetailDeviceActionVO.class);
            result.add(deviceActionVO);
            if (!map.containsKey(device.getProductId())) {
                continue;
            }
            List<SceneDeviceAttributeVO> attributeVOS = map.get(device.getProductId());
            if (CollectionUtils.isEmpty(attributeVOS)) {
                continue;
            }
            List<WebSceneDetailAttributeVO> attributeListData = Lists.newArrayList();
            attributeVOS.forEach(attribute -> {
                WebSceneDetailAttributeVO attributeVO = BeanUtil.mapperBean(attribute, WebSceneDetailAttributeVO.class);
                Map<String, String> infoBOMap = device.getInfoBOS().stream().collect(Collectors.toMap(SceneAttributeInfoBO::getAttributeId, SceneAttributeInfoBO::getVal));
                if (infoBOMap.containsKey(attribute.getId())) {
                    attributeVO.setSelected(1);
                    attributeVO.setVal(infoBOMap.get(attribute.getId()));
                } else {
                    attributeVO.setSelected(0);
                }

                attributeListData.add(attributeVO);
            });
            deviceActionVO.setAttributeVOS(attributeListData);

        }
        return result;
    }
}
