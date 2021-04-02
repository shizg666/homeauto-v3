package com.landleaf.homeauto.center.device.model.vo;

import com.landleaf.homeauto.center.device.model.vo.device.DeviceVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 除去常用设备后的设备列表
 *
 * @author Yujiumin
 * @version 2020/8/17
 */
@Data
@Deprecated
@NoArgsConstructor
@AllArgsConstructor
public class FamilyUncommonDeviceVO {

    private String positionName;

    private List<DeviceVO> devices;

}
