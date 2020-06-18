package com.landleaf.homeauto.common.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author wenyilu
 */
@Data
@ApiModel("基础类")
public class BaseEntity extends Model<BaseEntity> {

    @TableId(value = "id", type = IdType.INPUT)
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "主键")
    private String id;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否可用")
    private Integer delFlag;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private String createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="CTT")
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新人")
    private String updateUser;
}
