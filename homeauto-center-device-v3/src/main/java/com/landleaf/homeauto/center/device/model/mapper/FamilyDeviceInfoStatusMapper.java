package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 设备基本状态表(暖通、数值、在线离线标记) Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
public interface FamilyDeviceInfoStatusMapper extends BaseMapper<FamilyDeviceInfoStatus> {

    FamilyDeviceInfoStatus selectForUpdate(@Param("familyId") Long familyId, @Param("deviceId")Long deviceId);
}
