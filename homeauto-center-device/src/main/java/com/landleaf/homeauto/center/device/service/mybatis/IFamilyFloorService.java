package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyFloorConfigVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyFloorDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 家庭楼层表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IFamilyFloorService extends IService<FamilyFloorDO> {

    /**
     * 根据家庭查询楼层
     * @param familyId
     * @return
     */
    List<FamilyFloorDO> getFloorByFamilyId(String familyId);

    void add(FamilyFloorDTO request);

    void update(FamilyFloorDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 根据家庭id获取楼层房间（房间包含设备数量）的信息
     * @param familyId
     * @return
     */
    List<FamilyFloorConfigVO> getListFloorDetail(String familyId);

    /**
     * 查询家庭楼层名称
     * @param familyId
     * @return
     */
    List<String> getListNameByFamilyId(String familyId);
}
