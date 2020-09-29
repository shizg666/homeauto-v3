package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
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

    List<ProjectPathVO> getListPathProjects(@Param("paths") List<String> path);

    List<SelectedVO> getListSeclects(@Param("paths") List<String> path);

    List<HomeAutoProject> getListCascadeSeclects(@Param("paths") List<String> path);


    /**获取项目path信息
     *
     * @param projectId
     * @return
     */
    @Select("SELECT p.path,p.name from home_auto_project p where p.id = #{projectId} ")
    PathBO getProjectPathInfoById(@Param("projectId") String projectId);

    @Select("SELECT p.realestate_id from home_auto_project p where p.type = #{type} ")
    List<String> getRealestateIdsByfreed(@Param("type") Integer type);
}
