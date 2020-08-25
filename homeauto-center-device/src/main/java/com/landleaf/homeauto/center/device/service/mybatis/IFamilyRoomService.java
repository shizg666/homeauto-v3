package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.vo.FamilyRoomVO;
import com.landleaf.homeauto.center.device.model.vo.FamilySimpleDeviceVO;

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
     * 根据家庭ID获取家庭房间列表
     *
     * @param familyId 家庭ID
     * @return 房间列表
     */
    List<FamilyRoomVO> getRoomListByFamilyId(String familyId);

    /**
     * 通过房间ID获取设备列表
     *
     * @param roomId 房间ID
     * @return 设备列表
     */
    List<FamilySimpleDeviceVO> getDeviceListByRoomId(String roomId);

}
