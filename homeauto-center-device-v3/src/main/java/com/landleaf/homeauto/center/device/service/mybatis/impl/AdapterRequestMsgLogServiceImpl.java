package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.AdapterRequestMsgLog;
import com.landleaf.homeauto.center.device.model.mapper.AdapterRequestMsgLogMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IAdapterRequestMsgLogService;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageBaseDTO;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

/**
 * <p>
 * 控制命令操作日志 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Service
@Slf4j
public class AdapterRequestMsgLogServiceImpl extends ServiceImpl<AdapterRequestMsgLogMapper, AdapterRequestMsgLog> implements IAdapterRequestMsgLogService {

    @Async(value = "adapterRequestMsgLogExecute")
    @Override
    public void saveRecord(AdapterMessageBaseDTO message, String content) {

        AdapterRequestMsgLog saveData = new AdapterRequestMsgLog();
        BeanUtils.copyProperties(message, saveData);
        saveData.setFamilyId(BeanUtil.convertString2Long(message.getFamilyId()));
        saveData.setContent(content);
        saveData.setRetryTimes(0);
        save(saveData);
    }

    @Async(value = "adapterRequestMsgLogExecute")
    @Override
    public void updateRecord(AdapterMessageAckDTO message) {

        QueryWrapper<AdapterRequestMsgLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_id", message.getMessageId());
        queryWrapper.eq("family_id", BeanUtil.convertString2Long(message.getFamilyId()));
        queryWrapper.orderByDesc("create_time");
        List<AdapterRequestMsgLog> list = list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            AdapterRequestMsgLog updateLog = list.get(0);
            updateLog.setCode(message.getCode());
            updateLog.setMessage(message.getMessage());
            updateById(updateLog);
        }
    }

    @Async(value = "adapterRequestMsgLogExecute")
    @Override
    public void updateRecordRetry(String messageId, String familyId) {

        UpdateWrapper<AdapterRequestMsgLog> updateWrapper = new UpdateWrapper<>();
        updateWrapper.setSql("retry_times = retry_times+1");
        updateWrapper.eq("message_id", messageId);
        updateWrapper.eq("family_id", BeanUtil.convertString2Long(familyId));
        update(updateWrapper);

    }

    @Override
    public void updateRecordRetryResult(AdapterMessageAckDTO message) {
        QueryWrapper<AdapterRequestMsgLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("family_id", BeanUtil.convertString2Long(message.getFamilyId()));
        queryWrapper.eq("message_id", message.getMessageId());
        queryWrapper.orderByDesc("update_time");
        List<AdapterRequestMsgLog> list = list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            AdapterRequestMsgLog updateLog = list.get(0);
            updateLog.setMessage(message.getMessage());
            updateLog.setCode(message.getCode());
            updateById(updateLog);
        }
    }

    /**
     * 查询需要重试的记录
     *
     * @return
     */
    @Override
    public List<AdapterRequestMsgLog> findRetryRecord() {
        QueryWrapper<AdapterRequestMsgLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_name", "family_config_update");
        queryWrapper.notIn("code", 200);
        queryWrapper.lt("retry_times", 3);
        String endTime = DateFormatUtils.format(LocalDateTimeUtil.offsetDate(new Date(), -2, HOURS), "yyyy-MM-dd HH:mm:ss");
        String startTime = DateFormatUtils.format(LocalDateTimeUtil.offsetDate(new Date(), -4, HOURS), "yyyy-MM-dd HH:mm:ss");
        queryWrapper.apply("update_time>= TO_TIMESTAMP('" + startTime + "','yyyy-mm-dd hh24:mi:ss')");
        queryWrapper.apply("update_time<= TO_TIMESTAMP('" + endTime + "','yyyy-mm-dd hh24:mi:ss')");
        queryWrapper.orderByDesc("update_time");
        log.info("[重试配置更新通知]-查询时间段:{}-{}",startTime,endTime);
        return list(queryWrapper);
    }

}
