package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFaultReportMapper;
import com.landleaf.homeauto.center.device.remote.UserRemote;
import com.landleaf.homeauto.center.device.service.SobotService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomAutoFaultReportLogService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeautoFaultReportService;
import com.landleaf.homeauto.center.device.service.mybatis.ISobotTicketService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.constant.enums.FaultReportStatusEnum;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairDetailDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.AppRepairDetailLogDTO;
import com.landleaf.homeauto.common.domain.dto.device.repair.RepairAddReqDTO;
import com.landleaf.homeauto.common.domain.dto.device.sobot.ticket.callback.SobotCallBackContentDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerInfoDTO;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReport;
import com.landleaf.homeauto.common.domain.po.device.sobot.HomeAutoFaultReportLog;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
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
            HomeAutoFaultReport report = sobotService.createUserTicket(requestBody.getDeviceName(), requestBody.getContentCode(), BeanUtil.convertString2Long(requestBody.getFamilyId()), mobile, userId);
            save(report);
        }
    }

    @Override
    public List<AppRepairDetailDTO> listRepairs(String familyId) {


        List<AppRepairDetailDTO> data = Lists.newArrayList();
        if(StringUtils.isEmpty(familyId)){
            return data;
        }
        LambdaQueryWrapper<HomeAutoFaultReport> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(familyId)) {
            queryWrapper.eq(HomeAutoFaultReport::getFamilyId, familyId);
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
                dto.setTicketCode(report.getSobotTicketCode());
                dto.setStatusCode(String.valueOf(report.getStatus()));
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
        data.setTicketCode(report.getSobotTicketCode());
        data.setStatusCode(String.valueOf(report.getStatus()));
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

                int count = this.count(new QueryWrapper<HomeAutoFaultReport>().in("sobot_ticket_id", Arrays.asList(new String[]{ticketid})));
                if (count <= 0) {
                    continue;
                }
                updateStatus(ticketid, ticket_status, reply_content);
            }
        }
    }

    @Override
    public void completed(String repairId, String userId) {
        HomeAutoFaultReport report = getById(repairId);
        if (report == null) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_DATA_EXIST);
        }
        report.setStatus(Integer.parseInt(FaultReportStatusEnum.CLOSED.getCode()));
        updateById(report);
        //记录更新
        homAutoFaultReportLogService.saveOperate(report.getSobotTicketId(), report.getStatus(), "用户操作完成");
    }


    private void updateStatus(String ticketid, Integer ticket_status, String reply_content) {
        sobotTicketService.updateStatusByTicketId(ticketid, ticket_status);
        if (!FaultReportStatusEnum.exist(String.valueOf(ticket_status))) {
            return;
        }
        HomeAutoFaultReport faultReport = getByTicketId(ticketid);
        if(faultReport!=null&&faultReport.getStatus().intValue()!=Integer.parseInt(FaultReportStatusEnum.CLOSED.getCode())){

            UpdateWrapper<HomeAutoFaultReport> updateWrapper = new UpdateWrapper<HomeAutoFaultReport>();
            updateWrapper.eq("sobot_ticket_id", ticketid);
            updateWrapper.notIn("status", Integer.parseInt(FaultReportStatusEnum.CLOSED.getCode()));
            updateWrapper.set("status", ticket_status);
            update(updateWrapper);
            homAutoFaultReportLogService.saveOperate(ticketid, ticket_status, reply_content);
        }
    }

    private HomeAutoFaultReport getByTicketId(String ticketid) {

        QueryWrapper<HomeAutoFaultReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sobot_ticket_id", ticketid);
        List<HomeAutoFaultReport> list = list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().sorted(Comparator.comparing(HomeAutoFaultReport::getCreateTime));
            return list.get(0);
        }
        return null;
    }


}
