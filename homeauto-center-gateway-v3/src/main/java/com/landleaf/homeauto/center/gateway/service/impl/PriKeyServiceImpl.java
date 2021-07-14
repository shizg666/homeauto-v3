package com.landleaf.homeauto.center.gateway.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.gateway.model.domain.AppPriKeyConfig;
import com.landleaf.homeauto.center.gateway.model.mapper.AppPriKeyConfigMapper;
import com.landleaf.homeauto.center.gateway.service.PriKeyService;
import com.landleaf.homeauto.common.redis.RedisUtils;

/**
 * 私钥相关逻辑实现
 * 
 * @author hebin
 */
@Service
public class PriKeyServiceImpl implements PriKeyService {
	
	@Resource
	private RedisUtils redisUtils;
	
	@Resource
	private AppPriKeyConfigMapper appPriKeyConfigMapper;
	
	private static final String APPLET_KEY= "applet_key"; 

	@Override
	public String getPriKeyByAppKey(String appKey) {
		Object priKey = redisUtils.hget(APPLET_KEY, appKey);
		if (null != priKey) {
			return (String) priKey;
		}
		AppPriKeyConfig appConfig = appPriKeyConfigMapper.selectOne(new QueryWrapper<AppPriKeyConfig>().lambda().eq(AppPriKeyConfig::getAppKey, appKey));
		if (null != appConfig) {
			redisUtils.hset(APPLET_KEY, appKey, appConfig.getPriKey());
			return appConfig.getPriKey();
		}
		return null;
	}
}
