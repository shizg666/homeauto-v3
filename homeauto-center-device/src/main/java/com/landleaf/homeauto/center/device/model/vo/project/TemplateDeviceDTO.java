package com.landleaf.homeauto.center.device.model.vo.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@ApiModel(value="TemplateDeviceDTO", description="户型设备")
public class TemplateDeviceDTO {

    @ApiModelProperty(value = "主键")
    private String id;

    @NotEmpty(message = "设备名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @NotEmpty(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号")
    private String sn;

    @ApiModelProperty(value = "485端口号")
    private String port;

    @ApiModelProperty(value = "波特率")
    private Integer baudRate;

    @ApiModelProperty(value = "数据位")
    private String dataBit;

    @ApiModelProperty(value = "停止位")
    private String stopBit;

    @ApiModelProperty(value = "校验模式")
    private Integer checkMode;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "品类id")
    private String categoryId;

    @ApiModelProperty(value = "通信终端ID")
    private String terminalId;

    @ApiModelProperty(value = "房间ID")
    private String roomId;

    @ApiModelProperty(value = "户型ID")
    private String houseTemplateId;


}
