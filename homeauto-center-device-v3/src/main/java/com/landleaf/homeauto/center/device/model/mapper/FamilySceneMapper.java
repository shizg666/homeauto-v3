package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyScene;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.FamilySceneDeviceActionBO;
import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDeviceActionBO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
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

    /**
     * 同步家庭场景数据
     * @param familyId
     * @return
     */
    List<SyncSceneInfoDTO> getListSyncSceneByfId(@Param("familyId") Long familyId);

    /**
     * 获取三方家庭场景（场景图片是三方的）
     * @param familyId
     * @return
     */
    List<FamilyScene> getListThirdSceneByfId(@Param("familyId") Long familyId);
}
