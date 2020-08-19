package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.vo.app.FamilyVO;

import java.util.List;

/**
 * <p>
 * 家庭表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IHomeAutoFamilyService extends IService<HomeAutoFamilyDO> {

    /**
     * 通过用户ID获取家庭列表
     *
     * @param userId 用户ID
     * @return 家庭列表
     */
    FamilyVO getFamilyListByUserId(String userId);

}
