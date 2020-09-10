package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HouseTemplateScene;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
import com.landleaf.homeauto.center.device.model.vo.scene.house.HouseScenePageVO;
import com.landleaf.homeauto.center.device.model.vo.scene.house.SceneDetailQryDTO;
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
    List<HouseScenePageVO> getListScene(@Param("templageId") String templageId);

    /**
     * 查看场景主信息
     * @param sceneId
     * @return
     */
    WebSceneDetailDTO getSceneDetail(@Param("sceneId") String sceneId);

    /**
     * 查看场景非暖通配置-- 查看场景
     * @param request
     * @return
     */
    List<WebSceneDetailDeviceActionBO> getSceneDeviceAction(SceneDetailQryDTO request);

    /**
     * 获取场景暖通配置 -- 查看场景
     * @param sceneId
     * @return
     */
    List<WebSceneDetailHvacConfigVO> getListhvacCinfig(@Param("sceneId") String sceneId);
}
