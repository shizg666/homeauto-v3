package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateTerminalDO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 户型终端表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface TemplateTerminalMapper extends BaseMapper<TemplateTerminalDO> {

    @Select("select id as value,name as label from house_template_terminal where house_template_id = #{houseTemplateId}")
    List<SelectedVO> getTerminalSelects( @Param("houseTemplateId") String houseTemplateId);

    /**
     * 获取主大屏网关的id
     * @param houseTemplateId
     * @return
     */
    @Select("select t.id from house_template_terminal t where t.master_flag = 1 and t.house_template_id = #{houseTemplateId}")
    String getMasterID(@Param("houseTemplateId") String houseTemplateId);
}
