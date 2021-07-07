package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.*;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.*;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneMapper;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.WebSceneDetailVO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
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
 * @author wenyilu
 * @since 2020-08-28
 */
@Service
public class FamilySceneServiceImpl extends ServiceImpl<FamilySceneMapper, FamilyScene> implements IFamilySceneService {

    @Autowired
    private IdService idService;
    @Autowired
    private IFamilySceneActionConfigService iFamilySceneActionConfigService;
    @Autowired
    private ITemplateOperateService iTemplateOperateService;
    @Autowired
    private IThirdFamilySceneIconService iThirdFamilySceneIconService;
    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;

    @Override
    public List<FamilyScene> getListSceneByfId(Long familyId) {
        List<FamilyScene> data = list(new LambdaQueryWrapper<FamilyScene>().eq(FamilyScene::getFamilyId,familyId).select(FamilyScene::getName,FamilyScene::getIcon,FamilyScene::getId));
        return data;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addScene(Long familyId,JZFamilySceneDTO request) {
        checkName(familyId,request);
        FamilyScene scene = new FamilyScene();
        scene.setName(request.getName());
        scene.setId(idService.getSegmentId());
        scene.setDefaultFlag(0);
        scene.setFamilyId(familyId);
        scene.setType(1);
        scene.setUpdateFlag(1);
        save(scene);
        ThirdFamilySceneIcon familySceneIcon = new ThirdFamilySceneIcon();
        familySceneIcon.setIcon(request.getIcon());
        familySceneIcon.setSceneId(scene.getId());
        familySceneIcon.setFamilyId(familyId);
        iThirdFamilySceneIconService.save(familySceneIcon);
        saveDeviceAction(familyId,scene.getId(),request.getDevices());
        // todo 通知修改
//        iTemplateOperateService.sendEvent(TemplateOperateEvent.builder().templateId(request.getHouseTemplateId()).typeEnum(ContactScreenConfigUpdateTypeEnum.SCENE).build());
        return scene.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateScene(Long familyId, JZFamilySceneDTO request) {
        updateCheck(familyId,request);
        FamilyScene scene = BeanUtil.mapperBean(request,FamilyScene.class);
        updateById(scene);
        ThirdFamilySceneIcon familySceneIcon = new ThirdFamilySceneIcon();
        familySceneIcon.setIcon(request.getIcon());
        familySceneIcon.setSceneId(scene.getId());
        iThirdFamilySceneIconService.update(new LambdaUpdateWrapper<ThirdFamilySceneIcon>().eq(ThirdFamilySceneIcon::getSceneId,request.getSceneId()).set(ThirdFamilySceneIcon::getIcon,request.getIcon()));
        deleteAction(request.getSceneId());
        saveDeviceAction(familyId,scene.getId(),request.getDevices());
        // todo 通知修改
//        iTemplateOperateService.sendEvent(TemplateOperateEvent.builder().templateId(request.getHouseTemplateId()).typeEnum(ContactScreenConfigUpdateTypeEnum.SCENE).build());
    }

    @Override
    public JZSceneDetailVO getDetailBySceneId(Long sceneId) {
        FamilyScene scene = getById(sceneId);
        if (Objects.isNull(scene)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "场景不存在"+sceneId);
        }
        String  icon = iThirdFamilySceneIconService.getIconBySceneId(sceneId);
        JZSceneDetailVO sceneDetailVO = new JZSceneDetailVO();
        sceneDetailVO.setName(scene.getName());
        sceneDetailVO.setDefaultFlag(scene.getDefaultFlag());
        sceneDetailVO.setIcon(icon);
        // 场景暖通设备配置
        JzSceneDetailDeviceVO hvacConfig = null;
        List<JZSceneDetailRoomDeviceVO> deviceActions = getDeviceCinfig(sceneId,hvacConfig);
        sceneDetailVO.setRooms(deviceActions);
        sceneDetailVO.setSystemDevice(hvacConfig);
        return sceneDetailVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeByFamilyIds(List<Long> ids) {
        remove(new LambdaQueryWrapper<FamilyScene>().in(FamilyScene::getFamilyId, ids));
        iFamilySceneActionConfigService.remove(new LambdaQueryWrapper<FamilySceneActionConfig>().in(FamilySceneActionConfig::getFamilyId, ids));
        iThirdFamilySceneIconService.remove(new LambdaQueryWrapper<ThirdFamilySceneIcon>().in(ThirdFamilySceneIcon::getFamilyId, ids));

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeByFamilyId(Long familyId) {
        remove(new LambdaQueryWrapper<FamilyScene>().eq(FamilyScene::getFamilyId, familyId));
        iFamilySceneActionConfigService.remove(new LambdaQueryWrapper<FamilySceneActionConfig>().eq(FamilySceneActionConfig::getFamilyId, familyId));
        iThirdFamilySceneIconService.remove(new LambdaQueryWrapper<ThirdFamilySceneIcon>().eq(ThirdFamilySceneIcon::getFamilyId, familyId));
    }

    @Override
    public Long getFamilyIdById(Long sceneId) {
        return this.baseMapper.getFamilyIdById(sceneId);
    }

    private List<JZSceneDetailRoomDeviceVO> getDeviceCinfig(Long sceneId,JzSceneDetailDeviceVO hvacConfig) {
        List<FamilySceneDeviceActionBO> detailDeviceActionVOS = this.baseMapper.getListSceneDeviceAction(sceneId);
        if (CollectionUtils.isEmpty(detailDeviceActionVOS)){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<Long> productIds = detailDeviceActionVOS.stream().map(FamilySceneDeviceActionBO::getProductId).collect(Collectors.toList());
        //获取产品属性信息;
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceControlAttributeInfo(Lists.newArrayList(productIds));
        Map<Long,List<SceneDeviceAttributeVO>> productMap = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));

        Map<String,List<FamilySceneDeviceActionBO>> roomMap = detailDeviceActionVOS.stream().collect(Collectors.groupingBy(FamilySceneDeviceActionBO::getRoomName));
        List<JZSceneDetailRoomDeviceVO> result = Lists.newArrayList();
        for (Map.Entry<String, List<FamilySceneDeviceActionBO>> entry : roomMap.entrySet()) {
            String roomName = entry.getKey();
            List<FamilySceneDeviceActionBO> deviceList = entry.getValue();
            JZSceneDetailRoomDeviceVO roomDeviceVO = new JZSceneDetailRoomDeviceVO();
            roomDeviceVO.setRoomName(roomName);
            result.add(roomDeviceVO);
            List<JzSceneDetailDeviceVO> deviceConfigs = Lists.newArrayListWithExpectedSize(deviceList.size());
            for (FamilySceneDeviceActionBO deviceConfig : deviceList) {
                JzSceneDetailDeviceVO detailDeviceVO = BeanUtil.mapperBean(deviceConfig, JzSceneDetailDeviceVO.class);
                List<SceneDeviceAttributeVO> attributeVOS = productMap.get(deviceConfig.getProductId());
                if (CollectionUtils.isEmpty(attributeVOS)) {
                    continue;
                }
                List<JzSceneDetailDeviceActionVO> attributeListData = Lists.newArrayList();
                attributeVOS.forEach(attribute -> {
                    JzSceneDetailDeviceActionVO attributeVO = BeanUtil.mapperBean(attribute, JzSceneDetailDeviceActionVO.class);
                    Map<String, String> infoBOMap = deviceConfig.getActions().stream().collect(Collectors.toMap(SceneAttributeInfoBO::getAttributeCode, SceneAttributeInfoBO::getVal));
                    if (infoBOMap.containsKey(attribute.getCode())) {
                        attributeVO.setSelected(1);
                        attributeVO.setVal(infoBOMap.get(attribute.getCode()));
                    } else {
                        attributeVO.setSelected(0);
                    }
                    attributeListData.add(attributeVO);
                });
                detailDeviceVO.setActions(attributeListData);
                //暖通的拎出来
                if (CategoryTypeEnum.HVAC.getType().equals(deviceConfig.getCategoryCode())) {
                    hvacConfig = detailDeviceVO;
                }
                deviceConfigs.add(detailDeviceVO);
            }
        }
        return result;
    }

    /**
     * 删除场景动作配置
     * @param sceneId
     */
    private void deleteAction(Long sceneId) {
        //删除设备动作
        iFamilySceneActionConfigService.remove(new LambdaQueryWrapper<FamilySceneActionConfig>().eq(FamilySceneActionConfig::getSceneId,sceneId));
    }

    private void updateCheck(Long familyId,JZFamilySceneDTO request) {
        FamilyScene scene = getById(request.getSceneId());
        if (scene.getName().equals(request.getName())){
            return;
        }
        checkName(familyId,request);
    }

    private void checkName(Long familyId,JZFamilySceneDTO request) {
        int count = count(new LambdaQueryWrapper<FamilyScene>().eq(FamilyScene::getName,request.getName()).eq(FamilyScene::getFamilyId,familyId).last("limit 1"));
        if (count >0){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "场景名称已存在");
        }
    }

    /**
     * 保存设备动作信息
     */
    private void saveDeviceAction(Long familyId,Long sceneId,List<JZSceneDeviceDTO> devices) {
        if(CollectionUtils.isEmpty(devices)){
            return;
        }
        List<FamilySceneActionConfig> actionList = Lists.newArrayList();
        devices.forEach(device->{
            List<JZSceneDeviceActionDTO>  deviceActions = device.getActions();
            if(!CollectionUtils.isEmpty(deviceActions)){
                deviceActions.forEach(deviceAction->{
                    FamilySceneActionConfig sceneAction = FamilySceneActionConfig.builder().attributeCode(deviceAction.getAttributeCode()).attributeVal(deviceAction.getVal()).deviceId(device.getDeviceId()).deviceSn(device.getDeviceSn()).sceneId(sceneId).familyId(familyId).productCode(device.getProductCode()).productId(device.getProductId()).build();
                    actionList.add(sceneAction);
                });
            }
        });
        iFamilySceneActionConfigService.saveBatch(actionList);
    }
}
