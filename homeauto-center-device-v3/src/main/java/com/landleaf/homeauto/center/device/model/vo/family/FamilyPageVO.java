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

    @ApiModelProperty(value = "家庭   主键id")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "户号")
    private String doorplate;

    @ApiModelProperty(value = "户型名称")
    private String templateName;

    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "楼层名称")
    private String floorName;


    @ApiModelProperty(value = "单元codestr")
    private String unitCodeStr;

    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @ApiModelProperty(value = "楼栋codestr")
    private String buildingCodeStr;

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
        this.unitCodeStr = unitCode.concat("单元");
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
        this.buildingCodeStr = buildingCode.concat("栋");
    }

    public void setFloor(String floor) {
        this.floor = floor;
        this.floorName = floor.concat("楼");
    }
}
