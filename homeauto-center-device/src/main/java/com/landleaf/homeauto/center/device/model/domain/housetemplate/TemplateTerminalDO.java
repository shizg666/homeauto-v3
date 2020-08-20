package com.landleaf.homeauto.center.device.model.domain.housetemplate;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型终端表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("house_template_terminal")
@ApiModel(value="TemplateTerminal对象", description="户型终端表")
public class TemplateTerminalDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型 1大屏2 网关")
    private Integer type;

    @ApiModelProperty(value = "是否是主网关 ")
    private Integer masterFlag;

    @ApiModelProperty(value = "户型ID")
    private String houseTemplateId;


}
