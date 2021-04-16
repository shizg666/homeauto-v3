package com.landleaf.homeauto.common.domain.po.device;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import com.landleaf.homeauto.common.mybatis.typehandler.BooleanTypeHandler;
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
public class DicTagPO extends BaseEntity2 implements Serializable {

    @TableField("name")
    private String name;

    @TableField("value")
    private String value;

    @TableField("sort")
    private Integer sort;

    @TableField(value = "enabled", typeHandler = BooleanTypeHandler.class)
    private Boolean enabled;

    @TableField("parent")
    private String parent;

    @TableField("dic_code")
    private String dicCode;

    @TableField("dic_id")
    private Long dicId;

}
