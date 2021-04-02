package com.landleaf.homeauto.center.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.oauth.mapper.CustomerThirdSourceMapper;
import com.landleaf.homeauto.center.oauth.service.ICustomerThirdSourceService;
import com.landleaf.homeauto.common.domain.po.oauth.CustomerThirdSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author wyl
 */
@Slf4j
@Service
public class CustomerThirdSourceServiceImpl extends ServiceImpl<CustomerThirdSourceMapper, CustomerThirdSource> implements ICustomerThirdSourceService {

    @Override
    public CustomerThirdSource getRecord(String openid, String source) {
        QueryWrapper<CustomerThirdSource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id",openid);
        queryWrapper.apply("  source ={0} order by update_time desc limit {1} ",source,1);
        return getOne(queryWrapper);
    }

    @Override
    public void saveOrUpdateRecord(String userId, String openId, String source) {
        CustomerThirdSource data = new CustomerThirdSource();
        data.setOpenId(openId);
        data.setSource(source);
        data.setUserId(userId);
        QueryWrapper<CustomerThirdSource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("open_id",openId);
        queryWrapper.eq("source",source);
        int count = count(queryWrapper);
        if(count<=0){
            save(data);
        }else {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("user_id",userId);
            updateWrapper.eq("open_id",openId);
            updateWrapper.eq("source",source);
            update(updateWrapper);
        }
    }

    @Override
    public void removeByUserId(String userId) {
        QueryWrapper<CustomerThirdSource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        remove(queryWrapper);
    }
}
