package com.landleaf.homeauto.common.domain.po.contact.gateway;

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
 * @since 2020-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoBase对象", description="")
public class HomeAutoBase extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String name;


}
