package com.landleaf.homeauto.contact.screen.client.dto;

import com.landleaf.homeauto.common.domain.dto.screen.mqtt.request.ScreenMqttBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ContactScreenDomain
 * @Description: 大屏通讯模块-通用消息体
 * @Author wyl
 * @Date 2020/8/10
 * @Version V1.0
 **/
@Data
@Builder
@AllArgsConstructor
public class ContactScreenDomain implements Delayed {

    /**
     * 包含的内容
     */
    private ScreenMqttBaseDTO data;
    /**
     * 下发消息ID
     */
    private String outerMessageId;
    /**
     * 协议类型(与大屏交互)
     * {@link }
     */
    private String operateName;
    /**
     * 消息编号(内部编号，使用gatewayId+_+retCode+_+msgId实现)
     */
    private String messageKey;
    /**
     * 重试次数
     */
    private int retryTimes;
    /**
     * 最大重试次数
     */
    private int maxRetryTimes = 3;
    /**
     * 发送时间
     */
    private long sendTime;
    /**
     * 是否需要重试
     */
    private boolean retryFlag;
    /**
     * 错误码
     */
    private int errorCode;
    /**
     * 错误信息
     */
    private String errorMsg;

    @Override
    public int compareTo(Delayed o) {
        return this.getSendTime() > ((ContactScreenDomain) o).getSendTime() ? 1 : -1;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(sendTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public ContactScreenDomain() {
    }
}
