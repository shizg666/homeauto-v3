package com.landleaf.homeauto.center.device.model.domain.status;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.landleaf.homeauto.common.domain.BaseEntity;
import com.landleaf.homeauto.common.domain.BaseEntity2;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 设备当前故障值
 * </p>
 *
 * @author wenyilu
 * @since 2021-06-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="HomeAutoFaultDeviceCurrent对象", description="设备当前故障值")
public class HomeAutoFaultDeviceCurrent extends BaseEntity2 {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭")
    private Long familyId;
    @ApiModelProperty(value = "设备")
    private Long deviceId;

    @ApiModelProperty(value = "设备号")
    private String deviceSn;

    @ApiModelProperty(value = "产品编码")
    private String productCode;

    @ApiModelProperty(value = "所属楼盘")
    private Long realestateId;

    @ApiModelProperty(value = "所属项目")
    private Long projectId;

    @ApiModelProperty(value = "上传的暖通故障值如16")
    private Integer havcErrorValue;
    @ApiModelProperty(value = "在线离线值")
    private Integer onlineValue;


    @ApiModelProperty(value = "数值异常值（存储为属性:值）")
    private String numErrorValue;


}
