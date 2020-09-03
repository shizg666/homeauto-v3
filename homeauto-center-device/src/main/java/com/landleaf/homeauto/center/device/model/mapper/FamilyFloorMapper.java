package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyFloorConfigVO;
import org.apache.ibatis.annotations.Param;

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
}
