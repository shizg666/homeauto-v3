package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.realestate.ProjectOperationLog;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.project.ProjectOperationLogVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectLogQryDTO;

/**
 * <p>
 * 项目操作日志表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface IProjectOperationLogService  extends IService<ProjectOperationLog> {

    /**
     * 分页查询
     * @param projectLogQryDTO
     * @return
     */
    BasePageVO<ProjectOperationLogVO> page(ProjectLogQryDTO projectLogQryDTO);

}
