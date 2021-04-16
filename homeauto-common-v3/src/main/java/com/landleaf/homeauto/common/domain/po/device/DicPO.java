package com.landleaf.homeauto.common.domain.po.device;

import com.baomidou.mybatisplus.annotation.*;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import com.landleaf.homeauto.common.mybatis.typehandler.BooleanTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class DicPO extends BaseEntity2 implements Serializable {

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
    private Boolean systemCode;

    /**
     * 是否启用
     */
    @TableField(value = "enabled", typeHandler = BooleanTypeHandler.class)
    private Boolean enabled;

}
