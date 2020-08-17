package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.model.po.device.FamilySceneActionPO;
import com.landleaf.homeauto.model.vo.AttributionVO;

import java.util.List;

/**
 * <p>
 * 场景关联设备动作表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilySceneActionService extends IService<FamilySceneActionPO> {

    /**
     * 通过设备序列号获取设备的属性
     *
     * @param deviceSn 设备序列号
     * @return 属性集合
     */
    List<AttributionVO> getDeviceActionAttributionByDeviceSn(String deviceSn);

}
