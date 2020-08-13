package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

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
@ApiModel(value="ProjectStatusDTO", description="ProjectStatusDTO")
public class ProjectStatusDTO  {

    @NotEmpty(message = "项目id主键不能为空")
    @ApiModelProperty(value = "项目id主键")
    private String id;

    @NotEmpty(message = "状态不能为空")
    @ApiModelProperty(value = "状态 0未锁定1已锁定")
    private Integer status;

}
