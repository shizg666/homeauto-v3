package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.po.category.HomeAutoAttribureDic;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 属性字典表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Mapper
public interface HomeAutoAttribureDicMapper extends BaseMapper<HomeAutoAttribureDic> {

    AttributeDicDetailVO getInfoById(@Param("id") String id);
}
