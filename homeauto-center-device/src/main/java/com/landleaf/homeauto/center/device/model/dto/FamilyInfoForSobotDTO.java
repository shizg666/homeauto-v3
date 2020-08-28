package com.landleaf.homeauto.center.device.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭信息给报修使用
 *
 * @author wenyilu
 */
@Data
@NoArgsConstructor
@ApiModel("FamilyInfoForSobotDTO")
public class FamilyInfoForSobotDTO {

    @ApiModelProperty(value = "家庭id")
    private String familyId;
    @ApiModelProperty(value = "家庭名称")
    private String familyName;


    @ApiModelProperty(value = "房间号")
    private String roomNo;

    @ApiModelProperty(value = "单元id")
    private String unitId;

    @ApiModelProperty(value = "项目Id")
    private String projectId;

    @ApiModelProperty(value = "楼盘ID")
    private String realestateId;

    @ApiModelProperty(value = "楼栋id")
    private String buildingId;
    @ApiModelProperty(value = "单元")
    private String unitName;
    @ApiModelProperty(value = "项目名称")
    private String projectName;
    @ApiModelProperty(value = "楼盘名称")
    private String realestateName;

    @ApiModelProperty(value = "楼栋名称")
    private String buildingName;


}
