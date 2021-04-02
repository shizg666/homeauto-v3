package com.landleaf.homeauto.center.device.model.vo.scene;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 项目户型表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="SwitchSceneUpdateFlagDTO", description="SwitchSceneUpdateFlagDTO")
public class SwitchSceneUpdateFlagDTO {


    @ApiModelProperty(value = "1 app 2 大屏")
    private Integer type;

    @ApiModelProperty(value = "是否可编辑 0否 1是")
    private Integer updateFlag;

    @ApiModelProperty(value = "场景主键id 修改必传")
    private String id;






}
