package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.protocol.ProtocolAttrInfo;
import com.landleaf.homeauto.center.device.model.dto.protocol.ProtocolAttrInfoBO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 协议属性信息 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
public interface ProtocolAttrInfoMapper extends BaseMapper<ProtocolAttrInfo> {

    /**
     * 查询某一设备编号下的协议属性
     * @param attrCodePrex
     * @param categoryCode
     * @return
     */
    List<ProtocolAttrInfoBO> getListProtocolAttrByDevice(@Param("attrCodePrex") String attrCodePrex, @Param("categoryCode")String categoryCode, @Param("protocolId")String protocolId);


    @Select("select ai.id from protocol_attr_info ai where ai.protocol_id = #{protocolId} ")
    List<String> getListAttrIdsByProtocolId(@Param("protocolId") String protocolId);
}
