package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
public interface MsgTargetMapper extends BaseMapper<MsgTargetDO> {


//    List<String> getMsgByPaths(@Param("paths") List<String> paths, @Param("msgType") Integer msgType);
}
