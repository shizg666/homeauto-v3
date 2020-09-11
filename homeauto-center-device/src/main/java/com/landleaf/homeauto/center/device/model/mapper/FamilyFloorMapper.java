package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyFloorConfigVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 家庭楼层表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface FamilyFloorMapper extends BaseMapper<FamilyFloorDO> {

    List<FamilyFloorConfigVO> getListFloorDetail(@Param("familyId") String familyId);


    @Select("select f.name from family_floor f where f.family_id = #{familyId}")
    List<String> getListNameByFamilyId(@Param("familyId") String familyId);
}
