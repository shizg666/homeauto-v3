package com.landleaf.homeauto.center.oauth.service;

/**
 * @author pilo
 */
public interface ISysCacheService {

    void deleteCache(String id,String type);


    void deleteCacheBitch(String... type);


}
