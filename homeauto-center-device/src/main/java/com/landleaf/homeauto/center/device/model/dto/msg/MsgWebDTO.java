package com.landleaf.homeauto.center.device.model.dto.msg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/8/13 14:03
 * @description:
 */
@Data
@ToString(callSuper = true)
@ApiModel("后端msg基类")
public class MsgWebDTO extends MsgDTO {

    @ApiModelProperty("发布人")
    private String releaseUser;

    @ApiModelProperty( value = "发布标识", required = true)
    private Integer releaseFlag;

    @ApiModelProperty("发布时间")
    private Date releaseTime;

    @ApiModelProperty( value = "发布地址", required = true)
    private List<ShAddressDTO> shAddresses;

    @ApiModelProperty(value = "消息类型 公告/广告/场景/联动")
    private Integer msgType;

    @ApiModelProperty(value = "消息id")
    private String msgId;


    @ApiModelProperty(value = "项目path")
    private String targetPath;


    @ApiModelProperty(value = "楼盘id")
    private String realestateId;

    @ApiModelProperty(value = "项目id")
    private String projectId;

}
