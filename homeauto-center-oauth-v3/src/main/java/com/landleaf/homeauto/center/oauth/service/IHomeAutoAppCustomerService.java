package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.*;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.oauth.CustomerSelectVO;

import java.util.List;

/**
 * <p>
 * 客户列表（大屏/APP） 服务类
 * </p>
 *
 * @author wyl
 * @since 2019-08-12
 */
public interface IHomeAutoAppCustomerService extends IService<HomeAutoAppCustomer> {

    BasePageVO<HomeAutoCustomerDTO> pageListCustomer(CustomerPageReqDTO requestBody);

    void updateCustomer(CustomerUpdateReqDTO requestBody);

    void addCustomer(CustomerAddReqDTO requestBody);

    void bindFamilyNotice(String userId);

    void unbindFamilyNotice(String userId);

    /**
     * app客户注册
     *
     * @param requestBody
     * @return
     */
    CustomerRegisterResDTO register(CustomerRegisterDTO requestBody,String belongApp);

    /**
     * 忘记密码:通过短信验证码重置密码
     *
     * @param requestBody
     * @return
     */
    String forgetPassword(CustomerForgetPwdDto requestBody,String appType);

    void updateLoginTime(String userId);

    void modifyNickname(String nickname, String userId);

    void modifyHeaderImageUrl(String userId, String avatar);

    void modifyPassword(CustomerPwdModifyDTO requestBody, String userId);

    List<CustomerSelectVO> queryCustomerListByQuery(String query, String appType);

    List<HomeAutoCustomerDTO> getListByIds(List<String> userIds);

    List<SelectedVO> getCustomerListByName(String name, String appType);

    void destroyCustomer(String userId);

    /**
     * APP登录成功后处理
     *
     * @param userId
     * @param access_token
     */
    CustomerRegisterResDTO buildAppLoginSuccessData(String userId, String access_token);

    /**
     * 根据openId查询用户
     *
     * @param openid  三方下发openId
     * @param source  來源
     * @return
     */
    HomeAutoAppCustomer getCustomerByOpenId(String openid, String source);

    /**
     * 微信登录成功后处理
     *
     * @param userId       用户ID
     * @param access_token token
     * @param username     openId
     * @return
     */
    CustomerWechatLoginResDTO buildWechatLoginSuccessData(String userId, String access_token, String username);

    /**
     * 客户绑定openId
     *  @param openId
     * @param phone
     * @param name
     * @param avatar
     */
    HomeAutoAppCustomer bindOpenId(String openId, String phone, String appType, String source, String name, String avatar);

    /**
     * 根据手机号查找客户
     * @param mobile
     * @return
     */
    HomeAutoAppCustomer getCustomerByMobile(String mobile,String appType);

    /**
     * 修改手机号
     * @param mobile  修改后手机号
     * @param code    验证码
     * @param userId  用户Id
     */
    void modifyMobile(String mobile, String code, String userId);

    /**
     * 校验手机号是否为平台手机号
     * @param mobile
     * @param appType
     */
    void checkMobileIsPlat(String mobile, String appType);

    /**
     * @param: requestBody
     * @description: 住房管理-添加用户，若已存在，修改返回
     * @return: com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerInfoDTO
     * @author: wyl
     * @date: 2021/6/2
     */
    CustomerInfoDTO bindFamilySaveOrUpdateCustomer(CustomerBindFamilySaveReqDTO requestBody);
}
