package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.model.po.device.FamilyCommonDevicePO;
import com.landleaf.homeauto.model.vo.device.FamilyCommonDeviceVO;

import java.util.List;

/**
 * <p>
 * 家庭常用设备表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyCommonDeviceService extends IService<FamilyCommonDevicePO> {

    /**
     * 通过家庭ID获取常用设备
     *
     * @param familyId 家庭ID
     * @return 常用场景列表
     */
    List<FamilyCommonDeviceVO> getCommonDevicesByFamilyId(String familyId);

}
