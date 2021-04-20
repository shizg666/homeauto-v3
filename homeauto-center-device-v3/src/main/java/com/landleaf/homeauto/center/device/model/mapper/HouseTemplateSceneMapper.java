package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 户型情景表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
public interface HouseTemplateSceneMapper extends BaseMapper<HouseTemplateScene> {

    /**
     * 查看户型场景列表
     * @param templageId
     * @return
     */
    List<HouseScenePageVO> getListScene(@Param("templageId") Long templageId);

    /**
     *查看场景下某一设备的配置
     * @param requestObject
     * @return
     */
    List<SceneDeviceAcrionConfigDTO> getSceneDeviceAction(SceneAcionQueryVO requestObject);

    /**
     * 查看场景下的设备动作配置
     * @param sceneId
     * @return
     */
    List<WebSceneDetailDeviceActionBO> getListSceneDeviceAction(@Param("sceneId") Long sceneId);
}
