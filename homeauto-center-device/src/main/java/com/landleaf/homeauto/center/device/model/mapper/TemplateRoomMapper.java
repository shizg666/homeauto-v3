package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    List<SortNoBO> getListSortNoBo(@Param("floorId") String floorId,@Param("sortNo") int sortNo);

    void updateBatchSort(@Param("sortNoBOS") List<SortNoBO> sortNoBOS);

    @Select("SELECT r.ID FROM house_template_room r WHERE r.sort_no = #{sortNo} and r.floor_id = #{floorId} limit 1")
    String getIdBySort(@Param("sortNo")Integer sortNo, @Param("floorId")String floorId);
}
