package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.bo.FamilyBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.vo.FamilyUserInfoVO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyBaseInfoDTO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyBaseInfoVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyFloorDetailVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyPageVO;
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
     * 根据家庭ID获取城市天气码
     *
     * @param familyId 家庭ID
     * @return 城市天气码
     */
    String getWeatherCodeByFamilyId(@Param("familyId") String familyId);

    /**
     * 通过终端的mac地址获取家庭信息
     *
     * @param mac  mac地址
     * @param type 终端类型
     * @return 家庭信息业务对象
     */
    FamilyInfoBO getFamilyInfoByTerminalMac(@Param("mac") String mac, @Param("type") Integer type);

    List<MyFamilyInfoVO> getListFamilyInfo(@Param("userId") String userId);

    List<FloorRoomVO> getMyFamilyInfo(@Param("familyId") String familyId);

    List<FamilyUserInfoVO> getMyFamilyUserInfo(@Param("familyId")String familyId);

    FamilyInfoForSobotDTO getFamilyInfoForSobotById(@Param("familyId")String familyId);

    /**
     * 查看单元下的家庭列表
     * @param unitId
     * @return
     */
    List<FamilyPageVO> getListByUnitId(@Param("unitId") String unitId);

    /**
     * 获取家庭的基本信息
     * @param familyId
     * @return
     */
    FamilyBaseInfoVO getFamilyBaseInfo(@Param("familyId") String familyId);

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
}
