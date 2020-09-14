package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HvacConfig;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 场景暖通配置 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-28
 */
public interface HvacConfigMapper extends BaseMapper<HvacConfig> {


    /**
     * 根据设备号和户型id获取该设备关联的场景暖通配置主键集合
     * @param deviceSn
     * @param houseTemplateId
     * @return
     */
    @Select("select c.id from house_scene_hvac_config c where c.device_sn = #{deviceSn} and c.house_template_id = #{houseTemplateId}")
    List<String> getListIds(@Param("deviceSn") String deviceSn, @Param("houseTemplateId")String houseTemplateId);
}
