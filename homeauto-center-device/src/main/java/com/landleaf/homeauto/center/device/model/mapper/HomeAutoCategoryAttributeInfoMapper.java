package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoCategoryAttributeInfo;
import com.landleaf.homeauto.common.domain.vo.category.AttributeInfoDicDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 品类属性值表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
public interface HomeAutoCategoryAttributeInfoMapper extends BaseMapper<HomeAutoCategoryAttributeInfo> {

    @Select("select cai.name,cai.code,cai.order_num from home_auto_category_attribute ca,home_auto_category_attribute_info cai where ca.id = cai.attribute_id and ca.category_id  = #{categoryId} and ca.code = #{code}")
    List<AttributeInfoDicDTO> getListByAttributeCode(@Param("categoryId") String categoryId,@Param("code") String code);
}
