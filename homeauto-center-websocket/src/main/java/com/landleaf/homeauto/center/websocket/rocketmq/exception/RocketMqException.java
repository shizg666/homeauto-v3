package com.landleaf.homeauto.center.websocket.rocketmq.exception;

/**
 * @author Yujiumin
 * @version 2020/9/8
 */
public class RocketMqException extends RuntimeException {

    public RocketMqException(String format, Object... args) {
        super(String.format(format, args));
    }
}
