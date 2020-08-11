package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 品类表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="CategoryQryDTO", description="品类查询对象")
@EqualsAndHashCode(callSuper = false)
public class CategoryQryDTO extends BaseQry {



    @ApiModelProperty(value = "品类类型")
    private Integer type;


    @ApiModelProperty(value = "协议")
    private Integer protocol;



}
