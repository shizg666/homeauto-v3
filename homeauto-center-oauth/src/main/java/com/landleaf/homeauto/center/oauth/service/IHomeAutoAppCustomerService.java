package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.*;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.oauth.CheckResultVO;
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

    List<HomeAutoAppCustomer> queryAllCustomers();

    BasePageVO<HomeAutoCustomerDTO> pageListCustomer(CustomerPageReqDTO requestBody);

    void updateCustomer(CustomerUpdateReqDTO requestBody);

    void addCustomer(CustomerAddReqDTO requestBody);

    void bindProjectNotice(String userId, String projectId);

    void unbindProjectNotice(String userId);

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

    CheckResultVO destroyCustomer(String userId);

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
     * @param openid
     * @return
     */
    HomeAutoAppCustomer getCustomerByOpenId(String openid);

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
     *
     * @param openId
     * @param phone
     */
    HomeAutoAppCustomer bindOpenId(String openId, String phone,String appType);

    /**
     * 根据手机号查找客户
     * @param mobile
     * @return
     */
    HomeAutoAppCustomer getCustomerByMobile(String mobile,String appType);
}
