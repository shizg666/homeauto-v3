package com.landleaf.homeauto.common.domain.dto.sync;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Lokiy
 * @date 2019/8/29 10:06
 * @description: 邮箱信息
 */
@Data
@ToString
@ApiModel(value="SyncSceneHvacAtionBO", description="SyncSceneHvacAtionBO")
public class SyncSceneHvacAtionBO {

    @ApiModelProperty("开关code")
    private String switchCode;

    @ApiModelProperty("开关val")
    private String switchVal;

    @ApiModelProperty("场景id")
    private String sceneId;

    @ApiModelProperty("模式code")
    private String modeCode;

    @ApiModelProperty("模式val")
    private String modeVal;

    @ApiModelProperty("风量code")
    private String windCode;

    @ApiModelProperty("风量val")
    private String windVal;

    @ApiModelProperty("暖通动作id")
    private String actionId;

    @ApiModelProperty("设备号")
    private String sn;

}
