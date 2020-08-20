package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import lombok.Data;

/**
 * 户式化定时场景请求返回
 *
 * @author wenyilu
 */
@Data
public class AdapterHttpSaveOrUpdateTimingSceneDTO extends AdapterMessageHttpDTO {

    private String timingId;

    private String sceneName;

    private String executeTime;

    private Integer type;

    private Integer enabled;

    private Integer skipHoliday;

    private String weekday;

    private String startDate;

    private String endDate;

}
