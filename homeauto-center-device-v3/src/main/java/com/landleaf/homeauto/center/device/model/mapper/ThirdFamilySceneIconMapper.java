package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.ThirdFamilySceneIcon;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 三方家庭场景icon表 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2021-07-02
 */
public interface ThirdFamilySceneIconMapper extends BaseMapper<ThirdFamilySceneIcon> {

    /**
     * 获取场景图片信息
     * @param sceneId
     * @return
     */
    @Select("select sc.icon from third_family_scene_icon sc where sc.scene_id = #{sceneId}")
    String getIconBySceneId(@Param("sceneId") Long sceneId);
}
