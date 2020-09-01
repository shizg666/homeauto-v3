package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyAuthorization;

import java.time.LocalDateTime;

/**
 * <p>
 * 家庭授权记录表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-01
 */
public interface IFamilyAuthorizationService extends IService<FamilyAuthorization> {

    /**
     * 家庭授权
     * @param familyId
     */
    void authorization(String familyId);

    /**
     * 定时任务扫描超时的家庭审核，将超时的审核状态从授权中置成已审核
     * @param now
     */
    void timingScan(String now);
}
