package com.landleaf.homeauto.center.device.model.domain;

import java.time.LocalDate;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 假期记录表
 * </p>
 *
 * @author wenyilu
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="VacationSetting对象", description="假期记录表")
public class VacationSetting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "节假日类型 1-放假 2-补班")
    private Integer type;

    @ApiModelProperty(value = "日期")
    private LocalDate day;

    @ApiModelProperty(value = "节日描述")
    private String comment;

    @ApiModelProperty(value = "对应目标类型")
    private Integer targetType;

    @ApiModelProperty(value = "对应值")
    private String target;


}
