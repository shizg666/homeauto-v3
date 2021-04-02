package com.landleaf.homeauto.center.device.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 报警信息
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("home_auto_alarm_message")
@ApiModel(value = "HomeAutoAlarmMessage对象", description = "报警信息")
public class HomeAutoAlarmMessageDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "家庭ID")
    private String familyId;

    @ApiModelProperty(value = "和大屏的统一id")
    private Long alarmId;

    @ApiModelProperty(value = "报警类型 1-实时报警，2日志上报")
    private Integer alarmType;

    @ApiModelProperty(value = "报警设备名称：红外，烟感")
    private String alarmDevice;

    @ApiModelProperty(value = "防区路数，哪一路防区")
    private Integer alarmZone;

    @ApiModelProperty(value = "报警描述， 默认报警")
    private String alarmContext;

    @ApiModelProperty(value = "报警时间")
    private LocalDateTime alarmTime;

    @ApiModelProperty(value = "消警表示  0-未消警 1-消警 (只对报警类型为1的实时报警有效，其他默认1-消警)")
    private Integer alarmCancelFlag;



}
