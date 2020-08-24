package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDeviceDTO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 户型设备表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface IHouseTemplateDeviceService extends IService<TemplateDeviceDO> {

    void add(TemplateDeviceDTO request);

    void update(TemplateDeviceDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 根据房间id集合获取房间下的设备数量
     * @param roomIds
     * @return
     */
    List<CountBO> countDeviceByRoomIds(List<String> roomIds);

    /**
     * 根据房间id获取设备列表
     * @param roomId
     * @return
     */
    List<TemplateDevicePageVO> getListByRoomId(String roomId);
}
