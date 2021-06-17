package com.landleaf.homeauto.common.domain.dto.screen.http.response;

import lombok.Data;

/**
 * 户式化定时场景请求返回
 *
 * @author wenyilu
 */
@Data
public class ScreenHttpTimingSceneResponseDTO {

    /**
     * 配置Id
     */
    private Long timingId;

    /**
     * 场景id
     */
    private Long sceneId;
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
     * 是否跳过法定节假日(1|0 是|否)
     */
    private Integer skipHoliday;

    /**
     * 周配置(1,2,3,4)
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
