package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalOnlineStatusDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyTerminalOnlineStatusMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyTerminalOnlineStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyTerminalService;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 终端在线离线状态记录表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-01
 */
@Service
public class FamilyTerminalOnlineStatusServiceImpl extends ServiceImpl<FamilyTerminalOnlineStatusMapper, FamilyTerminalOnlineStatusDO> implements IFamilyTerminalOnlineStatusService {

    @Autowired
    private IFamilyTerminalService familyTerminalService;

    @Override
    public FamilyTerminalOnlineStatusDO getLatestRecord(String familyId, String mac, String terminalId) {
        return this.baseMapper.getLatestRecord(familyId, mac, terminalId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTerminalOnLineStatus(String familyId, String terminalMac, Integer status) {

        FamilyTerminalDO masterTerminal = familyTerminalService.getMasterTerminal(familyId);
        if (masterTerminal == null) {
            return;
        }
        String terminalId = masterTerminal.getId();
        // 查询最近一次状态记录,更新结束时间;若不一致或无记录,新增加一条记录
        FamilyTerminalOnlineStatusDO latestRecord = getLatestRecord(familyId, masterTerminal.getMac(), terminalId);
        boolean needSave = false;
        boolean needUpdate = false;
        if (latestRecord == null) {
            // 新增一条
            needSave = true;
        } else if (latestRecord.getStatus().intValue() != status.intValue()) {
            // 新增一条,同时更改已有记录
            needSave = true;
            needUpdate = true;
        } else {
            needUpdate = true;
        }
        Date currentDate = new Date();
        if (needSave) {
            FamilyTerminalOnlineStatusDO saveData = new FamilyTerminalOnlineStatusDO();
            saveData.setFamilyId(familyId);
            saveData.setMac(terminalMac);
            saveData.setTerminalId(terminalId);
            saveData.setStatus(status);
            saveData.setStartTime(LocalDateTimeUtil.date2LocalDateTime(currentDate));
            saveData.setEndTime(LocalDateTimeUtil.date2LocalDateTime(currentDate));
            save(saveData);
        }

        if (needUpdate) {
            latestRecord.setEndTime(LocalDateTimeUtil.date2LocalDateTime(currentDate));
            updateById(latestRecord);
        }

    }
}
