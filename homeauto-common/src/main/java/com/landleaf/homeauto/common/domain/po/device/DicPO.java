package com.landleaf.homeauto.common.domain.po.device;

import java.time.LocalDateTime;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yujiumin
 * @since 2020-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="TbDic对象", description="")
public class DicPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典名称")
    private String dicName;

    @ApiModelProperty(value = "字典值")
    private String dicValue;

    @ApiModelProperty(value = "字典代码")
    private String dicCode;

    @ApiModelProperty(value = "父级字典代码")
    private String dicParentCode;

    @ApiModelProperty(value = "字典描述")
    private String dicDesc;

    @ApiModelProperty(value = "系统代码")
    private Integer sysCode;

    @ApiModelProperty(value = "字典排序值")
    private Integer order;

    @ApiModelProperty(value = "是否启用：0为否，1为是")
    private String isEnabled;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreated;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime gmtUpdated;


}
