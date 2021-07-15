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

        Map<Long, List<FamilyDevicePowerHistory>> glcMap =  historyService.getGlcPowerYesterday();

        Map<Long, List<FamilyDeviceEnergyDay>> glcYesterdayMap = energyDayService.getGlcEnergyYesterday();


        String uploadTime = DateUtil.yesterday().toDateStr()+ " 23:59:59";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (glcMap != null && !glcMap.isEmpty()){

            for (Map.Entry entry:glcMap.entrySet()) {
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

                    FamilyDeviceEnergyDay newGlc = new FamilyDeviceEnergyDay();

                    FamilyDevicePowerHistory history1 = glcList.get(0);

                    newGlc.setDeviceSn(history1.getDeviceSn());
                    newGlc.setBasicValue(0.00);
                    newGlc.setProjectId(history1.getProjectId());
                    newGlc.setTodayValue(todayValue);
                    newGlc.setRealestateId(history1.getRealestateId());
                    newGlc.setFamilyId(familyId);
                    newGlc.setStatusCode(history1.getStatusCode());
                    newGlc.setUploadTime(LocalDateTime.parse(uploadTime,df));

                    if (glcYesterdayMap !=null && !glcYesterdayMap.isEmpty()){
                        if (glcYesterdayMap.containsKey(familyId)){
                            List<FamilyDeviceEnergyDay> list2 = glcYesterdayMap.get(familyId);

                            if (list2.size() >0){
                                FamilyDeviceEnergyDay glcYest = list2.get(0);
                                newGlc.setBasicValue(glcYest.getBasicValue()+glcYest.getTodayValue());//的basic为前天的+昨天的

                            }
                        }
                    }

                    energyDayService.save(newGlc);//插入



                }


            }
        }



    }

}
