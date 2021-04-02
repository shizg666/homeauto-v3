package com.landleaf.homeauto.common.domain.po.device.sobot;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="SobotTicketType对象", description="")
public class SobotTicketType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单分类id")
    private String typeid;

    @ApiModelProperty(value = "工单分类名称")
    private String typeName;

    private String companyid;

    private String nodeFlag;

    private String parentid;

    private String typeLevel;


}
