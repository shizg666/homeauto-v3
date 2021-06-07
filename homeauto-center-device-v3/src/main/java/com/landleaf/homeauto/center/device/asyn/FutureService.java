package com.landleaf.homeauto.center.device.asyn;

import com.alibaba.druid.util.StringUtils;
import com.landleaf.homeauto.center.device.cache.ConfigCacheProvider;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.screenapk.ProjectScreenUpgradeDetail;
import com.landleaf.homeauto.center.device.service.bridge.IBridgeAppService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import com.landleaf.homeauto.common.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service
public class FutureService implements IFutureService {

    @Autowired
    private RedisUtils redisUtil;
    @Autowired
    private ConfigCacheProvider configCacheProvider;
    @Autowired
    private IBridgeAppService bridgeAppService;


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

    @Override
    public Future<Boolean> notifyUpgrade(String url, List<ProjectScreenUpgradeDetail> details) {
        Future<Boolean> future = null;
        try {
            for (ProjectScreenUpgradeDetail detail : details) {

                ScreenFamilyBO familyInfo = configCacheProvider.getFamilyInfo(detail.getFamilyId());
                AdapterConfigUpdateDTO sendData = new AdapterConfigUpdateDTO();
                sendData.setUpdateType(ContactScreenConfigUpdateTypeEnum.APK_UPDATE.code);
                sendData.setFamilyId(String.valueOf(detail.getFamilyId()));
                sendData.setFamilyCode(familyInfo.getCode());
                sendData.setTerminalMac(familyInfo.getScreenMac());
                sendData.setTime(System.currentTimeMillis());
                try {
                    bridgeAppService.configUpdate(sendData);
                    Thread.sleep(300);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            future = (Future<Boolean>) new AsyncResult<Boolean>(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return future;
    }

}
