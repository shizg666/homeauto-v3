package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyScene;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.FamilySceneDeviceActionBO;
import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDeviceActionBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 户型情景表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
public interface FamilySceneMapper extends BaseMapper<FamilyScene> {


    /**
     * 场景设备配置信息
     * @param sceneId
     * @return
     */
    List<FamilySceneDeviceActionBO> getListSceneDeviceAction(@Param("sceneId") Long sceneId);

    @Select("select fs.family_id from family_scene fs where fs.id = #{sceneId}")
    Long getFamilyIdById(@Param("sceneId") Long sceneId);
}
