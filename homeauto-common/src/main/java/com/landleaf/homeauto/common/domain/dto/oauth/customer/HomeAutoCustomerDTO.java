package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 客户数据传输DTO
 *
 * @author wenyilu
 */
@Data
@ApiModel(value = "HomeAutoCustomerDTO", description = "HomeAutoCustomerDTO数据传输DTO")
public class HomeAutoCustomerDTO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "头像地址(http://)")
    private String avatar;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "是否绑定工程")
    private Integer bindFlag;

    @ApiModelProperty(value = "首次绑定时间")
    private Date bindTime;

    @ApiModelProperty(value = "绑定工程数")
    private Integer bindCount;

    @ApiModelProperty(value = "上次登录时间")
    private Date loginTime;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "是否可用")
    private Integer delFlag;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;
}
