package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;

import java.util.List;

/**
 * @author pilo
 */
public interface ITemplateFloorService {

    /**
     * 根据户型获取楼层房间设备信息
     * @param templateId   户型ID
     * @param deviceShowApp  设备是否在app展示（1：是，0：否）
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.FloorRoomVO>
     * @author wenyilu
     * @date  2021/1/6 10:11
     */
    List<FloorRoomVO> getFloorAndRoomDevices(String templateId, Integer deviceShowApp);
    /**
     * 根据户型获取楼层信息
     * @param templateId 户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO>
     * @author wenyilu
     * @date  2021/1/5 17:17
     */
    List<TemplateFloorDO> getFloorByTemplateId(Long templateId);
}
