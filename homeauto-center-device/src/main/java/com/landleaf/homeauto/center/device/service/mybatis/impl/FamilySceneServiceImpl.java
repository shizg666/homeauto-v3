package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneMapper;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneVO;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyTerminalService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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

}
