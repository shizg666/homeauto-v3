package com.landleaf.homeauto.center.device.model.vo.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
@ApiModel(value="FamilyTempalteUpdateDTO", description="FamilyTempalteUpdateDTO")
public class
FamilyTempalteUpdateDTO {

    @ApiModelProperty(value = "家庭集合")
    private List<Long> familyIds;

    @ApiModelProperty(value = "户型id")
    private Long templateId;


}
