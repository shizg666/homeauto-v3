package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 户型设备表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface TemplateDeviceMapper extends BaseMapper<TemplateDeviceDO> {

    int existParam(@Param("name") String name, @Param("sn")  String sn, @Param("roomId")  String roomId);

    List<CountBO> countDeviceByRoomIds(@Param("roomIds") List<String> roomIds);

    List<TemplateDevicePageVO> getListByRoomId(@Param("roomId")String roomId);

    /**
     * 查询房间下比该序号大的设备列表
     * @param roomId
     * @param sortNo
     * @return
     */
    List<SortNoBO> getListSortNoBoGT(@Param("roomId") String roomId, @Param("sortNo") Integer sortNo);

    /**
     * 批量更新序号
     * @param sortNoBOS
     */
    void updateBatchSort(@Param("sortNoBOS") List<SortNoBO> sortNoBOS);

    /**
     * 根据房间id和序号查询相应设备id
     * @param sortNo
     * @param roomId
     * @return
     */
    @Select("SELECT d.ID FROM house_template_device d WHERE d.sort_no = #{sortNo} and d.room_id = #{roomId} limit 1")
    String getIdBySort(@Param("sortNo")int sortNo, @Param("roomId")String roomId);

    /**
     * 查询比这个序号小的房间列表
     * @param roomId
     * @param sortNo
     * @return
     */
    List<SortNoBO> getListSortNoBoLT(@Param("roomId") String roomId, @Param("sortNo") Integer sortNo);
}
