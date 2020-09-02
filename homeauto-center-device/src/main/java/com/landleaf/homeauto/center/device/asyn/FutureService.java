package com.landleaf.homeauto.center.device.asyn;

import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.HomeAutoScreenApkUpdateDetailDO;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyTerminalService;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
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

    @Value("${homeauto.apk.update.single:false}")
    private boolean single;
    @Autowired
    private IAppService appService;
    @Autowired
    private IFamilyTerminalService familyTerminalService;
    @Autowired
    private IHomeAutoFamilyService homeAutoFamilyService;

    @Override
    public Future notifyApkUpdate(String apkUrl, List<HomeAutoScreenApkUpdateDetailDO> details) {
        Future<Boolean> future = null;
        try {
            for (HomeAutoScreenApkUpdateDetailDO detail : details) {
                HomeAutoFamilyDO familyDO = homeAutoFamilyService.getById(detail.getFamilyId());
                if (familyDO == null) {
                    continue;
                }
                FamilyTerminalDO masterTerminal = familyTerminalService.getMasterTerminal(detail.getFamilyId());
                if (masterTerminal == null) {
                    continue;
                }
                AdapterConfigUpdateDTO sendData = new AdapterConfigUpdateDTO();
                sendData.setUpdateType(ContactScreenConfigUpdateTypeEnum.APK_UPDATE.code);
                sendData.setFamilyId(detail.getFamilyId());
                sendData.setFamilyCode(familyDO.getCode());
                sendData.setTerminalMac(masterTerminal.getMac());
                sendData.setTerminalType(masterTerminal.getType());
                sendData.setTime(System.currentTimeMillis());
                try {
                    if (single) {
                        signlePush(sendData);
                        continue;
                    }
                    appService.configUpdate(sendData);
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

    @Override
    public Future<String> getAppControlCache(String messageId, Long timeout) {
        long currentTimeMillis = System.currentTimeMillis();
        long expireTimeMillis = currentTimeMillis + timeout;
        Future<String> future;

        String cache = null;

        try {
            String key = RedisCacheConst.ADAPTER_APP_MSG_WAIT_ACK_PREFIX.concat(messageId);
            while (System.currentTimeMillis() < expireTimeMillis) {
                Thread.sleep(300L);
                if (redisUtil.hasKey(key)) {
                    cache = (String) redisUtil.get(key);
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        future = new AsyncResult<>(cache);

        return future;
    }


    /**
     * 单条推送（一次只推送一条）
     */
    private void signlePush(AdapterConfigUpdateDTO sendData) throws InterruptedException {
        /**
         * 若加了同步锁，那么需要在通知响应里将锁释放
         * 若响应结果为失败，那么直接更新推送结果为失败
         */
        String lockKey = RedisCacheConst.UPDATE_APK_EXPIRE_LOCK;
        long startTime = System.currentTimeMillis();
        while (true) {
            boolean getLock = false;
            LOGGER.info("升级开始...家庭：{}", sendData.getFamilyId());
            getLock = redisUtil.getLock(lockKey, 1 * 60L);
            if (getLock) {
                appService.configUpdate(sendData);
                break;
            }
            Thread.sleep(5000);
            long endTime = System.currentTimeMillis();
            LOGGER.info("升级等待中...已等待时间:{}秒，家庭：{}", (endTime - startTime) / 1000, sendData.getFamilyId());
        }
    }
}
