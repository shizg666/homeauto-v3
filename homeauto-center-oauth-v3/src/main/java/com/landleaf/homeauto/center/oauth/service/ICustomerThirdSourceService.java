package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.po.oauth.CustomerThirdSource;

import java.util.List;

/**
 * @author wyl
 * @since 2019-08-12
 */
public interface ICustomerThirdSourceService extends IService<CustomerThirdSource> {

    /**
     *  查找三方跟用户绑定记录
     * @param openid
     * @param source
     * @return com.landleaf.homeauto.common.domain.po.oauth.CustomerThirdSource
     * @author wenyilu
     * @date 2021/3/8 9:30
     */
    CustomerThirdSource getRecord(String openid, String source);
    /**
     *  查找三方跟用户绑定记录
     * @param userId
     * @param source
     * @return com.landleaf.homeauto.common.domain.po.oauth.CustomerThirdSource
     * @author wenyilu
     * @date 2021/3/8 9:30
     */
    List<CustomerThirdSource> getRecordByUserId(String userId, String source);

    /**
     *  新增或修改三方绑定客户记录
     * @param userId  客户id
     * @param openId  三方id
     * @param source  来源
     * @return void
     * @author wenyilu
     * @date 2021/3/8 10:14
     */
    void saveOrUpdateRecord(String userId, String openId, String source);

    /**
     *  删除了账号，这里删除记录重新登录
     * @param userId
     * @return void
     * @author wenyilu
     * @date 2021/3/8 10:38
     */
    void removeByUserId(String userId);
}
