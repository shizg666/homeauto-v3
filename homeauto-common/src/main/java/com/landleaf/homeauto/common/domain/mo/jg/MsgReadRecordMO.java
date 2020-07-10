package com.landleaf.homeauto.common.domain.mo.jg;

import com.landleaf.homeauto.common.constance.MongoClsConst;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lokiy
 * @date 2019/10/18 15:17
 * @description: mongo对象
 */
@Document(collection = MongoClsConst.T_MSG_READ_RECORD)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("mongo消息记录对象")
public class MsgReadRecordMO implements Serializable {

    @Id
    private String id;

    @Indexed
    private Integer msgType;

    @Indexed
    private String userId;

    @Indexed
    private String msgId;

    @CreatedDate
    private Date createTime;


    public MsgReadRecordMO(Integer msgType,
                           String userId,
                           String msgId) {
        this.msgType = msgType;
        this.userId = userId;
        this.msgId = msgId;
    }
}
