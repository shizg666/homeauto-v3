package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.po.address.HomeAutoAddress;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 地址表 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@Mapper
public interface AddressMapper extends BaseMapper<HomeAutoAddress> {

}
