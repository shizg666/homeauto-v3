package com.landleaf.homeauto.center.device.model.vo.project;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 家庭终端表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@Accessors(chain = true)
@ApiModel(value="HouseTemplateTerminalVO", description="户型终端")
public class HouseTemplateTerminalVO {

    @ApiModelProperty(value = "项目id(必传)")
    private String projectId;

    @ApiModelProperty(value = "主键id(修改必传)")
    private String id;

    @NotEmpty(message = "名称不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型 1大屏2 网关")
    private Integer type;

    @ApiModelProperty(value = "是否是主网关 ")
    private Integer masterFlag;




}
