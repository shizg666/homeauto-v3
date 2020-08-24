package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 家庭房间表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface FamilyRoomMapper extends BaseMapper<FamilyRoomDO> {

    /**
     * 通过家庭ID获取房间列表
     *
     * @param familyId
     * @return
     */
    List<FamilyRoomBO> getRoomListByFamilyId(@Param("familyId") String familyId);
}
