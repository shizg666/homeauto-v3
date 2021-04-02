package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationDTO implements Serializable {

    private static final long serialVersionUID = 6855104098683526365L;

    private String token;

    private SysUserDTO sysUser;

    public AuthenticationDTO() {
    }

    public AuthenticationDTO(String token) {
        this.token = token;
    }
}
