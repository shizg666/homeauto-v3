package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectBuildingUnit;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectBuildingUnitVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 楼栋单元表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface ProjectBuildingUnitMapper extends BaseMapper<ProjectBuildingUnit> {

    @Select("select id,code from project_building_unit where building_id = #{id}")
    List<ProjectBuildingUnitVO> getListByProjectId(@Param("id") String id);

    /**
     * 获取单元编号
     * @param unitId
     * @return
     */
    @Select("SELECT u.code from project_building_unit u   where u.id= #{unitId} ")
    String getUnitNoById(@Param("unitId") String unitId);
}
