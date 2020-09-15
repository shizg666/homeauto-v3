package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.center.device.model.vo.family.TemplateSelectedVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplatePageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 项目户型表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface ProjectHouseTemplateMapper extends BaseMapper<ProjectHouseTemplate> {


    List<HouseTemplatePageVO> getListByProjectId(@Param("id") String id);

    List<TemplateSelectedVO> getListSelectByProjectId(@Param("projectId") String projectId);

    List<CountBO> countByProjectIds(@Param("projectIds") List<String> projectIds);

    @Select("SELECT t.NAME FROM project_house_template t WHERE t.project_id = #{projectId}")
    List<String> getListHoustTemplateNames(@Param("projectId") String projectId);
}
