package com.landleaf.homeauto.common.domain.dto.screen.http.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Lokiy
 * @Date 2021/8/9 11:10
 * @Description 大屏家庭model返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("大屏家庭model返回对象")
public class ScreenFamilyModelResponseDTO {

    @ApiModelProperty("项目编号")
    private String projectCode;

    @ApiModelProperty("户型号")
    private String houseType;

    @ApiModelProperty("楼栋号")
    private Integer buildingCode;

    @ApiModelProperty("单元号")
    private Integer unitCode;

    @ApiModelProperty("门牌号")
    private String doorplate;

    @ApiModelProperty("楼层")
    private Integer floor;
}
