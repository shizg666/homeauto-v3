package com.landleaf.homeauto.center.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.cache.CustomerCacheProvider;
import com.landleaf.homeauto.center.oauth.mapper.HomeAutoAppCustomerMapper;
import com.landleaf.homeauto.center.oauth.remote.JgRemote;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.common.constance.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.context.TokenContext;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.*;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.oauth.CheckResultVO;
import com.landleaf.homeauto.common.domain.vo.oauth.CustomerSelectVO;
import com.landleaf.homeauto.common.enums.DelFlagEnum;
import com.landleaf.homeauto.common.enums.jg.JgSmsTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.exception.JgException;
import com.landleaf.homeauto.common.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constance.ErrorCodeEnumConst.*;

/**
 * <p>
 * 客户列表（大屏/APP） 服务实现类
 * </p>
 *
 * @author wyl
 */
@Slf4j
@Service
public class HomeAutoAppCustomerServiceImpl extends ServiceImpl<HomeAutoAppCustomerMapper, HomeAutoAppCustomer> implements IHomeAutoAppCustomerService {
    @Autowired(required = false)
    private JgRemote jgRemote;
    @Autowired
    private CustomerCacheProvider customerCacheProvider;

    @Override
    public List<HomeAutoAppCustomer> queryAllCustomers() {
        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", DelFlagEnum.UNDELETE.getType());
        return list(queryWrapper);
    }

