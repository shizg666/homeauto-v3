package com.landleaf.homeauto.common.domain.po.device;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.thandler.BooleanTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Yujiumin
 * @since 2020-07-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dic")
public class DicPO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    @TableField("name")
    private String name;

    /**
     * 字典码
     */
    @TableField("code")
    private String code;

    /**
     * 系统代码
     */
    @TableField(value = "is_system", typeHandler = BooleanTypeHandler.class)
    private boolean systemCode;

    /**
     * 是否启用
     */
    @TableField(value = "enabled", typeHandler = BooleanTypeHandler.class)
    private boolean enabled;

}
