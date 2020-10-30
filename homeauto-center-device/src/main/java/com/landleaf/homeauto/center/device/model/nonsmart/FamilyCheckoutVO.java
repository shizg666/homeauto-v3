package com.landleaf.homeauto.center.device.model.nonsmart;

import com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO;
import com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO;
import com.landleaf.homeauto.center.device.model.vo.EnvironmentVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Yujiumin
 * @version 2020/10/29
 */
@Data
@ApiModel("自由方舟APP切换家庭视图对象")
public class FamilyCheckoutVO {

    @ApiModelProperty("环境参数")
    private EnvironmentVO environmentVO;

    @ApiModelProperty("常用场景")
    private List<FamilySceneVO> commonScenes;

    @ApiModelProperty("房间设备")
    private List<FamilyDeviceVO> roomDevices;

}
