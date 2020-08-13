package com.landleaf.homeauto.center.device.init;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Maps;
import com.landleaf.homeauto.center.device.service.mybatis.IAreaService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.po.address.HomeAutoArea;
import com.landleaf.homeauto.common.domain.vo.area.AreaInfo;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 启动加载行政区缓存
 */
@Component
@Slf4j
public class AreaDataCacheInit implements CommandLineRunner {


    @Autowired
    private RedisUtils   redisUtil;
    @Autowired
    private IAreaService iAreaService;


    @Override
    public void run(String... args) throws Exception {
        if (redisUtil.hasKey(RedisCacheConst.AREA_INFO.concat("CN"))){
            return;
        }
        List<HomeAutoArea> list = iAreaService.list(new QueryWrapper<>());
        if (list == null){
            return;
        }
        Map<String,String> map = Maps.newHashMapWithExpectedSize(list.size());
        list.forEach(o->{
            String path = iAreaService.getAreaPath(o.getCode());
            String pathName = iAreaService.getAreaPathName(o.getCode());
            AreaInfo areaVo = new AreaInfo(o.getCode(),o.getName(),o.getType(), path, pathName);
            map.put(RedisCacheConst.AREA_INFO.concat(o.getCode()),JsonUtil.beanToJson(areaVo));
        });
        redisUtil.pipleSet((RedisConnection connection)-> {
                map.forEach((k,v)->{
                    connection.set(k.getBytes(),v.getBytes());
                });
                return null;
        });
    }



}




