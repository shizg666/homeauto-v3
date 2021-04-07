package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateRoomPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 户型房间表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface TemplateRoomMapper extends BaseMapper<TemplateRoomDO> {


    @Select("SELECT r.ID FROM house_template_room r WHERE r.sort_no = #{sortNo} and r.floor_id = #{floorId} limit 1")
    String getIdBySort(@Param("sortNo")Integer sortNo, @Param("floorId")String floorId);

    @Select("select r.name from house_template_room r where r.house_template_id = #{templateId}")
    List<String> getListNameByTemplateId(@Param("templateId") String templateId);
    /**
     * 根据户型模板统计各户型房间数据
     * @param templateIds
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.project.CountBO>
     * @author wenyilu
     * @date  2021/1/6 9:56
     */
    List<CountBO> getCountByTemplateIds(@Param("templateIds")List<String> templateIds);


    /**
     * 获取房间code
     * @param roomId
     * @return
     */
    @Select("select r.code from house_template_room r where r.id = #{roomId}")
    String getRoomCodeById(@Param("roomId") String roomId);

    /**
     * 根据户型id获取房间列表
     * @param templateId
     * @return
     */
    @Select("select r.id,r.name,r.area,r.type,r.floor from house_template_room r where r.house_template_id = #{templateId}")
    List<TemplateRoomPageVO> getListRoomByTemplateId(@Param("templateId") Long templateId);
}
