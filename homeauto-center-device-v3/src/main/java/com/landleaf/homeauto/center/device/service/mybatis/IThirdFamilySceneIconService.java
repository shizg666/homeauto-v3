package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.ThirdFamilySceneIcon;

/**
 * <p>
 * 三方家庭场景icon表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-07-02
 */
public interface IThirdFamilySceneIconService extends IService<ThirdFamilySceneIcon> {

    /**
     * 获取场景图片
     * @param sceneId
     * @return
     */
    String getIconBySceneId(Long sceneId);
}
