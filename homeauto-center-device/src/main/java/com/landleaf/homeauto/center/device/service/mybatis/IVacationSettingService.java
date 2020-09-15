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
     * 判断某天的类别 工作日-0  节假日（包括周末（法定节假日）-1
     * @param day
     * @return
     */
    Integer getSomeDayType(String day);
}
