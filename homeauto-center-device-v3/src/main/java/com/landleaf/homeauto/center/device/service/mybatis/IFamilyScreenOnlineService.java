package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.online.FamilyScreenOnline;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-08
 */
public interface IFamilyScreenOnlineService extends IService<FamilyScreenOnline> {

    void updateStatus(List<FamilyScreenOnline> screenOnlineList);
}
