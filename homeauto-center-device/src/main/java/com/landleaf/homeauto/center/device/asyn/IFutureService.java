package com.landleaf.homeauto.center.device.asyn;

import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDetailDO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;


public interface IFutureService {

    @Async
    Future notifyApkUpdate(String apkUrl, List<HomeAutoScreenApkUpdateDetailDO> details);

    @Async
    Future<String> getAppControlCache(String messageId,Long timeout);

}
