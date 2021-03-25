package com.landleaf.homeauto.center.device.model.smart.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * app获取设备属性状态
 *
 * @author pilo*/
@Data
@ApiModel("app获取设备属性状态")
public class AppDeviceStatusVO {
    @ApiModelProperty("页面需要展示的属性")
    private List<String> showAttrs;
    @ApiModelProperty("属性的状态值")
    private Map<String, Object> attrStatus;

    public AppDeviceStatusVO() {
    }

    public AppDeviceStatusVO(List<String> showAttrs, Map<String, Object> attrStatus) {
        this.showAttrs = showAttrs;
        this.attrStatus = attrStatus;
    }
}
