package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.FamilyCommonSceneDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategory;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.*;
import com.landleaf.homeauto.center.device.model.mapper.HouseTemplateSceneMapper;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseScenePageVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.SceneDetailQryDTO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
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
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;

    @Autowired
    private IHomeAutoProductService iHomeAutoProductService;
    @Autowired
    private ITemplateSceneActionConfigService iTemplateSceneActionConfigService;
    @Autowired
    private IdService idService;

    public static final Integer ROOM_FLAG = 0;
    public static final Integer OPEARATE_FLAG_APP = 1;

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
        removeById(request.getId());
        iTemplateSceneActionConfigService.deleteSecneActionBySeneId(request.getId());
    }

    @Override
    public List<HouseScenePageVO> getListScene(String templageId) {
        return this.baseMapper.getListScene(templageId);
    }

    @Override
    public WebSceneDetailDTO getSceneDetail(SceneDetailQryDTO request) {
        WebSceneDetailDTO detailDTO = this.baseMapper.getSceneDetail(request.getId());
        if (detailDTO == null){
            return null;
        }
        return detailDTO;
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
    public List<HouseTemplateScene> getScenesByTemplate(String templateId) {
        QueryWrapper<HouseTemplateScene> familySceneDOQueryWrapper = new QueryWrapper<>();
        familySceneDOQueryWrapper.eq("house_template_id", templateId);
        familySceneDOQueryWrapper.orderByAsc("create_time");
        return list(familySceneDOQueryWrapper);
    }

    @Override
    public List<FamilySceneBO> getFamilySceneWithIndex(String familyId, String templateId, List<HouseTemplateScene> templateScenes, List<FamilyCommonSceneDO> familyCommonSceneDOList, boolean commonUse) {
        // 常用场景设备业务对象列表
        List<FamilySceneBO> familySceneBOListForCommon = new LinkedList<>();
        // 不常用场景设备业务对象列表
        List<FamilySceneBO> familySceneBOListForUnCommon = new LinkedList<>();
        List<String> commonSceneIds = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(familyCommonSceneDOList)) {
            commonSceneIds = familyCommonSceneDOList.stream().map(FamilyCommonSceneDO::getSceneId).collect(Collectors.toList());
        }
        // 遍历所有场景, 筛选出常用场景和不常用场景
        for (int i = 0; i < templateScenes.size(); i++) {
            HouseTemplateScene templateScene = templateScenes.get(i);
            FamilySceneBO familySceneBO = new FamilySceneBO();
//            familySceneBO.setSceneId(templateScene.getId());
            familySceneBO.setSceneName(templateScene.getName());
            familySceneBO.setSceneIcon(templateScene.getIcon());
            familySceneBO.setSceneIndex(i);
            familySceneBO.setFamilyId(familyId);
            familySceneBO.setTemplateId(templateId);
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


}
