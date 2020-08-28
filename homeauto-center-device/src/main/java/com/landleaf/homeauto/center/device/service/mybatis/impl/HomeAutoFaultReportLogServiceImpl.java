package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFaultReportLogMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IHomAutoFaultReportLogService;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReportLog;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Service
public class HomeAutoFaultReportLogServiceImpl extends ServiceImpl<HomeAutoFaultReportLogMapper, HomeAutoFaultReportLog> implements IHomAutoFaultReportLogService {


    @Override
    public List<HomeAutoFaultReportLog> getLogsByRepairId(String repairId) {
        QueryWrapper<HomeAutoFaultReportLog>  queryWrapper= new QueryWrapper<>();
        queryWrapper.eq("report_id",repairId);
        queryWrapper.orderByAsc("create_time");
        return list(queryWrapper);
    }
}
