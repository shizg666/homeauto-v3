package com.landleaf.homeauto.common.util;


import org.apache.commons.lang.StringUtils;

/**
 * @ClassName DesensitizationUtil
 * @Description: 数据脱敏工具类
 * @Author shizg
 * @Date 2020/6/5
 * @Version V1.0
 **/
public class DesensitizationUtil {

    /**
     * 手机号码前三后四脱敏
     *
     * @param mobile
     * @return
     */
    public static String mobileEncrypt(String mobile) {
        if (StringUtil.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }


    /**
     * 身份证前三后四脱敏
     *
     * @param id
     * @return
     */
    public static String idEncrypt(String id) {
        if (StringUtil.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }

    /**
     * 名字加*号
     *
     * @param realname
     * @return
     */
    public static String nameEncrypt(String realname) {
        String name = "";
        if (StringUtil.isEmpty(realname)) {
            return name;
        }
        char[] r = realname.toCharArray();
        if (r.length == 1) {
            name = realname;
        }
        if (r.length == 2) {
            name = realname.replaceFirst(realname.substring(1), "*");
        }
        if (r.length > 2) {
            name = realname.replaceFirst(realname.substring(1, r.length - 1), "*");
        }
        return name;
    }

    /**
     * 匿名身份证号码
     *
     * @param phone
     *            手机号码
     * @return 混淆后的手机号码
     */
    public static String anonymousPhoneNum(String phone) {
        if (StringUtils.isBlank(phone)) {
            return StringUtils.EMPTY;
        }
        if (phone.length() != 11) {
            return phone;
        }

        StringBuilder result = new StringBuilder();
        return result.append(phone.substring(0, 3)).append("****")
                .append(phone.substring(phone.length() - 4)).toString();
    }
}
