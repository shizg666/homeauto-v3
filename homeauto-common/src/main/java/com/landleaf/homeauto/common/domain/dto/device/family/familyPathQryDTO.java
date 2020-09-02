package com.landleaf.homeauto.common.domain.dto.device.family;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
@ApiModel(value="familyPathQryDTO", description="familyPathQryDTO")
public class familyPathQryDTO {

    @ApiModelProperty("path")
    private List<String> path;

}
