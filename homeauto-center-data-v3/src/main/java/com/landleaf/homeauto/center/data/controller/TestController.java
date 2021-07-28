package com.landleaf.homeauto.center.data.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceEnergyDay;
import com.landleaf.homeauto.center.data.domain.FamilyDevicePowerHistory;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceStatusHistory;
import com.landleaf.homeauto.center.data.domain.HistoryQryDTO2;
import com.landleaf.homeauto.center.data.mapper.FamilyDevicePowerHistoryMapper;
import com.landleaf.homeauto.center.data.schedule.EnergySchedule;
import com.landleaf.homeauto.center.data.service.impl.FamilyDeviceEnergyDayServiceImpl;
import com.landleaf.homeauto.center.data.service.impl.FamilyDevicePowerHistoryServiceImpl;
import com.landleaf.homeauto.common.constant.RocketMqConst;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceControlAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterDeviceControlDTO;
import com.landleaf.homeauto.common.domain.dto.screen.ScreenDeviceAttributeDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.enums.adapter.AdapterMessageNameEnum;
import com.landleaf.homeauto.common.rocketmq.producer.processor.MQProducerSendMsgProcessor;
import com.landleaf.homeauto.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description 通信桥测试
 * @Author zhanghongbin
 * @Date 2020/8/27 17:01
 */
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {


    @Autowired
    private FamilyDevicePowerHistoryServiceImpl powerHistoryService;

    @Autowired
    private FamilyDeviceEnergyDayServiceImpl energyDayService;

    @Autowired
    private EnergySchedule schedule;




    /**
     * 测试mq发送
     */
    @GetMapping("/power")
    public Map<Long, List<FamilyDevicePowerHistory>> getList(){
        return powerHistoryService.getGlcPowerYesterday("2021-07-15 00:00:00","2021-07-15 23:59:59");
    }

    /**
     * 测试在线的大屏
     */
    @GetMapping("/history")
    public BasePageVO<FamilyDeviceStatusHistory> screenConut(){

        HistoryQryDTO2 historyQryDTO = new HistoryQryDTO2();
        historyQryDTO.setPageSize(9999);
        historyQryDTO.setFamilyId(202035L);
        historyQryDTO.setDeviceSn("1");
        historyQryDTO.setCode("glcPower");
        List<String> upTimes = Arrays.asList("2021-07-15 00:00:00","2021-07-15 23:59:59");
        historyQryDTO.setUploadTimes(upTimes);

        return powerHistoryService.getStatusByFamily(historyQryDTO);
    }

    @GetMapping("/test33")
    public void deviceEngergy1(){
        schedule.dealEnergytest();
    }

    /**
     * 测试mq消费
     */
    @GetMapping("/save")
    public void deviceEngergy(){

            //1.先计算glc

            Map<Long, List<FamilyDevicePowerHistory>> glcMap = getList();

            Map<Long, List<FamilyDeviceEnergyDay>> glcYesterdayMap = energyDayService.getGlcEnergyYesterday(-5);


            String uploadTime =  "2021-07-15 23:59:59";

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
                                    * Duration.between(iObject.getUploadTime(),iObject2.getUploadTime()).toHours();//符号为wh

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
