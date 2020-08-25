package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 户型房间表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface IHouseTemplateRoomService extends IService<TemplateRoomDO> {

    void add(TemplateRoomDTO request);

    void update(TemplateRoomDTO request);


    void delete(ProjectConfigDeleteDTO request);


    /**
     * 获取房间类型下拉选项
     * @return
     */
    List<SelectedIntegerVO> getRoomTypeSelects();

    /**
     * 房间上移排序
     * @param roomId
     */
    void moveUp(String roomId);

    /**
     * 房间下移排序
     * @param roomId
     */
    void moveDown(String roomId);

    /**
     * 房间置顶
     * @param roomId
     */
    void moveTop(String roomId);

    /**
     * 房间置底
     * @param roomId
     */
    void moveEnd(String roomId);
}
