package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.oauth.sysuser.*;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;

import java.util.List;

/**
 * <p>
 * 后台账号表 服务类
 * </p>
 *
 * @author wyl
 * @since 2019-08-12
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 获取个人资料
     *
     * @param userId
     */
    SysPersonalInformationDTO getPersonalInformation(String userId);

    /**
     * 修改头像
     *
     * @param userId 系统账号id
     * @param avatar 头像
     */
    boolean updateAvatar(String userId, String avatar);

    /**
     * 修改账号名称/手机号
     *
     * @param userId
     * @param mobile
     * @param code
     * @param name
     * @return
     */
    boolean updatePersonalInfo(String userId, String mobile, String code, String name);

    /**
     * 重置密码
     *
     * @param userId
     * @param newPassword
     * @param oldPassword
     * @return
     */
    boolean resetPersonalPwd(String userId, String newPassword, String oldPassword);

    /**
     * 分面查询系统账户列表
     *
     * @param requestBody
     * @return
     */
    BasePageVO<SysPersonalInformationDTO> pageListSysUsers(SysUserPageReqDTO requestBody);

    /**
     * 修改账号
     *
     * @param requestBody
     * @return
     */
    boolean updateSysUser(SysUserUpdateReqDTO requestBody);

    /**
     * 新建账号
     *
     * @param requestBody
     * @return
     */
    boolean addSysUser(SysUserAddReqDTO requestBody);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<SysUser> queryAllSysUser();

    /**
     * 忘记密码
     *
     * @param requestBody
     * @return
     */
    boolean forgetPwd(SysUserForgetPasswordDTO requestBody);

    /**
     * 根据名称模糊查询
     *
     * @param name
     * @return
     */
    List<SysUser> getSysUserByName(String name);

    void updateStatus(SysUserUpdateStatusReqDTO requestBody);

    List<SelectedVO> getUserScopeByPath(List<String> paths);

    List<SelectedVO> getUserListByName(String name);

    /**
     *  根据手机号或邮箱获取后台账号信息
     * @param account  手机号/邮箱
     * @return
     */
    SysUser resolveSysUser(String account);

    Object buildWebLoginSuccessData(String userId, String access_token);

    /**
     * 获取用户信息及用户按钮权限信息
     * @param userId
     * @return
     */
    SysUserInfoButtonComplexDTO getSysUserInfoButtonComplexDTO(String userId);
}
