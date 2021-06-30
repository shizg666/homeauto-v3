package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilySceneActionConfig;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateSceneActionConfig;
import com.landleaf.homeauto.center.device.model.mapper.FamilySceneActionConfigMapper;
import com.landleaf.homeauto.center.device.model.mapper.TemplateSceneActionConfigMapper;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttrInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseSceneDeviceConfigVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.SceneAcionQueryVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.SceneDeviceAcrionConfigDTO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilySceneActionConfigService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateSceneService;
import com.landleaf.homeauto.center.device.service.mybatis.ITemplateSceneActionConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-04-07
 */
@Service
public class FamilySceneActionConfigServiceImpl extends ServiceImpl<FamilySceneActionConfigMapper, FamilySceneActionConfig> implements IFamilySceneActionConfigService {

}
