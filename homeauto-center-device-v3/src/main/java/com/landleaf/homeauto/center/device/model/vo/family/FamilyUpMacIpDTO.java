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
@ApiModel(value="FamilyUpMacIpDTO", description="家庭对象")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class
FamilyUpMacIpDTO {

    @ApiModelProperty(value = "家庭id")
    private Long familyId;

    @ApiModelProperty(value = "家庭大屏Mac")
    private String mac;

    @ApiModelProperty(value = "家庭大屏ip")
    private String ip;



}
