package com.landleaf.homeauto.common.domain.po.device;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntityNew;
import com.landleaf.homeauto.common.thandler.BooleanTypeHandler;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yujiumin
 * @since 2020-07-10
 */
@Data
@Accessors(chain = true)
@ApiModel("字典表对象")
@TableName("tb_dic")
@EqualsAndHashCode(callSuper = true)
public class DicPO extends BaseEntityNew implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("dic_name")
    @ApiModelProperty(value = "字典名称")
    private String dicName;

    @TableField("dic_value")
    @ApiModelProperty(value = "字典值")
    private String dicValue;

    @TableField("dic_code")
    @ApiModelProperty(value = "字典代码")
    private String dicCode;

    @TableField("dic_parent_code")
    @ApiModelProperty(value = "父级字典代码")
    private String dicParentCode;

    @TableField("dic_desc")
    @ApiModelProperty(value = "字典描述")
    private String dicDesc;

    @TableField("sys_code")
    @ApiModelProperty(value = "系统代码")
    private Integer sysCode;

    @TableField("dic_order")
    @ApiModelProperty(value = "字典排序值")
    private Integer dicOrder;

    @TableField(value = "is_enabled", typeHandler = BooleanTypeHandler.class)
    @ApiModelProperty(value = "是否启用：0为否，1为是")
    private Boolean enabled;

}
