package com.landleaf.homeauto.common.domain.vo.realestate;

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
@ApiModel(value="RealestateDeveloperVO", description="楼盘基本信息对象")
public class RealestateDeveloperVO {

    @ApiModelProperty(value = "path名称")
    private String pathName;
    @ApiModelProperty(value = "开发商Name")
    private String developerName;

}
