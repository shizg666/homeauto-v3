package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeautoFaultReportMapper;
import com.landleaf.homeauto.center.device.service.SobotService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeautoFaultReportService;
import com.landleaf.homeauto.common.constant.enums.FaultReportStatusEnum;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairDetailDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairPageReqDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.RepairAddReqDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeautoFaultReport;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Service
public class HomeautoFaultReportServiceImpl extends ServiceImpl<HomeautoFaultReportMapper, HomeautoFaultReport> implements IHomeautoFaultReportService {

    @Autowired
    private SobotService sobotService;

    @Override
    public BasePageVO<AppRepairDetailDTO> pageRepirs(AppRepairPageReqDTO requestBody, String userId) {
        BasePageVO<AppRepairDetailDTO> result = new BasePageVO<AppRepairDetailDTO>();
        List<AppRepairDetailDTO> data = Lists.newArrayList();
        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        LambdaQueryWrapper<HomeautoFaultReport> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(userId)) {
            queryWrapper.eq(HomeautoFaultReport::getRepairUserId, userId);
        }
        queryWrapper.orderByDesc(HomeautoFaultReport::getUpdateTime);
        List<HomeautoFaultReport> reports = list(queryWrapper);
        PageInfo pageInfo = new PageInfo(reports);
        if (!CollectionUtils.isEmpty(reports)) {
            data.addAll(reports.stream().map(report -> {
                AppRepairDetailDTO dto = new AppRepairDetailDTO();
                dto.setDescription(report.getDescription());
                dto.setRepairAppearance(report.getRepairAppearance());
                dto.setRepairId(report.getId());
                dto.setStatus(report.getStatus());
                dto.setStatusName(FaultReportStatusEnum.getStatusByCode(report.getStatus()).getMsg());
                return dto;
            }).collect(Collectors.toList()));
        }
        pageInfo.setList(data);
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }

    @Override
    public void createRepair(RepairAddReqDTO requestBody, String userId, String phone) {

        // 创建工单-客户
        HomeautoFaultReport report = sobotService.createUserTicket(requestBody.getDescription(), requestBody.getRepairAppearance(), phone);

        save(report);
    }


}
