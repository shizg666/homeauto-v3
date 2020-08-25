package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDetailDO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ApkPushingResDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ApkUpdateDetailPageDTO;
import com.landleaf.homeauto.center.device.model.dto.screenapk.ApkUpdateDetailResDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;

import java.util.List;

/**
 * <p>
 * 大屏apk更新记录详情 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-25
 */
public interface IHomeAutoScreenApkUpdateDetailService extends IService<HomeAutoScreenApkUpdateDetailDO> {

    /**
     * 将历史推送记录中未成功的推送消息置为失败
     * @param familyIds
     */
    void updateHistoryUnSuccessRecordsToFail(List<String> familyIds);

    BasePageVO<ApkUpdateDetailResDTO> pageListApkUpdateDetail(ApkUpdateDetailPageDTO requestBody);

    void updateResponseSuccess(String id);

    void updateResponseFail(String id,String errorMsg);

    /**
     * 正在推送记录
     * @param apkId 应用 Id
     * @return
     */
    ApkPushingResDTO pushingDetails(String apkId);
}
