package com.landleaf.homeauto.center.device.model.domain.mqtt;

import com.baomidou.mybatisplus.annotation.*;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 家庭组表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-19
 */
@Data
@Accessors(chain = true)
@TableName("mqtt_user")
@ApiModel(value="MqttUser", description="mqtt用户表")
public class MqttUser  {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    @TableField(fill = FieldFill.INSERT)
    private String id;

    @TableField("is_superuser")
    @ApiModelProperty(value = "是否是管理员")
    private Integer isSuperuser;

    @TableField("username")
    @ApiModelProperty(value = "客户id")
    private String userName;

    @TableField("password")
    @ApiModelProperty(value = "密码")
    private String password;

    @TableField("salt")
    private String salt;


}
