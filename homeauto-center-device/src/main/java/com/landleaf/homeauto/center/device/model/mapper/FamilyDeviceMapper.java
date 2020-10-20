package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceBaseInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.device.PanelBO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.project.CheckDeviceParamBO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneDeviceVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneFloorVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneHvacDeviceVO;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneDeviceBO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 家庭设备表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface FamilyDeviceMapper extends BaseMapper<FamilyDeviceDO> {

    /**
     * 不推荐混杂注解和Xml
     *
     * @param id 产品id
     * @return 产品下的设备数量
     */
    @Deprecated
    @Select("select count(1) from family_device where product_id = #{id}")
    int existByProductId(String id);

    /**
     * @param productIds
     * @return
     */
    List<CountBO> getCountByProducts(@Param("productIds") List<String> productIds);

    /**
     * @param familyIds
     * @return
     */
    List<CountBO> getCountByFamilyIds(@Param("familyIds") List<String> familyIds);

    /**
     * @param familyId
     * @return
     */
    List<SelectedVO> getListHvacByFamilyId(@Param("familyId") String familyId);

    /**
     * 获取暖通设备序列号
     *
     * @param familyId
     * @param categoryCode
     * @return
     */
    FamilyDeviceDO getDeviceByFamilyIdAndCategory(@Param("familyId") String familyId, @Param("categoryCode") String categoryCode);

    int existParam(@Param("name") String name, @Param("sn")  String sn, @Param("familyId")  String familyId, @Param("categoryId")  String categoryId);

    int existParamCheck(CheckDeviceParamBO request);

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
    @Select("SELECT d.ID FROM family_device d WHERE d.sort_no = #{sortNo} and d.room_id = #{roomId} limit 1")
    String getIdBySort(@Param("sortNo")int sortNo, @Param("roomId")String roomId);

    /**
     * 查询比这个序号小的房间列表
     * @param roomId
     * @param sortNo
     * @return
     */
    List<SortNoBO> getListSortNoBoLT(@Param("roomId") String roomId, @Param("sortNo") Integer sortNo);

    List<FamilyDevicePageVO> getListByRoomId(@Param("roomId") String roomId);

    List<CountBO> countDeviceByRoomIds(@Param("roomIds") List<String> roomIds);

    /**
     * 查询户型下的面板设备号集合
     * @param familyId
     * @return
     */
    List<String> getListPanel(@Param("familyId") String familyId);


    /**
     * 获取家庭暖通设备信息-----添加场景
     * @param familyId
     * @return
     */
    List<SceneHvacDeviceVO> getListHvacInfo(@Param("familyId") String familyId);

    /**
     * 查询户家庭的面板下拉选择信息（场景配置）
     * @param familyId
     * @return
     */
    List<PanelBO> getListPanelSelects(@Param("familyId")String familyId);

    /**
     * 根据家庭id获取面板设置温度的属性范围（目前只考虑一户家庭中有一种类型的面板，假如有多个则随便取一个）
     * @param familyId
     * @return
     */
    AttributeScopeVO getPanelSettingTemperature(@Param("familyId")String familyId);

    /**
     * 根据家庭d获取楼层房间非暖通设备集合
     *
     * @param familyId
     * @return
     */
    List<SceneFloorVO> getListdeviceInfo(@Param("familyId") String familyId);

    List<SceneDeviceVO> getListDevice(@Param("familyId") String familyId);

    List<SyncSceneDeviceBO> getListSyncSceneDevice(@Param("familyId") String familyId, @Param("deviceSns") List<String> deviceSns);

    DeviceBaseInfoDTO getDeviceInfo(@Param("familyId") String familyId, @Param("deviceSn") String deviceSn);

    /**
     * 查询家庭安防报警设备id
     *
     * @param familyId
     * @return
     */
    @Select("select d.id from family_device d,home_auto_product p where d.product_id = p.id and d.family_id = #{familyId}  and p.code like '12%' limit 1")
    String getFamilyAlarm(@Param("familyId") String familyId);
}
