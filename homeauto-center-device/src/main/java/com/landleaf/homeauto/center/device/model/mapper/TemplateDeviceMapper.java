package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.vo.project.CountBO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 户型设备表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-20
 */
public interface TemplateDeviceMapper extends BaseMapper<TemplateDeviceDO> {

    int existParam(@Param("name") String name, @Param("sn")  String sn, @Param("houseTemplateId")  String houseTemplateId);

    List<CountBO> countDeviceByRoomIds(@Param("roomIds") List<String> roomIds);

    List<TemplateDevicePageVO> getListByRoomId(@Param("roomId")String roomId);
}
