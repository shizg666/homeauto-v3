package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectHouseTemplate;
import com.landleaf.homeauto.center.device.model.vo.family.TemplateSelectedVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.CountLongBO;
import com.landleaf.homeauto.center.device.model.vo.project.HouseTemplatePageVO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateRoomPageVO;
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


    List<HouseTemplatePageVO> getListByProjectId(@Param("id") Long id);

    List<TemplateSelectedVO> getListSelectByProjectId(@Param("projectId") Long projectId);

    List<CountLongBO> countByProjectIds(@Param("projectIds") List<Long> projectIds);

    @Select("SELECT t.NAME FROM project_house_template t WHERE t.project_id = #{projectId}")
    List<String> getListHoustTemplateNames(@Param("projectId") String projectId);

    @Select("SELECT t.area FROM project_house_template t WHERE t.id = #{templateId}")
    String getTemplateArea(@Param("templateId") String templateId);

    /**
     * 户型所在项目是否包含网关
     * @param houseTemplateId
     * @return
     */
    @Select("select p.gateway_flag from project_house_template t ,home_auto_project p where p.id = t.project_id and t.id = #{houseTemplateId}")
    int isGateWayProject(@Param("houseTemplateId") Long houseTemplateId);

    /**
     *统计户型的房间数量
     * @param projectId
     * @return
     */
    @Select("select tr.house_template_id as id,count(tr.id) from house_template_room tr where tr.project_id = #{projectId} GROUP BY tr.house_template_id")
    List<CountLongBO> getRoomNumByTemplateId(@Param("projectId") Long projectId);

    /**
     * 查询项目下的户型id集合
     * @param projectIds
     * @return
     */
    List<String> getTemplateIdsByPtojectIds(@Param("projectIds")List<String> projectIds);

}