    @Override
    public BasePageVO<HomeAutoCustomerDTO> pageListCustomer(CustomerPageReqDTO requestBody) {
        BasePageVO<HomeAutoCustomerDTO> result = new BasePageVO<HomeAutoCustomerDTO>();
        List<HomeAutoCustomerDTO> data = Lists.newArrayList();
        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        LambdaQueryWrapper<HomeAutoAppCustomer> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(requestBody.getBindFlag())) {
            queryWrapper.eq(HomeAutoAppCustomer::getBindFlag, requestBody.getBindFlag());
        }
        if (!StringUtils.isEmpty(requestBody.getName())) {
            queryWrapper.and(wapper -> wapper.like(HomeAutoAppCustomer::getName, requestBody.getName()));
        }
        if (!StringUtils.isEmpty(requestBody.getMobile())) {
            queryWrapper.and(wapper -> wapper.like(HomeAutoAppCustomer::getMobile, requestBody.getMobile()));
        }
        queryWrapper.orderByDesc(HomeAutoAppCustomer::getUpdateTime);
        Page<HomeAutoAppCustomer> page = new Page<>();
        BeanUtils.copyProperties(requestBody, page);
        List<HomeAutoAppCustomer> smarthomeCustomers = list(queryWrapper);
        PageInfo pageInfo = new PageInfo(smarthomeCustomers);
        if (!CollectionUtils.isEmpty(smarthomeCustomers)) {
            data.addAll(smarthomeCustomers.stream().map(i -> {
                HomeAutoCustomerDTO tmp = new HomeAutoCustomerDTO();
                BeanUtils.copyProperties(i, tmp);
                return tmp;
            }).collect(Collectors.toList()));
        }
        pageInfo.setList(data);
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }

    @Override
    public void updateCustomer(CustomerUpdateReqDTO requestBody) {
        if (!saveOrUpdateValidParams(requestBody, true)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        HomeAutoAppCustomer updateCustomer = new HomeAutoAppCustomer();
        BeanUtils.copyProperties(requestBody, updateCustomer);
        HomeAutoAppCustomer existCustomer = getById(updateCustomer.getId());
        if (existCustomer == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        String password = updateCustomer.getPassword();
        if (!StringUtils.isEmpty(password)) {
            updateCustomer.setPassword(PasswordUtil.md5Hex(password));
        }
        updateById(updateCustomer);
    }

    /**
     * web端新增客户账号时前端未加密（后端加密）
     *
     * @param requestBody
     */
    @Override
    public void addCustomer(CustomerAddReqDTO requestBody) {
        CustomerUpdateReqDTO params = new CustomerUpdateReqDTO();
        BeanUtils.copyProperties(requestBody, params);
        if (!saveOrUpdateValidParams(params, false)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        HomeAutoAppCustomer saveCustomer = new HomeAutoAppCustomer();
        BeanUtils.copyProperties(requestBody, saveCustomer);
        String initPassword = requestBody.getPassword();
        String md5Password = PasswordUtil.md5Hex(initPassword);
        saveCustomer.setPassword(md5Password);
        save(saveCustomer);
    }

    @Override
    public void bindProjectNotice(String userId, String projectId) {
        //更新统计信息
        HomeAutoAppCustomer customer = getById(userId);
        Integer bindCount = customer.getBindCount();
        Date bindTime = customer.getBindTime();
        Integer bindFlag = customer.getBindFlag();
        HomeAutoAppCustomer updateCustomer = new HomeAutoAppCustomer();
        BeanUtils.copyProperties(customer, updateCustomer);
        updateCustomer.setBindCount(bindCount == null ? 1 : bindCount + 1);
        updateCustomer.setBindFlag(1);
        updateCustomer.setBindTime(bindTime == null ? new Date() : bindTime);
        updateById(updateCustomer);
    }

    @Override
    public void unbindProjectNotice(String userId) {
        //更新统计信息
        HomeAutoAppCustomer customer = getById(userId);
        Integer bindCount = customer.getBindCount();
        Date bindTime = customer.getBindTime();
        Integer bindFlag = customer.getBindFlag();
        if (bindCount != null) {
            if (bindCount.intValue() > 1) {
                bindCount = bindCount - 1;
                bindFlag = 1;
            } else {
                bindCount = 0;
                bindTime = null;
                bindFlag = 0;
            }
        }
        customer.setBindCount(bindCount);
        customer.setBindTime(bindTime);
        customer.setBindFlag(bindFlag);
        HomeAutoAppCustomer updateCustomer = new HomeAutoAppCustomer();
        BeanUtils.copyProperties(customer, updateCustomer);
        updateById(updateCustomer);
    }

    @Override
    public CustomerRegisterResDTO register(CustomerRegisterDTO requestBody) {
        CustomerRegisterResDTO result = new CustomerRegisterResDTO();
        CustomerUpdateReqDTO params = new CustomerUpdateReqDTO();
        BeanUtils.copyProperties(requestBody, params);
        if (!saveOrUpdateValidParams(params, false)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        boolean codeFlag = veryCodeFlag(requestBody.getCode(), requestBody.getMobile(), JgSmsTypeEnum.REGISTER_LOGIN.getMsgType());
        if (!codeFlag) {
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_JG_CODE_VERIFY_ERROR);
        }
        HomeAutoAppCustomer saveData = new HomeAutoAppCustomer();
        BeanUtils.copyProperties(requestBody, saveData);
        String name = saveData.getName();
        if (StringUtils.isEmpty(name)) {
            saveData.setName(saveData.getMobile());
        }
        //处理密码
        String password = saveData.getPassword();
        if (!StringUtils.isEmpty(password)) {
            saveData.setPassword(PasswordUtil.md5Hex(password));
        }
        save(saveData);
        BeanUtils.copyProperties(saveData, result);
        // 注册成功重新登录

        return result;
    }


    @Override
    public void updateLoginTime(String userId) {
        customerCacheProvider.remove(userId);
        HomeAutoAppCustomer customer = new HomeAutoAppCustomer();
        customer.setId(userId);
        customer.setLoginTime(new Date());
        updateById(customer);
        customerCacheProvider.getSmarthomeCustomer(userId);
    }

    private HomeAutoAppCustomer getCustomerByMobile(String mobile) {

        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        return getOne(queryWrapper);
    }

    @Override
    public String forgetPassword(CustomerForgetPwdDto requestBody) {
        HomeAutoAppCustomer smarthomeCustomer = getCustomerByMobile(requestBody.getMobile());
        if (smarthomeCustomer == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        boolean codeFlag = veryCodeFlag(requestBody.getCode(), requestBody.getMobile(), JgSmsTypeEnum.REGISTER_LOGIN.getMsgType());
        if (!codeFlag) {
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_JG_CODE_VERIFY_ERROR);
        }

        smarthomeCustomer.setPassword(PasswordUtil.md5Hex(requestBody.getPassword()));
        updateById(smarthomeCustomer);
        return smarthomeCustomer.getId();

    }

    @Override
    public void modifyNickname(String nickname) {
        String userId = TokenContext.getToken().getUserId();
        HomeAutoAppCustomer currentUser = getById(userId);
        currentUser.setName(nickname);
        updateById(currentUser);
    }

    @Override
    public void modifyHeaderImageUrl(CustomerUpdateAvatarReqDTO requestBody) {
        String userId = TokenContext.getToken().getUserId();
        HomeAutoAppCustomer customer = new HomeAutoAppCustomer();
        customer.setId(userId);
        customer.setAvatar(requestBody.getAvatar());
        updateById(customer);
    }

    @Override
    public void modifyPassword(CustomerPwdModifyDTO requestBody) {
        String oldPwd = requestBody.getOldPwd();
        String newPwd = requestBody.getNewPwd();
        String confirmPwd = requestBody.getConfirmPwd();
        if (org.apache.commons.lang.StringUtils.isEmpty(oldPwd) ||
                org.apache.commons.lang.StringUtils.isEmpty(newPwd) ||
                org.apache.commons.lang.StringUtils.isEmpty(confirmPwd)
        ) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        HomeAutoAppCustomer currentUser = getById(TokenContext.getToken().getUserId());
        if (!org.apache.commons.lang.StringUtils.equalsIgnoreCase(PasswordUtil.md5Hex(oldPwd), currentUser.getPassword())) {
            throw new BusinessException(PASSWORD_OLD_INPUT_ERROE);
        }

        if (!org.apache.commons.lang.StringUtils.equals(newPwd, confirmPwd)) {
            throw new BusinessException(CUSTOMER_PASSWORD_TWICE_INPUT_DIFFER);
        }

        HomeAutoAppCustomer modifyCustomer = new HomeAutoAppCustomer();
        modifyCustomer.setId(currentUser.getId());
        modifyCustomer.setPassword(PasswordUtil.md5Hex(newPwd));
        updateById(modifyCustomer);
    }

    @Override
    public List<CustomerSelectVO> queryCustomerListByQuery(String query) {
        List<CustomerSelectVO> result = Lists.newArrayList();
        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(query)) {
            queryWrapper.and(wrapper -> wrapper.like("name", query).or().like("mobile", query));
        }
        List<HomeAutoAppCustomer> queryResult = list(queryWrapper);
        if (!CollectionUtils.isEmpty(queryResult)) {
            result.addAll(queryResult.stream().map(i -> {
                CustomerSelectVO vo = new CustomerSelectVO();
                vo.setMobile(i.getMobile());
                vo.setUserId(i.getId());
                vo.setName(i.getName());
                return vo;
            }).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public List<HomeAutoCustomerDTO> getListByIds(List<String> userIds) {
        List<HomeAutoCustomerDTO> result = Lists.newArrayList();
        Collection<HomeAutoAppCustomer> smarthomeCustomers = listByIds(userIds);
        if (!CollectionUtils.isEmpty(smarthomeCustomers)) {
            result.addAll(smarthomeCustomers.stream().map(i -> {
                HomeAutoCustomerDTO tmp = new HomeAutoCustomerDTO();
                BeanUtils.copyProperties(i, tmp);
                tmp.setPassword("");
                return tmp;
            }).collect(Collectors.toList()));
        }
        return result;
    }


    @Override
    public List<SelectedVO> getCustomerListByName(String name) {
        List<SelectedVO> result = Lists.newArrayList();
        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        List<HomeAutoAppCustomer> queryResult = list(queryWrapper);
        if (!CollectionUtils.isEmpty(queryResult)) {
            result.addAll(queryResult.stream().map(i -> {
                SelectedVO data = new SelectedVO();
                data.setLabel(i.getName());
                data.setValue(i.getId());
                return data;
            }).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public HomeAutoAppCustomer thirdSpeakerUserLogin(String username, String password) {
        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", DelFlagEnum.UNDELETE.getType());
        queryWrapper.eq("mobile", username);
        queryWrapper.eq("password", password);
        List<HomeAutoAppCustomer> list = list(queryWrapper);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 销毁客户账号
     *
     * @param userId 客户账号ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CheckResultVO destroyCustomer(String userId) {
        removeById(userId);
//        Response<CheckResultVO> emResponse = emRemote.logOutUserCHeck(userId);
        Response<CheckResultVO> emResponse = new Response<>();
        log.info("销毁账号工程返回信息:{}", JSON.toJSONString(emResponse));
        CheckResultVO result = emResponse.getResult();

        if (result == null || !result.isCheckFlag()) {
            String errorMsg = CUSTOMER_DESTROY_ERROR.getMsg();
            if (result != null && !StringUtils.isEmpty(result.getMessage())) {
                errorMsg = result.getMessage();
            }
            throw new BusinessException(String.valueOf(CUSTOMER_DESTROY_ERROR.getCode()), errorMsg);
        }
        return result;
    }

    @Override
    public CustomerRegisterResDTO buildAppLoginSuccessData(String userId, String access_token) {
        CustomerRegisterResDTO result = new CustomerRegisterResDTO();
        result.setToken(access_token);
        //更新登录时间
        updateLoginTime(userId);
        return result;
    }


    private boolean saveOrUpdateValidParams(CustomerUpdateReqDTO params, boolean update) {
        if (params == null) {
            return false;
        }
        String id = params.getId();

        if (StringUtils.isEmpty(id) && update) {
            return false;
        }
        if (StringUtils.isEmpty(params.getMobile())) {
            return false;
        }
        //校验手机号是否存在
        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", params.getMobile())
                .eq("del_flag", DelFlagEnum.UNDELETE.getType());
        if (update) {
            List<String> ids = Lists.newArrayList();
            ids.add(params.getId());
            queryWrapper.notIn("id", ids);
        }
        int count = count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(MOBILE_EXIST_ERROE);
        }
        return true;
    }

    /**
     * 验证码校验一致返回true
     *
     * @param veryCode
     * @param mobile
     * @param type
     * @return
     */
    private boolean veryCodeFlag(String veryCode, String mobile, Integer type) {
        JgMsgDTO jgMsgDTO = new JgMsgDTO();
        jgMsgDTO.setCodeType(type);
        jgMsgDTO.setCode(veryCode);
        jgMsgDTO.setMobile(mobile);
        Response response = jgRemote.verifyCode(jgMsgDTO);
        return response.isSuccess();
    }

}
