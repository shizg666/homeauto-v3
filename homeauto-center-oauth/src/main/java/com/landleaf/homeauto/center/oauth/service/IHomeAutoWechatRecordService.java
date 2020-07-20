package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoWechatRecord;

/**
 * <p>
 * 微信账号登录信息记录 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-07-20
 */
public interface IHomeAutoWechatRecordService extends IService<HomeAutoWechatRecord> {

    HomeAutoWechatRecord getRecordByOpenId(String openid);
}
