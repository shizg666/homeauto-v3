package com.landleaf.homeauto.center.oauth.service.impl;

import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.oauth.mapper.HomeAutoWechatRecordMapper;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoWechatRecordService;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoWechatRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 微信账号登录信息记录 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-07-20
 */
@Service
public class HomeAutoWechatRecordServiceImpl extends ServiceImpl<HomeAutoWechatRecordMapper, HomeAutoWechatRecord> implements IHomeAutoWechatRecordService {

    @Override
    public HomeAutoWechatRecord getRecordByOpenId(String openid) {
        QueryWrapper<HomeAutoWechatRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id", openid);
        return getOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateBindCodeAndToken(String openId, String access_token) {
        String authroizeCode = String.format("%s%s",UuidUtils.generateUuid(),System.currentTimeMillis());
        HomeAutoWechatRecord record = getRecordByOpenId(openId);
        record.setAccessToken(access_token);
        record.setCode(authroizeCode);
        updateById(record);
        return authroizeCode;
    }

    @Override
    public HomeAutoWechatRecord getRecordByAuthroizeCode(String code) {
        QueryWrapper<HomeAutoWechatRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        return getOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HomeAutoWechatRecord saveOrUpdateRecord(String openid, String sessionKey) {
        HomeAutoWechatRecord record = getRecordByOpenId(openid);
        // 处理记录
        if (record == null) {
            // 新插入一条记录
            record = new HomeAutoWechatRecord();
            record.setOpenId(openid);
            record.setSessionKey(sessionKey);
            save(record);
        } else {
            record.setSessionKey(sessionKey);
            updateById(record);
        }
        return  record;
    }
}
