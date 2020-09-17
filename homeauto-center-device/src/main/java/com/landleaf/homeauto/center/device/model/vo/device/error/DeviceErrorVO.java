package com.landleaf.homeauto.center.device.model.vo.device.error;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ApiModelProperty("类型")
    private String categoryName;

    @ApiModelProperty("故障信息")
    private String message;

    @ApiModelProperty("所属楼盘")
    private String realestateName;

    @ApiModelProperty("所属项目")
    private String projectName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime createTime;

    @ApiModelProperty("家庭名称")
    private String familyName;

    @ApiModelProperty("故障状态")
    private String statusStr;

    @ApiModelProperty("故障状态")
    private Integer status;

    @ApiModelProperty("参考值")
    private String reference;

}
