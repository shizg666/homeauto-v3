package com.landleaf.homeauto.center.device.model.vo.device;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 家庭设备
 *
 * @author Yujiumin
 * @version 2020/8/14
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "DeviceBaseInfoDTO", description = "设别基本信息对象")
public class DeviceBaseInfoDTO {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "设备号")
    private String sn;

//    @ApiModelProperty(value = "485端口号")
//    private String port;
//
//    @ApiModelProperty(value = "序号")
//    private Integer sortNo;

    @ApiModelProperty(value = "产品ID")
    private String productId;

//    @ApiModelProperty(value = "通信终端ID")
//    private String terminalId;

//    @ApiModelProperty(value = "房间ID")
//    private String roomId;
//
//    @ApiModelProperty(value = "家庭ID")
//    private String familyId;

    @ApiModelProperty(value = "品类")
    private String categoryId;

//    @ApiModelProperty(value = "ip地址")
//    private String ip;


}
