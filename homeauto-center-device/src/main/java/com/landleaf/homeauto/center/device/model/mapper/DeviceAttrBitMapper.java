package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrBit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 协议属性二进制值配置 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
public interface DeviceAttrBitMapper extends BaseMapper<DeviceAttrBit> {

    @Select("SELECT d.bit_0,d.bit_1 FROM device_attr_bit d WHERE d.attr_id = #{attrId} ORDER BY d.bit_pos asc ")
    List<DeviceAttrBit> getListByAttrId(@Param("attrId") String attrId);
}
