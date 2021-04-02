package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyDeviceUpDTO", description="家庭修改设备")
public class FamilyDeviceUpDTO {

    @ApiModelProperty(value = "主键")
    private String id;

    @NotEmpty(message = "设备名称不能为空")
    @ApiModelProperty(value = "名称")
    @Length(min=1, max=5,message = "名称不能超过五个字符")
    private String name;

    @NotEmpty(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String sn;

    @ApiModelProperty(value = "485端口号")
    private String port;

    @ApiModelProperty(value = "ip地址")
    private String ip;

    @ApiModelProperty(value = "房间ID")
    private String roomId;

    @ApiModelProperty(value = "通信终端ID")
    private String terminalId;




}
