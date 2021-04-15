package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.enums.realestate.ProjectStatusEnum;
import com.landleaf.homeauto.common.enums.realestate.ProjectTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 楼盘表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectBaseInfoVO", description="ProjectBaseInfoVO")
public class ProjectBaseInfoVO {
    @ApiModelProperty(value = "楼盘id")
    private Long realestateId;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "类型Str")
    private String typeSr;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态Str")
    private String statusStr;


    @ApiModelProperty(value = "名称")
    private String name;

    public void setType(Integer type) {
        this.type = type;
        this.typeSr = ProjectTypeEnum.getInstByType(type) != null?ProjectTypeEnum.getInstByType(type).getName():"";
    }

    public void setStatus(Integer status) {
        this.status = status;
        this.statusStr = ProjectStatusEnum.getInstByType(status) != null?ProjectTypeEnum.getInstByType(status).getName():"";
    }
}
