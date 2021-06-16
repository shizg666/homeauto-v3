package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.vo.statistics.DeviceStatisticsBO;
import com.landleaf.homeauto.center.device.model.vo.statistics.FamilyStatistics;
import com.landleaf.homeauto.center.device.model.vo.statistics.KanBanStatistics;
import com.landleaf.homeauto.center.device.model.vo.statistics.KanBanStatisticsQry;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * @ClassName IKanBanServiceImpl
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/11
 * @Version V1.0
 **/
@Slf4j
@Service
public class IKanBanServiceImpl implements IKanBanService {
    @Autowired
    private IHomeAutoFamilyService iHomeAutoFamilyService;

    @Autowired
    private IProjectHouseTemplateService iProjectHouseTemplateService;
    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IFamilyDeviceInfoStatusService iFamilyDeviceInfoStatusService;
    @Qualifier("serviceTaskExecutor")
    private ThreadPoolTaskExecutor executor;
    //统计的品类
    private static Set<String> totalCategory = Sets.newHashSet(CategoryTypeEnum.TEMPERATURE_PANEL.getType(),CategoryTypeEnum.MULTI_PARAM.getType(),CategoryTypeEnum.AIRCONDITIONER.getType(),CategoryTypeEnum.FRESH_AIR.getType(),CategoryTypeEnum.HOST.getType());

    @Override
        public List<KanBanStatistics> getKanbanStatistics(KanBanStatisticsQry request)  {
        List<KanBanStatistics> result = Lists.newArrayList();
        List<String> paths = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(request.getPaths())){
            String realestatedStr = String.valueOf(request.getRealestateId()).concat("/");
            paths = request.getPaths().stream().map(o->realestatedStr.concat(o)).collect(Collectors.toList());
        }else {
            paths.add(request.getRealestateId());
        }
        //获取家庭信息
        List<FamilyStatistics> familyStatisticsList = iHomeAutoFamilyService.getFamilyCountByPath2(paths);
        KanBanStatistics familyStatistic = new KanBanStatistics();
        familyStatistic.setCode("999");
        familyStatistic.setName("住户统计");
        if (CollectionUtils.isEmpty(familyStatisticsList)){
            familyStatistic.setCount(0);
        }else {
            familyStatistic.setCount(familyStatisticsList.size());
        }
        result.add(familyStatistic);

        //获取设备故障数
        List<Long> familyIds  = familyStatisticsList.stream().map(FamilyStatistics::getFamilyId).distinct().collect(Collectors.toList());

        //设备总数统计 DEVICE_TOTAL 和各分类设备数量统计
        FutureTask<Map<String,Integer>> counttask = new FutureTask(() -> deviceCountStatistic(familyStatisticsList));
        //统计各品类故障设备和在线设备
        FutureTask<Map<String,Map<String,Integer>>> task = new FutureTask(() ->getErrorDeviceCount(familyIds));
        executor.submit(counttask);
        executor.submit(task);
        try {
            Map<String,Integer> countMap = counttask.get();
        }catch (Exception ex){

        }



