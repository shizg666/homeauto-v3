package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.bo.FamilySceneBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneDO;
import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailDeviceActionBO;
import com.landleaf.homeauto.center.device.model.vo.scene.WebSceneDetailHvacConfigVO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilySceneDetailQryDTO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.FamilyScenePageVO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 家庭情景表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface FamilySceneMapper extends BaseMapper<FamilySceneDO> {

    /**
     * 通过家庭ID获取常用场景
     *
     * @param familyId 家庭ID
     * @return 家庭常用场景
     */
    List<FamilySceneBO> getCommonScenesByFamilyId(@Param("familyId") String familyId);

    /**
     * 通过家庭ID获取不常用场景
     *
     * @param familyId 家庭ID
     * @return 家庭不常用场景
     */
    List<FamilySceneBO> getAllScenesByFamilyId(@Param("familyId") String familyId);

    /**
     * 查看家庭场景列表
     * @param familyId
     * @return
     */
    List<FamilyScenePageVO> getListScene(@Param("familyId") String familyId);

    /**
     * 查询场景主信息
     * @param sceneId
     * @return
     */
    @Select("SELECT s.id,s.name,s.icon,s.hvac_flag from family_scene s where s.id = #{sceneId}")
    WebSceneDetailDTO getSceneDetail(@Param("sceneId") String sceneId);

    /**
     * 查看场景非暖通配置-- 查看场景
     * @param request
     * @return
     */
    List<WebSceneDetailDeviceActionBO> getSceneDeviceAction(FamilySceneDetailQryDTO request);

    /**
     * 获取场景暖通配置 -- 查看场景
     * @param sceneId
     * @return
     */
    List<WebSceneDetailHvacConfigVO> getListhvacCinfig(@Param("sceneId") String sceneId);

    /**
     * 大屏同步家庭场景主信息
     * @param familyId
     * @return
     */
    List<SyncSceneInfoDTO> getListSyncScene(@Param("sceneId") String familyId);
}
