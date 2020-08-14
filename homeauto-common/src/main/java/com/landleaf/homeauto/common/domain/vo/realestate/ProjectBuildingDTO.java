package com.landleaf.homeauto.common.domain.vo.realestate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value="ProjectBuildingDTO", description="ProjectBuildingDTO")
public class ProjectBuildingDTO {


    @ApiModelProperty(value = "主键id(修改必传)")
    private String id;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @NotEmpty
    @Length(min=1, max=3,message = "名称不能超过3个字符")
    @ApiModelProperty(value = "楼栋号")
    private String code;

}