        return null;
    }

    /**
     * 设备故障和在线离线统计
     * @param familyIds
     * @return
     */
    private Map<String,Map<String,Integer>> getErrorDeviceCount(List<Long> familyIds) {
        Map<String,Map<String,Integer>> result = Maps.newHashMap();
        List<FamilyDeviceInfoStatus> deviceInfoStatuses = iFamilyDeviceInfoStatusService.getListStatistic(familyIds);
        if (CollectionUtils.isEmpty(deviceInfoStatuses)){
            Map<String,Integer> defaultMap =  deviceDefaultStatistic();
            result.put("error",defaultMap);
            result.put("online",defaultMap);
            return result;
        }

        List<FamilyDeviceInfoStatus> errorList = deviceInfoStatuses.stream().filter(status->1==status.getHavcFaultFlag()).collect(Collectors.toList());
        List<FamilyDeviceInfoStatus> onlineList = deviceInfoStatuses.stream().filter(status->1==status.getHavcFaultFlag()).collect(Collectors.toList());
        Map<String,List<FamilyDeviceInfoStatus>> errorMapBo = errorList.stream().collect(Collectors.groupingBy(FamilyDeviceInfoStatus::getCategoryCode));
        Map<String,List<FamilyDeviceInfoStatus>> onlineMapBo =onlineList.stream().collect(Collectors.groupingBy(FamilyDeviceInfoStatus::getCategoryCode));
        Map<String,Integer> errorMap = Maps.newHashMap();
        Map<String,Integer> onlineMap = Maps.newHashMap();
        errorMapBo.forEach((category,deviceErrorList)->{
            if (totalCategory.contains(category)){
                errorMap.put(category,deviceErrorList.size());
            }
        });
        onlineMapBo.forEach((category,deviceErrorList)->{
            if (totalCategory.contains(category)){
                onlineMap.put(category,deviceErrorList.size());
            }
        });
        result.put("error",errorMap);
        result.put("online",onlineMap);
        return result;
    }

    //设备总数统计 DEVICE_TOTAL 和各分类设备数量统计
    public Map<String,Integer>deviceCountStatistic(List<FamilyStatistics> familyStatisticsList){
        Map<String,Integer> result = Maps.newHashMap();
        List<Long> templateIds  = familyStatisticsList.stream().map(FamilyStatistics::getTemplateId).distinct().collect(Collectors.toList());
        //获取户型设备列表
        List<DeviceStatisticsBO> listDeviceStatistics = iHouseTemplateDeviceService.getListDeviceStatistics(templateIds);
        if (CollectionUtils.isEmpty(listDeviceStatistics)){
            return deviceDefaultStatistic();
        }
        //户型-设备
        Map<Long,List<DeviceStatisticsBO>> templateDeviceMap = listDeviceStatistics.stream().collect(Collectors.groupingBy(DeviceStatisticsBO::getTemplateId));
        //户型-家庭数量
        Map<Long,List<FamilyStatistics>> templateFamilyMap = familyStatisticsList.stream().collect(Collectors.groupingBy(FamilyStatistics::getTemplateId));
        int count = 0;
        for (Map.Entry<Long, List<DeviceStatisticsBO>> entry : templateDeviceMap.entrySet()) {
            Long templateId = entry.getKey();
            List<DeviceStatisticsBO> deviceList = entry.getValue();
            if (CollectionUtils.isEmpty(deviceList)) {
                continue;
            }
            int templateFamily = Objects.isNull(templateFamilyMap.get(templateId)) ? 0 : templateFamilyMap.get(templateId).size();
            //户型设备总数 = 户型设备数*户型家庭数
            count = count + deviceList.size() * templateFamily;
            //分类统计
            categoryDeviceTotal(deviceList,result,templateFamily);

        }
        result.put("device_total",count);
        return result;
    }

    /**
     * 设备分类统计
     * @param deviceList
     * @param result
     * @param familyCount
     */
    private void categoryDeviceTotal(List<DeviceStatisticsBO> deviceList, Map<String, Integer> result, int familyCount) {
        Map<String,List<DeviceStatisticsBO>> categoryMap = deviceList.stream().collect(Collectors.groupingBy(DeviceStatisticsBO::getCategoryCode));
        categoryMap.forEach((categoryCode,devices)->{
            if (result.containsKey(categoryCode)){
                result.put(categoryCode,result.get(categoryCode)+devices.size()*familyCount);
            }else {
                result.put(categoryCode,devices.size()*familyCount);
            }
        });
    }

    /**
     * 设备相关数量统计返回默认值
     * @return
     */
    private Map<String, Integer> deviceDefaultStatistic() {
        Map<String,Integer> data = Maps.newHashMapWithExpectedSize(1);
        data.put("device_total",0);
        data.put(CategoryTypeEnum.TEMPERATURE_PANEL.getType(),0);
        data.put(CategoryTypeEnum.MULTI_PARAM.getType(),0);
        data.put(CategoryTypeEnum.AIRCONDITIONER.getType(),0);
        data.put(CategoryTypeEnum.FRESH_AIR.getType(),0);
        data.put(CategoryTypeEnum.HOST.getType(),0);
        return data;
    }

    //设备故障统计 DEVICE_ERROR
    public int deviceErrorStatistic(KanBanStatisticsQry request){
        return 0;
    }
    //维保统计 maintenance
    public int maintenanceStatistic(KanBanStatisticsQry request){
        return 0;
    }

}
