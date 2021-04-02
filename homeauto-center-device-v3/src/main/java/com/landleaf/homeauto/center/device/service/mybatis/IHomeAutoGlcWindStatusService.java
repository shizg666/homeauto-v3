package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoGlcWindStatusDO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-20
 */
public interface IHomeAutoGlcWindStatusService extends IService<HomeAutoGlcWindStatusDO> {

    void saveRecord(String familyId, String deviceCode, String productCode, String code, String value);
}
