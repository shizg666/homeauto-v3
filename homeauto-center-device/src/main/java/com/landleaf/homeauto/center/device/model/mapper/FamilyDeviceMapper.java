package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyDeviceWithPositionBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyDeviceDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 家庭设备表 Mapper 接口
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Mapper
@Repository
public interface FamilyDeviceMapper extends BaseMapper<FamilyDeviceDO> {

    /**
     * 通过家庭ID获取常用设备
     *
     * @param familyId 家庭ID
     * @return 家庭常用设备表ID
     */
    List<FamilyDeviceWithPositionBO> getCommonDevicesByFamilyId(@Param("familyId") String familyId);

    /**
     * 通过家庭ID获取常用设备
     *
     * @param familyId 家庭ID
     * @return 家庭常用设备表ID
     */
    List<FamilyDeviceWithPositionBO> getUnCommonDevicesByFamilyId(@Param("familyId") String familyId);

    /**
     * 通过家庭ID获取常用设备
     *
     * @param familyId 家庭ID
     * @return 家庭常用设备表ID
     */
    List<FamilyDeviceWithPositionBO> getAllDevicesByFamilyId(@Param("familyId") String familyId);


    /**
     * 通过设备序列号获取设备信息
     *
     * @param sceneId 设备序列号集合
     * @return 设备信息集合
     */
    List<FamilyDeviceWithPositionBO> getDeviceInfoByDeviceSn(@RequestParam String sceneId);

    /**
     * 不推荐混杂注解和Xml
     *
     * @param id 产品id
     * @return 产品下的设备数量
     */
    @Deprecated
    @Select("select count(1) from family_device where product_id = #{id}")
    int existByProductId(String id);

    /**
     * 根据房间ID获取设备列表
     *
     * @param roomId 房间ID
     * @return 设备列表
     */
    List<FamilyDeviceBO> getDeviceListByRoomId(@Param("roomId") String roomId);
}
