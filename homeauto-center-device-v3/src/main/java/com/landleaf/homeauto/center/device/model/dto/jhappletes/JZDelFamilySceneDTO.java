package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 户式化APP家庭场景信息
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="JZDelFamilySceneDTO", description="江左自定义场景")
public class JZDelFamilySceneDTO {

    @NotNull(message = "场景id不能为空")
    @ApiModelProperty(value = "场景id")
    private Long sceneId;

}
