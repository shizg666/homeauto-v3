package com.landleaf.homeauto.contact.gateway.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Lokiy
 * @date 2019/8/5 16:45
 * @description:
 */
@Data
@ApiModel("基础类")
public class BaseEntity extends Model<BaseEntity> {

    @TableId(type = IdType.AUTO)
    private Long id;

//    @TableLogic
//    @TableField(fill = FieldFill.INSERT)
//    @ApiModelProperty(value = "是否可用")
//    private Integer delFlag;
//
//    @TableField(fill = FieldFill.INSERT)
//    @ApiModelProperty(value = "创建时间")
//    private LocalDateTime createTime;
//
//    @TableField(fill = FieldFill.INSERT)
//    @ApiModelProperty(value = "创建人")
//    private String createUser;
//
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    @ApiModelProperty(value = "更新时间")
//    private LocalDateTime updateTime;
//
//    @TableField(fill = FieldFill.INSERT_UPDATE)
//    @ApiModelProperty(value = "更新人")
//    private String updateUser;
}
