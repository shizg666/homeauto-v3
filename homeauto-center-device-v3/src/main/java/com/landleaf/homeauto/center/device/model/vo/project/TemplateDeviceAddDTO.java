package com.landleaf.homeauto.center.device.model.vo.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.enums.FamilySystemFlagEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 户型设备表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@ApiModel(value="TemplateDeviceAddDTO", description="户型设备")
public class TemplateDeviceAddDTO {

    @ApiModelProperty(value = "主键 修改必填")
    private Long id;

    @NotEmpty(message = "设备名称不能为空")
    @ApiModelProperty(value = "名称")
    @Length(min=1, max=5,message = "名称不能超过五个字符")
    private String name;

    @ApiModelProperty(value = "设备编号")
    private String sn;

    @ApiModelProperty(value = "设备地址")
    private String addressCode;

    @NotNull(message = "产品ID不能为空")
    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "房间ID")
    private Long roomId;

    @ApiModelProperty(value = "品类id")
    private Long categoryId;

    @NotNull(message = "户型ID不能为空")
    @ApiModelProperty(value = "户型ID")
    private Long houseTemplateId;

    /**
     * {@link FamilySystemFlagEnum}
     */
    @ApiModelProperty(value = "设备类型 1普通设备 2系统下的子设备 3系统设备")
    private Integer systemFlg;

}
