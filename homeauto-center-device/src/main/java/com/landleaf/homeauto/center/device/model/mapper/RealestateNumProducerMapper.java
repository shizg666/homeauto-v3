package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.po.realestate.SequenceProducer;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface RealestateNumProducerMapper extends BaseMapper<SequenceProducer> {

    @Select("select num from sequence_producer where name = #{name} for UPDATE")
    Integer getNum(@Param("name") String name);

    @Select("update  sequence_producer set num = num +1 where name = #{name}")
    void updateNum(@Param("name") String name);
}
