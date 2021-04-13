package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateSceneActionConfig;
import com.landleaf.homeauto.center.device.model.vo.scene.house.SceneAcionQueryVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.SceneDeviceAcrionConfigVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2021-04-07
 */
public interface TemplateSceneActionConfigMapper extends BaseMapper<TemplateSceneActionConfig> {

    /**
     * 查看场景下某一设备的配置（修改场景动作）
     * @param requestObject
     * @return
     */
    List<SceneDeviceAcrionConfigVO> getSceneDeviceAction(SceneAcionQueryVO requestObject);
}
