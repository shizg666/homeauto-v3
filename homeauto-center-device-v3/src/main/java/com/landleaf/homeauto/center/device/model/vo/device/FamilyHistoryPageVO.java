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
@ApiModel(value = "FamilyHistoryPageVO", description = "家庭设备属性分页返回VO")
public class FamilyHistoryPageVO {
    @ApiModelProperty(value = "属性编号")
    private String code;

    @ApiModelProperty(value = "单位类型")
    private String unitType;

    @ApiModelProperty("时间序列")
    private List<LocalDateTime>  xList;

    @ApiModelProperty(value = "值序列")
    private List<String> yList;

    @ApiModelProperty(value = "code属性信息")
    private AttributeDicDetailVO  attributeDetailVO;
    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private Long total;


    @ApiModelProperty("总页数")
    private Integer pages;
}
