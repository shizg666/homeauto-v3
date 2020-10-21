package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 家庭房间表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface FamilyRoomMapper extends BaseMapper<FamilyRoomDO> {

    /**
     * @param familyIds
     * @return
     */
    List<CountBO> getCountByFamilyIds(@Param("familyIds") List<String> familyIds);

    /**
     * 查询比这个序号大的房间列表
     * @param floorId
     * @param sortNo
     * @return
     */
    List<SortNoBO> getListSortNoBoGT(@Param("floorId") String floorId,@Param("sortNo") int sortNo);

    /**
     * 查询比这个序号小的房间列表
     * @param floorId
     * @param sortNo
     * @return
     */
    List<SortNoBO> getListSortNoBoLT(@Param("floorId") String floorId,@Param("sortNo") int sortNo);

    void updateBatchSort(@Param("sortNoBOS") List<SortNoBO> sortNoBOS);

    @Select("SELECT r.ID FROM family_room r WHERE r.sort_no = #{sortNo} and r.floor_id = #{floorId} limit 1")
    String getIdBySort(@Param("sortNo")Integer sortNo, @Param("floorId")String floorId);

    @Select("select r.name from family_room r where r.family_id = #{familyId}")
    List<String> getListNameByFamilyId(@Param("familyId") String familyId);
}
