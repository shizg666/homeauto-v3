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


    /**
     * 生成绑定手机号与openId对应关系接口访问凭证
     * @param openId
     * @param access_token
     * @return
     */
    String updateBindCodeAndToken(String openId, String access_token);

    /**
     *  根据授权码获取微信账户
     * @param code
     * @return com.landleaf.homeauto.common.domain.po.oauth.HomeAutoWechatRecord
     * @author wenyilu
     * @date 2021/3/5 16:42
     */
    HomeAutoWechatRecord getRecordByAuthroizeCode(String code);

    /**
     *  新增或修改微信登錄記錄
     * @param openid
     * @param sessionKey
     * @return com.landleaf.homeauto.common.domain.po.oauth.HomeAutoWechatRecord
     * @author wenyilu
     * @date 2021/3/8 9:24
     */
    HomeAutoWechatRecord saveOrUpdateRecord(String openid, String sessionKey);
}
