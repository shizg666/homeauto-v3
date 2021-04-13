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
     * 查看场景主信息
     * @param sceneId
     * @return
     */
    WebSceneDetailBO getSceneDetail(@Param("sceneId") Long sceneId);


    /**
     * 获取场景暖通配置 -- 查看场景
     * @param sceneId
     * @return
     */
    List<WebSceneDetailHvacConfigVO> getListhvacCinfig(@Param("sceneId") String sceneId);

    /**
     *查看场景下某一设备的配置
     * @param requestObject
     * @return
     */
    List<SceneDeviceAcrionConfigDTO> getSceneDeviceAction(SceneAcionQueryVO requestObject);
}
