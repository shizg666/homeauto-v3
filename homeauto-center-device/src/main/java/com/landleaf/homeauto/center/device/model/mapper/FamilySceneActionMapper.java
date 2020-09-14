package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneActionDO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneBO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneHvacAtionBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 场景关联设备动作表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface FamilySceneActionMapper extends BaseMapper<FamilySceneActionDO> {

    /**
     * 获取家庭场景的非暖通配置 ---- 场景同步
     * @param familyId
     * @return
     */
    List<SyncSceneBO> getListSyncSceneDTO(@Param("familyId") String familyId);


    /**
     * 获取家庭场景的暖通配置 ---- 场景同步
     * @param familyId
     * @return
     */
    List<SyncSceneHvacAtionBO> getListSceneHvacAction(@Param("familyId") String familyId);

    /**
     * 获取家庭场景的非暖通配置设备号集合 ---- 场景同步
     * @param familyId
     * @return
     */
    @Select("select sa.device_sn from family_scene_action sa where sa.family_id = #{familyId}")
    List<String> getListDeviceSn(@Param("familyId")String familyId);
}
