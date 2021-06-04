package com.landleaf.homeauto.center.device.model.dto.house;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 户型房间表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@ApiModel(value="TemplateRoomDTO", description="户型房间")
public class TemplateRoomDTO  {

    @ApiModelProperty(value = "房间id（修改必传）")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "楼层")
    private String floor;

    @ApiModelProperty(value = "面积")
    private String area;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "户型ID")
    private Long houseTemplateId;

    @ApiModelProperty(value = "项目id")
    private Long projectId;



}
