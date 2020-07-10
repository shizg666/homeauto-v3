package com.landleaf.homeauto.center.oauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.oauth.sysrole.*;
import com.landleaf.homeauto.common.domain.po.oauth.SysRole;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.oauth.SysRoleSelectVO;

import java.util.List;

/**
 * <p>
 * 后台账号角色表 服务类
 * </p>
 *
 * @author wyl
 */
public interface ISysRoleService extends IService<SysRole> {

    List<SysRole> queryRolesByIds(List<String> roleIds);

    /**
     * 获取角色基本信息
     *
     * @param roleId
     * @return
     */
    SysRoleDTO getSysRoleInfo(String roleId);

    /**
     * 分页查询角色列表
     *
     * @param requestBody
     * @return
     */
    BasePageVO<SysRoleDTO> pageListSysRoles(SysRolePageReqDTO requestBody);

    /**
     * 修改角色
     *
     * @param requestBody
     * @return
     */
    boolean updateSysRole(SysRoleUpdateComplexReqDTO requestBody);

    /**
     * 新增角色
     *
     * @param requestBody
     * @return
     */
    boolean addSysRole(SysRoleAddComplexReqDTO requestBody);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    boolean deleteSysRoles(List<String> ids);

    List<SysRole> queryAllRole();

    List<SysRoleSelectVO> listSysRolesByStatus(Integer status);

    void updateStatus(SysRoleUpdateStatusReqDTO requestBody);

    SysRoleAddComplexReqDTO getSysRoleComplexInfo(String roleId);
}
