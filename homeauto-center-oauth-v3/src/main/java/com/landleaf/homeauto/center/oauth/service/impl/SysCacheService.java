package com.landleaf.homeauto.center.oauth.service.impl;

import com.landleaf.homeauto.center.oauth.cache.CacheProvider;
import com.landleaf.homeauto.center.oauth.service.ISysCacheService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.landleaf.homeauto.common.constant.RedisCacheConst.*;

/**
 * @ClassName SysCacheService
 * @Description: 系统缓存相关操作
 * @Author wyl
 * @Date 2021/4/15
 * @Version V1.0
 **/
@Service
public class SysCacheService implements ISysCacheService {
    @Resource
    private List<CacheProvider> cacheProviderList;

    @Override
    public void deleteCacheBitch(String... type) {
        for (String s : type) {
            deleteCache(null, s);
        }
    }

    /**
     * 根据主键及类型删除缓存
     *
     * @param type
     * @param id
     */
    @Override
    public void deleteCache(String id, String type) {
        for (CacheProvider cacheProvider : cacheProviderList) {
            if (cacheProvider.checkType(type)) {
                cacheProvider.remove(id);
            }
        }
    }


}
