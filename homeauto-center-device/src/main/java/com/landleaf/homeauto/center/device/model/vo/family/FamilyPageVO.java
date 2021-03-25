package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.center.device.enums.FamilyDeliveryStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyEnableStatusEnum;
import com.landleaf.homeauto.center.device.enums.FamilyReviewStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * 家庭视图对象
 *
 * @author Yujiumin
 * @version 2020/8/19
 */
@Data
@NoArgsConstructor
@ApiModel(value="DeviceMangeFamilyPageVO", description="家庭列表对象")
public class FamilyPageVO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "户号")
    private String roomNo;


    @ApiModelProperty(value = "户型id")
    private String templateId;

    @ApiModelProperty(value = "户型名称")
    private String templateName;

    @ApiModelProperty(value = "户型面积")
    private String templateArea;

    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "大屏通信Mac")
    private String screenMac;

    @ApiModelProperty(value = "启用停用状态")
    private Integer enableStatus;

    @ApiModelProperty(value = "启用停用状态")
    private String enableStatusStr;

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
        this.enableStatusStr = FamilyEnableStatusEnum.getInstByType(enableStatus) != null?FamilyEnableStatusEnum.getInstByType(enableStatus).getName():"";
    }

}
