package com.landleaf.homeauto.center.device.model.vo.project;

import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
@ApiModel(value="HomeDeviceStatisticsBO", description="HomeDeviceStatisticsBO")
public class HomeDeviceStatisticsBO {

    @ApiModelProperty(value = "品类")
    private String categoryCode;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "户型id")
    private String templateId;






//    @ApiModelProperty(value = "设备号")
//    private String sn;

//    @ApiModelProperty(value = "485端口号")
//    private String port;
//
//    @ApiModelProperty(value = "波特率")
//    private Integer baudRate;
//
//    @ApiModelProperty(value = "波特率字符串")
//    private String baudRateStr;
//
//    @ApiModelProperty(value = "数据位")
//    private String dataBit;
//
//    @ApiModelProperty(value = "停止位")
//    private String stopBit;
//
//    @ApiModelProperty(value = "校验模式")
//    private Integer checkMode;
//
//    @ApiModelProperty(value = "校验模式字符串")
//    private String checkModeStr;


//    @ApiModelProperty(value = "通信终端ID")
//    private String terminalId;
//
//    @ApiModelProperty(value = "通信终端名称")
//    private String terminalName;



}
