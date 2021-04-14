package com.landleaf.homeauto.common.domain.dto.device.fault;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-29
 */
@Data
@Accessors(chain = true)
@ApiModel(value="HomeAutoFaultDeviceHavcDTO对象", description="")
public class HomeAutoFaultDeviceHavcDTO  extends HomeAutoFaultDeviceBaseDTO{

    private static final long serialVersionUID = 1L;


}
