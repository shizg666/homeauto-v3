package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.vo.FamilyRoomVO;

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
     * @param familyId
     * @return
     */
    List<FamilyRoomVO> getRoomListByFamilyId(String familyId);

}
