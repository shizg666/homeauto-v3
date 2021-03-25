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
     * 更新家庭状态
     */
    void updateByFamilyId(String familyId);
}
