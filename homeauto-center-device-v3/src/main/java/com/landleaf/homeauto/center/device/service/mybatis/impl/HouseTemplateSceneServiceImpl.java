package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.eventbus.event.TemplateOperateEvent;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.*;
import com.landleaf.homeauto.center.device.model.mapper.HouseTemplateSceneMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.*;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;

    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;
    @Autowired
    private ITemplateSceneActionConfigService iTemplateSceneActionConfigService;
    @Autowired
    private IdService idService;
    @Autowired
    private ITemplateOperateService iTemplateOperateService;


    //是否是默认场景 0否 1是
    public static final Integer SCENE_DEFAULT = 1;
    public static final Integer SCENE_UNDEFAULT = 0;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(HouseSceneDTO request) {
//        checkNo(request);
        checkName(request);
        HouseTemplateScene scene = BeanUtil.mapperBean(request,HouseTemplateScene.class);
        scene.setId(idService.getSegmentId());
//        if(SCENE_UNDEFAULT.equals(request.getDefaultFlag())){
//            //不是默认场景 场景编号=id
//            scene.setSceneNo(scene.getId());
//        }
        save(scene);
        request.setId(scene.getId());
        saveDeviceAction(request);
        iTemplateOperateService.sendEvent(TemplateOperateEvent.builder().templateId(request.getHouseTemplateId()).typeEnum(ContactScreenConfigUpdateTypeEnum.SCENE).build());
    }

    /**
     * 保存设备动作信息
     * @param request
     */
    private void saveDeviceAction(HouseSceneDTO request) {
        if(CollectionUtils.isEmpty(request.getDeviceActions())){
            return;
        }
        List<TemplateSceneActionConfig> actionList = Lists.newArrayList();
        request.getDeviceActions().forEach(device->{
            List<HouseSceneActionConfigDTO>  deviceActions = device.getActions();
            if(!CollectionUtils.isEmpty(deviceActions)){
                deviceActions.forEach(deviceAction->{
                    TemplateSceneActionConfig sceneAction = TemplateSceneActionConfig.builder().attributeCode(deviceAction.getAttributeCode()).attributeVal(deviceAction.getVal()).deviceId(device.getDeviceId()).deviceSn(device.getDeviceSn()).sceneId(request.getId()).templateId(request.getHouseTemplateId()).productCode(device.getProductCode()).productId(device.getProductId()).build();
                    actionList.add(sceneAction);
                });
            }
        });
        iTemplateSceneActionConfigService.saveBatch(actionList);
    }

    /**
     * 添加场景场景编号校验
     * @param request
     */
