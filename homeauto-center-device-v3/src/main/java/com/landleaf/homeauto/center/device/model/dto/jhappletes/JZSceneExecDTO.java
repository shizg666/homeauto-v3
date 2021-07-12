package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName JzappletesUserDTO
 * @Description: TODO
 * @Author shizg
 * @Date 2021/6/22
 * @Version V1.0
 **/
@Data
@Accessors(chain = true)
@ApiModel(value="JZSceneExecDTO", description="场景执行")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JZSceneExecDTO implements Serializable {


    @NotNull(message = "楼栋号不能为空")
    @ApiModelProperty(value = "楼栋")
    private String buildCode;

    @NotNull(message = "单元不能为空")
    @ApiModelProperty(value = "单元")
    private String unitCode;

    @NotNull(message = "门牌号不能为空")
    @ApiModelProperty(value = "门牌号  (格式 楼层+（两位的门牌）ps: 101 : 一楼01室  1101 11楼01室)")
    private String doorplate;

    @NotNull(message = "场景类型不能为空")
    @ApiModelProperty(value = "场景类型 0否 1是")
    private Integer type;

    @NotNull(message = "场景id不能为空")
    @ApiModelProperty(value = "场景id")
    private Long sceneId;

}
