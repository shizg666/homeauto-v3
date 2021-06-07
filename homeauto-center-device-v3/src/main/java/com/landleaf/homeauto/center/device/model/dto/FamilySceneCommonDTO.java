package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 场景数据传输对象
 *
 * @author Yujiumin
 * @version 2020/8/17
 */
@Data
@NoArgsConstructor
@ApiModel("常用场景数据传输对象")
public class FamilySceneCommonDTO {

    @ApiModelProperty("家庭ID")
    private Long familyId;

    @ApiModelProperty("场景ID集合")
    private List<Long> scenes;

}

