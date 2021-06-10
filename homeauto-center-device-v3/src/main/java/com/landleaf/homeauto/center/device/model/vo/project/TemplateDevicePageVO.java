package com.landleaf.homeauto.center.device.model.vo.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.enums.category.BaudRateEnum;
import com.landleaf.homeauto.common.enums.category.CategoryTypeEnum;
import com.landleaf.homeauto.common.enums.category.CheckEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@ApiModel(value="TemplateDevicePageVO", description="户型设备分页对象")
public class TemplateDevicePageVO {

    @ApiModelProperty(value = "设备id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设备地址")
    private String addressCode;

    @ApiModelProperty(value = "设备编号")
    private String sn;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌code")
    private String brandCode;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "品类ID")
    private String categoryId;

    @ApiModelProperty(value = "品类名称")
    private String categoryName;

//    @ApiModelProperty(value = "品类名称")
//    private String categoryName;

    @ApiModelProperty(value = "房间ID")
    private String roomId;

    @ApiModelProperty(value = "设备类型 1普通设备 2系统下的子设备 3系统设备")
    private Integer systemFlag;


//    public void setCategoryCode(String categoryCode) {
//        this.categoryCode = categoryCode;
//        this.categoryName = CategoryTypeEnum.getInstByType(categoryCode) == null?"-":CategoryTypeEnum.getInstByType(categoryCode).getName();
//    }



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
