package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.maintenance.FamilyMaintenanceRecord;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 家庭维修记录 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2021-05-24
 */
public interface FamilyMaintenanceRecordMapper extends BaseMapper<FamilyMaintenanceRecord> {

    /**
     * 维保统计 --看板
     * @param familyIds
     * @param startTime
     * @param endTime
     * @return
     */
    int maintenanceStatistic(@Param("familyIds") List<Long> familyIds, @Param("startTime")LocalDateTime startTime, @Param("endTime")LocalDateTime endTime);
}
