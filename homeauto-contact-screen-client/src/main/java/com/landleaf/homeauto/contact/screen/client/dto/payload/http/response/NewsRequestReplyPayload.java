package com.landleaf.homeauto.contact.screen.client.dto.payload.http.response;

import com.landleaf.homeauto.contact.screen.client.dto.payload.ContactScreenNews;
import lombok.Data;

import java.util.List;

/**
 * 消息公告请求响应payload
 *
 * @author wenyilu
 */
@Data
public class NewsRequestReplyPayload {


    List<ContactScreenNews> data;
}
