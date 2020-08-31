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
@ApiModel(value="FamilyAddDTO", description="家庭详情对象")
public class FamilyDetailVO {

    @ApiModelProperty(value = "基本信息")
    private FamilyBaseInfoVO baseInfo;




}
