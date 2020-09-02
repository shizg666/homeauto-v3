package com.landleaf.homeauto.center.device.model.domain.msg;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.common.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="MsgTargetDO", description="")
@TableName("msg_target")
public class MsgTargetDO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息类型 公告/广告/场景/联动")
    private Integer msgType;

    @ApiModelProperty(value = "消息id")
    private String msgId;


    @ApiModelProperty(value = "项目名")
    private String projectName;


    @ApiModelProperty(value = "楼盘id")
    private String realestateId;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "楼盘名")
    private String realestateName;


}
