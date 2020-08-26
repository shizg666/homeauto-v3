package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkDO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.*;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 大屏apk 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
public interface IHomeAutoScreenApkService extends IService<HomeAutoScreenApkDO> {

    void saveApk(ScreenApkSaveDTO requestBody);

    void updateApk(ScreenApkDTO requestBody);

    BasePageVO<ScreenApkResDTO> pageListApks(ScreenApkPageDTO requestBody);

    void deleteApkByIds(List<String> ids);

    ScreenApkConditionDTO getCondition();

    ScreenApkResDTO getInfoById(String id);
}
