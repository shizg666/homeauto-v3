package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
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

    /**
     * 查询户型房间集合
     * @param templateId
     * @return
     */
    List<String> getListNameByTemplateId(String templateId);

    /**
     * 获取户型楼层下房间信息
     * @param floorId     楼层ID
     * @param templateId  户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO>
     * @author wenyilu
     * @date  2021/1/5 17:25
     */
    List<TemplateRoomDO> getFamilyRoomBOByTemplateAndFloor(String floorId, String templateId);

    /**
     * 根据户型模板统计各户型房间数据
     * @param templateIds
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.project.CountBO>
     * @author wenyilu
     * @date  2021/1/6 9:56
     */
    List<CountBO> getCountByTemplateIds(List<String> templateIds);


    /**
     * 获取房间下拉列表
     * @param tempalteId
     * @return
     */
    List<SelectedVO> getRoomSelects(String tempalteId);

    /**
     * 根据家庭id获取户型房间列表
     * @param familyId
     * @return
     */
    List<TemplateRoomDO> getListRoomDOByFamilyId(String familyId);


    /**
     * 获取房间code
     * @param roomId
     * @return
     */
    String getRoomCodeById(String roomId);

    List<TemplateRoomDO> getRoomsByTemplateId(String templateId);
}
