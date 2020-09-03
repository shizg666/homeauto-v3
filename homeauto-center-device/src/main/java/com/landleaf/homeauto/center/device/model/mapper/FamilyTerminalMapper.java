package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyConfigVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyTerminalPageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * 家庭终端表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface FamilyTerminalMapper extends BaseMapper<FamilyTerminalDO> {

    @Select("select id as value,name as label from family_terminal where family_id = #{familyId}")
    List<SelectedVO> getTerminalSelects(@Param("familyId") String familyId);

    /**
     * 获取主大屏网关的id
     * @param houseTemplateId
     * @return
     */
    @Select("select t.id from family_terminal t where t.master_flag = 1 and t.family_id = #{familyId}")
    String getMasterID(@Param("familyId") String houseTemplateId);

    List<FamilyTerminalPageVO> getListByFamilyId(@Param("familyId") String familyId);
}
