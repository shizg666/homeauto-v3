package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.online.FamilyScreenOnline;
import com.landleaf.homeauto.center.device.model.mapper.FamilyScreenOnlineMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceInfoStatusService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyScreenOnlineService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-08
 */
@Service
public class FamilyScreenOnlineServiceImpl extends ServiceImpl<FamilyScreenOnlineMapper, FamilyScreenOnline> implements IFamilyScreenOnlineService {
    @Autowired
    private IFamilyDeviceInfoStatusService deviceInfoStatusService;
    @Override
    public void updateStatus(List<FamilyScreenOnline> screenOnlineList) {
        List<String> screenMacs = screenOnlineList.stream().map(i -> i.getScreenMac()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(screenMacs)){
            return;
        }
        QueryWrapper<FamilyScreenOnline> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("screen_mac",screenMacs);
        queryWrapper.eq("current",1);
        List<FamilyScreenOnline> existFamilyScreenOnlines = list(queryWrapper);
        List<FamilyScreenOnline> saveData = Lists.newArrayList();
        List<FamilyScreenOnline> updateData = Lists.newArrayList();
        if(CollectionUtils.isEmpty(existFamilyScreenOnlines)){
            saveData.addAll(screenOnlineList);
        }else {
            Map<String, FamilyScreenOnline> screenOnlineMap = existFamilyScreenOnlines.stream().collect(Collectors.toMap(FamilyScreenOnline::getScreenMac, i -> i, (n, o) -> o));
            List<String> existMacs = existFamilyScreenOnlines.stream().map(i -> i.getScreenMac()).collect(Collectors.toList());
            saveData.addAll(screenOnlineList.stream().filter(i->{
                if(!existMacs.contains(i.getScreenMac())){
                    return true;
                }
                FamilyScreenOnline familyScreenOnline = screenOnlineMap.get(i.getScreenMac());
                if(familyScreenOnline!=null&&familyScreenOnline.getStatus()!=i.getStatus().intValue()){
                    return true;
                }
                return false;
            }).collect(Collectors.toList()));
            updateData.addAll(screenOnlineList.stream().filter(i->{
                FamilyScreenOnline familyScreenOnline = screenOnlineMap.get(i.getScreenMac());
                if(familyScreenOnline!=null&&familyScreenOnline.getStatus()==i.getStatus().intValue()){
                    return true;
                }
                return false;
            }).map(i->{
                FamilyScreenOnline familyScreenOnline = screenOnlineMap.get(i.getScreenMac());
                familyScreenOnline.setEndTime(LocalDateTime.now());
                return familyScreenOnline;
            }).collect(Collectors.toList()));
        }
        if(!CollectionUtils.isEmpty(saveData)){
            //更新其它的记录为0
            List<String> historyRecords = Lists.newArrayList();
            saveData.stream().forEach(i->{
                i.setStartTime(LocalDateTime.now());
                i.setEndTime(LocalDateTime.now());
                i.setCurrent(1);
                historyRecords.add(i.getScreenMac());
            });
            UpdateWrapper<FamilyScreenOnline> updateWrapper = new UpdateWrapper<>();
            updateWrapper.in("screen_mac",historyRecords);
            updateWrapper.set("current",0);
            update(updateWrapper);
            saveBatch(saveData);
        }
        if(!CollectionUtils.isEmpty(updateData)){
           updateBatchById(updateData);
        }
        // 通知修改设备上下线状态，上线==》全上线，下线==》全下线
        for (FamilyScreenOnline info : screenOnlineList) {
            deviceInfoStatusService.updateOnLineFlagByFamily(info.getFamilyId(),info.getStatus());
        }


    }
}
