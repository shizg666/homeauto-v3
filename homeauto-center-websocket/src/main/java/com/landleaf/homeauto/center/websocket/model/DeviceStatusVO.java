package com.landleaf.homeauto.center.websocket.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 设备状态数据值对象
 *
 * @author Yujiumin
 * @version 2020/9/4
 */
@Data
@NoArgsConstructor
@ApiModel("设备状态数据值对象")
public class DeviceStatusVO {

    private String deviceSn;

    private String category;

    private Map<String, String> attributes;

}
