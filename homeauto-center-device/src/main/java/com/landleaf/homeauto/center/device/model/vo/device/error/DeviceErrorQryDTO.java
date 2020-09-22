package com.landleaf.homeauto.center.device.model.vo.device.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.qry.BaseQry;
import com.landleaf.homeauto.common.enums.category.AttributeErrorTypeEnum;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 属性视图对象
 *
 * @author Yujiumin
 * @version 2020/8/15
 */
@Data
@NoArgsConstructor
@ApiModel(value="DeviceErrorQryDTO", description="设备故障信息")
public class DeviceErrorQryDTO extends BaseQry {

    @ApiModelProperty("产品code")
    private String productCode;

    @ApiModelProperty("家庭名称")
    private String familyName;

    @ApiModelProperty("状态")

    private Integer faultstatus;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    /**
     *  * {@link AttributeErrorTypeEnum}
     */
    @ApiModelProperty("类别 1暖通 2 通信 3数值")
    private Integer type;


    @ApiModelProperty("楼盘id")
    private String realestateId;

    @ApiModelProperty("项目id")
    private String projectId;

    @ApiModelProperty("楼盘项目path")
    private String path;


}
