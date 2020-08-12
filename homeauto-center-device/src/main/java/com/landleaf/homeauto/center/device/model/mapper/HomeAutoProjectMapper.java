package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.common.domain.po.realestate.HomeAutoProject;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateCountBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface HomeAutoProjectMapper extends BaseMapper<HomeAutoProject> {

    List<RealestateCountBO> countByRealestateId(@Param("ids") List<String> ids);

    List<ProjectVO> page(ProjectQryDTO request);
}
