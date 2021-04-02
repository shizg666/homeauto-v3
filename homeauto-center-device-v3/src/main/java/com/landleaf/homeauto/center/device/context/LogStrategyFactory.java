package com.landleaf.homeauto.center.device.context;

import cn.hutool.core.lang.Assert;
import com.landleaf.homeauto.center.device.service.mybatis.LogService;
import io.swagger.models.auth.In;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName LogStrategyFactory
 * @Description: TODO
 * @Author shizg
 * @Date 2020/8/13
 * @Version V1.0
 **/
public class LogStrategyFactory {

    private static Map<Integer, LogService> logMap = new ConcurrentHashMap<Integer,LogService>();

    public  static LogService getLogServiceByType(Integer type){
        return logMap.get(type);
    }

    public static void register(Integer type,LogService logService){
        Assert.notNull(type,"type can't be null");
        logMap.put(type,logService);
    }

}
