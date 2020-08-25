package com.landleaf.homeauto.center.device.asyn;

import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDetailDO;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class FutureService implements IFutureService {

    public static final Logger LOGGER = LoggerFactory.getLogger(FutureService.class);
    @Autowired
    private RedisUtils redisUtil;

    @Value("${homeauto.apk.update.single:true}")
    private boolean single;

    @Override
    public Future notifyApkUpdate(String apkUrl, List<HomeAutoScreenApkUpdateDetailDO> details) {
        Future<Boolean> future = null;
        try {
            for (HomeAutoScreenApkUpdateDetailDO detail : details) {
                try {
                    if (single) {
                        signlePush(apkUrl, detail);
                        continue;
                    }
                    // TODO 调用洪滨接口通知家庭需要更新apk
                    Thread.sleep(5000);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            future = (Future<Boolean>) new AsyncResult<Boolean>(true);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return future;
    }

    /**
     * 单条推送（一次只推送一条）
     *
     * @param apkUrl
     * @param detail
     */
    private void signlePush(String apkUrl, HomeAutoScreenApkUpdateDetailDO detail) throws InterruptedException {
        /**
         * 若加了同步锁，那么需要在通知响应里将锁释放
         * 若响应结果为失败，那么直接更新推送结果为失败
         */
        String lockKey = RedisCacheConst.UPDATE_APK_EXPIRE_LOCK;
        long startTime = System.currentTimeMillis();
        while (true) {
            boolean getLock = false;
            LOGGER.info("升级开始...家庭：{},url:{}", detail.getFamilyId(), apkUrl);
            getLock = redisUtil.getLock(lockKey, 60 * 60L);
            if (getLock) {
                // TODO 调用洪滨接口通知家庭需要更新apk
                break;
            }
            Thread.sleep(5000);
            long endTime = System.currentTimeMillis();
            LOGGER.info("升级等待中...已等待时间:{}秒，家庭：{},url:{}", (endTime - startTime) / 1000, detail.getFamilyId(), apkUrl);
        }
    }
}
