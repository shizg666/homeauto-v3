package com.landleaf.homeauto.center.data.schedule;

import cn.hutool.core.date.DateUtil;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceEnergyDay;
import com.landleaf.homeauto.center.data.domain.FamilyDevicePowerHistory;
import com.landleaf.homeauto.center.data.service.IFamilyDevicePowerHistoryService;
import com.landleaf.homeauto.center.data.service.impl.FamilyDeviceEnergyDayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @Classname EnergySchedule
 * @Description 每天凌晨执行昨天的功耗计算
 * @Date 2021/7/15 16:24
 * @Created by binfoo
 */
@Component
@Slf4j
public class EnergySchedule {

    @Autowired
    private IFamilyDevicePowerHistoryService historyService;

    @Autowired
    private FamilyDeviceEnergyDayServiceImpl energyDayService;

    /**
     * 每凌晨1分计算昨天的
     */
    @Scheduled(cron = "0 1 0 * * *")
    public  void  dealEnergy(){

        //1.先计算glc

       deal("glcPower");
       deal("glvPower");



    }

    public void  deal(String code){
        //1.先计算glc
        Map<Long, List<FamilyDevicePowerHistory>> map;
        Map<Long, List<FamilyDeviceEnergyDay>> yesterdayMap;

        if (code.equals("glcPower")) {
             map = historyService.getGlcPowerYesterday("", "");
             yesterdayMap =  energyDayService.getGlcEnergyYesterday(-2);
        }else if(code.equals("glvPower")){
            map = historyService.getGlvPowerYesterday("", "");
            yesterdayMap =  energyDayService.getGlvEnergyYesterday(-2);
        }else {
            return;
        }




        String uploadTime = DateUtil.yesterday().toDateStr()+ " 23:59:59";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (map != null && !map.isEmpty()){

            for (Map.Entry entry:map.entrySet()) {
                Long familyId = (Long) entry.getKey();

                List<FamilyDevicePowerHistory> glcList = (List<FamilyDevicePowerHistory>) entry.getValue();

                if (glcList.size() > 1){//至少两个数字才能计算

                    Double todayValue = 0.00;

                    for (int i =0; i< glcList.size()-1;i++){

                        FamilyDevicePowerHistory iObject = glcList.get(i);
                        FamilyDevicePowerHistory iObject2 = glcList.get(i+1);

                        todayValue = todayValue + (Double.valueOf(iObject.getStatusValue()) + Double.valueOf(iObject2.getStatusValue()))/2
                                * Duration.between(iObject2.getUploadTime(),iObject.getUploadTime()).toHours();//符号为wh

                    }

                    FamilyDeviceEnergyDay newDay = new FamilyDeviceEnergyDay();

                    FamilyDevicePowerHistory history1 = glcList.get(0);

                    newDay.setDeviceSn(history1.getDeviceSn());
                    newDay.setBasicValue(0.00);
                    newDay.setProjectId(history1.getProjectId());
                    newDay.setTodayValue(todayValue);
                    newDay.setRealestateId(history1.getRealestateId());
                    newDay.setFamilyId(familyId);
                    newDay.setStatusCode(history1.getStatusCode());
                    newDay.setUploadTime(LocalDateTime.parse(uploadTime,df));

                    if (yesterdayMap !=null && !yesterdayMap.isEmpty()){
                        if (yesterdayMap.containsKey(familyId)){
                            List<FamilyDeviceEnergyDay> list2 = yesterdayMap.get(familyId);

                            if (list2.size() >0){
                                FamilyDeviceEnergyDay glcYest = list2.get(0);
                                newDay.setBasicValue(glcYest.getBasicValue()+glcYest.getTodayValue());//的basic为前天的+昨天的

                            }
                        }
                    }

                    energyDayService.save(newDay);//插入



                }


            }
        }


    }

}
