package com.landleaf.homeauto.common.domain.po.device;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.thandler.BooleanTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Yujiumin
 * @version 2020/7/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dic_tag")
public class DicTagPO extends BaseEntity implements Serializable {

    @TableField("name")
    private String name;

    @TableField("value")
    private String value;

    @TableField("sort")
    private int sort;

    @TableField(value = "enabled", typeHandler = BooleanTypeHandler.class)
    private boolean enabled;

    @TableField("parent")
    private String parent;

    @TableField("dic_code")
    private String dicCode;

}
