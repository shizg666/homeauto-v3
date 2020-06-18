package com.landleaf.homeauto.common.domain.message;

import lombok.Data;
import lombok.ToString;

/**
 * @ClassName TestMessage
 * @Description: TODO
 * @Author shizg
 * @Date 2020/6/18
 * @Version V1.0
 **/
@Data
@ToString
public class TestMessage {
    private String msgId;
    private String content;
}
