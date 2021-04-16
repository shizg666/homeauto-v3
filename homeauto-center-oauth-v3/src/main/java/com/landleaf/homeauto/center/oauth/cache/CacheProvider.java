package com.landleaf.homeauto.center.oauth.cache;

import org.springframework.beans.factory.InitializingBean;

public interface CacheProvider extends InitializingBean {

    public boolean checkType(String type);

    public void remove(String id);
}
