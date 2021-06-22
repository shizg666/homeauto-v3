package com.landleaf.homeauto.common.domain.dto.oauth.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ApiModelProperty(value = "是否绑定家庭")
    private Integer bindFlag;
    @ApiModelProperty(value = "是否绑定家庭")
    private String bindFlagName;

    @ApiModelProperty(value = "首次绑定时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private Date bindTime;

    @ApiModelProperty(value = "绑定工程数")
    private Integer bindCount;

    @ApiModelProperty(value = "上次登录时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private Date loginTime;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "是否可用")
    private Integer delFlag;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    private String updateUser;
    @ApiModelProperty(value = "所属app")
    private String belongApp;
    @ApiModelProperty(value = "性别 1：男，2：女，3：未知")
    private Integer sex;
    @ApiModelProperty(value = "证件类型 1:身份证 2：军官证")
    private Integer certType;
    @ApiModelProperty(value = "证件")
    private String cert;
    @ApiModelProperty(value = "户籍路径")
    private String censusPath;
    @ApiModelProperty(value = "户籍")
    private String censusName;
}
