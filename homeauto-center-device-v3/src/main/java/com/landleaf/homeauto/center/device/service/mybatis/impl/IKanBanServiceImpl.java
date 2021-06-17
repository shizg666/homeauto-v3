package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.device.model.domain.status.FamilyDeviceInfoStatus;
import com.landleaf.homeauto.center.device.model.vo.statistics.*;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.DateUtils;
import com.landleaf.homeauto.center.device.util.LocalDateTimeUtil;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
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
    @Autowired
    private IFamilyMaintenanceRecordService iFamilyMaintenanceRecordService;

    @Autowired
    private ThreadPoolTaskExecutor bussnessExecutor;
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
            paths.add(String.valueOf(request.getRealestateId()));
        }
        //获取家庭信息
        List<FamilyStatistics> familyStatisticsList = iHomeAutoFamilyService.getFamilyCountByPath2(paths);
        if (CollectionUtils.isEmpty(familyStatisticsList)){
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR.getCode(),"楼盘基本信息不全，请选择其他楼盘查看");        }
        KanBanStatistics familyStatistic = new KanBanStatistics();
        familyStatistic.setCode("family");
        familyStatistic.setName("住户统计");
        familyStatistic.setCount(familyStatisticsList.size());
        result.add(familyStatistic);

        List<Long> familyIds  = familyStatisticsList.stream().map(FamilyStatistics::getFamilyId).distinct().collect(Collectors.toList());

        //设备数量统计
        CompletableFuture<Map<String,Integer>> deviceCountTotal = CompletableFuture.supplyAsync(() -> deviceCountStatistic(familyStatisticsList),bussnessExecutor);
        //设别故障和现在统计
        CompletableFuture<List<KanBanStatistics>> errorDeviceList = CompletableFuture.supplyAsync(() -> getErrorDeviceCount(familyIds),bussnessExecutor);

        deviceCountTotal.thenAcceptBothAsync(errorDeviceList, new BiConsumer<Map<String, Integer>, List<KanBanStatistics>>() {
            @Override
            public void accept(Map<String, Integer> deviceCount, List<KanBanStatistics> errorDeviceList) {
                KanBanStatistics deviceTotal = new KanBanStatistics();
                deviceTotal.setCode("deviceTotal");
                deviceTotal.setName("设备总数统计");
                deviceTotal.setCount(deviceCount.get("deviceTotal"));
                result.add(deviceTotal);
                for (String categoryCode : totalCategory) {
                    Map<String,List<KanBanStatistics>> errorMap = errorDeviceList.stream().collect(Collectors.groupingBy(KanBanStatistics::getCode));
                    DeviceStatistics statistics = (DeviceStatistics) errorMap.get(categoryCode).get(0);
                    statistics.setCount(Objects.isNull(deviceCount.get(categoryCode))?0:deviceCount.get(categoryCode));
                    //离线 = 总数-在线
                    statistics.setOfflineCount(statistics.getCount()-statistics.getOnlineCount());
                }
                result.addAll(errorDeviceList);
            }
        },bussnessExecutor);

        //维保统计
        CompletableFuture<Void> maintenance = CompletableFuture.supplyAsync(() ->iFamilyMaintenanceRecordService.maintenanceStatistic(familyIds),bussnessExecutor).thenAccept(maint->result.add(maint));
        return result;
    }

    /**
     * 设备故障和在线离线统计
     * @param familyIds
     * @return
     */
    private List<KanBanStatistics> getErrorDeviceCount(List<Long> familyIds) {
        List<FamilyDeviceInfoStatus> deviceInfoStatuses = iFamilyDeviceInfoStatusService.getListStatistic(familyIds);
        if (CollectionUtils.isEmpty(deviceInfoStatuses)){
            return deviceDefaultStatistic();
        }
        List<KanBanStatistics> result = Lists.newArrayList();
        //故障设备
        List<FamilyDeviceInfoStatus> errorList = deviceInfoStatuses.stream().filter(status->1==status.getHavcFaultFlag()).collect(Collectors.toList());
        //故障设备的家庭数
        int errorFamilyCount = (int) errorList.stream().map(FamilyDeviceInfoStatus::getFamilyId).distinct().count();

        DeviceErrorStatistics errortotal = new DeviceErrorStatistics();
        errortotal.setCode("errorDeviceTotal");
        errortotal.setName("设备故障统计");
        result.add(errortotal);
        errortotal.setCount(errorList.size());
        errortotal.setFamilyCount(errorFamilyCount);

        //在线设备
        List<FamilyDeviceInfoStatus> onlineList = deviceInfoStatuses.stream().filter(status->1==status.getHavcFaultFlag()).collect(Collectors.toList());

        Map<String,List<FamilyDeviceInfoStatus>> errorMapBo = errorList.stream().collect(Collectors.groupingBy(FamilyDeviceInfoStatus::getCategoryCode));
        Map<String,List<FamilyDeviceInfoStatus>> onlineMapBo =onlineList.stream().collect(Collectors.groupingBy(FamilyDeviceInfoStatus::getCategoryCode));

        errorMapBo.forEach((categoryCode,deviceErrorList)->{
            if (totalCategory.contains(categoryCode)){
                DeviceStatistics deviceStatistics = new DeviceStatistics();
                deviceStatistics.setCode(categoryCode);
                deviceStatistics.setName(CategoryTypeEnum.getInstByType(categoryCode).getName());
                deviceStatistics.setErrorCount(deviceErrorList.size());
                if (CollectionUtils.isEmpty(onlineMapBo.get(categoryCode))){
                    deviceStatistics.setOnlineCount(0);
                }else {
                    deviceStatistics.setOnlineCount(onlineMapBo.get(categoryCode).size());
                }
                result.add(deviceStatistics);
            }
        });
        return result;
    }
    //设备总数统计 DEVICE_TOTAL 和各分类设备数量统计
    public Map<String,Integer>deviceCountStatistic(List<FamilyStatistics> familyStatisticsList){
        Map<String,Integer> result = Maps.newHashMap();
        List<Long> templateIds  = familyStatisticsList.stream().map(FamilyStatistics::getTemplateId).distinct().collect(Collectors.toList());
        //获取户型设备列表
        List<DeviceStatisticsBO> listDeviceStatistics = iHouseTemplateDeviceService.getListDeviceStatistics(templateIds);
        if (CollectionUtils.isEmpty(listDeviceStatistics)){
            return deviceDefaultStatistic2();
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
        result.put("deviceTotal",count);
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
            if (totalCategory.contains(categoryCode)){
                if (result.containsKey(categoryCode)){
                    result.put(categoryCode,result.get(categoryCode)+devices.size()*familyCount);
                }else {
                    result.put(categoryCode,devices.size()*familyCount);
                }
            }
        });
    }

    /**
     * 设备相关数量统计返回默认值
     * @return
     */
    private List<KanBanStatistics> deviceDefaultStatistic() {
        List<KanBanStatistics> data = Lists.newArrayList();
        DeviceErrorStatistics errortotal = new DeviceErrorStatistics();
        errortotal.setCode("errorDeviceTotal");
        errortotal.setName("设备故障统计");
        errortotal.setCount(0);
        errortotal.setFamilyCount(0);
        data.add(errortotal);
        totalCategory.forEach(categoryCode->{
            DeviceStatistics deviceStatistics = new DeviceStatistics();
            deviceStatistics.setCode(categoryCode);
            deviceStatistics.setName(CategoryTypeEnum.getInstByType(categoryCode).getName());
            deviceStatistics.setCount(0);
            deviceStatistics.setOfflineCount(0);
            deviceStatistics.setOnlineCount(0);
            deviceStatistics.setErrorCount(0);
            data.add(deviceStatistics);
        });
        return data;
    }

    private Map<String, Integer> deviceDefaultStatistic2() {
        Map<String,Integer> data = Maps.newHashMapWithExpectedSize(1);
        data.put("device_total",0);
        data.put(CategoryTypeEnum.TEMPERATURE_PANEL.getType(),0);
        data.put(CategoryTypeEnum.MULTI_PARAM.getType(),0);
        data.put(CategoryTypeEnum.AIRCONDITIONER.getType(),0);
        data.put(CategoryTypeEnum.FRESH_AIR.getType(),0);
        data.put(CategoryTypeEnum.HOST.getType(),0);
        return data;
    }


}
