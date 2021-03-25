package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.device.DeviceAttrInfo;
import com.landleaf.homeauto.center.device.model.dto.protocol.DeviceAttrInfoCacheBO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorDTO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeErrorQryDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 协议属性信息 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2021-01-04
 */
public interface DeviceAttrInfoMapper extends BaseMapper<DeviceAttrInfo> {


    AttributeErrorDTO getAttrError(AttributeErrorQryDTO request);

    List<AttributeErrorDTO> getListAttrError(@Param("deviceId")String deviceId);

    /**
     * 获取设备的故障属性code列表
     * @param deviceId
     * @return
     */
    @Select("SELECT attr.code FROM device_attr_info attr WHERE attr.TYPE = 2  AND attr.device_id = #{deviceId}")
    List<String> getAttrErrorCodeListByDeviceId(@Param("deviceId") String deviceId);

    @Select("SELECT attr.id,attr.name,attr.code,attr.type,attr.value_type FROM device_attr_info attr WHERE attr.house_template_id = #{templateId}  AND attr.code = #{attrCode} limit 1")
    DeviceAttrInfoCacheBO getAndSaveAttrInfoCache(@Param("templateId")String templateId, @Param("attrCode")String attrCode);
}
