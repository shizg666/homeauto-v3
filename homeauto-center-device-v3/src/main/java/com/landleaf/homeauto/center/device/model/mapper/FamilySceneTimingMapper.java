package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilySceneTimingBO;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneTimingDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 场景定时配置表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface FamilySceneTimingMapper extends BaseMapper<FamilySceneTimingDO> {

    /**
     * 通过家庭ID获取定时场景
     *
     * @param familyId
     * @return
     */
    List<ScreenFamilySceneTimingBO> getSceneTimingByFamilyId(@Param("familyId") String familyId);

}
