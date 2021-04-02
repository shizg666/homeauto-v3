package com.landleaf.homeauto.common.domain.po.oauth;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 微信账号登录信息记录
 * </p>
 *
 * @author wenyilu
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "HomeAutoWechatRecord对象", description = "微信账号登录信息记录")
public class HomeAutoWechatRecord extends Model<BaseEntity> {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.INPUT)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "微信返回")
    private String openId;

    @ApiModelProperty(value = "微信返回字段")
    private String sessionKey;

    @ApiModelProperty(value = "绑定手机接口请求凭证")
    private String code;

    @ApiModelProperty(value = "访问凭证")
    private String accessToken;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime updateTime;


}
