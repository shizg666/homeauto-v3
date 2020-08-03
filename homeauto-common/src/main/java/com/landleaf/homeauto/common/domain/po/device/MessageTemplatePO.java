package com.landleaf.homeauto.common.domain.po.device;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.landleaf.homeauto.common.domain.po.device.base.BasePO;
import com.landleaf.homeauto.common.mybatis.typehandler.BooleanTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 消息模板持久层对象
 *
 * @author Yujiumin
 * @version 2020/7/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageTemplatePO extends BasePO {

    /**
     * 主键
     */
    @TableId
    @TableField("id")
    private String id;

    /**
     * 主题
     */
    @TableField("subject")
    private String subject;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 过期时间
     */
    @TableField("ttl")
    private Integer ttl;

    /**
     * 消息类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 推送方式
     */
    @TableField("push_way")
    private Integer pushWay;

    /**
     * 逻辑删除标识位
     */
    @TableField(value = "is_delete", typeHandler = BooleanTypeHandler.class)
    private Boolean isDelete;

}
