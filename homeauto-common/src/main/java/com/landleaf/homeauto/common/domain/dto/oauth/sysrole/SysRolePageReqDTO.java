package com.landleaf.homeauto.common.domain.dto.oauth.sysrole;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统账号角色列表分页请求DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "系统账号角色列表分页请求DTO", description = "系统账号角色列表分页请求DTO")
public class SysRolePageReqDTO extends BaseQry {

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "启用标识，0：禁用，1：启用")
    private Integer status;
    @ApiModelProperty(value = "启用标识，0：禁用，1：启用")
    private Integer statusName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime createTime;

}
