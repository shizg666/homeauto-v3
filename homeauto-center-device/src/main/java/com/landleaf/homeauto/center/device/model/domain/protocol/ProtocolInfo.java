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
 * 协议表
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("protocol_info")
@ApiModel(value="Info对象", description="协议表")
public class ProtocolInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "协议名称")
    private String name;

    @ApiModelProperty(value = "协议标志")
    private String code;

    @ApiModelProperty(value = "协议场景类型")
    private Integer type;




}
