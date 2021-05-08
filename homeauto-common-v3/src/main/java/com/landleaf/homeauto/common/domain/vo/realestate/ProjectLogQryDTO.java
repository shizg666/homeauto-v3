package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
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
@ApiModel(value="ProjectLogQryDTO", description="ProjectLogQryDTO")
public class ProjectLogQryDTO extends BaseQry {


    private static final long serialVersionUID = -1083009607018779779L;

    @ApiModelProperty(value = "项目id")
    private Long projectId;





}
