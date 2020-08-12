package com.landleaf.homeauto.contact.screen.common.context;

import com.landleaf.homeauto.contact.screen.dto.ContactScreenHeader;

/**
 * 存取当前请求上下文信息，暂存为heaer信息
 *
 * @author wenyilu
 */
public class ContactScreenContext {

    /**
     * 用threadLocal存储当前登陆的token
     */
    private static final ThreadLocal<ContactScreenHeader> SCREEN_HOLDER = new ThreadLocal<ContactScreenHeader>();

    /**
     * 获取当前上下文信息
     */
    public static ContactScreenHeader getContext() {
        return SCREEN_HOLDER.get();
    }

    /**
     * 设置当前上下文信息
     */
    public static void setContext(ContactScreenHeader context) {
        SCREEN_HOLDER.set(context);
    }

    /**
     * 清除上下文
     */
    public static void remove() {
        SCREEN_HOLDER.remove();
    }
}
