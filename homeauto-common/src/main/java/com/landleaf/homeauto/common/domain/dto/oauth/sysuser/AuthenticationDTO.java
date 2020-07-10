package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionButtonDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionMenuDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthenticationDTO implements Serializable {

    private static final long serialVersionUID = 6855104098683526365L;

    private String token;

    private SysUserDTO sysUser;

    private List<SysPermissionMenuDTO> menuPermisssions;

    private List<SysPermissionButtonDTO> buttonPermisssions;

    public AuthenticationDTO() {
    }

    public AuthenticationDTO(String token) {
        this.token = token;
    }
}
