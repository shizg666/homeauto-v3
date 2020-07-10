package com.landleaf.homeauto.center.device.common.msg.cache;

import com.landleaf.homeauto.common.domain.mo.jg.MsgReadRecordMO;
import com.landleaf.homeauto.common.enums.msg.MsgTypeEnum;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消息读取记录
 * @author wenyilu
 */
@Component
public class MsgReadRecordMongoDao extends MsgMongoDao {

    /**
     * 读取记录插入
     *
     * @param msgReadRecordMO
     * @return
     */
    public MsgReadRecordMO insert(MsgReadRecordMO msgReadRecordMO) {
        if (shouldInsertCheck(msgReadRecordMO)) {
            return this.mongoTemplate.insert(msgReadRecordMO);
        }
        return msgReadRecordMO;
    }


    /**
     * 查看消息和用户查看
     *
     * @param msgId
     * @param userId
     * @return
     */
    public long countMsgAndUser(String msgId, String userId) {
        return this.mongoTemplate.count(
                new Query().addCriteria(new Criteria().andOperator(
                        Criteria.where("msgId").is(msgId),
                        Criteria.where("userId").is(userId))),
                MsgReadRecordMO.class);
    }


    /**
     * 统计消息总读取数
     *
     * @param msgId
     * @return
     */
    public long countMsg(String msgId) {
        return this.mongoTemplate.count(
                new Query().addCriteria(Criteria.where("msgId").is(msgId)),
                MsgReadRecordMO.class);
    }


    /**
     * 获取用户消息
     *
     * @param userId
     * @param msgType
     * @return
     */
    public List<MsgReadRecordMO> getUserAndType(String userId, Integer msgType) {
        return this.mongoTemplate.find(
                new Query().addCriteria(new Criteria().andOperator(
                        Criteria.where("msgType").is(msgType),
                        Criteria.where("userId").is(userId))),
                MsgReadRecordMO.class);
    }


    /**
     * 判断是否需要入库
     * 1.为公告消息的时候 如果消息存在 则不需要继续入库
     *
     * @param msg
     * @return
     */
    private boolean shouldInsertCheck(MsgReadRecordMO msg) {
        boolean flag = true;
        if (MsgTypeEnum.NOTICE.getType().equals(msg.getMsgType())) {
            long count = countMsgAndUser(msg.getMsgId(), msg.getUserId());
            if (count > 0) {
                flag = false;
            }
        }
        return flag;
    }
}
