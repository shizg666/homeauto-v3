package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectDTO", description="项目对象")
public class ProjectDTO  {


    private static final long serialVersionUID = 241232301510405354L;

    @ApiModelProperty(value = "主键id 修改必传")
    private String id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "楼盘id")
    private String realestateId;


}
