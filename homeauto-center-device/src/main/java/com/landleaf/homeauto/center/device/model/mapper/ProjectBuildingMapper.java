package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectBuilding;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectBuildingVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 楼栋表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface ProjectBuildingMapper extends BaseMapper<ProjectBuilding> {

//    @Select("select b.code,b.id,bu.id as unitId,bu.code as unitCode from project_building b,project_building_unit bu where b.id = bu.building_id and project_id = #{projectId}")
    List<ProjectBuildingVO> getListByProjectId(@Param("projectId") String projectId);
}
