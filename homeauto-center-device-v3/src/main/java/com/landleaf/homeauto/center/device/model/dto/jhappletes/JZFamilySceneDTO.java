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
@ApiModel(value="JZFamilySceneDTO", description="江左自定义场景")
public class JZFamilySceneDTO {

    @NotNull(message = "楼栋号不能为空")
    @ApiModelProperty(value = "楼栋")
    private String buildCode;

    @NotNull(message = "单元不能为空")
    @ApiModelProperty(value = "单元")
    private String unitCode;

    @NotNull(message = "门牌号不能为空")
    @ApiModelProperty(value = "门牌号")
    private String doorplate;

    @ApiModelProperty(value = "场景id主键 （修改必传）")
    private Long sceneId;

    @NotEmpty(message = "场景名称不能为空")
    @Length(max=10,message = "名称不能超过10个字符")
    @ApiModelProperty(value = "场景名称")
    private String name;

    @ApiModelProperty(value = "场景设备列表")
    private List<JZSceneDeviceDTO> devices;



}
