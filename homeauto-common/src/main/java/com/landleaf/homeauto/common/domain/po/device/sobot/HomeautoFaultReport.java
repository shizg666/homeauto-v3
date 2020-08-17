package com.landleaf.homeauto.common.domain.po.device.sobot;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 故障报修
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeautoFaultReport对象", description="")
public class HomeautoFaultReport extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报修日期")
    private String repairTime;

    @ApiModelProperty(value = "报修现象code")
    private String repairAppearance;

    @ApiModelProperty(value = "报修现象")
    private String repairAppearanceName;

    @ApiModelProperty(value = "问题描述")
    private String description;

    @ApiModelProperty(value = "处理人")
    private String dealUser;

    @ApiModelProperty(value = "对应智齿平台工单id")
    private String sobotTicketId;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "app用户ID")
    private String repairUserId;

    @ApiModelProperty(value = "报修用户手机号")
    private String repairUserPhone;

    @ApiModelProperty(value = "智齿平台公司id")
    private String sobotCompanyid;

    @ApiModelProperty(value = "智齿平台工单分类id")
    private String sobotTypeid;

    @ApiModelProperty(value = "智齿平台工单来源")
    private String sobotFrom;


}
