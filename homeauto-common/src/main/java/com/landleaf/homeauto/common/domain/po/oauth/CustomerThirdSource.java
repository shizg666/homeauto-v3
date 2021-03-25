package com.landleaf.homeauto.common.domain.po.oauth;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.enums.oauth.CustomerThirdTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <p>
 * APP客户第三方来源表
 * </p>
 *
 * @author wyl
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CustomerThirdSource", description = "客户表第三方登录来源表")
public class CustomerThirdSource extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "所属客户ID")
    private String userId;
    @ApiModelProperty(value = "三方ID")
    private String openId;
    /**
     * {@link CustomerThirdTypeEnum}
     */
    @ApiModelProperty(value = "来源")
    private String source;


}
