package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyConfigVO;

import java.util.List;

/**
 * <p>
 * 家庭终端表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyTerminalService extends IService<FamilyTerminalDO> {

    /**
     * 获取家庭中的主网关设备
     *
     * @param familyId 家庭ID
     * @return 网关/大屏
     */
    FamilyTerminalDO getMasterTerminal(String familyId);

}
