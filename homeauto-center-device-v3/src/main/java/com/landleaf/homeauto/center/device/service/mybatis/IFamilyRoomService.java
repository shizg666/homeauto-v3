package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateRoomDO;
import com.landleaf.homeauto.center.device.model.dto.house.TemplateRoomDTO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZDeviceStatusCategoryVO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZFamilyRoomInfoVO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZRoomDeviceStatusCategoryVO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZSceneConfigDataVO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateRoomPageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家庭房间表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface IFamilyRoomService extends IService<FamilyRoomDO> {


    /**
     * 家庭房间列表查询
     * @param familyId
     * @return
     */
    List<FamilyRoomDO> getListRooms(Long familyId);

    /**
     * 保存户型房间信息到家庭房间信息里面
     * @param templateId
     * @param id
     */
    void addTemplateRoomByTid(Long templateId, Long id);

    /**
     * 导入家庭是保存户型房间信息
     * @param familyTempalte
     */
    void addRoomOnImportFamily(Map<Long, Long> familyTempalte);

    /**
     * 根据户型房间id删除
     * @param tRoomId
     */
    void removeByTemplateRoomId(Long tRoomId);

    /**
     * 获取家庭下的房间，设备，设备属性信息 --- 嘉宏小程序添加场景
     * @param familyId
     * @return
     */
    JZSceneConfigDataVO getRoomDeviceAttr(Long familyId,Long templateId);


    /**
     * 查看家庭某一品类下设备状态（只返回第一个房间的设备状态信息）
     * @param familyId
     * @param templateId
     * @param categoryCode
     * @return
     */
    JZDeviceStatusCategoryVO getRoomDeviceAttrByCategoryCode(String familyCode,Long familyId, Long templateId, String categoryCode);
    /**
     * 查看家庭某个房间下某一品类下设备状态
     * @param familyId
     * @param templateId
     * @param categoryCode
     * @return
     */
    JZRoomDeviceStatusCategoryVO getDeviceStatusByRIdAndCategory(String familyCode,Long familyId, Long templateId, Long roomId, String categoryCode);
}
