package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.LocalCollectExtranetUrlConfig;
import com.landleaf.homeauto.center.device.model.vo.ExtranetUrlConfigVO;
import com.landleaf.homeauto.center.device.model.vo.family.ExtranetUrlConfigDTO;

/**
 * <p>
 * 本地数采外网URL配置 服务类
 * </p>
 *
 * @author lokiy
 * @since 2021-07-28
 */
public interface ILocalCollectExtranetUrlConfigService extends IService<LocalCollectExtranetUrlConfig> {

    /**
     * 本地数采外网配置
     * @param request
     */
    void addConfig(ExtranetUrlConfigDTO request);

    ExtranetUrlConfigVO getLocalCollectConfig(Long realestateId);
}
