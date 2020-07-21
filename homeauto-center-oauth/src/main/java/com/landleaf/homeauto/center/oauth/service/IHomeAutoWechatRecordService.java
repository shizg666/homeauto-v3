package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerWechatLoginResDTO;
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


    /**
     * 生成绑定手机号与openId对应关系接口访问凭证
     * @param openId
     * @param access_token
     * @return
     */
    String updateBindCodeAndToken(String openId, String access_token);
}
