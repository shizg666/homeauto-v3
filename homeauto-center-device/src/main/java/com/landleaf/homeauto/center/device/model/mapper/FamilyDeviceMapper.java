package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.enums.CategoryEnum;
import com.landleaf.homeauto.center.device.model.bo.DeviceSensorBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import com.landleaf.homeauto.center.device.model.vo.device.PanelBO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.SortNoBO;
import com.landleaf.homeauto.center.device.model.vo.scene.AttributeScopeVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneFloorVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneHvacDeviceVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

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
     * 通过家庭ID获取常用设备
     *
     * @param familyId 家庭ID
     * @return 家庭常用设备表ID
     */
    List<FamilyDeviceWithPositionBO> getCommonDevicesByFamilyId(@Param("familyId") String familyId);

    /**
     * 通过家庭ID获取常用设备
     *
     * @param familyId 家庭ID
     * @return 家庭常用设备表ID
     */
    List<FamilyDeviceWithPositionBO> getUnCommonDevicesByFamilyId(@Param("familyId") String familyId);

    /**
     * 通过家庭ID获取常用设备
     *
     * @param familyId 家庭ID
     * @return 家庭常用设备表ID
     */
    List<FamilyDeviceWithPositionBO> getAllDevicesByFamilyId(@Param("familyId") String familyId);


    /**
     * 通过设备序列号获取设备信息
     *
     * @param sceneId 设备序列号集合
     * @return 设备信息集合
     */
    List<FamilyDeviceWithPositionBO> getDeviceInfoByDeviceSn(@RequestParam String sceneId);

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
     * 根据房间ID获取设备列表
     *
     * @param roomId 房间ID
     * @return 设备列表
     */
    List<FamilyDeviceBO> getDeviceListByRoomId(@Param("roomId") String roomId);

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
     * 根据家庭ID获取家里的传感器设备
     *
     * @param familyId      家庭ID
     * @param categoryEnums 品类限制
     * @return 传感器设备列表
     */
    DeviceSensorBO getDeviceSensorBO(@Param("familyId") String familyId, @Param("categories") CategoryEnum... categoryEnums);

    /**
     * @param familyId
     * @return
     */
    List<SelectedVO> getListHvacByFamilyId(@Param("familyId") String familyId);

    /**
     * 获取暖通设备序列号
     *
     * @param familyId
     * @param categoryId
     * @return
     */
    FamilyDeviceDO getHvacDeviceByFamilyId(@Param("familyId") String familyId, @Param("category") Integer categoryId);

    int existParam(@Param("name") String name, @Param("sn")  String sn, @Param("roomId")  String roomId);

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
     * @param familyId
     * @return
     */
    List<SceneFloorVO> getListdeviceInfo(@Param("familyId")String familyId);
}
