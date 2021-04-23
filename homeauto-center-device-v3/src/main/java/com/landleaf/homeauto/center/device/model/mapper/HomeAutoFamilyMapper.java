package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.bo.FamilyBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.vo.FamilyUserInfoVO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceManageQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceMangeFamilyPageVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticPageVO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 家庭表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface HomeAutoFamilyMapper extends BaseMapper<HomeAutoFamilyDO> {

    /**
     * 通过用户ID获取家庭
     *
     * @param userId 用户ID
     * @return 家庭列表
     */
    List<FamilyBO> getFamilyByUserId(@Param("userId") String userId);

    /**
     * 根据家庭获取城市天气Code
     * @param familyId
     * @return java.lang.String
     * @author wenyilu
     * @date  2020/12/28 16:15
     */
    String getWeatherCodeByFamilyId(@Param("familyId") Long familyId);

    /**
     * 通过终端的mac地址获取家庭信息
     *
     * @param mac  mac地址
     * @return 家庭信息业务对象
     */
    FamilyInfoBO getFamilyInfoByTerminalMac(@Param("mac") String mac);
    /**
     * APP获取我的家庭家庭列表
     * @param userId
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO>
     * @author wenyilu
     * @date  2020/12/28 16:23
     */
    List<MyFamilyInfoVO> getMyFamily(@Param("userId") String userId);

    List<FloorRoomVO> getMyFamilyInfo(@Param("familyId") String familyId);

    /**
     * 获取家庭成员信息
     * @param familyId
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.FamilyUserInfoVO>
     * @author wenyilu
     * @date  2021/1/6 10:20
     */
    List<FamilyUserInfoVO> getMyFamilyUserInfo(@Param("familyId")Long familyId);

    FamilyInfoForSobotDTO getFamilyInfoForSobotById(@Param("familyId")Long familyId);

    /**
     * 查看单元下的家庭列表
     * @param unitId
     * @return
     */
    List<FamilyPageVO> getListByUnitId(@Param("unitId") String unitId);

//    /**
//     * 获取家庭的基本信息
//     * @param familyId
//     * @return
//     */
//    FamilyBaseInfoVO getFamilyBaseInfo(@Param("familyId") String familyId);

    /**
     * 获取家庭楼层房间设备信息
     * @param familyId
     * @return
     */
    List<FamilyFloorDetailVO> getFamilyFloorDetail(@Param("familyId") String familyId);

    List<Long> getListIdByPaths(@Param("paths") List<String> paths);


    @Select("select f.id as familyId,f.code from home_auto_family f where f.project_id = #{familyId}")
    List<FamilyBaseInfoDTO> getBaseInfoByProjectId(@Param("familyId") String familyId);

    List<FamilyBaseInfoDTO> getBaseInfoByPath(@Param("paths") List<String> paths);

    /**
     * App用户查看绑定的家庭列表
     * @param userId
     * @return
     */
    List<FamilyUserVO> getListByUser(@Param("userId") String userId);

    @Select("select count(1) from home_auto_family f where f.building_code = #{buildNo} and f.unit_code = #{unitCode}  and f.room_no = #{roomNo} limit 1")
    int existRoomNo(@Param("roomNo") String roomNo, @Param("buildNo") String buildNo, @Param("unitCode") String unitCode);

    @Select("select f.code from home_auto_family f where f.id = #{familyId} ")
    String getFamilyCodeByid(@Param("familyId") String familyId);

    /**
     * 根据用户权限获取家庭列表
     * @param paths
     * @return
     */
    List<SelectedVO> getListFamilyByPaths(@Param("paths") List<String> paths,@Param("projectId") String projectId);


    /**
     * 家庭分页查询
     * @param request
     * @return
     */
    List<FamilyPageVO> getListPage(FamilyQryDTO request);

    @Select("select t.family_id from family_terminal t where t.mac = #{mac}")
    String getFamilyIdByMac(@Param("mac") String mac);

    @Select("select f.template_id from home_auto_family f where f.id = #{familyId}")
    String getTemplateIdById(@Param("familyId")String familyId);

    @Select("select f.screen_mac from home_auto_family f where f.id = #{familyId}")
    String getScreenMacByFamilyId(@Param("familyId")String familyId);

    List<DeviceMangeFamilyPageVO> getListDeviceMangeFamilyPage(DeviceManageQryDTO request);

    @Select("select distinct(t.screen_mac) from home_auto_family t")
    List<String> getScreenMacList();

    @Select("select f.template_id from home_auto_family f where f.code = #{familyCode} limit 1")
    String getTemplateIdByFamilyCode(@Param("familyCode") String familyCode);

    /**
     * 项目楼房管理
     * @return
     */
    @Select("select f.id,f.building_code ,f.unit_code,f.floor,f.template_id from home_auto_family f where f.project_id = #{projcetId} order by f.building_code asc")
    List<ProjectFamilyTotalBO> getProjectFamilyTotal(@Param("projcetId") Long projectId);

    /**
     * 批量获取家庭code
     * @param ids
     * @return
     */
    List<String> getFamilyCodelistByIds(@Param("ids") List<Long> ids);

    /**
     * 分页统计家庭在空间管理维度值
     * @param realestateId  楼盘ID
     * @param projectId     项目ID
     * @param buildingCode  楼栋号
     * @return
     */
    List<SpaceManageStaticPageVO> spaceManageStatistics(@Param("realestateId")Long realestateId,
                                                        @Param("projectId")Long projectId,
                                                        @Param("buildingCode")String buildingCode);

    /**
     * 查询家庭设备列表
     * @param realestateId   楼盘
     * @param projectId      项目
     * @param buildingCode   楼栋号
     * @param familyName     房屋名称
     * @param deviceName     设备名称
     * @param deviceSn       设备号
     * @return
     */
    List<FamilyDevicePageVO> listFamilyDevice(@Param("realestateId")Long realestateId,
                                              @Param("projectId")Long projectId,
                                              @Param("buildingCode")String buildingCode,
                                              @Param("familyName")String familyName,
                                              @Param("deviceName")String deviceName,
                                              @Param("deviceSn")String deviceSn);

    /**
     * 获取项目下的楼栋列表
     * @return
     */
    @Select("select DISTINCT f.building_code from home_auto_family f where f.project_id = #{projectId}")
    List<String> geListtBuildByProjectId(@Param("projectId") Long projectId);

    /**
     * 楼栋单元下拉列表
     * @param projectId
     * @param buildCode
     * @return
     */
    @Select("select DISTINCT f.unit_code from home_auto_family f where f.project_id = #{projectId} and f.building_code = #{buildCode} ")
    List<String> getSelectsUnitByBuild(@Param("projectId")Long projectId, @Param("buildCode") String buildCode);

    /**
     * 楼栋楼层下拉列表
     * @param projectId
     * @param buildCode
     * @return
     */
    @Select("select DISTINCT f.floor from home_auto_family f where f.project_id = #{projectId} and f.building_code = #{buildCode} ")
    List<String> getSelectsfloorByBuild(@Param("projectId")Long projectId, @Param("buildCode") String buildCode);
}
