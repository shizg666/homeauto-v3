package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * 公告消息表 Mapper 接口
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
public interface MsgNoticeMapper extends BaseMapper<MsgNoticeDO> {

    List<MsgNoticeDO> queryMsgNoticeByProjectIdForScreen(String projectId);
}
