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
 * 户型楼层表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("house_template_floor")
@ApiModel(value="TemplateFloor对象", description="户型楼层表")
public class TemplateFloorDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "户型ID")
    private String houseTemplateId;


}
