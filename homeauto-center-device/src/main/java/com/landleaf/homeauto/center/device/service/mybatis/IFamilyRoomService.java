package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.vo.RoomVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceSimpleVO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import org.apache.ibatis.annotations.Param;

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
     * @param request
     */
    void updateRoomName(FamilyUpdateVO request);
}
