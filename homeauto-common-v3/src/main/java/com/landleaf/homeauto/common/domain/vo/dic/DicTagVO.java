package com.landleaf.homeauto.common.domain.vo.dic;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/7/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DicTagVO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("标签名")
    private String name;

    @ApiModelProperty("标签值")
    private String value;

    @ApiModelProperty("排序值")
    private Integer sort;

    @ApiModelProperty("启用状态")
    private Integer enabled;

    @ApiModelProperty("子级标签")
    private List<DicTagVO> childList;
}
