package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import com.google.common.collect.Lists;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import com.landleaf.homeauto.common.domain.vo.oauth.SysPermissionPageVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统账号信息及按钮权限信息复合体
 *
 * @author wenyilu
 */
@Data
public class SysUserInfoButtonComplexDTO implements Serializable {

    /**
     * 用户基础信息
     */
    private SysUser sysUser;

    /**
     * 用户页面权限信息
     */
    private List<SysPermissionPageVO> pages = Lists.newArrayList();

}
