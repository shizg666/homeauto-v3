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

    //app操作
    public static final Integer OPEARATE_FLAG_APP = 1;
    public static final Integer ROOM_FLAG = 0;
    //是否是暖通
    public static final Integer HVAC_FLAG_NO = 0;
    public static final Integer HVAC_FLAG_YES = 1;

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
    public AdapterConfigUpdateAckDTO notifyConfigUpdate(String familyId, ContactScreenConfigUpdateTypeEnum typeEnum) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        FamilyTerminalDO familyTerminalDO = familyTerminalService.getMasterTerminal(familyDO.getId());

        AdapterConfigUpdateDTO adapterConfigUpdateDTO = new AdapterConfigUpdateDTO();
        adapterConfigUpdateDTO.setFamilyId(familyDO.getId());
        adapterConfigUpdateDTO.setFamilyCode(familyDO.getCode());
        adapterConfigUpdateDTO.setTerminalType(familyTerminalDO.getType());
        adapterConfigUpdateDTO.setTerminalMac(familyTerminalDO.getMac());
        adapterConfigUpdateDTO.setTime(System.currentTimeMillis());
        adapterConfigUpdateDTO.setUpdateType(typeEnum.code);
        return appService.configUpdate(adapterConfigUpdateDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(FamilySceneDTO request) {
        addCheck(request);
        FamilySceneDO scene = BeanUtil.mapperBean(request,FamilySceneDO.class);
        scene.setId(IdGeneratorUtil.getUUID32());
        save(scene);
        request.setId(scene.getId());
        saveDeviceAction(request);
        saveHvacAction(request);
    }

    private void saveHvacAction(FamilySceneDTO request) {
        List<SceneHvacConfigDTO> hvacConfigDTOs = request.getHvacConfigDTOs();
        if (CollectionUtils.isEmpty(hvacConfigDTOs)){
            return;
        }
        List<FamilySceneHvacConfig> configs = Lists.newArrayListWithCapacity(hvacConfigDTOs.size());
        hvacConfigDTOs.forEach(obj->{
            FamilySceneHvacConfig sceneHvacConfig = BeanUtil.mapperBean(obj,FamilySceneHvacConfig.class);
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
            if (CollectionUtils.isEmpty(config.getHvacActionDTOs())){
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
                if (ROOM_FLAG.equals(hvacAction.getRoomFlag())){
                    //不是分室控制查询所有房间面板
                    panels = getListPanel(hvacAction,request.getFamilyId());
                }else{
                    List<SceneHvacPanelActionDTO> actionDTOS = hvacActionDTO.getPanelActionDTOs();
                    if (CollectionUtils.isEmpty(actionDTOS)){
                        continue;
                    }
                    panels = BeanUtil.mapperList(actionDTOS,FamilySceneHvacConfigActionPanel.class);
                }
                if (!CollectionUtils.isEmpty(panels)){
                    panels.forEach(panel->{
                        panel.setId(IdGeneratorUtil.getUUID32());
                        panel.setHvacActionId(hvacAction.getId());
                        panel.setFamilyId(hvacAction.getId());
                    });
                }
                panelActions.addAll(panels);
            }
        }
        if (!CollectionUtils.isEmpty(saveHvacs)){
            iFamilySceneHvacConfigActionService.saveBatch(saveHvacs);
        }
        if (!CollectionUtils.isEmpty(panelActions)){
            iFamilySceneHvacConfigActionPanelService.saveBatch(panelActions);
        }
    }

    /**
     * 场景暖通配置非分室控制查询所有面板信息
     * @param hvacAction
     * @param familyId
     * @return
     */
    private List<FamilySceneHvacConfigActionPanel> getListPanel(FamilySceneHvacConfigAction hvacAction,String familyId) {
        List<String> panels = iFamilyDeviceService.getListPanel(familyId);
        if (CollectionUtils.isEmpty(panels)){
            return Lists.newArrayListWithCapacity(0);
        }
        List<FamilySceneHvacConfigActionPanel> hvacPanelActions = Lists.newArrayListWithCapacity(panels.size());
        panels.forEach(panelSn->{
            FamilySceneHvacConfigActionPanel panelAction = BeanUtil.mapperBean(hvacAction,FamilySceneHvacConfigActionPanel.class);
            panelAction.setDeviceSn(panelSn);
            panelAction.setId(null);
            hvacPanelActions.add(panelAction);
        });
        return hvacPanelActions;
    }

    /**
     * 保存设备动作信息
     * @param request
     */
    private void saveDeviceAction(FamilySceneDTO request) {
        if(CollectionUtils.isEmpty(request.getDeviceActions())){
            return;
        }
        String sceneId = request.getId();
        List<FamilySceneActionDO> actionList = Lists.newArrayList();
        request.getDeviceActions().forEach(device->{
            List<SceneDeviceActionDetailDTO>  deviceInfos = device.getInfos();
            if(!CollectionUtils.isEmpty(deviceInfos)){
                deviceInfos.forEach(deviceInfo->{
                    FamilySceneActionDO sceneAction = BeanUtil.mapperBean(deviceInfo,FamilySceneActionDO.class);
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
        int count = count(new LambdaQueryWrapper<FamilySceneDO>().eq(FamilySceneDO::getName,request.getName()).eq(FamilySceneDO::getFamilyId,request.getFamilyId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "场景名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FamilySceneDTO request) {
        updateCheck(request);
        FamilySceneDO scene = BeanUtil.mapperBean(request,FamilySceneDO.class);
        updateById(scene);
        deleteAction(request.getId());
        saveDeviceAction(request);
        saveHvacAction(request);
    }

    /**
     * 删除场景动作配置
     * @param sceneId
     */
    private void deleteAction(String sceneId) {
        //删除暖通配置
        iFamilySceneHvacConfigService.remove(new LambdaQueryWrapper<FamilySceneHvacConfig>().eq(FamilySceneHvacConfig::getSceneId,sceneId));
        iFamilySceneHvacConfigActionPanelService.remove(new LambdaQueryWrapper<FamilySceneHvacConfigActionPanel>().eq(FamilySceneHvacConfigActionPanel::getSceneId,sceneId));
        iFamilySceneHvacConfigActionService.remove(new LambdaQueryWrapper<FamilySceneHvacConfigAction>().eq(FamilySceneHvacConfigAction::getSceneId,sceneId));
        //删除非暖通配置
        iFamilySceneActionService.remove(new LambdaQueryWrapper<FamilySceneActionDO>().eq(FamilySceneActionDO::getSceneId,sceneId));
    }

    private void updateCheck(FamilySceneDTO request) {
        FamilySceneDO scene = getById(request.getId());
        if (scene.getName().equals(request.getName())){
            return;
        }
        addCheck(request);
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
        if(OPEARATE_FLAG_APP.equals(request.getType())){
            scene.setUpdateFlagApp(request.getUpdateFlag());
        }else {
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
        if (detailDTO == null){
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
        if (CollectionUtils.isEmpty(scenes)){
            return null;
        }
        List<String> deviceSns = Lists.newArrayList();
        //获取非暖通配置设备号
        List<String> deviceSnList = iFamilySceneActionService.getListDeviceSn(familyId);
        if (!CollectionUtils.isEmpty(deviceSnList)){
            deviceSns.addAll(deviceSnList);
        }

        //获取暖通配置
        List<SyncSceneHvacAtionBO> hvacActions = iFamilySceneActionService.getListSceneHvacAction(familyId);
        if (!CollectionUtils.isEmpty(hvacActions)){
            List<String> ids = hvacActions.stream().map(SyncSceneHvacAtionBO::getSn).collect(Collectors.toList());
            deviceSns.addAll(ids);
        }
        //获取面板配置
        List<FamilySceneHvacConfigActionPanel> panelActionDTOS = iFamilySceneHvacConfigActionPanelService.getListSyncPanelAction(familyId);
        if (!CollectionUtils.isEmpty(panelActionDTOS)){
            List<String> ids = panelActionDTOS.stream().map(FamilySceneHvacConfigActionPanel::getDeviceSn).collect(Collectors.toList());
            deviceSns.addAll(ids);
        }

        Map<String,String> productCodeMap = null;
        if (!CollectionUtils.isEmpty(deviceSns)){
            List<String> disdeviceSns = deviceSns.stream().distinct().collect(Collectors.toList());
            List<SyncSceneDeviceBO> deviceBOS = iFamilyDeviceService.getListSyncSceneDevice(familyId,disdeviceSns);
            if (!CollectionUtils.isEmpty(deviceBOS)){
                productCodeMap = deviceBOS.stream().collect(Collectors.toMap(SyncSceneDeviceBO::getSn,SyncSceneDeviceBO::getProductCode));
            }
        }

        //组装数据
//        if (!CollectionUtils.isEmpty(actions)){
//            actions.stream().collect(Collectors.groupingBy(SyncSceneBO::getSceneId));
//            for (SyncSceneBO action : actions) {
//                action.setProductTag(productCodeMap.get(action.getSn()));
//                action.setHvacTag(HVAC_FLAG_NO);
//            }
//        }

        //组装暖通配置
//        Map<String,List<SyncSceneDTO>> map = buildHvacData(hvacActions,panelActionDTOS,productCodeMap);



//        getSceneHvacAction(familyId,scenes);


        return null;
    }

//    private Map<String, List<SyncSceneDTO>> buildHvacData(List<SyncSceneHvacAtionBO> hvacActions, List<FamilySceneHvacConfigActionPanel> panelActionDTOS,Map<String,String> productCodeMap) {
//        if (!CollectionUtils.isEmpty(hvacActions)){
//            return Maps.newHashMapWithExpectedSize(0);
//        }
//        Map<String,List<SyncSceneDTO>> sceneMap = Maps.newLinkedHashMapWithExpectedSize(hvacActions.size());
////            Map<String,List<SyncSceneHvacAtionBO>> sceneActionMap = hvacActions.stream().collect(Collectors.groupingBy(SyncSceneHvacAtionBO::getSceneId));
//        for (SyncSceneHvacAtionBO sceneHvacAtionBO : hvacActions) {
//            //设备信息
//            SyncSceneDTO sceneDTO = new SyncSceneDTO();
//            sceneDTO.setSn(sceneHvacAtionBO.getSn());
//            sceneDTO.setProductTag(productCodeMap.get(sceneHvacAtionBO.getSn()));
//            sceneDTO.setHvacTag(HVAC_FLAG_YES);
//            List<SyncSceneActionDTO> attrs = Lists.newArrayListWithExpectedSize(1);
//            SyncSceneActionDTO sceneActionDTO = new SyncSceneActionDTO();
//            sceneActionDTO.setAttrValue(sceneHvacAtionBO.getSwitchVal());
//            sceneActionDTO.setAttrTag(sceneHvacAtionBO.getSwitchCode());
//            attrs.add(sceneActionDTO);
//            sceneDTO.setAttrs(attrs);
//
////            if (StringUtil.isEmpty(sceneHvacAtionBO.getActionId())){
////                con
////            }
//            List<SyncSceneDTO> syncSceneDTOS = sceneMap.get(sceneHvacAtionBO.getSceneId());
//            if (CollectionUtils.isEmpty(syncSceneDTOS)){
//                syncSceneDTOS = Lists.newArrayListWithCapacity(hvacActions.size());
//                sceneMap.put(sceneHvacAtionBO.getSceneId(),syncSceneDTOS);
//            }
//            syncSceneDTOS.add(sceneDTO);
//
//            //动作
//            List<SyncSceneHvacActionDTO> syncSceneHvacActionDTOS = Lists.newArrayList();
//            SyncSceneHvacActionDTO hvacActionDTO = new SyncSceneHvacActionDTO();
//            List<SyncSceneActionDTO> attrs = Lists.newArrayListWithExpectedSize(2);
//            //模式
//            SyncSceneActionDTO sceneActionDTO = new SyncSceneActionDTO();
//            sceneActionDTO.setAttrTag(sceneHvacAtionBO.getModeCode());
//            sceneActionDTO.setAttrValue(sceneHvacAtionBO.getModeVal());
//            //风量
//            SyncSceneActionDTO sceneActionDTO2 = new SyncSceneActionDTO();
//            sceneActionDTO2.setAttrTag(sceneHvacAtionBO.getModeCode());
//            sceneActionDTO2.setAttrValue(sceneHvacAtionBO.getWindVal());
//            attrs.add(sceneActionDTO);
//            attrs.add(sceneActionDTO2);
//            hvacActionDTO.setAttrs(attrs);
//            syncSceneHvacActionDTOS.add(hvacActionDTO);
//
//            //面板
//            List<SyncScenePanelActionDTO> temPanel =  Lists.newArrayList();
//
//
//        }
//        return null;
//    }

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
     * @param request
     * @return
     */
    private List<WebSceneDetailHvacConfigVO> gethvacCinfig(FamilySceneDetailQryDTO request) {
        List<WebSceneDetailHvacConfigVO> hvacConfigVOS = this.baseMapper.getListhvacCinfig(request.getId());
        if (CollectionUtils.isEmpty(hvacConfigVOS)){
            return null;
        }
        List<SceneHvacDeviceVO> hvacDeviceVOS = iFamilyDeviceService.getListHvacInfo(request.getFamilyId());
        if (CollectionUtils.isEmpty(hvacDeviceVOS)){
            return null;
        }
        List<WebSceneDetailHvacConfigVO> result = Lists.newArrayListWithExpectedSize(hvacDeviceVOS.size());
        Map<String,List<WebSceneDetailHvacConfigVO>> map = hvacConfigVOS.stream().collect(Collectors.groupingBy(WebSceneDetailHvacConfigVO::getDeviceSn));
        for (SceneHvacDeviceVO hvacDeviceVO : hvacDeviceVOS) {
            WebSceneDetailHvacConfigVO hvacConfigVO = BeanUtil.mapperBean(hvacDeviceVO,WebSceneDetailHvacConfigVO.class);
            if (CollectionUtils.isEmpty(map.get(hvacDeviceVO.getDeviceSn()))){
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
     * @param request
     * @return
     */
    private List<WebSceneDetailDeviceActionVO> getDeviceCinfig(FamilySceneDetailQryDTO request) {
        List<WebSceneDetailDeviceActionBO> detailDeviceActionVOS = this.baseMapper.getSceneDeviceAction(request);
        if (CollectionUtils.isEmpty(detailDeviceActionVOS)){
            return null;
        }
        List<String> productIds = detailDeviceActionVOS.stream().map(WebSceneDetailDeviceActionBO::getProductId).collect(Collectors.toList());
        //获取产品属性信息
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceAttributeInfo(Lists.newArrayList(productIds));
        Map<String,List<SceneDeviceAttributeVO>> map = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));

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
            attributeVOS.forEach(attribute->{
                WebSceneDetailAttributeVO attributeVO = BeanUtil.mapperBean(attribute,WebSceneDetailAttributeVO.class);
                if (attribute.getId().equals(device.getAttributeId())){
                    attributeVO.setSelected(1);
                    attributeVO.setVal(device.getVal());
                }else {
                    attributeVO.setSelected(0);
                }

                attributeListData.add(attributeVO);
            });
            deviceActionVO.setAttributeVOS(attributeListData);

        }
        return result;
    }

}
