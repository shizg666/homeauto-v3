package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfigActionPanel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
@Mapper
@Repository
public interface FamilySceneHvacConfigActionPanelMapper extends BaseMapper<FamilySceneHvacConfigActionPanel> {

    /**
     * 获取家庭场景配置---面板动作 ---- 场景同步
     * @param familyId
     * @return
     */
    List<FamilySceneHvacConfigActionPanel> getListSyncPanelAction(@Param("familyId") String familyId);
}
