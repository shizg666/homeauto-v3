package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectOperationLog;
import com.landleaf.homeauto.common.domain.vo.project.ProjectOperationLogVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectLogQryDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 项目操作日志表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface ProjectOperationLogMapper extends BaseMapper<ProjectOperationLog> {

    @Select("select name,account,ip,create_time from project_operation_log where project_id = #{projectId}")
    List<ProjectOperationLogVO> page(ProjectLogQryDTO request);
}
