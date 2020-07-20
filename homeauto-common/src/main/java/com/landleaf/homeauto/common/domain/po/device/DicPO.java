package com.landleaf.homeauto.common.domain.po.device;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.thandler.BooleanTypeHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yujiumin
 * @since 2020-07-10
 */
@Data
@Accessors(chain = true)
@TableName("tb_dic")
public class DicPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典名称
     */
    @TableField("dic_name")
    private String name;

    /**
     * 字典值
     */
    @TableField("dic_value")
    private String value;

    /**
     * 字典码
     */
    @TableField("dic_code")
    private String code;

    /**
     * 父级字典码
     */
    @TableField("dic_parent_code")
    private String parentCode;

    /**
     * 字典组
     */
    @TableField("dic_group")
    private String group;

    /**
     * 父级字典代码
     */
    @TableField("dic_parent_group")
    private String parentGroup;

    /**
     * 字典描述
     */
    @TableField("dic_desc")
    private String desc;

    /**
     * 系统代码
     */
    @TableField("sys_code")
    private Integer sysCode;

    /**
     * 字典排序值
     */
    @TableField("dic_order")
    private Integer order;

    /**
     * 是否启用
     */
    @TableField(value = "is_enabled", typeHandler = BooleanTypeHandler.class)
    private Boolean enabled;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CTT")
    private LocalDateTime updateTime;

}
