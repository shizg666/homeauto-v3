package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.center.device.context.LogStrategyFactory;
import com.landleaf.homeauto.center.device.service.mybatis.IProjectOperationLogService;
import com.landleaf.homeauto.center.device.service.mybatis.LogService;
import com.landleaf.homeauto.common.domain.dto.log.OperationLog;
import com.landleaf.homeauto.common.domain.po.realestate.ProjectOperationLog;
import com.landleaf.homeauto.common.enums.log.OperationLogTypeEnum;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.JsonUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
public class ProjectLogServiceImpl implements  LogService, InitializingBean {

    @Autowired
    private IProjectOperationLogService iProjectOperationLogService;


    @Override
    @Async("logExecutor")
    public void saveLog(OperationLog operationLog) {
        ProjectOperationLog log = BeanUtil.mapperBean(operationLog,ProjectOperationLog.class);
        String userName = TokenContext.getToken().getUserName();
        JSONObject jsonObject = JsonUtil.parseToObject(operationLog.getParams());
        String projectId = (String) jsonObject.get("projectId");
        log.setProjectId(projectId);
        log.setParams(jsonObject.toJSONString());
        log.setAccount(userName);
        iProjectOperationLogService.save(log);
    }

    @Override
    public int getType() {
        return OperationLogTypeEnum.PROJECT_OPERATION.getType();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LogStrategyFactory.register(getType(),this);
    }
}
