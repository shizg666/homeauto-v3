package com.landleaf.homeauto.center.device.service.mybatis;

import com.landleaf.homeauto.center.device.model.dto.jhappletes.BindFamilyDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JzappletesUserDTO;

/**
 * <p>
 * 嘉宏小程序业务
 * </p>
 *
 * @since 2020-08-20
 */
public interface IJHAppletsrService {


    /**
     * 新增用户
     * @param request
     * @return
     */
    JzappletesUserDTO addUser(JzappletesUserDTO request);

    /**
     * 绑定家庭
     * @param request
     * @return
     */
    Long bindFamily(BindFamilyDTO request);
}
