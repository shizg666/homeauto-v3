package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgReadNote;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 消息已读记录表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-10-13
 */
public interface MsgReadNoteMapper extends BaseMapper<MsgReadNote> {

    @Select("select n.msg_id from msg_read_note n where n.user_id = #{userId} and n.msg_type = #{type}")
    List<String> getListUserAndType(@Param("userId") String userId,@Param("type") Integer type);
}
