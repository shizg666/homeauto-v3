package com.landleaf.homeauto.center.device.model.mapper;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilyAuthorization;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 家庭授权记录表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-01
 */
public interface FamilyAuthorizationMapper extends BaseMapper<FamilyAuthorization> {

    int getExpiredData(@Param("endTime") String endTime);

    /**
     *
     * @param now
     * @return
     */
    List<FamilyAuthorization> getListExpiredData(@Param("endTime") DateTime now);

    @Select("select count(1) from family_authorization where family_id =  #{familyId} and execute_flag = 0 ")
    int countByFamilyId(@Param("familyId") String familyId);


    void updateByFamilyId(@Param("familyId") String familyId);
}
