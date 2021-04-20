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
@ApiModel(value="ProjectDetailVO", description="ProjectDetailVO")
public class ProjectDetailVO {

    @ApiModelProperty(value = "开发商Name")
    private String developerName;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "类型Str")
    private String typeStr;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "楼盘名称")
    private String realestateName;

    @ApiModelProperty(value = "楼盘Id")
    private Long realestateId;

    @ApiModelProperty(value = "项目经理")
    private String projectManager;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态str")
    private String statusStr;

    @ApiModelProperty(value = "pathName")
    private String pathName;

    public void setStatus(Integer status) {
        this.status = status;
        this.statusStr = ProjectStatusEnum.getInstByType(status) != null? ProjectStatusEnum.getInstByType(status).getName():"";
    }



    public void setType(Integer type) {
        this.type = type;
        this.typeStr = ProjectTypeEnum.getInstByType(type) != null?ProjectTypeEnum.getInstByType(type).getName():"";
    }
}
