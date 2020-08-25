package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectPathVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateCountBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface HomeAutoProjectMapper extends BaseMapper<HomeAutoProject> {

    List<RealestateCountBO> countByRealestateId(@Param("ids") List<String> ids);

    List<ProjectVO> page(ProjectQryDTO request);

    @Select("SELECT p.id as projectId,re.country_code,re.province_code,re.city_code,re.area_code,re.country,re.province,re.city,re.area,re.id as realestateId,re.name as realestateName,p.name as projectName  from home_auto_realestate re ,home_auto_project p where re.id = p.realestate_id  ")
    List<ProjectPathVO> getListPathProjects();

    List<SelectedVO> getListSeclects(@Param("paths") List<String> path);
}
