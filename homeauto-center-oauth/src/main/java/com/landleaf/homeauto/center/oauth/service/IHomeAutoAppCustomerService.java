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

    CustomerRegisterResDTO register(CustomerRegisterDTO requestBody);

    String forgetPassword(CustomerForgetPwdDto requestBody);

    void updateLoginTime(String userId);

    void modifyNickname(String nickname);

    void modifyHeaderImageUrl(CustomerUpdateAvatarReqDTO requestBody);

    void modifyPassword(CustomerPwdModifyDTO requestBody);

    List<CustomerSelectVO> queryCustomerListByQuery(String query);

    List<HomeAutoCustomerDTO> getListByIds(List<String> userIds);

    List<SelectedVO> getCustomerListByName(String name);

    HomeAutoAppCustomer thirdSpeakerUserLogin(String username, String password);

    CheckResultVO destroyCustomer(String userId);

    /**
     * APP登录成功后处理
     * @param userId
     * @param access_token
     */
    CustomerRegisterResDTO buildAppLoginSuccessData(String userId, String access_token);

    /**
     * 根据openId查询用户
     * @param openid
     * @return
     */
    HomeAutoAppCustomer getCustomerByOpenId(String openid);

    /**
     * 微信登录成功后处理
     * @param userId        用户ID
     * @param access_token  token
     * @param username   openId
     * @return
     */
    CustomerWechatLoginResDTO buildWechatLoginSuccessData(String userId, String access_token, String username);

    /**
     * 客户绑定openId
     * @param openId
     * @param phone
     */
    HomeAutoAppCustomer bindOpenId(String openId, String phone);
}
