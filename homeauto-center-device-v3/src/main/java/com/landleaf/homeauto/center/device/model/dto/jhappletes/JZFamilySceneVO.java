package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 户式化APP家庭场景信息
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="JZFamilySceneVO", description="户式化APP家庭场景信息")
public class JZFamilySceneVO {


    @ApiModelProperty("场景ID")
    private Long sceneId;

    @ApiModelProperty("场景名称")
    private String sceneName;

    @ApiModelProperty("场景图标")
    private String sceneIcon;

    @ApiModelProperty("是否是默认场景 0否 1是")
    private Integer defaultFlag;

}
