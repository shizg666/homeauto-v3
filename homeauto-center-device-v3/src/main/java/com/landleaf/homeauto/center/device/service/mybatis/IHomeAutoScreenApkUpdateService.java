package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ScreenApkUpdateSaveDTO;

/**
 * <p>
 * 大屏apk更新记录 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
public interface IHomeAutoScreenApkUpdateService extends IService<HomeAutoScreenApkUpdateDO> {


    /**
     * 批量添加更新记录返回记录id
     *
     * @param requestBody
     * @return
     */
    HomeAutoScreenApkUpdateDO saveApkUpdate(ScreenApkUpdateSaveDTO requestBody);

    /**
     * 重新推送
     *
     * @param detailId 推送记录详情Id
     * @return
     */
    void retrySave(String detailId);


}
