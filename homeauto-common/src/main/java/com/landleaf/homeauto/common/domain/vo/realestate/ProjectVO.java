package com.landleaf.homeauto.common.domain.vo.realestate;

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
@ApiModel(value="ProjectVO", description="ProjectVO")
public class ProjectVO {


    @ApiModelProperty(value = "主键id")
    private String id;

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

    @ApiModelProperty(value = "状态 0未锁定1已锁定")
    private Integer status;

    public void setType(Integer type) {
        this.type = type;
        this.typeSr = ProjectTypeEnum.getInstByType(type) != null?ProjectTypeEnum.getInstByType(type).getName():"";
    }
}
