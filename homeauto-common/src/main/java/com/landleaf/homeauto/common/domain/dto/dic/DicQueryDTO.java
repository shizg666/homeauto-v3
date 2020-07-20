package com.landleaf.homeauto.common.domain.dto.dic;

import com.landleaf.homeauto.common.domain.Pagination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Yujiumin
 * @version 2020/07/13
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("字典查询条件")
public class DicQueryDTO {

    @ApiModelProperty("字典名称")
    private String name;

    @ApiModelProperty("字典组")
    private String groupCode;

    @ApiModelProperty("父级字典组")
    private String parentGroupCode;

    @ApiModelProperty("字典码")
    private String code;

    @ApiModelProperty("父级字典码")
    private String parentCode;

    @ApiModelProperty(value = "标签信息", notes = "根据标签来决定返回内容")
    private String tag;

    @ApiModelProperty("页码信息")
    private Pagination pagination;

}
