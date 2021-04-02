package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateFloorDTO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateFloorDetailVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 户型楼层表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface IHouseTemplateFloorService extends IService<TemplateFloorDO> {

    void add(TemplateFloorDTO request);

    void update(TemplateFloorDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 根据户型id获取楼层房间的信息
     * @param templateId
     * @return
     */
    List<TemplateFloorDetailVO> getListFloorDetail(String templateId);

    /**
     * 获取户型楼层名称集合
     * @param templateId
     * @return
     */
    List<String> getListNameByTemplateId(String templateId);

    /**
     * 根据户型获取楼层信息
     * @param templateId 户型ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO>
     * @author wenyilu
     * @date  2021/1/5 17:17
     */
    List<TemplateFloorDO> getFloorByTemplateId(String templateId);

    /**
     * 根据户型获取楼层房间设备信息
     * @param templateId   户型ID
     * @param deviceShowApp  设备是否在app展示（1：是，0：否）
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.FloorRoomVO>
     * @author wenyilu
     * @date  2021/1/6 10:11
     */
    List<FloorRoomVO> getFloorAndRoomDevices(String templateId,Integer deviceShowApp);

    /**
     * 根据家庭id获取户型楼层信息
     * @param familyId
     * @return
     */
    List<TemplateFloorDO> getListFloorDOByFamilyId(String familyId);
}
