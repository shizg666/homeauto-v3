package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.status.HomeAutoFaultDeviceCurrent;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 设备当前故障值 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
public interface HomeAutoFaultDeviceCurrentMapper extends BaseMapper<HomeAutoFaultDeviceCurrent> {

    /**
     *
     *
     * @param familyId
     * @param deviceId
     * @return
     */
    HomeAutoFaultDeviceCurrent selectForUpdate(@Param("familyId") Long familyId, @Param("deviceId") Long deviceId,
                                               @Param("type") Integer type, @Param("code") String code);


}
