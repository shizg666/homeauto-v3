package com.landleaf.homeauto.common.domain.vo.realestate;

import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

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
@ApiModel(value="RealestateDTO", description="楼盘对象")
public class RealestateDTO {



    private static final long serialVersionUID = -1083009607018779779L;

    @ApiModelProperty(value = "主键id 修改必传")
    private String id;

    @ApiModelProperty(value = "楼盘名称")
    private String name;

    @ApiModelProperty(value = "开发商code")
    private String developerCode;

    @ApiModelProperty(value = "地址")
    private String address;

    @NotBlank(message = "地址path不能为空")
    @ApiModelProperty(value = "地址path")
    private String path;

    @NotBlank(message = "path名称不能为空")
    @ApiModelProperty(value = "path名称")
    private String pathName;


}
