package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.po.realestate.RealestateNumProducer;
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
public interface RealestateNumProducerMapper extends BaseMapper<RealestateNumProducer> {

    @Select("select num from realestate_num_producer where name = #{name} for UPDATE")
    Integer getNum(@Param("name") String name);

    @Select("update  realestate_num_producer set num = num +1 where name = #{name}")
    int updateNum(@Param("name") String name);
}
