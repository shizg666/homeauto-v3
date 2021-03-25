package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyRepairRecordEnclosure;

import java.util.List;

/**
 * <p>
 * 家庭维修记录附件 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
public interface IFamilyRepairRecordEnclosureService extends IService<FamilyRepairRecordEnclosure> {

    List<FamilyRepairRecordEnclosure> getByRecordId(String repairId);
}
