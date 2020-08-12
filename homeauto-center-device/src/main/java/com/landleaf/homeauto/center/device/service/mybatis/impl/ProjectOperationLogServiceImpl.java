package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.ProjectOperationLogMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectOperationLogService;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectOperationLog;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目操作日志表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class ProjectOperationLogServiceImpl extends ServiceImpl<ProjectOperationLogMapper, ProjectOperationLog> implements IProjectOperationLogService {

}
