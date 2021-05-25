package com.landleaf.homeauto.center.oauth.service.impl;

import cn.hutool.crypto.digest.BCrypt;
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
import com.landleaf.homeauto.center.oauth.remote.DeviceRemote;
import com.landleaf.homeauto.center.oauth.service.ICustomerThirdSourceService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoWechatRecordService;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.device.family.familyUerRemoveDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.*;
import com.landleaf.homeauto.common.domain.po.oauth.CustomerThirdSource;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.oauth.CustomerSelectVO;
import com.landleaf.homeauto.common.domain.vo.oauth.FamilyVO;
import com.landleaf.homeauto.common.enums.jg.JgSmsTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.AppTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.exception.JgException;
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

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.*;

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
    private DeviceRemote deviceRemote;
    @Autowired
    private CustomerCacheProvider customerCacheProvider;
    @Autowired
    private IHomeAutoWechatRecordService homeAutoWechatRecordService;
    @Autowired
    private ICustomerThirdSourceService customerThirdSourceService;

    @Override
    public BasePageVO<HomeAutoCustomerDTO> pageListCustomer(CustomerPageReqDTO requestBody) {
        BasePageVO<HomeAutoCustomerDTO> result = new BasePageVO<HomeAutoCustomerDTO>();
        List<HomeAutoCustomerDTO> data = Lists.newArrayList();
        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        LambdaQueryWrapper<HomeAutoAppCustomer> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(requestBody.getBelongApp())) {
            queryWrapper.eq(HomeAutoAppCustomer::getBelongApp, requestBody.getBelongApp());
        }
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
                tmp.setBindFlagName(i.getBindFlag() == null ? "否" : i.getBindFlag().intValue() == 1 ? "是" : "否");
                return tmp;
            }).collect(Collectors.toList()));
        }
        pageInfo.setList(data);
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustomer(CustomerUpdateReqDTO requestBody) {
        String belongApp = requestBody.getBelongApp();
        if (!saveOrUpdateValidParams(requestBody, true, belongApp)) {
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
            updateCustomer.setPassword(BCrypt.hashpw(password));
        }
        updateById(updateCustomer);
    }

    /**
     * web端新增客户账号时前端未加密（后端加密）
     *
     * @param requestBody
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCustomer(CustomerAddReqDTO requestBody) {
        String belongApp = requestBody.getBelongApp();
        CustomerUpdateReqDTO params = new CustomerUpdateReqDTO();
        BeanUtils.copyProperties(requestBody, params);
        if (!saveOrUpdateValidParams(params, false, belongApp)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        HomeAutoAppCustomer saveCustomer = new HomeAutoAppCustomer();
        BeanUtils.copyProperties(requestBody, saveCustomer);
        String initPassword = requestBody.getPassword();
        if(StringUtils.isEmpty(initPassword)){
            initPassword="123456";
        }
        saveCustomer.setPassword(BCrypt.hashpw(initPassword));
        saveCustomer.setBelongApp(belongApp);
        save(saveCustomer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bindFamilyNotice(String userId) {
        //更新统计信息
        HomeAutoAppCustomer customer = getById(userId);
        Integer bindCount = customer.getBindCount();
        Date bindTime = customer.getBindTime();
        HomeAutoAppCustomer updateCustomer = new HomeAutoAppCustomer();
        BeanUtils.copyProperties(customer, updateCustomer);
        updateCustomer.setBindCount(bindCount == null ? 1 : bindCount + 1);
        updateCustomer.setBindFlag(1);
        updateCustomer.setBindTime(bindTime == null ? new Date() : bindTime);
        updateById(updateCustomer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unbindFamilyNotice(String userId) {
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerRegisterResDTO register(CustomerRegisterDTO requestBody, String belongApp) {
        if (StringUtils.isEmpty(belongApp)) {
            belongApp = AppTypeEnum.SMART.getCode();
        }
        CustomerRegisterResDTO result = new CustomerRegisterResDTO();
        CustomerUpdateReqDTO params = new CustomerUpdateReqDTO();
        BeanUtils.copyProperties(requestBody, params);
        if (!saveOrUpdateValidParams(params, false, belongApp)) {
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
            saveData.setPassword(BCrypt.hashpw(password));
        }
        saveData.setBelongApp(belongApp);
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
        customerCacheProvider.getCustomer(userId);
    }

    @Override
    public HomeAutoAppCustomer getCustomerByMobile(String mobile, String belongApp) {

        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        queryWrapper.eq("belong_app", belongApp);
        return getOne(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyMobile(String mobile, String code, String userId) {
        HomeAutoAppCustomer exist = getById(userId);
        if (exist == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        if(!StringUtils.isEmpty(code)){
            boolean codeFlag = veryCodeFlag(code, mobile, JgSmsTypeEnum.REGISTER_LOGIN.getMsgType());
            if (!codeFlag) {
                throw new JgException(ErrorCodeEnumConst.ERROR_CODE_JG_CODE_VERIFY_ERROR);
            }
        }
        // 校验手机号
        exist.setMobile(mobile);
        CustomerUpdateReqDTO reqDTO = new CustomerUpdateReqDTO();
        BeanUtils.copyProperties(exist, reqDTO);
        saveOrUpdateValidParams(reqDTO, true, reqDTO.getBelongApp());
        updateById(exist);
    }

    @Override
    public void checkMobileIsPlat(String mobile, String belongApp) {
        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        queryWrapper.eq("belong_app",belongApp);
        int count = count(queryWrapper);
        if(count<=0){
            throw new BusinessException(ErrorCodeEnumConst.USER_NOT_FOUND);
        }

    }

@Transactional(rollbackFor = Exception.class)
    @Override
    public String forgetPassword(CustomerForgetPwdDto requestBody, String belongApp) {
        HomeAutoAppCustomer customer = getCustomerByMobile(requestBody.getMobile(), belongApp);
        if (customer == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        boolean codeFlag = veryCodeFlag(requestBody.getCode(), requestBody.getMobile(), JgSmsTypeEnum.REGISTER_LOGIN.getMsgType());
        if (!codeFlag) {
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_JG_CODE_VERIFY_ERROR);
        }

        customer.setPassword(BCrypt.hashpw(requestBody.getPassword()));
        updateById(customer);
        return customer.getId();

    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyNickname(String nickname, String userId) {
        HomeAutoAppCustomer currentUser = getById(userId);
        currentUser.setName(nickname);
        updateById(currentUser);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyHeaderImageUrl(String userId, String avatar) {
        HomeAutoAppCustomer customer = new HomeAutoAppCustomer();
        customer.setId(userId);
        customer.setAvatar(avatar);
        updateById(customer);
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modifyPassword(CustomerPwdModifyDTO requestBody, String userId) {
        String password = requestBody.getPassword();
        if (
                org.apache.commons.lang.StringUtils.isEmpty(password)
        ) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        HomeAutoAppCustomer currentUser = getById(userId);

        HomeAutoAppCustomer modifyCustomer = new HomeAutoAppCustomer();
        modifyCustomer.setId(currentUser.getId());
        modifyCustomer.setPassword(BCrypt.hashpw(password));
        updateById(modifyCustomer);
    }

    @Override
    public List<CustomerSelectVO> queryCustomerListByQuery(String query, String belongApp) {
        List<CustomerSelectVO> result = Lists.newArrayList();
        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(query)) {
            queryWrapper.and(wrapper -> wrapper.like("name", query).or().likeRight("mobile", query));
        }
        queryWrapper.eq("belong_app", belongApp);
        queryWrapper.last("limit 99 offset 0");
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
    public List<SelectedVO> getCustomerListByName(String name, String belongApp) {
        List<SelectedVO> result = Lists.newArrayList();
        QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(belongApp)) {
            queryWrapper.eq("belong_app", belongApp);
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

    /**
     * 销毁客户账号
     *
     * @param userId 客户账号ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void destroyCustomer(String userId) {
        familyUerRemoveDTO removeDTO = new familyUerRemoveDTO();
        removeDTO.setUserId(userId);
        Response deviceRes = deviceRemote.removeUser(removeDTO);

        log.info("销毁账号工程返回信息:{}", JSON.toJSONString(deviceRes));

        if (deviceRes == null || !deviceRes.isSuccess()) {
            String errorMsg = CUSTOMER_DESTROY_ERROR.getMsg();
            if (deviceRes != null && !StringUtils.isEmpty(deviceRes.getErrorMsg())) {
                errorMsg = deviceRes.getErrorMsg();
            }
            throw new BusinessException(String.valueOf(CUSTOMER_DESTROY_ERROR.getCode()), errorMsg);
        }
        removeById(userId);
        // 删除三方记录表
        customerThirdSourceService.removeByUserId(userId);
    }

    @Override
    public CustomerRegisterResDTO buildAppLoginSuccessData(String userId, String access_token) {
        CustomerRegisterResDTO result = new CustomerRegisterResDTO();
        HomeAutoAppCustomer customer = customerCacheProvider.getCustomer(userId);
        BeanUtils.copyProperties(customer, result);
        result.setUserId(customer.getId());
        result.setToken(access_token);
        //更新登录时间
        updateLoginTime(userId);
        // 获取家庭信息
        try {
            Response<FamilyVO> familyVOResponse = deviceRemote.getFamily(customer.getId());
            if (familyVOResponse != null && familyVOResponse.isSuccess()) {
                FamilyVO familyVO = familyVOResponse.getResult();
                result.setFamilyMessage(familyVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HomeAutoAppCustomer getCustomerByOpenId(String openid, String source) {
        HomeAutoAppCustomer customer=null;
        CustomerThirdSource customerThirdSource=customerThirdSourceService.getRecord(openid,source);
        if(customerThirdSource!=null&&!StringUtils.isEmpty(customerThirdSource.getUserId())){
            QueryWrapper<HomeAutoAppCustomer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", customerThirdSource.getUserId());
            customer=getOne(queryWrapper);
        }
        return customer;
    }

    @Override
    public CustomerWechatLoginResDTO buildWechatLoginSuccessData(String userId, String access_token, String openId) {
        CustomerWechatLoginResDTO resDTO = new CustomerWechatLoginResDTO(null, null, null, null, openId, false, false, false, null);
        HomeAutoAppCustomer customer =null;
        if(!StringUtils.isEmpty(userId)){
            customer = getById(userId);
        }
        if(customer==null){
            // 这种情况，将生成的绑定code给出去，同时保存token,绑定后赋值userId再给到调用方
            String bindCode = homeAutoWechatRecordService.updateBindCodeAndToken(openId, access_token);
            resDTO.setBindAuthroizeCode(bindCode);
            return resDTO;
        }
        resDTO.setHaveUser(true);
        resDTO.setAccessToken(access_token);
        resDTO.setUserId(userId);
        resDTO.setName(customer.getName());
        resDTO.setMobile(customer.getMobile());
        resDTO.setAvatar(customer.getAvatar());
        return resDTO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public HomeAutoAppCustomer bindOpenId(String openId, String phone, String belongApp, String source, String name, String avatar) {
        HomeAutoAppCustomer customer = getCustomerByMobile(phone, belongApp);
        if (customer == null) {
            // 默认创建账号
            customer=buildCustomerByMobile(phone,belongApp);
        }
        customerThirdSourceService.saveOrUpdateRecord(customer.getId(),openId,source);
        updateLoginTime(customer.getId());
        if(!StringUtils.isEmpty(name)){
            customer.setName(name);
        }
        if(!StringUtils.isEmpty(avatar)){
            customer.setAvatar(avatar);
        }
        updateById(customer);
        return customer;
    }

    /**
     *  根据手机号创建账号
     * @param phone
     * @param belongApp
     * @return com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer
     * @author wenyilu
     * @date 2021/3/8 10:13
     */
    private HomeAutoAppCustomer buildCustomerByMobile(String phone, String belongApp) {
        HomeAutoAppCustomer homeAutoAppCustomer = new HomeAutoAppCustomer();
        homeAutoAppCustomer.setMobile(phone);
        homeAutoAppCustomer.setBelongApp(belongApp);
        homeAutoAppCustomer.setLoginTime(new Date());
        homeAutoAppCustomer.setName(phone);
        homeAutoAppCustomer.setPassword(BCrypt.hashpw(phone));
        save(homeAutoAppCustomer);
        return homeAutoAppCustomer;
    }


    private boolean saveOrUpdateValidParams(CustomerUpdateReqDTO params, boolean update, String belongApp) {
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

        queryWrapper.eq("mobile", params.getMobile());

        queryWrapper.eq("belong_app", belongApp);

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
        Response response = deviceRemote.verifyCode(jgMsgDTO);
        return response.isSuccess();
    }

}
