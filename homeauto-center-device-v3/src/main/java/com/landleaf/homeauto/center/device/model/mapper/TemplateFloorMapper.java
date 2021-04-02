package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateFloorDO;
import com.landleaf.homeauto.center.device.model.vo.FloorRoomVO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateFloorDetailVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 户型楼层表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface TemplateFloorMapper extends BaseMapper<TemplateFloorDO> {

    List<TemplateFloorDetailVO> getListFloorDetail(@Param("templateId") String templateId);

    @Select("select f.name from house_template_floor f where f.house_template_id = #{templateId} ")
    List<String> getListNameByTemplateId(@Param("templateId")String templateId);
    /**
     * 根据户型获取楼层房间设备信息
     * @param templateId   户型ID
     * @param deviceShowApp
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.FloorRoomVO>
     * @author wenyilu
     * @date  2021/1/6 10:11
     */
    List<FloorRoomVO> getFloorAndRoomDevices(@Param("templateId") String templateId,@Param("deviceShowApp") Integer deviceShowApp);
}
