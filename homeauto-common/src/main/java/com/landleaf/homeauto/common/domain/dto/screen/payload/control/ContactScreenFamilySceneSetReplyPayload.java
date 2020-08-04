package com.landleaf.homeauto.common.domain.dto.screen.payload.control;

import com.landleaf.homeauto.common.domain.dto.screen.payload.ContactScreenCommonDetailReply;
import lombok.Data;

import java.util.List;

/**
 * 执行场景返回payload
 *
 * @author wenyilu
 */
@Data
public class ContactScreenFamilySceneSetReplyPayload {

    /**
     * 场景号
     */
    private String sceneId;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 成功或错误信息
     */
    private String message;

    /**
     * 返回详情信息
     */
    private List<ContactScreenCommonDetailReply> data;


}