//    private void checkNo(HouseSceneDTO request) {
//        if(SCENE_DEFAULT.equals(request.getDefaultFlag())){
//            if ( StringUtil.isEmpty(request.getSceneNo())){
//                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "默认场景编号不能为空");
//            }
//            int count1 = count(new LambdaQueryWrapper<HouseTemplateScene>().eq(HouseTemplateScene::getSceneNo, request.getSceneNo()).eq(HouseTemplateScene::getHouseTemplateId, request.getHouseTemplateId()));
//            if (count1 > 0) {
//                throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "场景编号已存在");
//            }
//        }
//    }

    private void checkName(HouseSceneDTO request) {
        int count = count(new LambdaQueryWrapper<HouseTemplateScene>().eq(HouseTemplateScene::getName,request.getName()).eq(HouseTemplateScene::getHouseTemplateId,request.getHouseTemplateId()).last("limit 1"));
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
        iTemplateOperateService.sendEvent(TemplateOperateEvent.builder().templateId(request.getHouseTemplateId()).typeEnum(ContactScreenConfigUpdateTypeEnum.SCENE).build());
    }

    /**
     * 删除场景动作配置
     * @param sceneId
     */
    private void deleteAction(Long sceneId) {
        //删除非暖通配置
        iTemplateSceneActionConfigService.remove(new LambdaQueryWrapper<TemplateSceneActionConfig>().eq(TemplateSceneActionConfig::getSceneId,sceneId));
    }


    private void updateCheck(HouseSceneDTO request) {
        HouseTemplateScene scene = getById(request.getId());
        if (scene.getName().equals(request.getName())){
           return;
        }
        checkName(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProjectConfigDeleteDTO request) {
        HouseTemplateScene scene = getById(request.getId());
        removeById(request.getId());
        iTemplateSceneActionConfigService.deleteSecneActionBySeneId(request.getId());
        iTemplateOperateService.sendEvent(TemplateOperateEvent.builder().templateId(scene.getHouseTemplateId()).typeEnum(ContactScreenConfigUpdateTypeEnum.SCENE).build());
    }

    @Override
    public List<HouseScenePageVO> getListScene(Long templageId) {
        return this.baseMapper.getListScene(templageId);
    }

    @Override
    public WebSceneDetailVO getSceneDetail(Long sceneId) {
        HouseTemplateScene scene = getById(sceneId);
        if (Objects.isNull(scene)){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode()), "场景id不存在");
        }
        WebSceneDetailVO sceneDetailVO = BeanUtil.mapperBean(scene,WebSceneDetailVO.class);
        // 场景设备
        //非暖通配置信息
        List<WebSceneDetailDeviceActionVO> deviceActions = getDeviceCinfig(sceneId);
        sceneDetailVO.setDeviceConfigs(deviceActions);
        return sceneDetailVO;
    }

    /**
     * 设备配置信息
     * @param sceneId
     * @return
     */
    private List<WebSceneDetailDeviceActionVO> getDeviceCinfig(Long sceneId) {
        List<WebSceneDetailDeviceActionBO> detailDeviceActionVOS = this.baseMapper.getListSceneDeviceAction(sceneId);
        if (CollectionUtils.isEmpty(detailDeviceActionVOS)){
            return null;
        }
        List<Long> productIds = detailDeviceActionVOS.stream().map(WebSceneDetailDeviceActionBO::getProductId).collect(Collectors.toList());
        //获取产品属性信息;
        List<SceneDeviceAttributeVO> attributes = iHomeAutoProductService.getListdeviceAttributeInfo(Lists.newArrayList(productIds));
        Map<Long,List<SceneDeviceAttributeVO>> map = attributes.stream().collect(Collectors.groupingBy(SceneDeviceAttributeVO::getProductId));

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
                Map<String,String> infoBOMap = device.getActions().stream().collect(Collectors.toMap(SceneAttributeInfoBO::getAttributeCode,SceneAttributeInfoBO::getVal));
                if (infoBOMap.containsKey(attribute.getCode())){
                    attributeVO.setSelected(1);
                    attributeVO.setVal(infoBOMap.get(attribute.getCode()));
                }else {
                    attributeVO.setSelected(0);
                }

                attributeListData.add(attributeVO);
            });
            deviceActionVO.setAttributeVOS(attributeListData);
        }
        return result;
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTempalteId(String templateId) {
        remove(new LambdaQueryWrapper<HouseTemplateScene>().eq(HouseTemplateScene::getHouseTemplateId,templateId));
    }
    /**
     * 根据户型ID查询所属场景
     * @param templateId   户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene>
     * @author wenyilu
     * @date  2021/1/5 15:42
     */
    @Override
    public List<HouseTemplateScene> getScenesByTemplate(Long templateId) {
        QueryWrapper<HouseTemplateScene> familySceneDOQueryWrapper = new QueryWrapper<>();
        familySceneDOQueryWrapper.eq("house_template_id", templateId);
        familySceneDOQueryWrapper.orderByAsc("create_time");
        return list(familySceneDOQueryWrapper);
    }

    @Override
    public List<FamilySceneBO> getFamilySceneWithIndex(Long familyId, Long templateId, List<HouseTemplateScene> templateScenes, List<FamilyCommonSceneDO> familyCommonSceneDOList, boolean commonUse) {
        // 常用场景设备业务对象列表
        List<FamilySceneBO> familySceneBOListForCommon = new LinkedList<>();
        // 不常用场景设备业务对象列表
        List<FamilySceneBO> familySceneBOListForUnCommon = new LinkedList<>();
        List<Long> commonSceneIds = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(familyCommonSceneDOList)) {
            commonSceneIds = familyCommonSceneDOList.stream().map(FamilyCommonSceneDO::getSceneId).collect(Collectors.toList());
        }
        // 遍历所有场景, 筛选出常用场景和不常用场景
        for (int i = 0; i < templateScenes.size(); i++) {
            HouseTemplateScene templateScene = templateScenes.get(i);
            FamilySceneBO familySceneBO = new FamilySceneBO();
            familySceneBO.setSceneId(BeanUtil.convertLong2String(templateScene.getId()));
            familySceneBO.setSceneName(templateScene.getName());
            familySceneBO.setSceneIcon(templateScene.getIcon());
            familySceneBO.setSceneIndex(i);
            familySceneBO.setFamilyId(BeanUtil.convertLong2String(familyId));
            familySceneBO.setTemplateId(BeanUtil.convertLong2String(templateId));
            boolean isCommonScene = false;
            if (commonSceneIds.contains(templateScene.getId())) {
                isCommonScene = true;
                familySceneBOListForCommon.add(familySceneBO);
            }
            if (!isCommonScene) {
                familySceneBOListForUnCommon.add(familySceneBO);
            }
        }

        return commonUse ? familySceneBOListForCommon : familySceneBOListForUnCommon;
    }

    @Override
    public void switchUpdateFlagStatus(Long sceneId) {
        HouseTemplateScene scene = getById(sceneId);
        scene.setUpdateFlag((scene.getUpdateFlag()+1)%2);
        updateById(scene);
    }

    @Override
    public List<SceneDeviceAcrionConfigDTO> getSceneDeviceAction(SceneAcionQueryVO requestObject) {
        return this.baseMapper.getSceneDeviceAction(requestObject);
    }


}
