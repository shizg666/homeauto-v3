package com.landleaf.homeauto.common.domain.po.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 楼栋表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ProjectBuilding对象", description="楼栋表")
public class ProjectBuilding extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "楼栋名称")
    private String name;

    @ApiModelProperty(value = "工程id")
    private String projectId;

    @ApiModelProperty(value = "楼栋号")
    private String code;


}
