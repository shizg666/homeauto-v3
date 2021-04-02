package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import lombok.Data;

/**
 * 定时场景修改DTO
 *
 * @author wenyilu
 */
@Data
public class AdapterHttpSaveOrUpdateTimingSceneDTO extends AdapterMessageHttpDTO {

    /**
     * 配置Id
     */
    private String timingId;

    /**
     * 场景id
     */
    private String sceneId;
    /**
     * 场景名称
     */
    private String sceneName;

    /**
     * 开始时间 格式HH:mm
     */
    private String executeTime;

    /**
     * 设置类型：1不重复 2自定义周 3自定义日历
     */
    private Integer type;

    /**
     * 启用标志
     */
    private Integer enabled;

    /**
     * 是否跳过法定节假日
     */
    private Integer skipHoliday;

    /**
     * 周配置
     */
    private String weekday;

    /**
     * 开始日期  yyyy.MM.dd
     */
    private String startDate;

    /**
     * 结束日期 yyyy.MM.dd
     */
    private String endDate;

}
