package com.landleaf.homeauto.common.domain.vo.category;

import com.landleaf.homeauto.common.domain.qry.BaseQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 属性字典表
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-03
 */
@Data
@Accessors(chain = true)
@ApiModel(value="AttributeDicQryDTO", description="属性字典查询对象")
public class AttributeDicQryDTO extends BaseQry {


    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "性质 只读，控制")
    private Integer nature;




}
