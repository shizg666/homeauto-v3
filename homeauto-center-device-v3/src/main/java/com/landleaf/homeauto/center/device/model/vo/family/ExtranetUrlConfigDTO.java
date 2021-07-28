package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * <p>
 * 本地数采外网URL配置
 * </p>
 *
 * @author lokiy
 * @since 2021-07-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="ExtranetUrlConfigDTO", description="本地数采外网URL配置")
public class ExtranetUrlConfigDTO extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "楼盘id不能为空")
    private Long realestateId;

    @NotEmpty(message = "url不能为空")
    @ApiModelProperty(value = "数据源外网访问url")
    private String url;

}
