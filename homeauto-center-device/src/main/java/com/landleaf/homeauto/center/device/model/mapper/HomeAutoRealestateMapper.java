package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateDeveloperVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 楼盘表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface HomeAutoRealestateMapper extends BaseMapper<HomeAutoRealestate> {

    List<RealestateVO> page(RealestateQryDTO request);

    @Select("SELECT re.path_name,dc.NAME AS developerName FROM home_auto_realestate re LEFT JOIN sys_dic_tag dc ON re.developer_code = dc.VALUE WHERE dc.dic_code = 'developer' AND re.id = #{id}")
    RealestateDeveloperVO getDeveloperInfoById(String id);

    List<SelectedVO> getListSeclects(@Param("paths") List<String> path);

    @Select("SELECT r.code from home_auto_realestate r where r.id = #{realestateId} ")
    String getRealestateNoById(@Param("realestateId") String realestateId);

    @Select("SELECT r.code,r.path_oauth as path,r.path_name from home_auto_realestate r where r.id = #{realestateId} ")
    PathBO getRealestatePathInfoById(@Param("realestateId")String realestateId);

    List<CascadeVo> getListCascadeSeclects(@Param("ids") List<String> ids);
}
