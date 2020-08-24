package com.landleaf.homeauto.center.device.model.dto.house;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 户型楼层表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@Accessors(chain = true)
@ApiModel(value="TemplateFloorDTO", description="户型楼层")
public class TemplateFloorDTO {

    @ApiModelProperty(value = "楼层id（修改必传）")
    private String id;

    @NotEmpty(message = "楼层不能为空")
    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "户型ID")
    private String houseTemplateId;


}
