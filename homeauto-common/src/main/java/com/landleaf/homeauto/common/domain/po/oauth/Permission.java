package com.landleaf.homeauto.common.domain.po.oauth;

import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.enums.oauth.PermissionType;
import lombok.Data;

import java.io.Serializable;

/**
 * 权限表
 */
@Data
public class Permission extends BaseEntity implements Serializable {


    private static final long serialVersionUID = -7559506107525485531L;
    /**
     * 权限名称
     */
    private String authName;
    /**
     * 权限编码
     */
    private String authCode;
    /**
     * 权限类型
     */
    private PermissionType authType;
    /**
     * 路由地址
     */
    private String requestUrl;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 父权限
     */
    private Long parentPermission;
}
