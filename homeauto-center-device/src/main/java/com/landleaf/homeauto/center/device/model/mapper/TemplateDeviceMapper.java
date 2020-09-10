package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HvacPanelAction;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.model.vo.device.PanelBO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceAttributeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneFloorVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneHvacDeviceVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
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

    /**
     * 查询户型下的面板设备号集合
     * @param templateId
     * @return
     */
    List<String> getListPanel(@Param("templateId") String templateId);

    /**
     * 查询户型下的面板下拉选择信息（场景配置）
     * @param templateId
     * @return
     */
    List<PanelBO> getListPanelSelects(@Param("templateId") String templateId);

    List<SceneHvacDeviceVO> getListHvacInfo(@Param("templateId") String templateId);

    /**
     * 根据户型id获取面板设置温度的属性范围（目前只考虑一户家庭中有一种类型的面板，假如有多个则随便取一个）
     * @param templateId
     * @return
     */
    AttributeScopeVO getPanelSettingTemperature(@Param("templateId")String templateId);

    /**
     * 根据户型id获取楼层房间设备集合
     * @param templateId
     * @return
     */
    List<SceneFloorVO> getListdeviceInfo(@Param("templateId") String templateId);


}
