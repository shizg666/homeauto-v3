package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.mapper.ProjectOperationLogMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectOperationLogService;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectOperationLog;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.project.ProjectOperationLogVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectLogQryDTO;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 项目操作日志表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Service
public class ProjectOperationLogServiceImpl extends ServiceImpl<ProjectOperationLogMapper, ProjectOperationLog> implements IProjectOperationLogService{


    @Override
    public BasePageVO<ProjectOperationLogVO> page(ProjectLogQryDTO request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize(), true);
        List<ProjectOperationLogVO> logs = this.baseMapper.page(request);
        PageInfo pageInfo = new PageInfo(logs);
        BasePageVO<ProjectOperationLogVO> resultData = BeanUtil.mapperBean(pageInfo,BasePageVO.class);
        return resultData;
    }
}
