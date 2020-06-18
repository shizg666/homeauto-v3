package com.landleaf.homeauto.common.domain.vo;

import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.po.contact.gateway.HomeAutoBase;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="HomeAutoBaseVO对象", description="")
public class HomeAutoBaseVO extends HomeAutoBase {

    private static final long serialVersionUID = 1L;




}
