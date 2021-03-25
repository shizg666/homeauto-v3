package com.landleaf.homeauto.center.device.model.domain.protocol;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 协议属性二进制值配置
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("protocol_attr_bit")
@ApiModel(value="AttrBit对象", description="协议属性二进制值配置")
public class ProtocolAttrBit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "1 代表的描述")
    private String bit1;

    @ApiModelProperty(value = "0 代表的描述")
    private String bit0;

    @ApiModelProperty(value = "位")
    private Integer bitPos;

    @ApiModelProperty(value = "属性主键id")
    private String attrId;




}
