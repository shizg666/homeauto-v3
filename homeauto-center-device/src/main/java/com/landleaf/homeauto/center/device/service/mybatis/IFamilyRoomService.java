package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyRoomDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 家庭房间表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyRoomService extends IService<FamilyRoomDO> {

    /**
     * 统计家庭房间数量
     *
     * @param familyIds
     * @return
     */
    List<CountBO> getCountByFamilyIds(List<String> familyIds);

    /**
     * 通过家庭ID获取房间列表
     *
     * @param familyId 家庭ID
     * @return 房间列表
     */
    List<FamilyRoomDO> getRoom(String familyId);

    /**
     * 获取位置信息
     *
     * @param roomId 房间ID
     * @return 位置信息
     */
    String getPosition(String roomId);

    /**
     * 获取家庭房间列表
     *
     * @param familyId
     * @return
     */
    List<FamilyRoomBO> getRoomListByFamilyId(String familyId);

    /**
     * app 修改房间名称
     *
     * @param request
     */
    void updateRoomName(FamilyUpdateVO request);

    /**
     * 获取暖通场景的房间列表
     *
     * @param sceneId
     * @return
     */
    List<FamilyRoomDO> getHvacSceneRoomList(String sceneId);

    void add(FamilyRoomDTO request);

    void update(FamilyRoomDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 房间上移
     *
     * @param roomId
     */
    void moveUp(String roomId);

    /**
     * 房间下移
     *
     * @param roomId
     */
    void moveDown(String roomId);

    /**
     * 房间置顶
     *
     * @param roomId
     */
    void moveTop(String roomId);

    /**
     * 房间置底
     *
     * @param roomId
     */
    void moveEnd(String roomId);

    /**
     * 是否存在面板
     *
     * @param roomId
     * @return
     */
    boolean existPanel(String roomId);
}
