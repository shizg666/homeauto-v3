package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectSoftConfig;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectSoftConfigDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 项目软件配置表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface ProjectSoftConfigMapper extends BaseMapper<ProjectSoftConfig> {

    @Select("SELECT id,name,code,project_id,show_flag,biz_type,sys_type from project_soft_config where project_id = #{id}")
    List<ProjectSoftConfigDTO> getConfigByProjectId(@Param("id") String id);
}
