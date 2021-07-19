package com.landleaf.homeauto.center.data.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceEnergyDay;
import com.landleaf.homeauto.center.data.domain.FamilyDevicePowerHistory;
import com.landleaf.homeauto.center.data.domain.bo.FamilyDevicePowerDO;
import com.landleaf.homeauto.center.data.mapper.FamilyDeviceEnergyDayMapper;
import com.landleaf.homeauto.center.data.mapper.FamilyDevicePowerHistoryMapper;
import com.landleaf.homeauto.center.data.service.IFamilyDeviceEnergyDayService;
import com.landleaf.homeauto.center.data.service.IFamilyDevicePowerHistoryService;
import com.landleaf.homeauto.center.data.util.DateUtil2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备能耗
 * </p>
 *
 */
@Service
@Slf4j
public class FamilyDeviceEnergyDayServiceImpl extends ServiceImpl<FamilyDeviceEnergyDayMapper, FamilyDeviceEnergyDay> implements IFamilyDeviceEnergyDayService {
    @Override
    public Map<Long, List<FamilyDeviceEnergyDay>> getGlcEnergyYesterday(int offset) {


        LambdaQueryWrapper<FamilyDeviceEnergyDay> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.eq(FamilyDeviceEnergyDay::getStatusCode,"glcPower");

        //定时任务是-2，查询是-1
        String startTime = DateUtil.offsetDay(new Date(), offset).toDateStr() + " 00:00:00";
        String endTime = DateUtil.offsetDay(new Date(), offset).toDateStr()+ " 23:59:59";
        queryWrapper.apply("(upload_time>= TO_TIMESTAMP('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and " +
                "upload_time<= TO_TIMESTAMP('" + endTime + "','YYYY-MM-DD hh24:mi:ss')) ");

        queryWrapper.orderByDesc(FamilyDeviceEnergyDay::getUploadTime);

        List<FamilyDeviceEnergyDay> list = list(queryWrapper);

        if (list.size() <= 0){
            return null;
        }

        Map<Long, List<FamilyDeviceEnergyDay>> map = list.stream().collect(Collectors.groupingBy(FamilyDeviceEnergyDay::getFamilyId));

        return map;
    }

    @Override
    public Map<Long, List<FamilyDeviceEnergyDay>> getGlvEnergyYesterday(int offset) {


        LambdaQueryWrapper<FamilyDeviceEnergyDay> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.eq(FamilyDeviceEnergyDay::getStatusCode,"glvPower");

        String startTime = DateUtil.offsetDay(new Date(), offset).toDateStr() + " 00:00:00";
        String endTime = DateUtil.offsetDay(new Date(), offset).toDateStr()+" 23:59:59";
        queryWrapper.apply("(upload_time>= TO_TIMESTAMP('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and " +
                "upload_time<= TO_TIMESTAMP('" + endTime + "','YYYY-MM-DD hh24:mi:ss')) ");

        queryWrapper.orderByDesc(FamilyDeviceEnergyDay::getUploadTime);

        List<FamilyDeviceEnergyDay> list = list(queryWrapper);

        if (list.size() <= 0){
            return null;
        }

        Map<Long, List<FamilyDeviceEnergyDay>> map = list.stream().collect(Collectors.groupingBy(FamilyDeviceEnergyDay::getFamilyId));

        return map;
    }

    @Override
    public double getLastValue(Long familyId, String statusCode, String uploadTime) {
        LambdaQueryWrapper<FamilyDeviceEnergyDay> queryWrapper = new LambdaQueryWrapper();

        double last = 0.00;

        if (familyId > 0){
            queryWrapper.eq(FamilyDeviceEnergyDay::getFamilyId,familyId);
        }

        if (StringUtils.isNotBlank(statusCode)) {
            queryWrapper.eq(FamilyDeviceEnergyDay::getStatusCode, statusCode);
        }



        if (StringUtils.isNotBlank(uploadTime)){
            String startTime = uploadTime + " 00:00:00";
            String endTime = uploadTime + " 23:59:59";
            queryWrapper.apply("(upload_time>= TO_TIMESTAMP('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and upload_time<= TO_TIMESTAMP('" + endTime + "','YYYY-MM-DD hh24:mi:ss')) ");
        }

        List<FamilyDeviceEnergyDay> list = list(queryWrapper);

        if (list.size() <= 0){
            return last;
        }

        last =  list.get(0).getTodayValue() +  list.get(0).getBasicValue();

        return last;

    }


//    @Override
//    public BasePageVO<FamilyDeviceStatusHistory> getStatusByFamily(HistoryQryDTO2 historyQryDTO) {
//
//        BasePageVO<FamilyDeviceStatusHistory> basePageVO = new BasePageVO<>();
//
//        PageHelper.startPage(historyQryDTO.getPageNum(),historyQryDTO.getPageSize(),true);
//
//        LambdaQueryWrapper<FamilyDeviceStatusHistory> queryWrapper = new LambdaQueryWrapper<>();
//
//        Long familyId = historyQryDTO.getFamilyId();
//        String sn = historyQryDTO.getDeviceSn();
//
//        if (StringUtils.isNotBlank(historyQryDTO.getCode())){
//            queryWrapper.eq(FamilyDeviceStatusHistory::getStatusCode ,historyQryDTO.getCode());
//        }
//
//        if (StringUtils.isNotBlank(sn)){
//            queryWrapper.eq(FamilyDeviceStatusHistory::getDeviceSn ,sn);
//        }
//        if (familyId > 0){
//            queryWrapper.eq(FamilyDeviceStatusHistory::getFamilyId,familyId);
//        }
//
//        List<String> uploadTimes = historyQryDTO.getUploadTimes();
//
//        String startTime = "";
//        String endTime = "";
//
//
//        if (!CollectionUtils.isEmpty(uploadTimes) && uploadTimes.size() ==2){
//            startTime = uploadTimes.get(0);
//            endTime = uploadTimes.get(1);
//        }
//
//        if (StringUtils.isNotBlank(startTime)&&StringUtils.isNotBlank(endTime)){
//            queryWrapper.apply("(upload_time>= TO_TIMESTAMP('" + startTime + "','YYYY-MM-DD hh24:mi:ss') and upload_time<= TO_TIMESTAMP('" + endTime + "','YYYY-MM-DD hh24:mi:ss')) ");
//        }
//
//        queryWrapper.orderByDesc(FamilyDeviceStatusHistory::getUploadTime);
//        List<FamilyDeviceStatusHistory> result = list(queryWrapper);
//
//
//        PageInfo pageInfo = new PageInfo(result);
//
//        pageInfo.setList(result);
//
//        basePageVO = BeanUtil.mapperBean(pageInfo, BasePageVO.class);
//
//
//        return basePageVO;
//    }

    public static void main(String[] args) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(LocalDateTime.parse("2021-07-15 19:29:00",df));

        LocalDateTime localDateTime = LocalDateTime.now();

        DateUtil2.localDateTime2Date(localDateTime);

        String date = DateUtil.formatDate( DateUtil2.localDateTime2Date(localDateTime));

        System.out.println("****" +date);




        String tempDay = DateUtil.formatDate(DateUtil.offsetDay(  DateUtil.parse("2021-07-19"),-1));

        System.out.println(tempDay);



//        System.out.println(DateUtil.offsetDay(new Date(), -5).toDateStr());
    }
}
