package com.landleaf.homeauto.center.oauth.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.oauth.cache.UserInfoCacheProvider;
import com.landleaf.homeauto.center.oauth.mapper.SysUserMapper;
import com.landleaf.homeauto.center.oauth.remote.DeviceRemote;
import com.landleaf.homeauto.center.oauth.service.ISysUserService;
import com.landleaf.homeauto.common.constant.DateFormatConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.email.EmailMsgDTO;
import com.landleaf.homeauto.common.domain.dto.jg.JgMsgDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.sysuser.*;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.enums.StatusEnum;
import com.landleaf.homeauto.common.enums.email.EmailMsgTypeEnum;
import com.landleaf.homeauto.common.enums.jg.JgSmsTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.exception.JgException;
import com.landleaf.homeauto.common.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.*;


/**
 * <p>
 * 后台账号表 服务实现类
 * </p>
 *
 * @author wyl
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private UserInfoCacheProvider userInfoCacheProvider;

    @Autowired(required = false)
    private DeviceRemote deviceRemote;

    @Autowired(required = false)
    private SysUserMapper sysUserMapper;

    @Override
    public SysPersonalInformationDTO getPersonalInformation(String userId) {
        SysPersonalInformationDTO result = new SysPersonalInformationDTO();
        SysUser userInfo = userInfoCacheProvider.getUserInfo(userId);
        if (userInfo != null) {
            BeanUtils.copyProperties(userInfo, result);
            Date loginTime = userInfo.getLoginTime();
            LocalDateTime createTime = userInfo.getCreateTime();
            if (loginTime != null) {
                result.setLoginTimeFormat(DateFormatUtils.format(loginTime, DateFormatConst.PATTERN_YYYY_MM_DD_HH_MM_SS));
            }
            if (createTime != null) {
                result.setCreateTimeFormat(LocalDateTimeUtil.formatTime(createTime, DateFormatConst.PATTERN_YYYY_MM_DD_HH_MM_SS));
            }
        }
        result.setStatusName(StatusEnum.getStatusByType(result.getStatus()).getName());
        return result;
    }

    @Override
    public boolean updateAvatar(String userId, String avatar) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(avatar)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        SysUser sysUser = getById(userId);
        if (sysUser == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        SysUser updateUser = new SysUser();
        BeanUtils.copyProperties(sysUser, updateUser);
        updateUser.setAvatar(avatar);
        return updateById(updateUser);
    }

    @Override
    public boolean updatePersonalInfo(String userId, String mobile, String code, String name) {
        if (StringUtils.isEmpty(userId) || (
                StringUtils.isEmpty(mobile) &&
                        StringUtils.isEmpty(name)
        )) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        SysUser sysUser = getById(userId);
        if (sysUser == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        SysUser updateUser = new SysUser();
        BeanUtils.copyProperties(sysUser, updateUser);

        if (StringUtils.isNotEmpty(mobile)) {
            updateUser.setMobile(mobile);
            veryMobile(userId, mobile, code, true,sysUser.getPlat());
        }
        if (StringUtils.isNotEmpty(name)) {
            updateUser.setName(name);
        }
        return updateById(updateUser);
    }

    @Override
    public boolean resetPersonalPwd(String userId, String newPassword, String oldPassword) {

        if (StringUtils.isEmpty(userId) ||
                StringUtils.isEmpty(oldPassword) ||
                StringUtils.isEmpty(newPassword)
        ) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        SysUser sysUser = getById(userId);
        if (sysUser == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        if (!BCrypt.checkpw(oldPassword, sysUser.getPassword())) {
            throw new BusinessException(PASSWORD_OLD_INPUT_ERROE);
        }
        SysUser updateUser = new SysUser();
        BeanUtils.copyProperties(sysUser, updateUser);
        updateUser.setPassword(newPassword);
        return updateById(updateUser);
    }

    @Override
    public BasePageVO<SysPersonalInformationDTO> pageListSysUsers(SysUserPageReqDTO requestBody) {
        BasePageVO<SysPersonalInformationDTO> result = new BasePageVO<SysPersonalInformationDTO>();
        List<SysPersonalInformationDTO> data = Lists.newArrayList();
        PageHelper.startPage(requestBody.getPageNum(), requestBody.getPageSize(), true);
        String mobile = requestBody.getMobile();
        String name = requestBody.getName();
        String email = requestBody.getEmail();
        List<String> createTimeFormat = requestBody.getCreateTimeFormat();
        String startTime = null;
        String endTime = null;
        if (!CollectionUtils.isEmpty(createTimeFormat) && createTimeFormat.size() == 2) {
            startTime = createTimeFormat.get(0);
            endTime = createTimeFormat.get(1);
        }
        List<SysPersonalInformationDTO> queryResult = sysUserMapper.listSysUsers(requestBody.getPlat(), name, mobile, requestBody.getStatus(), email, startTime, endTime);
        if (!CollectionUtils.isEmpty(queryResult)) {
            data.addAll(queryResult.stream().map(i -> {
                SysPersonalInformationDTO tmp = new SysPersonalInformationDTO();
                BeanUtils.copyProperties(i, tmp);
                Date loginTime = tmp.getLoginTime();
                Date createTime = tmp.getCreateTime();
                if (loginTime != null) {
                    tmp.setLoginTimeFormat(DateFormatUtils.format(loginTime, DateFormatConst.PATTERN_YYYY_MM_DD_HH_MM_SS));
                }
                if (createTime != null) {
                    tmp.setCreateTimeFormat(DateFormatUtils.format(createTime, DateFormatConst.PATTERN_YYYY_MM_DD_HH_MM_SS));
                }
                return tmp;
            }).collect(Collectors.toList()));
        }
        PageInfo pageInfo = new PageInfo(queryResult);
        pageInfo.setList(data);
        BeanUtils.copyProperties(pageInfo, result);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUser(SysUserUpdateReqDTO requestBody) {
        if(requestBody.getPlat()==null){
            requestBody.setPlat(UserTypeEnum.WEB_DEPLOY.getType());
        }
        if (!saveOrUpdateValidParams(requestBody, true)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        SysUser updateUser = new SysUser();
        BeanUtils.copyProperties(requestBody, updateUser);
        SysUser existUser = getById(requestBody.getId());
        if (existUser == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        //手机号不一致需要校验唯一性
        if (!StringUtils.equalsIgnoreCase(requestBody.getMobile(), existUser.getMobile())) {
            veryMobileUnique(requestBody.getId(), requestBody.getMobile(), true,requestBody.getPlat());
        }
        String initPassword = requestBody.getPassword();
        if (!StringUtils.isEmpty(initPassword)) {
            String hashpw = BCrypt.hashpw(initPassword);
            updateUser.setPassword(hashpw);
        }
        boolean updateUserFlag = updateById(updateUser);
        return updateUserFlag;
    }

    @Transactional
    @Override
    public boolean addSysUser(SysUserAddReqDTO requestBody) {
        String password = requestBody.getPassword();
        Integer plat = requestBody.getPlat();
        if (StringUtils.isEmpty(password)) {
            requestBody.setPassword("123456");
        }
        if (plat==null) {
            requestBody.setPlat(UserTypeEnum.WEB_DEPLOY.getType());
        }
        SysUserUpdateReqDTO params = new SysUserUpdateReqDTO();
        BeanUtils.copyProperties(requestBody, params);
        if (!saveOrUpdateValidParams(params, false)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        veryMobileUnique(null, requestBody.getMobile(), false,plat);
        SysUser saveUser = new SysUser();
        BeanUtils.copyProperties(requestBody, saveUser);
        saveUser.setStatus(StatusEnum.ACTIVE.getType());
        if (requestBody.getStatus() != null) {
            saveUser.setStatus(requestBody.getStatus());
        }
        String initPassword = requestBody.getPassword();
        // bcrypt加密
        String bcryptPassword = BCrypt.hashpw(initPassword);
        saveUser.setPassword(bcryptPassword);
        save(saveUser);
        //发送邮件
        try {
            EmailMsgDTO emailMsgDTO = new EmailMsgDTO();
            emailMsgDTO.setEmail(saveUser.getEmail());
            emailMsgDTO.setEmailMsgType(EmailMsgTypeEnum.EMAIL_DEFAULT_PWD.getType());
            emailMsgDTO.setMsg(initPassword);
            deviceRemote.sendEmail(emailMsgDTO);
        } catch (Exception e) {
            log.error("发送初始密码错误", e);
        }
        return true;
    }

    @Override
    public List<SysUser> queryAllSysUser() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        return list(queryWrapper);
    }


    @Override
    public boolean forgetPwd(SysUserForgetPasswordDTO requestBody) {
        Integer plat = requestBody.getPlat();
        if(plat==null){
            requestBody.setPlat(UserTypeEnum.WEB_DEPLOY.getType());
        }
        switch (requestBody.getType()) {
            case 1:
                forgetPwdByEmail(requestBody);
                break;
            case 2:
                forgetPwdByMobile(requestBody);
                break;
            default:
                break;
        }
        return true;
    }

    //通过邮箱找回密码
    private void forgetPwdByEmail(SysUserForgetPasswordDTO requestBody) {

        String email = requestBody.getEmail();
        Integer plat = requestBody.getPlat();
        SysUser sysUser = getOne(new QueryWrapper<SysUser>().eq("email", email).eq("plat", plat));
        if (sysUser == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        boolean codeFlag = veryCodeFlagFEmail(requestBody.getCode(), requestBody.getEmail());
        if (!codeFlag) {
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_MC_EMAIL_CODE_NOT_ERROR);
        }
        sysUser.setEmail(email);
        sysUser.setPassword(BCrypt.hashpw(requestBody.getNewPassword()));
        updateById(sysUser);
    }

    private void forgetPwdByMobile(SysUserForgetPasswordDTO requestBody) {
        String mobile = requestBody.getMobile();
        Integer plat = requestBody.getPlat();
        SysUser sysUser = getOne(new QueryWrapper<SysUser>().eq("mobile", mobile).eq("plat", mobile));
        if (sysUser == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        boolean codeFlag = veryCodeFlag(requestBody.getCode(), requestBody.getMobile(), JgSmsTypeEnum.RESET.getMsgType());
        if (!codeFlag) {
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_JG_CODE_VERIFY_ERROR);
        }
        sysUser.setMobile(mobile);
        sysUser.setPassword(BCrypt.hashpw(requestBody.getNewPassword()));
        updateById(sysUser);
    }

    @Override
    public List<SysUser> getSysUserByName(String name) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>();
        wrapper.like("name", name);
        return list(wrapper);
    }

    @Override
    public void updateStatus(SysUserUpdateStatusReqDTO requestBody) {
        String userId = requestBody.getUserId();
        Integer status = requestBody.getStatus();
        SysUser existUser = getById(userId);
        if (existUser == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }
        SysUser updateUser = new SysUser();
        updateUser.setId(userId);
        updateUser.setStatus(status);
        updateById(updateUser);
    }


    @Override
    public List<SelectedVO> getUserListByName(String name) {
        List<SelectedVO> result = Lists.newArrayList();
        PageHelper.startPage(1, 30, true);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (!org.springframework.util.StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        List<SysUser> queryResult = list(queryWrapper);
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
    public SysUser resolveSysUser(String account,Integer plat) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<SysUser>();
        lambdaQueryWrapper.and(wrapper -> wrapper.eq(SysUser::getEmail, account).or().eq(SysUser::getMobile, account));
        lambdaQueryWrapper.eq(SysUser::getPlat,plat);
        SysUser sysUser = getOne(lambdaQueryWrapper);
        if (sysUser == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND.getMsg());
        }
        if (Objects.equals(sysUser.getStatus(), StatusEnum.INACTIVE.getType())) {
            throw new DisabledException(USER_INACTIVE_ERROE.getMsg());
        }
        return sysUser;
    }

    @Override
    public AuthenticationDTO buildWebLoginSuccessData(String userId, String access_token) {
        AuthenticationDTO result = new AuthenticationDTO();
        // 更新登录时间
        updateLoginTime(userId);
        SysUser existUser = getById(userId);
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(existUser, sysUserDTO);
        result.setSysUser(sysUserDTO);
        result.setToken(access_token);
        return result;
    }


    @Override
    public boolean delete(List<String> ids) {
        return removeByIds(ids);
    }

    @Override
    public SysUserCheckCodeResDTO checkCode(Integer type, String code, String account) {
        SysUserCheckCodeResDTO result = new SysUserCheckCodeResDTO();
        switch (type) {
            case 1:
                result.setResult(veryCodeFlagFEmail(code, account));
                break;
            case 2:
                result.setResult(veryCodeFlag(code, account, JgSmsTypeEnum.RESET.getMsgType()));
                break;
            default:
                break;
        }
        return result;
    }

    private void updateLoginTime(String userId) {
        userInfoCacheProvider.remove(userId);
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setLoginTime(new Date());
        updateById(sysUser);
        userInfoCacheProvider.getUserInfo(userId);
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
        jgMsgDTO.setCode(veryCode);
        jgMsgDTO.setCodeType(type);
        jgMsgDTO.setMobile(mobile);
        Response response = deviceRemote.verifyCode(jgMsgDTO);
        return response.isSuccess();
    }

    /**
     * 邮箱验证码验证
     *
     * @param veryCode
     * @return
     */
    private boolean veryCodeFlagFEmail(String veryCode, String email) {
        EmailMsgDTO emailMsgDTO = new EmailMsgDTO();
        emailMsgDTO.setEmail(email);
        emailMsgDTO.setEmailMsgType(EmailMsgTypeEnum.EMAIL_CODE.getType());
        emailMsgDTO.setMsg(veryCode);
        Response response = deviceRemote.verifyEmailCode(emailMsgDTO);
        return response.isSuccess();
    }


    private boolean saveOrUpdateValidParams(SysUserUpdateReqDTO params, boolean update) {
        if (params == null) {
            return false;
        }
        String id = params.getId();

        if (StringUtils.isEmpty(id) && update) {
            return false;
        }
        if (StringUtils.isEmpty(params.getEmail()) ||
                StringUtils.isEmpty(params.getMobile()) ||
                StringUtils.isEmpty(params.getName())) {
            return false;
        }

        //校验邮箱唯一性
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", params.getEmail());
        queryWrapper.eq("plat", params.getPlat());
        if (update) {
            List<String> ids = Lists.newArrayList();
            ids.add(params.getId());
            queryWrapper.notIn("id", ids);
        }
        int count = count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(EMAIL_EXIST_ERROE);
        }
        return true;
    }

    //校验手机号
    private boolean veryMobile(String userId, String mobile, String code, boolean update,Integer plat) {
        veryMobileUnique(userId,mobile,update,plat);
        boolean b = veryCodeFlag(code, mobile, JgSmsTypeEnum.REGISTER_LOGIN.getMsgType());
        if (!b) {
            throw new JgException(ErrorCodeEnumConst.ERROR_CODE_JG_CODE_VERIFY_ERROR);
        }
        return true;

    }

    //校验手机号唯一性
    private boolean veryMobileUnique(String userId, String mobile, boolean update,Integer plat) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        queryWrapper.eq("plat", plat);
        if (update) {
            List<String> ids = Lists.newArrayList();
            ids.add(userId);
            queryWrapper.notIn("id", ids);
        }
        int count = count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(MOBILE_EXIST_ERROE);
        }
        return true;

    }


}
