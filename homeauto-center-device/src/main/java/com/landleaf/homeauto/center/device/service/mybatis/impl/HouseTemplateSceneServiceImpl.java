package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateSceneAction;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.mapper.HouseTemplateSceneMapper;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDTO;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(HouseSceneDTO request) {
        addCheck(request);
        HouseTemplateScene scene = BeanUtil.mapperBean(request,HouseTemplateScene.class);
        scene.setId(IdGeneratorUtil.getUUID32());
        save(scene);
        request.setIcon(scene.getId());
        saveDeviceAction(request);
        saveHvacAction(request);
    }

    /**
     * 保存暖通动作信息
     * @param request
     */
    private void saveHvacAction(HouseSceneDTO request) {

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
    public void update(HouseSceneDTO request) {

    }

    @Override
    public void delete(ProjectConfigDeleteDTO request) {

    }
}
