package com.landleaf.homeauto.center.device.model.vo.device;

import com.landleaf.homeauto.common.domain.vo.category.AttributeDicDetailVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "FamilyCurrentVO", description = "家庭设备属性当前返回VO")
public class FamilyCurrentVO {
    @ApiModelProperty(value = "属性编号")
    private String code;

    @ApiModelProperty(value = "属性编号")
    private String attrValue;

    @ApiModelProperty(value = "单位类型")
    private String unitType;

    @ApiModelProperty(value = "code属性信息")
    private AttributeDicDetailVO  attributeDetailVO;
}
