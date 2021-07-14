package com.landleaf.homeauto.center.gateway.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity2;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 控制命令操作日志
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("app_pri_key_config")
@ApiModel(value = "AppPriKeyConfig对象", description = "appKey和priKey的配置信息")
public class AppPriKeyConfig extends BaseEntity2 {
	private static final long serialVersionUID = 1L;

	/**
	 * appKey
	 */
    @ApiModelProperty(value = "appKey")
	private String appKey;
	
	/**
	 * priKey
	 */
    @ApiModelProperty(value = "对应的pk")
	private String priKey;
}