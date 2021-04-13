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
import com.landleaf.homeauto.center.device.model.vo.family.*;
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

    List<String> getListIdByPaths(@Param("paths") List<String> paths);


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
}
