package com.landleaf.homeauto.common.domain.po.category;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品类协议信息表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("home_auto_category_protocol_info")
@ApiModel(value="HomeAutoCategoryProtocolInfo对象", description="品类协议信息表")
public class HomeAutoCategoryProtocolInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "波特率")
    private String baudRate;

    @ApiModelProperty(value = "数据位")
    private String dataBit;

    @ApiModelProperty(value = "停止位")
    private String stopBit;

    @ApiModelProperty(value = "校验模式")
    private String checkMode;

    @ApiModelProperty(value = "品类id")
    private String categoryId;


}
