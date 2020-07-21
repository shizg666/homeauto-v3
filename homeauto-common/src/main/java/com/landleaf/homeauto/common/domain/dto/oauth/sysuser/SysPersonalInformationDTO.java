package com.landleaf.homeauto.common.domain.dto.oauth.sysuser;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 系统账号个人资料 DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "SysPersonalInformationDTO", description = "系统账号个人资料")
public class SysPersonalInformationDTO {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "账号名称")
    private String name;

    @ApiModelProperty(value = "账号状态名称")
    private String statusName;

    @ApiModelProperty(value = "账号状态")
    private Integer status;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱（账号）")
    private String email;

    @ApiModelProperty(value = "所属角色")
    private String roleName;

    @ApiModelProperty(value = "所属角色")
    private String roleId;

    @ApiModelProperty(value = "最近登录时间")
    private String loginTimeFormat;

    @ApiModelProperty(value = "最近登录时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private Date loginTime;

    @ApiModelProperty(value = "创建时间")
    private String createTimeFormat;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private Date createTime;


}
