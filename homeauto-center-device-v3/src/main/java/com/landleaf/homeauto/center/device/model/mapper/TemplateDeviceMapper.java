package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.vo.TotalCountBO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceAttrInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceBaseInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.device.PanelBO;
import com.landleaf.homeauto.center.device.model.vo.project.*;
import com.landleaf.homeauto.center.device.model.vo.scene.*;
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

    int existParam(@Param("name") String name, @Param("sn")  String sn, @Param("templateId")  String templateId, @Param("categoryId")  String categoryId);

    int existParamCheck(CheckDeviceParamBO request);

    List<CountBO> countDeviceByRoomIds(@Param("roomIds") List<String> roomIds);

    List<TemplateDevicePageVO> getListByRoomId(@Param("roomId")Long roomId);

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

    /**
     * 获取户型暖通设备信息-----添加场景
     * @param templateId
     * @return
     */
    List<SceneHvacDeviceVO> getListHvacInfo(@Param("templateId") String templateId);

    /**
     * 根据户型id获取面板设置温度的属性范围（目前只考虑一户家庭中有一种类型的面板，假如有多个则随便取一个）
     * @param templateId
     * @return
     */
    AttributeScopeVO getPanelSettingTemperature(@Param("templateId")String templateId);

    /**
     * 根据户型id获取楼层房间非暖通设备集合(包含层级关系)
     * @param templateId
     * @return
     */
    List<SceneFloorVO> getListdeviceInfo(@Param("templateId") String templateId);


    /**
     * 根据户型id获取楼层房间设备属性集合(不包含层级关系)---新增场景设备
     * @param templateId
     * @return
     */
    List<SceneDeviceVO> getListDevice(@Param("templateId") Long templateId);
    /**
     * 根据户型统计设备数量
     * @param templateIds
     * @param showApp
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.project.CountBO>
     * @author wenyilu
     * @date  2021/1/6 10:02
     */
    List<CountBO> getCountByTemplateIds(@Param("templateIds") List<Long> templateIds);

    /**
     * 查询户型设备列表
     * @param templateId
     * @return
     */
    List<TemplateDevicePageVO> getListByTemplateId(@Param("templateId") Long templateId);

    TemplateDeviceDetailVO detailById(@Param("deviceId")String deviceId);

    List<DeviceBaseInfoDTO> getSelectDeviceError(@Param("tempalteId") String tempalteId);

    TemplateDeviceDO getDeviceByTemplateAndAttrCode(@Param("tempalteId")String templateId,@Param("attrCode") String attrCode);

    /**
     * 统计各户型各品类设备数量
     * @return
     */
    @Select("SELECT d.house_template_id as templateId,d.category_code,count(d.id) from house_template_device d group by d.house_template_id,d.category_code")
    List<HomeDeviceStatisticsBO> getDeviceStatistics(@Param("data") List<String> data);


    /**
     * 户型下家庭统计
     * @return
     */
//    @Select("select f.template_id,count(f.id) from home_auto_family f GROUP BY f.template_id")
    List<HomeDeviceStatisticsBO> getFamilyStatistics(@Param("data") List<String> data);


    /**
     * 查询楼盘下的户型id集合
     * @param realestateId
     * @return
     */
    List<String> getTemplateIdsByRealestateId(@Param("realestateId")String realestateId);

    /**
     * 查询项目下的户型id集合
     * @param projectIds
     * @return
     */
    List<String> getTemplateIdsByPtojectIds(@Param("projectIds")List<String> projectIds);


    /**
     * 统计家庭绑定用户数
     * @param realestateId
     * @param projectIds
     * @return
     */
    int getCountFamilyUser(@Param("realestateId") String realestateId, @Param("projectIds")List<String> projectIds);

    /**
     * 按房间统计户型下的设备数量
     * @param templateId
     * @return
     */
    @Select("select d.room_id,count(d.id) from house_template_device d where d.house_template_id = #{templateId} GROUP BY d.room_id")
    List<TotalCountBO> getDeviceNumGroupByRoom(@Param("templateId") Long templateId);

    /**
     * 获取设备的产品id
     * @param deviceId
     * @return
     */
    @Select("SELECT d.product_id from house_template_device d where d.id = #{deviceId}")
    Long getProdcutIdByDeviceId(@Param("deviceId") Long deviceId);

    /**
     * 获取设备的产品code
     * @param deviceId
     * @return
     */
    @Select("SELECT d.product_code from house_template_device d where d.id = #{deviceId}")
    String getProdcutCodeByDeviceId(@Param("deviceId") Long deviceId);

    /**
     * 获取设备的属性信息
     * @param productId
     * @return
     */
    List<DeviceAttrInfoDTO> getDeviceAttrsInfo(@Param("productId")Long productId);
}
