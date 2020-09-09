package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.*;
import com.landleaf.homeauto.center.device.model.mapper.HouseTemplateSceneMapper;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDTO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 户型情景表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
@Service
public class HouseTemplateSceneServiceImpl extends ServiceImpl<HouseTemplateSceneMapper, HouseTemplateScene> implements IHouseTemplateSceneService {

    @Autowired
    private IHouseTemplateSceneActionService iHouseTemplateSceneActionService;
    @Autowired
    private IHvacConfigService iHvacConfigService;
    @Autowired
    private IHvacActionService iHvacActionService;
    @Autowired
    private IHvacPanelActionService iHvacPanelActionService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;

    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;

    public static final Integer ROOM_FLAG = 1;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(HouseSceneDTO request) {
        addCheck(request);
        HouseTemplateScene scene = BeanUtil.mapperBean(request,HouseTemplateScene.class);
        scene.setId(IdGeneratorUtil.getUUID32());
        save(scene);
        request.setId(scene.getId());
        saveDeviceAction(request);
        saveHvacAction(request);
    }

    /**
     * 保存暖通动作信息
     * @param request
     */
    private void saveHvacAction(HouseSceneDTO request) {
        List<SceneHvacConfigDTO> hvacConfigDTOs = request.getHvacConfigDTOs();
        if (CollectionUtils.isEmpty(hvacConfigDTOs)){
            return;
        }
        hvacConfigDTOs.forEach(obj->{
            obj.setId(IdGeneratorUtil.getUUID32());
            obj.setSceneId(request.getId());
        });
        List<HvacConfig> configs = BeanUtil.mapperList(request.getHvacConfigDTOs(),HvacConfig.class);
        iHvacConfigService.saveBatch(configs);
        List<HvacAction> actions = Lists.newArrayListWithCapacity(hvacConfigDTOs.size());
        List<HvacPanelAction> panelActions = null;
        for (SceneHvacConfigDTO config : hvacConfigDTOs) {
            if (config.getHvacActionDTO() == null) {
                continue;
            }
            HvacAction hvacAction = BeanUtil.mapperBean(config.getHvacActionDTO(), HvacAction.class);
            hvacAction.setHvacConfigId(config.getId());
            hvacAction.setId(IdGeneratorUtil.getUUID32());
            hvacAction.setSceneId(request.getId());
            actions.add(hvacAction);
            List<SceneHvacPanelActionDTO> actionDTOS = config.getHvacActionDTO().getPanelActionDTOs();

            if (ROOM_FLAG.equals(hvacAction.getRoomFlag())){
                //不是分室控制查询所有房间面板
                panelActions = getListPanel(hvacAction,request.getHouseTemplateId());
            }
            if (CollectionUtils.isEmpty(actionDTOS)){
                continue;
            }
            panelActions = BeanUtil.mapperList(config.getHvacActionDTO().getPanelActionDTOs(),HvacPanelAction.class);
        }
        if (!CollectionUtils.isEmpty(actions)){
            iHvacActionService.saveBatch(actions);
        }
        if (!CollectionUtils.isEmpty(panelActions)){
            iHvacPanelActionService.saveBatch(panelActions);
        }
    }

    /**
     * 场景暖通配置非分室控制查询所有面板信息
     * @param hvacAction
     * @param templateId
     * @return
     */
    private List<HvacPanelAction> getListPanel(HvacAction hvacAction,String templateId) {
        List<String> panels = iHouseTemplateDeviceService.getListPanel(templateId);
        if (CollectionUtils.isEmpty(panels)){
            return Lists.newArrayListWithCapacity(0);
        }
        List<HvacPanelAction> hvacPanelActions = Lists.newArrayListWithCapacity(panels.size());
        panels.forEach(panelSn->{
            HvacPanelAction panelAction = BeanUtil.mapperBean(hvacAction,HvacPanelAction.class);
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
    private void saveDeviceAction(HouseSceneDTO request) {
        if(CollectionUtils.isEmpty(request.getDeviceActions())){
            return;
        }
        List<HouseTemplateSceneAction> actionList = BeanUtil.mapperList(request.getDeviceActions(),HouseTemplateSceneAction.class);
        String sceneId = request.getId();
        actionList.forEach(action ->{
            action.setSceneId(sceneId);
        });
        iHouseTemplateSceneActionService.saveBatch(actionList);
    }

    private void addCheck(HouseSceneDTO request) {
        int count = count(new LambdaQueryWrapper<HouseTemplateScene>().eq(HouseTemplateScene::getName,request.getName()).eq(HouseTemplateScene::getHouseTemplateId,request.getHouseTemplateId()));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "场景名称已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HouseSceneDTO request) {
        updateCheck(request);
        HouseTemplateScene scene = BeanUtil.mapperBean(request,HouseTemplateScene.class);
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
        iHvacConfigService.remove(new LambdaQueryWrapper<HvacConfig>().eq(HvacConfig::getSceneId,sceneId));
        iHvacPanelActionService.remove(new LambdaQueryWrapper<HvacPanelAction>().eq(HvacPanelAction::getSceneId,sceneId));
        iHvacActionService.remove(new LambdaQueryWrapper<HvacAction>().eq(HvacAction::getSceneId,sceneId));
        //删除非暖通配置
        iHouseTemplateSceneActionService.remove(new LambdaQueryWrapper<HouseTemplateSceneAction>().eq(HouseTemplateSceneAction::getSceneId,sceneId));
    }



    private void updateCheck(HouseSceneDTO request) {
        HouseTemplateScene scene = getById(request.getId());
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
    public List<ScenePageVO> getListScene(String templageId) {
        return this.baseMapper.getListScene(templageId);
    }

    @Override
    public WebSceneDetailDTO getSceneDetail(WebSceneDetailQryDTO request) {
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

    /**
     * 暖通配置信息
     * @param request
     * @return
     */
    private List<WebSceneDetailHvacConfigVO> gethvacCinfig(WebSceneDetailQryDTO request) {

        List<WebSceneDetailHvacConfigVO> hvacConfigVOS = this.baseMapper.getListhvacCinfig(request.getId());
        if (CollectionUtils.isEmpty(hvacConfigVOS)){
            return null;
        }
        List<SceneHvacDeviceVO> hvacDeviceVOS = iHouseTemplateDeviceService.getListHvacInfo(request.getHouseTemplateId());
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
            hvacConfigVO.setWindSpeeds(map.get(hvacDeviceVO.getDeviceSn()).get(0).getWindSpeeds());
            hvacConfigVO.setHvacActionDTO(map.get(hvacDeviceVO.getDeviceSn()).get(0).getHvacActionDTO());
            result.add(hvacConfigVO);
        }
        return result;
    }

    /**
     * 非暖通配置信息
     * @param request
     * @return
     */
    private List<WebSceneDetailDeviceActionVO> getDeviceCinfig(WebSceneDetailQryDTO request) {
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
                }else {
                    attributeVO.setSelected(0);
                }
                attributeVO.setVal(device.getVal());
                attributeListData.add(attributeVO);
            });
            deviceActionVO.setAttributeVOS(attributeListData);

        }
        return result;
    }
}
