package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.FamilyRoomDO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes
        .FamilyRoomDeviceAttrBO;
import com.landleaf.homeauto.center.device.model.dto.jhappletes.JZFamilyRoom;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateRoomPageVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 户型房间表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface FamilyRoomMapper extends BaseMapper<FamilyRoomDO> {


    List<FamilyRoomDeviceAttrBO> getRoomDeviceAttr(@Param("familyId") Long familyId, @Param("templateId")Long templateId);

    /**
     * 获取特定家庭房间品类下的设备信息
     * @param familyId
     * @param templateId
     * @param roomId
     * @param categoryCode
     * @return
     */
    List<FamilyRoomDeviceAttrBO> getRoomCategoryDeviceAttr(@Param("familyId") Long familyId, @Param("templateId") Long templateId, @Param("roomId") Long roomId, @Param("categoryCode") String categoryCode);

    /**
     * 获取家庭设备类表
     * @param familyId
     * @return
     */
    List<JZFamilyRoom> getListRoomByFamilyId(@Param("familyId") Long familyId);
}
