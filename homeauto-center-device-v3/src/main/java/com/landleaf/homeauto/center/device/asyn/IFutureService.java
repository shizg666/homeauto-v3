package com.landleaf.homeauto.center.device.asyn;

import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeDetail;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.Future;

public interface IFutureService {

    @Async
    Future<String> getAppControlCache(String messageId,Long timeout);
    @Async
    Future<Boolean> notifyUpgrade(String url, List<ProjectScreenUpgradeDetail> details);
}
