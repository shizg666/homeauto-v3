package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgReadNote;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgReadNoteDTO;
import com.landleaf.homeauto.center.device.model.mapper.MsgReadNoteMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgReadNoteService;
import com.landleaf.homeauto.common.util.BeanUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息已读记录表 服务实现类
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-13
 */
@Service
public class MsgReadNoteServiceImpl extends ServiceImpl<MsgReadNoteMapper, MsgReadNote> implements IMsgReadNoteService {

    @Override
    public List<String> getListUserAndType(String userId, Integer type) {
        return this.baseMapper.getListUserAndType(userId,type);
    }
    /**
     *  添加消息已读记录
     * @param msgReadNoteDTO  消息
     * @return void
     * @author wenyilu
     * @date 2021/1/12 13:29
     */
    @Override
    public void addReadNote(MsgReadNoteDTO msgReadNoteDTO) {
        MsgReadNote msgReadNote = BeanUtil.mapperBean(msgReadNoteDTO,MsgReadNote.class);
        msgReadNote.setUserId(TokenContext.getToken().getUserId());
        save(msgReadNote);
    }

    @Override
    public void removeByMsgIds(List<String> msgIds) {
        remove(new LambdaQueryWrapper<MsgReadNote>().in(MsgReadNote::getMsgId,msgIds));
    }

    @Override
    public void removeByMsgId(String msgId) {
        remove(new LambdaQueryWrapper<MsgReadNote>().eq(MsgReadNote::getMsgId,msgId));
    }
}
