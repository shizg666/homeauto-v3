package com.landleaf.homeauto.center.device.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 家庭维修记录 新增请求对象
 * </p>
 *
 * @author wenyilu
 * @since 2021-01-29
 */
@Data
public class FamilyRepairRecordUpdateDTO extends FamilyRepairRecordAddOrUpdateBaseDTO{

    @ApiModelProperty(value = "记录ID")
    private String id;


}
