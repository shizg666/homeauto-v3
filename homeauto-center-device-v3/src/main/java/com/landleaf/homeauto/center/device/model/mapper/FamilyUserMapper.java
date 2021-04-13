package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilyUserDO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 家庭组表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-19
 */
public interface FamilyUserMapper extends BaseMapper<FamilyUserDO> {

    @Select("select family_id from family_user where user_id = #{userId}")
    List<String> getFamilyIdsByUserId(@Param("userId") String userId);

    List<CountBO> getCountByFamilyIds(@Param("familyIds")List<Long> familyIds);

    int checkAdmin(@Param("familyId")Long familyId, @Param("userId")String userId);

    /**
     * 获取家庭下的运维成员id
     * @param familyId
     * @return
     */
    @Select("select id from family_user where type =2 and family_id = #{familyId} ")
    String getOperationer(@Param("familyId") Long familyId);

    /**
     * 获取家庭管理员的id
     * @param familyId
     * @return
     */
    @Select("select user_id from family_user where type =1 and family_id = #{familyId} ")
    String getAdminMobileByFamilyId(@Param("familyId")Long familyId);
}
