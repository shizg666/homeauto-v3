package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.bo.FamilyForAppBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.vo.FamilyUserInfoVO;
import com.landleaf.homeauto.center.device.model.vo.FloorInfoVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
    List<FamilyForAppBO> getFamilyByUserId(@Param("userId") String userId);

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

    List<FloorInfoVO> getMyFamilyInfo(@Param("familyId") String familyId);

    List<FamilyUserInfoVO> getMyFamilyUserInfo(@Param("familyId")String familyId);
}
