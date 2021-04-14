package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoProject;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.*;
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

    List<RealestateCountBO> countByRealestateId(@Param("ids") List<Long> ids);

    List<ProjectVO> page(ProjectQryDTO request);

    List<ProjectPathVO> getListPathProjects(@Param("paths") List<String> path);

    List<SelectedVO> getListSeclects(@Param("paths") List<String> path);

    List<HomeAutoProject> getListCascadeSeclects(@Param("paths") List<String> path);


    /**获取项目path信息
     *
     * @param projectId
     * @return
     */
    @Select("SELECT p.path,p.name,p.code from home_auto_project p where p.id = #{projectId} ")
    PathBO getProjectPathInfoById(@Param("projectId") Long projectId);

    @Select("SELECT p.realestate_id from home_auto_project p where p.type = #{type} ")
    List<String> getRealestateIdsByfreed(@Param("type") Integer type);

    /**
     * 项目详情
     * @param projectId
     * @return
     */
    ProjectDetailVO getDetailById(@Param("projectId") Long projectId);

    /**
     * 获取楼盘下的项目列表
     * @param realestateId
     * @return
     */
    List<ProjectDetailVO> getListDetailByRealestateId(@Param("realestateId")Long realestateId);
}
