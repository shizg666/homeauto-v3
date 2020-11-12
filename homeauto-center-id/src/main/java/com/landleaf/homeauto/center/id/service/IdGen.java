package com.landleaf.homeauto.center.id.service;


import com.landleaf.homeauto.center.id.common.model.Result;

public interface IdGen {

    Result get(String key);

    boolean init();
}
