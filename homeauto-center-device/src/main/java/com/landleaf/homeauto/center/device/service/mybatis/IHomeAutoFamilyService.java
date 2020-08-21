package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.vo.app.FamilyVO;
import com.landleaf.homeauto.center.device.model.vo.app.WeatherVO;

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

    /**
     * 通过家庭ID获取城市天气码
     *
     * @param familyId 家庭ID
     * @return 城市天气码
     */
    WeatherVO getWeatherByFamilyId(String familyId);

    /**
     * 通过终端的mac地址获取家庭信息
     *
     * @param mac      mac地址
     * @param terminal 终端类型
     * @return
     */
    FamilyInfoBO getFamilyInfoByTerminalMac(String mac, Integer terminal);

}
