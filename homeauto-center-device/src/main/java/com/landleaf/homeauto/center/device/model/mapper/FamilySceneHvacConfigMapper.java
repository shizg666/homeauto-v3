package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilySceneHvacConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/9/2
 */
@Mapper
@Repository
public interface FamilySceneHvacConfigMapper extends BaseMapper<FamilySceneHvacConfig> {

    /**
     * 根据设备号和家庭id查询改设备关联的场景暖通配置id集合
     * @param deviceSn
     * @param familyId
     * @return
     */
    @Select("select c.id from family_scene_hvac_config c where c.device_sn = #{deviceSn} and c.family_id = #{familyId} ")
    List<String> getListIds(@Param("deviceSn") String deviceSn, @Param("familyId")String familyId);
}
