package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.familydevice.FamilyDevice;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.mapper.FamilyDeviceMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyDeviceService;
import com.landleaf.homeauto.center.device.service.mybatis.IHouseTemplateDeviceService;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import com.landleaf.homeauto.common.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2021-06-04
 */
@Service
public class FamilyDeviceServiceImpl extends ServiceImpl<FamilyDeviceMapper, FamilyDevice> implements IFamilyDeviceService {

    @Autowired
    private IHouseTemplateDeviceService iHouseTemplateDeviceService;
    @Autowired
    private IdService idService;

    @Override
    public void addFamilyDevice(HomeAutoFamilyDO familyDO) {
        FamilyDevice familyDevice = FamilyDevice.builder().familyId(familyDO.getId()).familyCode(familyDO.getCode()).path1(familyDO.getPath1()).path2(familyDO.getPath2()).build();
        //获取户型设备列表
        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.getListDeviceDOByTeamplateId(familyDO.getTemplateId());
        if (CollectionUtils.isEmpty(deviceDOS)){
            return;
        }
        List<FamilyDevice> familyDevices = deviceDOS.stream().map(device ->{
            FamilyDevice familyDevice1 = FamilyDevice.builder().deviceId(device.getId()).deviceSn(device.getSn()).productCode(device.getProductCode()).productId(device.getProductId()).categoryCode(device.getCategoryCode()).path1(familyDO.getPath1()).path2(familyDO.getPath2()).familyId(familyDO.getId()).familyCode(familyDO.getCode()).templateId(familyDO.getTemplateId()).build();
            return familyDevice1;
        }).collect(Collectors.toList());

        List<Long> ids = idService.getListSegmentId(familyDevices.size());
        for (int i = 0; i < familyDevices.size(); i++) {
            familyDevices.get(i).setId(ids.get(i));
        }
        saveBatch(familyDevices);
    }

    @Override
    public void addBatchFamilyDevice(List<HomeAutoFamilyDO> familyDOS) {
        List<Long> templateIds = familyDOS.stream().map(HomeAutoFamilyDO::getTemplateId).distinct().collect(Collectors.toList());
        //获取户型设备列表
        List<TemplateDeviceDO> deviceDOS = iHouseTemplateDeviceService.getListDeviceDOByTeamplateIds(templateIds);
        if (CollectionUtils.isEmpty(deviceDOS)){
            return;
        }
        List<FamilyDevice> familyDevicesData = Lists.newArrayList();
        Map<Long,List<HomeAutoFamilyDO>> teplateMap = familyDOS.stream().collect(Collectors.groupingBy(HomeAutoFamilyDO::getTemplateId));
        Map<Long,List<TemplateDeviceDO>> deviceMap = deviceDOS.stream().collect(Collectors.groupingBy(TemplateDeviceDO::getHouseTemplateId));
        deviceMap.forEach((tempId,devices)->{
            List<HomeAutoFamilyDO> familyDOs = teplateMap.get(tempId);
            if (!CollectionUtils.isEmpty(familyDOs)){
                familyDOs.forEach(familyDO->{
                    List<FamilyDevice> familyDevices = devices.stream().map(device ->{
                        FamilyDevice familyDevice = FamilyDevice.builder().deviceId(device.getId()).deviceSn(device.getSn()).productCode(device.getProductCode()).productId(device.getProductId()).categoryCode(device.getCategoryCode()).path1(familyDO.getPath1()).path2(familyDO.getPath2()).familyId(familyDO.getId()).familyCode(familyDO.getCode()).templateId(familyDO.getTemplateId()).build();
                        return familyDevice;
                    }).collect(Collectors.toList());
                    familyDevicesData.addAll(familyDevices);
                });
            }
        });

        List<Long> ids = idService.getListSegmentId(familyDevicesData.size());
        for (int i = 0; i < familyDevicesData.size(); i++) {
            familyDevicesData.get(i).setId(ids.get(i));
        }
        saveBatch(familyDevicesData);
    }
}
