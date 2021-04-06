package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.enums.realestate.ProjectTypeEnum;
import com.landleaf.homeauto.common.enums.realestate.ProjectStatusEnum;
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
@ApiModel(value="ProjectVO", description="ProjectVO")
public class ProjectVO {


    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "项目编码")
    private String code;

    @ApiModelProperty(value = "开发商Name")
    private String developerName;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "类型Str")
    private String typeSr;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "楼盘名称")
    private String realestateName;

    @ApiModelProperty(value = "楼盘Id")
    private String realestateId;

    @ApiModelProperty(value = "pathName")
    private String pathName;

    @ApiModelProperty(value = "户型数量")
    private int count;

//    @ApiModelProperty(value = "锁定状态")
//    private Integer lockFlag;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态str")
    private String statusStr;

    @ApiModelProperty(value = "项目经理")
    private String projectManager;

    @ApiModelProperty(value = "备注")
    private String remark;

    public void setType(Integer type) {
        this.type = type;
        this.typeSr = ProjectTypeEnum.getInstByType(type) != null?ProjectTypeEnum.getInstByType(type).getName():"";
    }

    public void setStatus(Integer status) {
        this.status = status;
        this.statusStr = ProjectStatusEnum.getInstByType(status) != null? ProjectStatusEnum.getInstByType(status).getName():"";
    }
}
