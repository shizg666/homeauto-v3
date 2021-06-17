package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
@ApiModel(value="FamilyCascadeBO", description="FamilyCascadeBO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class
FamilyCascadeBO {

    @ApiModelProperty(value = "家庭id")
    private Long familyId;

    @ApiModelProperty(value = "门牌")
    private String doorplate;

    @ApiModelProperty(value = "单元code")
    private String unitCode;

    @ApiModelProperty(value = "单元name")
    private String unitName;

    @ApiModelProperty(value = "楼栋code")
    private String buildingCode;

    @ApiModelProperty(value = "楼栋name")
    private String buildingName;






}
