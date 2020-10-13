package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;

/**
 * <p>
 * 家庭表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyQryDTO", description="FamilyQryDTO")
public class FamilyQryDTO {

    @ApiModelProperty(value = "单元id")
    private String uint;

    @ApiModelProperty(value = "项目id（必传）")
    private String name;

    @ApiModelProperty(value = "项目id（必传）")
    private String roomNo;


}
