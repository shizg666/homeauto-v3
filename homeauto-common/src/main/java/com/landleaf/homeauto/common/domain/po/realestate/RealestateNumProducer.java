package com.landleaf.homeauto.common.domain.po.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="RealestateNumProducer对象", description="")
public class RealestateNumProducer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "生产器名称")
    private String name;

    private Integer num;


}
