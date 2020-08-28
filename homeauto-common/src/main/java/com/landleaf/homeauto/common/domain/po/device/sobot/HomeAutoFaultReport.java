package com.landleaf.homeauto.common.domain.po.device.sobot;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class HomeAutoFaultReport extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报修日期")
    private String repairTime;

    @ApiModelProperty(value = "报修现象code")
    @TableField("sobot_ticket_id")
    private String sobotTicketId;

    @ApiModelProperty(value = "app用户ID")
    private String repairUserId;

    @ApiModelProperty(value = "报修用户手机号")
    private String repairUserPhone;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "故障内容")
    private String content;

    @ApiModelProperty(value = "故障状态")
    private Integer status;
    @ApiModelProperty(value = "家庭ID")
    private String familyId;




}
