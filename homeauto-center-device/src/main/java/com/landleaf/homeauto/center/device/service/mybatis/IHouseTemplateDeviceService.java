package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.HvacPanelAction;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDeviceDTO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDeviceUpDTO;
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

    void update(TemplateDeviceUpDTO request);

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

    /**
     * 上移
     * @param deviceId
     */
    void moveUp(String deviceId);
    /**
     * 下移
     * @param deviceId
     */
    void moveDown(String deviceId);

    /**
     * 置顶
     * @param deviceId
     */
    void moveTop(String deviceId);

    /**
     * 置底
     * @param deviceId
     */
    void moveEnd(String deviceId);

    /**
     * 查询户型下的房间面板设备号列表
     * @param templateId
     * @return
     */
    List<String> getListPanel(String templateId);

    /**
     * 查询户型下的面板下拉选择列表（添加场景）
     * @param templateId
     * @return
     */
    List<SelectedVO> getListPanelSelects(String templateId);
}
