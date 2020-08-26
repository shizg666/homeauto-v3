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

    List<CountBO> getCountByFamilyIds(List<String> familyIds);

    int checkAdmin(@Param("familyId")String familyId, @Param("userId")String userId);
}
