package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.VacationSetting;

/**
 * <p>
 * 假期记录表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-15
 */
public interface IVacationSettingService extends IService<VacationSetting> {

    /**
     * 获取当天的类别 0工作日 1节假日
     * @param day 2020-09-15
     * @return
     */
    Integer getSomeDayType(String day);
}
