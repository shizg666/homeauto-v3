package com.landleaf.homeauto.common.domain.dto.device.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 字典信息
 *
 * @author Yujiumin
 * @version 2020/07/10
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="familyUerRemoveDTO", description="familyUerRemoveDTO")
public class familyUerRemoveDTO {

    @ApiModelProperty("用户id")
    private String userId;

}
