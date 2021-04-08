package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoAttributeDic;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicDetailVO;
import com.landleaf.homeauto.common.domain.vo.category.AttributeDicVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 属性字典表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Mapper
public interface HomeAutoAttribureDicMapper extends BaseMapper<HomeAutoAttributeDic> {

    AttributeDicDetailVO getInfoById(@Param("id") String id);

    @Select("select code from home_auto_attribute_dic where id = #{id}")
    String getCodeById(@Param("id") String id);

    AttributeDicDetailVO getInfoByCode(@Param("code")String code);

    @Select("SELECT ad.ID,ad.code,ad.NAME,ad.nature FROM home_auto_attribute_dic ad ")
    List<AttributeDicVO> getListAttributes();
}
