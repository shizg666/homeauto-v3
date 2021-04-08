package com.landleaf.homeauto.center.device.model.vo.scene.house;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 场景联动关备属性表
 * </p>
 *
 * @author lokiy
 * @since 2019-08-22
 */
@Data
@Accessors(chain = true)
@ApiModel(value="HouseSceneDeleteDTO", description="")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseSceneDeleteDTO {


    @ApiModelProperty(value = "情景id")
    private Long sceneId;

    @ApiModelProperty(value = "设备id")
    private Long deviceId;

}
