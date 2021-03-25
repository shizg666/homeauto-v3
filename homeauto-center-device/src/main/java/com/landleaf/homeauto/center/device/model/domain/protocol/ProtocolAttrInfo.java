package com.landleaf.homeauto.center.device.model.domain.protocol;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 协议属性信息
 * </p>
 *
 * @author lokiy
 * @since 2020-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("protocol_attr_info")
@ApiModel(value="AttrInfo对象", description="协议属性信息")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProtocolAttrInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "通信编码")
    private String code;

    @ApiModelProperty(value = "操作权限")
    private String operateAcl;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "值类型")
    private Integer valueType;

    @ApiModelProperty(value = "app是否读写")
    private Integer appFlag;

    @ApiModelProperty(value = "协议主键id")
    private String protocolId;

    @ApiModelProperty(value = "品类")
    private String category;


}
