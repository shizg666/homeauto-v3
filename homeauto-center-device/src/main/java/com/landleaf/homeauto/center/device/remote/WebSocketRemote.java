package com.landleaf.homeauto.center.device.remote;

import com.landleaf.homeauto.common.constant.ServerNameConst;
import com.landleaf.homeauto.common.domain.dto.device.DeviceStatusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * websocket服务
 *
 * @author Yujiumin
 * @version 2020/9/4
 */
@FeignClient(ServerNameConst.HOMEAUTO_CENTER_WEBSOCKET)
public interface WebSocketRemote {

    /**
     * 推送设备的状态
     *
     * @param deviceStatusDTO 设备状态数据传输对象
     */
    @PostMapping("/websocket/device/status/push")
    void push(@RequestBody DeviceStatusDTO deviceStatusDTO);
}
