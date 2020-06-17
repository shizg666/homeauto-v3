package com.landleaf.homeauto.common.util;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lokiy
 * @date 2019/9/12 9:18
 * @description: 智能家居 路径范围 帮助类
 */
public class ShPathScopeUtil {


    /**
     * 根据传入的path 和 登陆人固有的paths 组合可见paths范围
     *
     * @param paramPaths
     * @return
     */
    public static List<String> combinePaths(List<String> paramPaths, List<String> userPaths) {
        if (CollectionUtil.isEmpty(paramPaths)) {
            return userPaths;
        }
        List<String> resultPaths = new ArrayList<>();
        paramPaths.forEach(pp -> userPaths.forEach(up -> {
            String realPath = getRealPath(pp, up);
            if (StringUtils.isNotBlank(realPath)) {
                resultPaths.add(realPath);
            }
        }));
        return resultPaths;
    }


    /**
     * 根据传入的path 和 登陆人固有的paths 组合可见paths范围
     *
     * @param paramPath
     * @return
     */
    public static List<String> combinePath(String paramPath, List<String> userPaths) {
        if (StringUtils.isBlank(paramPath)) {
            return userPaths;
        }
        List<String> resultPaths = new ArrayList<>();
        userPaths.forEach(up -> {
            String realPath = getRealPath(paramPath, up);
            if (StringUtils.isNotBlank(realPath)) {
                resultPaths.add(realPath);
            }
        });
        return resultPaths;
    }


    /**
     * 根据入参的paramPath 和 userPath确定路径
     *
     * @param paramPath
     * @param userPath
     * @return
     */
    private static String getRealPath(String paramPath, String userPath) {
        if (paramPath.contains(userPath)) {
            return paramPath;
        }
        if (userPath.contains(paramPath)) {
            return userPath;
        }
        return null;
    }


    /**
     * 判断用户是否有操作范围权限
     * 用户范围要 全部 包含 并大于等于 临时范围
     *
     * @param userPaths 用户范围
     * @param tempPaths 临时范围
     * @return 是否成立
     */
    public static boolean userPathsGtTempPaths(List<String> userPaths, List<String> tempPaths) {
        boolean flag = true;
        for (String temp : tempPaths) {
            //循环临时路径  校验用户路径是否有大于其范围的
            boolean tempFlag = false;
            for (String userPath : userPaths) {
                if (temp.contains(userPath)) {
                    tempFlag = true;
                }
            }
            //如果当前
            flag = flag && tempFlag;
            //如果flag此时直接为false 则表示 用户没有这个临时路径的 权限 则直接跳出
            if (!flag) {
                break;
            }
        }
        return flag;
    }

}
