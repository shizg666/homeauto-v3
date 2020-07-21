package com.landleaf.homeauto.center.oauth.service.impl;

import com.alibaba.nacos.common.utils.UuidUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.oauth.mapper.HomeAutoWechatRecordMapper;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoWechatRecordService;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoWechatRecord;
import org.springframework.stereotype.Service;

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

    @Override
    public String updateBindCodeAndToken(String openId, String access_token) {
        String authroizeCode = UuidUtils.generateUuid();
        HomeAutoWechatRecord record = getRecordByOpenId(openId);
        record.setAccessToken(access_token);
        record.setCode(authroizeCode);
        updateById(record);
        return authroizeCode;
    }
}
