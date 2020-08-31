package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectBuilding;
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

    /**
     * 获取项目下的楼栋单元信息
     * @param projectId
     * @return
     */
    List<ProjectBuildingVO> getListByProjectId(@Param("projectId") String projectId);

    /**
     * 获取楼栋编号
     * @param buildingId
     * @return
     */
    @Select("SELECT b.code from project_building b  where b.id = #{buildingId}")
    String getBuildingNoById(@Param("buildingId") String buildingId);
}
