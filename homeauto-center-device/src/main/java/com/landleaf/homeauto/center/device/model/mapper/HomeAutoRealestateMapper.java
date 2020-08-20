package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateDeveloperVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateVO;
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
}
