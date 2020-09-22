package com.landleaf.homeauto.center.device.model.vo.device.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.enums.category.ErrorStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 属性视图对象
 *
 * @author Yujiumin
 * @version 2020/8/15
 */
@Data
@NoArgsConstructor
@ApiModel(value="DeviceErrorVO", description="设备故障信息")
public class DeviceErrorVO {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("产品名称")
    private String productName;

    @ApiModelProperty("所属楼盘")
    private String realestateName;

    @ApiModelProperty("所属项目")
    private String projectName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime createTime;

    @ApiModelProperty("家庭名称")
    private String familyName;

    @ApiModelProperty("故障描述")
    private String faultMsg;

    @ApiModelProperty("故障状态")
    private Integer faultStatus;

    @ApiModelProperty("故障状态str")
    private String faultStatusStr;

    @ApiModelProperty("参考值")
    private String reference;

    @ApiModelProperty("当前值")
    private String current;

    public void setFaultStatus(Integer faultStatus) {
        this.faultStatus = faultStatus;
        this.faultStatusStr = ErrorStatusEnum.getInstByType(faultStatus) != null?ErrorStatusEnum.getInstByType(faultStatus).getName():"";
    }
}
