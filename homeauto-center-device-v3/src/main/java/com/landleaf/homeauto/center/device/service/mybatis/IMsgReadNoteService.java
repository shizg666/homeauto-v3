package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgReadNote;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgReadNoteDTO;

import java.util.List;

/**
 * <p>
 * 消息已读记录表 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-13
 */
public interface IMsgReadNoteService extends IService<MsgReadNote> {

    /**
     * 获取用户已读的某一消息列表
     * @param userId
     * @param type
     * @return
     */
    List<String> getListUserAndType(String userId, Integer type);
    /**
     *  添加消息已读记录
     * @param msgReadNoteDTO  消息
     * @return void
     * @author wenyilu
     * @date 2021/1/12 13:29
     */
    void addReadNote(MsgReadNoteDTO msgReadNoteDTO);

    /**
     * 删除用户已读消息记录
     * @param msgIds
     */
    void removeByMsgIds(List<String> msgIds);

    /**
     * 删除用户已读消息记录
     * @param msgId
     */
    void removeByMsgId(String msgId);
}
