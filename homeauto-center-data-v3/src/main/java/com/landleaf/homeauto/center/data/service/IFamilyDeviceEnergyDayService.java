package com.landleaf.homeauto.center.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.data.domain.FamilyDeviceEnergyDay;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 设备功率表 服务类
 * </p>
 *
 */
public interface IFamilyDeviceEnergyDayService extends IService<FamilyDeviceEnergyDay> {

    /**
     * 获取前天基础值，实际上
     * 默认-1昨天，-2前天
     * @return
     */
    Map<Long, List<FamilyDeviceEnergyDay>> getGlcEnergyYesterday(int offset);

    Map<Long,List<FamilyDeviceEnergyDay>> getGlvEnergyYesterday(int offset);

    double getLastValue(Long familyId,String statusCode,String uploadTime);

}
