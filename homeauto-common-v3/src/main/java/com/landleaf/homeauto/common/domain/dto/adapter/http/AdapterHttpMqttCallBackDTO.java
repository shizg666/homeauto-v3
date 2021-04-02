package com.landleaf.homeauto.common.domain.dto.adapter.http;

import com.landleaf.homeauto.common.domain.dto.adapter.AdapterMessageHttpDTO;
import lombok.Data;

/**
 * 接收到mqtt回调dto
 *
 * @author wenyilu
 */
@Data
public class AdapterHttpMqttCallBackDTO extends AdapterMessageHttpDTO {


    /**
     * 在线离线状态1：在线，0：离线
     */
    private Integer status;

}
