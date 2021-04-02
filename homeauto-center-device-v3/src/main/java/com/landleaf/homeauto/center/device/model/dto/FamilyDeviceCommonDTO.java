package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/8/20
 */
@Data
@NoArgsConstructor
@ApiModel("家庭常用设备数据传输对象")
public class FamilyDeviceCommonDTO {

    @ApiModelProperty("家庭ID")
    private String familyId;

    @ApiModelProperty("设备ID集合")
    private List<String> devices;

}
