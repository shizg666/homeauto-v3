package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFaultReportMapper;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.SobotService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomAutoFaultReportLogService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeautoFaultReportService;
import com.landleaf.homeauto.center.device.service.mybatis.ISobotTicketService;
import com.landleaf.homeauto.common.constant.enums.FaultReportStatusEnum;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairDetailDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairDetailLogDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.RepairAddReqDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.callback.SobotCallBackContentDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerInfoDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReport;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReportLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class HomeAutoFaultReportServiceImpl extends ServiceImpl<HomeAutoFaultReportMapper, HomeAutoFaultReport> implements IHomeautoFaultReportService {

    @Autowired
    private SobotService sobotService;
    @Autowired
    private IHomAutoFaultReportLogService homAutoFaultReportLogService;
    @Autowired
    private UserRemote userRemote;
    @Autowired
    private ISobotTicketService sobotTicketService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRepair(RepairAddReqDTO requestBody, String userId) {
        Response<CustomerInfoDTO> customerInfoRes = userRemote.getCustomerInfoById(userId);
        if (customerInfoRes.isSuccess() && customerInfoRes.getResult() != null) {
            CustomerInfoDTO customerInfoDTO = customerInfoRes.getResult();
            String mobile = customerInfoDTO.getMobile();
            // 创建工单-客户
            HomeAutoFaultReport report = sobotService.createUserTicket(requestBody.getDeviceName(), requestBody.getContentCode(), requestBody.getFamilyId(), mobile, userId);
            save(report);
        }
    }

    @Override
    public List<AppRepairDetailDTO> listRepairs(String userId) {
        List<AppRepairDetailDTO> data = Lists.newArrayList();
        LambdaQueryWrapper<HomeAutoFaultReport> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(userId)) {
            queryWrapper.eq(HomeAutoFaultReport::getRepairUserId, userId);
        }
        queryWrapper.orderByDesc(HomeAutoFaultReport::getUpdateTime);
        List<HomeAutoFaultReport> reports = list(queryWrapper);
        if (!CollectionUtils.isEmpty(reports)) {
            data.addAll(reports.stream().map(report -> {
                AppRepairDetailDTO dto = new AppRepairDetailDTO();
                dto.setRepairId(report.getId());
                dto.setContent(report.getContent());
                dto.setDeviceName(report.getDeviceName());
                dto.setTicketId(report.getSobotTicketId());
                dto.setRepairTime(report.getRepairTime());
                dto.setStatusName(FaultReportStatusEnum.getStatusByCode(String.valueOf(report.getStatus())).getMsg());
                return dto;
            }).collect(Collectors.toList()));
        }
        return data;
    }

    @Override
    public AppRepairDetailDTO getRepairDetail(String repairId) {
        HomeAutoFaultReport report = getById(repairId);
        List<HomeAutoFaultReportLog> logs = homAutoFaultReportLogService.getLogsByTikcketId(report.getSobotTicketId());
        if (report == null) {
            return null;
        }
        AppRepairDetailDTO data = new AppRepairDetailDTO();
        data.setRepairId(report.getId());
        data.setContent(report.getContent());
        data.setDeviceName(report.getDeviceName());
        data.setTicketId(report.getSobotTicketId());
        data.setStatusName(FaultReportStatusEnum.getStatusByCode(String.valueOf(report.getStatus())).getMsg());
        data.setRepairTime(report.getRepairTime());
        if (!CollectionUtils.isEmpty(logs)) {
            data.setLogs(logs.stream().map(i -> {
                AppRepairDetailLogDTO logDTO = new AppRepairDetailLogDTO();
                logDTO.setOperateTime(i.getCreateTime());
                logDTO.setStatusName(FaultReportStatusEnum.getStatusByCode(String.valueOf(report.getStatus())).getMsg());
                logDTO.setRemark(i.getRemark());
                return logDTO;
            }).collect(Collectors.toList()));
        }
        return data;
    }

    @Override
    public void updateStatus(List<SobotCallBackContentDTO> tickets) {
        if (!CollectionUtils.isEmpty(tickets)) {
            for (SobotCallBackContentDTO ticket : tickets) {
                String ticketid = ticket.getTicketid();
                Integer ticket_status = ticket.getTicket_status();
                String reply_content = ticket.getReply_content();
                if (StringUtils.isEmpty(ticketid)) {
                    continue;
                }
                updateStatus(ticketid, ticket_status, reply_content);
            }
        }
    }

    private void updateStatus(String ticketid, Integer ticket_status, String reply_content) {
        sobotTicketService.updateStatusByTicketId(ticketid, ticket_status);
        if (!FaultReportStatusEnum.exist(String.valueOf(ticket_status))) {
            return;
        }

        UpdateWrapper<HomeAutoFaultReport> updateWrapper = new UpdateWrapper<HomeAutoFaultReport>();
        updateWrapper.eq("sobot_ticket_id", ticketid);
        updateWrapper.set("status", ticket_status);
        update(updateWrapper);
        // 插入记录
        homAutoFaultReportLogService.saveOperate(ticketid, ticket_status, reply_content);
    }


}
