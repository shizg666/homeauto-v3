package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeAppDTO;
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

    /**
     * app获取家庭消息列表
     * @param projectId
     * @return
     */
    List<MsgNoticeAppDTO> getMsglist(@Param("projectId") Long projectId);
}
