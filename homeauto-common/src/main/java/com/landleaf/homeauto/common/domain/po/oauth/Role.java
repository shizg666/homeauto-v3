package com.landleaf.homeauto.common.domain.po.oauth;

import com.landleaf.homeauto.common.domain.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色表
 */
@Data
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4791049986935562849L;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 说明
     */
    private String comment;
    /**
     * 状态
     */
    private Integer status;
}
